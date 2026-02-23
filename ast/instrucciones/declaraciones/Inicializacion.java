package ast.instrucciones.declaraciones;

import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;
import ast.instrucciones.*;

public class Inicializacion extends Declaracion{
	
	private Expresion exp1;
	private Expresion exp2;

	public Inicializacion(Expresion exp1, Expresion exp2){
		this.exp1 = exp1;
		this.exp2 = exp2;
		if (exp2 instanceof Unitario) {
			Unitario u = (Unitario) exp2;
   			if(u.getCall()!=null) ((Unitario) exp2).setAsigned();
		}
	}

	public String toString(){
		return "(INICIALIZACION (" + exp1.toString() + ", " + exp2.toString() + "))";
	}

	@Override
	public void binding(){
		exp1.binding();
		exp2.binding();
	}
	
	@Override 
	public void checkType(){
		exp1.checkType();
		exp2.checkType();
		Tipo tipo1 = exp1.getTipo();
		System.out.println("Tipo 1 "+tipo1);
		Tipo tipo2 = exp2.getTipo();
		System.out.println("Tipo 2 "+tipo2);
		if (tipo1 == null || tipo2 == null || !(tipo1.equals(tipo2))){
			System.out.println("ERROR: mal tipado en Inicializacion " + this);
			Programa.setFin();
		}
		else setTipo(tipo1);
	}

	@Override
	public void generaCodigo() {
		if (!exp1.getModificable()) {
			System.out.println("ERROR: exp1 no modificable en Inicializacion");
			Programa.setFin();
			return;
		}

		Tipo tipo1 = exp1.getTipo();
		Tipo tipo2 = exp2.getTipo();

		if (tipo1 instanceof TipoRegistro ||
			(tipo1 instanceof TipoArray && ((TipoArray) tipo1).getTipoBasico() instanceof TipoRegistro)) {

			exp1.calcularDirRelativa();   
			exp2.generaCodigo();          
			Programa.codigo.println("\ti32.const " + tipo1.getTam() / 4);
			Programa.codigo.println("\tcall $copyn");

		} else {
			exp1.calcularDirRelativa();  
			exp2.generaCodigo();        
			Programa.codigo.println("\t" + tipo2.convertWasm() + ".store");
		}
	}

	@Override
	public void calcularDirRelativa() {
		if (this.link instanceof Creacion) {
			((Creacion) this.link).calcularDirRelativa();
		} else {
			System.out.println("ERROR: referencia no válida para calcularDirRelativa en " + this);
			Programa.setFin();
		}
	}

	public Expresion getExp1(){
		return exp1;
	}

	public Expresion getExp2(){
		return exp2;
	}

	@Override
	public String getNombre() {
		return this.exp1.toString();
	}
}