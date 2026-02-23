package ast.instrucciones;

import java.util.List;
import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;

public class Condicional extends Instruccion {

	private Check condicion;
	private List<Othercheck> otherchecks;
	private Other others;

	public Condicional(Check condicion, List<Othercheck> otherchecks) {
		this.condicion = condicion;
		this.otherchecks = otherchecks;
	}

	public Condicional(Check condicion, List<Othercheck> otherchecks, Other others) {
		this.condicion = condicion;
		this.otherchecks = otherchecks;
		this.others = others;
	}

	public String toString() {
		String s = condicion.toString();
		for (Othercheck o : otherchecks) {
			s += o.toString();
			s += ", ";
		}
		if (others != null) {
			s += others.toString();
		}
		return "( " + s + " )";
	}

	@Override
	public void binding() {
		condicion.binding();
		for (Othercheck o : otherchecks) {
			o.binding();
		}
		if (others != null) {
			others.binding();
		}
	}

	@Override
	public void checkType() {
		condicion.checkType();
		for (Othercheck c : otherchecks) {
			c.checkType();
		}
		if (others != null) {
			others.checkType();
		}
	}

	@Override
	public void setPos(int delta) {
		condicion.setPos(delta);
		for (Othercheck c : otherchecks) {
			c.setPos(delta);
		}
		if (others != null) {
			others.setPos(delta);
		}
	}

	@Override
	public int maxMemory() {
		int max = condicion.maxMemory();
		for (Othercheck c : otherchecks) {
			int m = c.maxMemory();
			if (m > max) max = m;
		}
		if (others != null) {
			int m = others.maxMemory();
			if (m > max) max = m;
		}
		return max;
	}

	@Override
	public boolean isBlock() {
		return true;
	}

	@Override
	public void generaCodigo() {

		Programa.codigo.println("\tblock $finCondicional");

		condicion.generaCodigo();

		for (Othercheck oc : otherchecks) {
			oc.generaCodigo();
		}

		if (others != null) {
			others.generaCodigo();              
		}

		Programa.codigo.println("\tend"); 

	}
}
