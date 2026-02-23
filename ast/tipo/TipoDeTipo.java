package ast.tipo;

import ast.instrucciones.*;
import ast.expresiones.*;
import ast.ASTNode;
import ast.NodeKind;

public enum TipoDeTipo{
	NUM("NUM"), TOF("TOF"), DEC("DEC"), NOTHING("NOTHING");

	private final String nombre;

	private TipoDeTipo(String nombre){
		this.nombre = nombre;
	}

	@Override
	public String toString(){
		return nombre;
	}
}