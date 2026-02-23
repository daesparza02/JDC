package ast.instrucciones.declaraciones;
import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;

public class Creacion extends Declaracion{

	String name;
    private int deltaReg = -1;
    boolean global = false;	


	public Creacion(){
		
	}

	public Creacion(Tipo tipo, String name){
		this.tipo = tipo;
		this.name = name;	
		tipo.setModificable(false);
	}

    public Creacion(Tipo tipo, String name, boolean global){
		this.tipo = tipo;
		this.name = name;	
		tipo.setModificable(false);
        this.global=global;
	}


	public String toString(){
		return "(CREACION " + tipo.toString() + " " + name.toString() + ")";
	}

    @Override
    public void binding() {
        tipo.binding();

        if (tipo instanceof TipoPersonalizado) {
            ASTNode def = tipo.getLink();
            if (def instanceof Entity) {
                this.tipo = ((Entity) def).getTipoEntity(); 
            }
        }

        ASTNode node = Programa.searchIdLastFun(name);
        if (node == null) {
            Programa.insertar(name, this);
        } else {
            System.out.println("ERROR: identificador en Creacion " + name + " no se puede utilizar en " + this);
            Programa.setFin();
        }
    }
    
	@Override 
	public void checkType(){
		setTipo(tipo);
	}
		

	public String getName(){
		return name;
	}

	public Tipo getTipo(){
		return tipo;
	}

	@Override
    public void generaCodigo() {
        if (tipo instanceof TipoBasico) {
        }

        else if (tipo instanceof TipoRegistro) {
            calcularDirRelativa();
            Programa.codigo.println("\ti32.const " + tipo.getTam() / 4);
            Programa.codigo.println("\tcall $zeron");
        }

        else if (tipo instanceof TipoArray && ((TipoArray) tipo).getTipoBasico() instanceof TipoRegistro) {
            TipoRegistro base = (TipoRegistro) ((TipoArray) tipo).getTipoBasico();
            int numElems = ((TipoArray) tipo).getNumElems();
            for (int i = 0; i < numElems; i++) {
                calcularDirRelativa();
                Programa.codigo.println("\ti32.const " + base.getTam() * i);
                Programa.codigo.println("\ti32.add");
                Programa.codigo.println("\ti32.const " + base.getTam() / 4);
                Programa.codigo.println("\tcall $zeron");
            }
        }
    }
    @Override
    public void calcularDirRelativa() {
        if (deltaReg != -1) {
            if (getGlobal()) {
                Programa.codigo.println("\ti32.const " + (deltaReg + delta + 4));
            } else {
                Programa.codigo.println("\ti32.const " + (deltaReg + delta));
                Programa.codigo.println("\tlocal.get $localsStart");
                Programa.codigo.println("\ti32.add");
            }
        } else if (getGlobal()) {
            Programa.codigo.println("\ti32.const " + (delta + 4));
        } else {
            Programa.codigo.println("\ti32.const " + delta);
            Programa.codigo.println("\tlocal.get $localsStart");
            Programa.codigo.println("\ti32.add");
        }
    }


    public void setDeltaReg(int d){
        deltaReg=d;
    }

    @Override
    public int getTamanyo() {
        return tipo.getTam(); 
    }

    @Override
    public boolean getGlobal() {
        return this.global;
    }

    @Override
	public String getNombre() {
		return this.name;
	}

}