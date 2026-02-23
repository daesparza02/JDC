package ast.tipo;

import java.util.List;
import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;
import java.util.List;
import java.util.ArrayList;

public class TipoRegistro extends Tipo{
	
	private List<Tipo> tiposReg;


	public TipoRegistro(List<Tipo> tiposReg){
		this.tiposReg = tiposReg;
	}

	@Override
	public String toString(){
		return "(REG (" + tiposReg.toString() + "))";
	}

	@Override
	public void binding(){
		for(Tipo t:tiposReg) t.binding();
	} 

	@Override 
	public void checkType(){
		for(Tipo t:tiposReg) t.checkType();
	}

	@Override
	public Tipo reduceAlias(){
		ArrayList<Tipo> lt = new ArrayList<Tipo>();
		for(Tipo t:tiposReg) lt.add(t.reduceAlias());
		tiposReg = lt;
		return this;
	}

	public int getTam(){
		int tam = 0;
		for(Tipo t : tiposReg) tam +=t.getTam();
		return tam;
	}

}