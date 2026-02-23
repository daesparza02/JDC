package ast.instrucciones;

import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;

public class Unbook extends Instruccion{

	private String nombre;

	public Unbook(String nombre){
		this.nombre = nombre;

	}
	public String toString(){
		return "(UNBOOK (" + nombre + "))"; 
	}

	@Override
	public void binding(){
	}
	
	@Override
	public void checkType(){
	}

}