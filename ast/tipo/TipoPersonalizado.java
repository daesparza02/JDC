package ast.tipo;

import ast.instrucciones.*;
import ast.expresiones.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;
import ast.instrucciones.declaraciones.*;

public class TipoPersonalizado extends Tipo{
	
	private String nombreTipo;

	public TipoPersonalizado (String nombreTipo) {
		this.nombreTipo = nombreTipo;
		this.modificable = true;
	}

	public String toString(){
		if(modificable) return nombreTipo;
		else return "FINAL "+nombreTipo;

	}

	@Override 
	public void binding(){
		ASTNode node = Programa.searchId(nombreTipo);
		if(node == null){
			System.out.println("ERROR en TipoPersonalizado " + this);
			Programa.setFin();
		}
		else{
			System.out.println("Linkeado a "+node.toString());
			this.link = node;
		}
	}

	@Override
	public void checkType(){
		if(!(this.link instanceof Registro)||!(this.link instanceof Entity)){
			System.out.println("ERROR en TipoPersonalizado " + this);
			Programa.setFin();
		} 
	}

	@Override
	public Tipo reduceAlias(){
		if(this.link instanceof Typedef){
			return link.getTipo();
		}
		return this;
	}

	public int getTam(){
		if(this.link instanceof Registro){
			Registro reg = (Registro) this.link;
			return reg.getTamanyo();
		}
		else if(this.link instanceof Entity){
			Entity e = (Entity) this.link;
			return e.getTamanyo();
		}
		else return 0;
		
	}
}