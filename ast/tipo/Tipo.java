package ast.tipo;

import ast.ASTNode;
import ast.NodeKind;
import ast.instrucciones.*;
import ast.expresiones.*;

public abstract class Tipo extends ASTNode{

	Boolean modificable;

	public abstract String toString();
	
	@Override
	public NodeKind nodeKind(){
		return NodeKind.TIPO;
	}

	public String getNombre(){
		return "";
	} 
	
	public void checkType(){
	}

	@Override
	public boolean equals(Object o){
		if (this == null || o == null){
			return false;
		}
		if (o.toString().equals(this.toString())){
			return true;
		}
		return false;
	}

	@Override
	public void binding(){

	}

	public ASTNode getLink(){
		return this.link;
	}

	public Tipo getTipo(){
		return tipo;
	}

	public void setModificable(Boolean b){
		modificable=b;
	}

	public Tipo reduceAlias(){
		return this;
	}
	
	public String convertWasm(){
		if (this.equals(new TipoBasico(TipoDeTipo.DEC))){
			return "f32";
		}
		return "i32";
	}

	public abstract int getTam();



}