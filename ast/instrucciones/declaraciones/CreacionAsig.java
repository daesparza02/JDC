package ast.instrucciones.declaraciones;
import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import java.util.List;
import java.util.ArrayList;
import ast.Programa;
import ast.instrucciones.*;



public class CreacionAsig extends Creacion{

	Expresion exp;
	private int deltaReg=-1;


	public CreacionAsig(Tipo tipo, String name, Expresion exp){
		super(tipo,name);
		this.exp = exp;	
		if (exp instanceof Unitario) {
			Unitario u = (Unitario) exp;
   			if(u.getCall()!=null) ((Unitario) exp).setAsigned();
		}
	}

	public CreacionAsig(Tipo tipo, String name, Expresion exp, Boolean modificable){
		super(tipo,name);
		this.exp = exp;	
		tipo.setModificable(modificable);
		if (exp instanceof Unitario) {
			Unitario u = (Unitario) exp;
   			if(u.getCall()!=null) ((Unitario) exp).setAsigned();
		}
	}

	public CreacionAsig(Tipo tipo, String name, Expresion exp, Boolean modificable, Boolean global){
		super(tipo,name);
		this.exp = exp;	
		tipo.setModificable(modificable);
		this.global=global;
		if (exp instanceof Unitario) {
			Unitario u = (Unitario) exp;
   			if(u.getCall()!=null) ((Unitario) exp).setAsigned();
		}
	}

	public String toString(){
		return  "(CREACION Y ASIGNACION (" + tipo.toString() + " " + name.toString() + "), " + exp.toString() + ")";
		
	}

	@Override
	public void binding(){
		tipo.binding();
		ASTNode node = Programa.searchIdLastFun(name);
		if (node == null){
			Programa.insertar(name, this);
			System.out.println("Insertando variable: " + name);

		}
		else{
			System.out.println("ERROR: identificador en CreacionAsig " + name + " no se puede utilizar en " + this);
			Programa.setFin();
		}
		exp.binding();
		
	}

	@Override 
	public void checkType(){
		if (tipo == null) {
        System.out.println("ERROR: tipo es null en CreacionAsig " + this.name);
        Programa.setFin();
        return;
    }
		this.tipo = tipo.reduceAlias();
		exp.checkType();
		Tipo tipo2 = exp.getTipo();

		if(tipo instanceof TipoPersonalizado){
			Tipo tipo3 = tipo.getLink().getTipo();
			if(!tipo3.equals(tipo2)){
				System.out.println("ERROR: mal tipado en CreacionAsig " + this);
				Programa.setFin();
			}
		}
		else if (tipo == null || !(tipo.equals(tipo2))){
			System.out.println("ERROR: mal tipado en CreacionAsig " + this);
			Programa.setFin();
		}
		
	}


/*@Override
public void generaCodigo() {
	if (tipo == null) {
		System.err.println("ERROR: tipo es null en CreacionAsig para " + name);
		Programa.setFin();
		return;
	}

	if (exp != null) {

		calcularDirRelativa();
		if (exp instanceof Unitario) {
			Unitario u = (Unitario) exp;
			if (u.getCall() != null) u.setAsigned();
		}
		exp.generaCodigo();
		if (tipo instanceof TipoRegistro ||
			(tipo instanceof TipoArray && ((TipoArray) tipo).getTipoBasico() instanceof TipoRegistro)) {
			Programa.codigo.println("\ti32.const " + tipo.getTam() / 4);
			Programa.codigo.println("\tcall $copyn");
		} else {
			Programa.codigo.println("\t" + tipo.convertWasm() + ".store");
		}
	}
}*/

@Override
public void generaCodigo() {
    if (tipo == null) {
        System.err.println("ERROR: tipo es null en CreacionAsig para " + name);
        Programa.setFin();
        return;
    }

    if (exp != null) {
		if(exp.getTipo() instanceof TipoArray) ((ExpresionArray) exp).generaCodigo(delta);
        // 1. Primero: VALOR
        else if(exp.getTipo() instanceof TipoBasico){
			 // 2. Después: DIRECCIÓN donde guardarlo
			calcularDirRelativa();
			 exp.generaCodigo();

			// 3. Hacer el store
			Programa.codigo.println("\t" + tipo.convertWasm() + ".store");
		}
        
    }
}





	public void setDeltaReg(int d) {
    	this.deltaReg = d;
	}

	@Override
	public void calcularDirRelativa() {
		if (deltaReg != -1) {
			System.out.println("Entrando en opcion 1 para "+ exp.toString());
			if (getGlobal()) {
				Programa.codigo.println("\ti32.const " + (deltaReg + delta + 4));
			} else {
				Programa.codigo.println("\ti32.const " + (deltaReg + delta));
				Programa.codigo.println("\tlocal.get $localsStart");
				Programa.codigo.println("\ti32.add");
			}
		} else if (getGlobal()) {
			Programa.codigo.println("\ti32.const " + (delta + 4));
			System.out.println("Entrando en opcion 2 para "+ exp.toString());
		} else {
			System.out.println("Guardando en dirección local: localsStart + " + delta);
			Programa.codigo.println("\tlocal.get $localsStart");
			Programa.codigo.println("\ti32.const " + delta);
			Programa.codigo.println("\ti32.add");
			System.out.println("Entrando en opcion 3 para "+ exp.toString());

		}
	}

	@Override
	public void setPos(int baseDelta) {
		System.out.println("Entrando a setPos de CreacionAsig: " + name);
		this.delta = baseDelta;
		this.setDelta(); 
	}

}