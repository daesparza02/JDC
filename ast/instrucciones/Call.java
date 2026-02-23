package ast.instrucciones;

import java.util.List;
import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import java.util.ArrayList;
import ast.Programa;
import ast.Parametro;

public class Call extends Instruccion{
	private String nombreFun;
	private List<Expresion> argumentosFuncion;
	private boolean asigned;
	private Expresion objeto;
	public Call(String nombreFun, List<Expresion> argumentosFuncion){
		this.nombreFun = nombreFun;
		this.argumentosFuncion = argumentosFuncion;
	}

	public Call(String nombreFun, Expresion exp){
		this.nombreFun = nombreFun;
		this.argumentosFuncion = new ArrayList<Expresion>();
		this.argumentosFuncion.add(exp);
	}

	public Call(String nombreFun, List<Expresion> argumentosFuncion, Expresion objeto){
		this.nombreFun = nombreFun;
		this.argumentosFuncion = argumentosFuncion;
		this.objeto=objeto;
	}

	public Call(String nombreFun, Expresion exp, Expresion objeto){
		this.nombreFun = nombreFun;
		this.argumentosFuncion = new ArrayList<Expresion>();
		this.argumentosFuncion.add(exp);
		this.objeto=objeto;
		
	}

	@Override
	public String toString(){
		return 	"(CALL (" + nombreFun + ", " + argumentosFuncion.toString() + "))";	
	}

		@Override
	public void binding() {
		if (objeto != null) {
			objeto.binding(); 

			Tipo tipo = objeto.getTipo();
			if (tipo instanceof TipoEntity) {
				TipoEntity tipoEnt = (TipoEntity) tipo;
				Funcion metodo = tipoEnt.getFuncion(nombreFun);
				if (metodo != null) {
					this.link = metodo;
				} else {
					System.err.println("ERROR: Método '" + nombreFun + "' no existe en entidad.");
					Programa.setFin();
				}
			} else {
				System.err.println("ERROR: '" + nombreFun + "' llamado sobre algo que no es entidad.");
				Programa.setFin();
			}
		} else {
			ASTNode node = Programa.searchId(nombreFun);
			if (node != null) {
				this.link = node;
			} else {
				System.err.println("ERROR: función '" + nombreFun + "' no declarada.");
				Programa.setFin();
			}
		}

		for (Expresion arg : argumentosFuncion) {
			arg.binding();
		}
	}

	public String getName(){
		return nombreFun;
	}

	@Override
	public void checkType(){
		if (!(this.getLink() instanceof Funcion)){
			System.out.println("ERROR en Call " + this);
			Programa.setFin();
		}
		else{	
			Funcion fun = (Funcion) this.getLink();
			List<Parametro> listaArgs = fun.getArgs();
			if (listaArgs.size() != argumentosFuncion.size()){
				System.out.println("ERROR en Call " + this);
				Programa.setFin();
			}
			else{
				for(int i = 0; i < listaArgs.size(); i++){
					argumentosFuncion.get(i).checkType();
					Parametro p = listaArgs.get(i);
					if (!(argumentosFuncion.get(i).getTipo().equals(p.getTipo()))){
						System.out.println("ERROR en Call " + this);
						Programa.setFin();
					}	
				}
			}
		} 
		setTipo(this.link.getTipo());
	}

@Override
public void generaCodigo() {
    Funcion fun = (Funcion) this.getLink();
    List<Parametro> parametros = fun.getArgs();

    int offset = 4; 
    for (int i = 0; i < argumentosFuncion.size(); i++) {
        Expresion e = argumentosFuncion.get(i);
        Parametro param = parametros.get(i);
		int dir = param.getDelta()+offset;
        Programa.codigo.println("\tlocal.get $localsStart");
        Programa.codigo.println("\ti32.const " + dir);
        Programa.codigo.println("\ti32.add");

        if (param.getRef()) {
            e.calcularDirRelativa(); 
            Programa.codigo.println("\ti32.store");
        } else {
            e.generaCodigo();       
            Programa.codigo.println("\ti32.store");
        }

        offset += param.getTam(); 
    }

    Programa.codigo.println("\tcall $" + nombreFun);

    if (!asigned && fun.getTipo() instanceof TipoBasico
        && !fun.getTipo().equals(new TipoBasico(TipoDeTipo.NOTHING))) {
        Programa.codigo.println("\tdrop");
    }
}

public int getTamanyo(){
	int t =0;
	for(Expresion e: argumentosFuncion){
		t+= e.getTipo().getTam();
	}
	return t;
}
	public void setAsigned(){
		this.asigned = true;
	}


	public boolean getAsigned(){
		return this.asigned;
	}

	public void setObjeto(Expresion obj){
		objeto=obj;
	}

	
}