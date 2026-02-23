package ast.tipo;

import ast.instrucciones.*;
import ast.expresiones.*;
import ast.ASTNode;
import ast.NodeKind;

public class TipoPuntero extends Tipo{
	
	public TipoPuntero(Tipo tipo){
		this.tipo = tipo;
		this.modificable = true;
	}	

	@Override
	public String toString(){
		if(modificable) return "PUNTERO " + tipo.toString();
		else return "FINAL PUNTERO " + tipo.toString();
	}

	@Override
	public void binding(){
		tipo.binding();
	}

	public int getTam(){
		return 4;
	}

	public Tipo reduceAlias(){
		this.tipo = tipo.reduceAlias();
		return this;
	}

	public Tipo getTipoBasico(){
		return tipo;
	}

}