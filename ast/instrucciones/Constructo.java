package ast.instrucciones;

import java.util.List;
import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Parametro;
import ast.instrucciones.declaraciones.*;
import ast.Programa;

public class Constructo extends Instruccion {
	private List<Parametro> argumentosFun;
	private List<Inicializacion> bodyFun;

	public Constructo(List<Parametro> argumentosFun, List<Inicializacion> bodyFun) {
		this.argumentosFun = argumentosFun;
		this.bodyFun = bodyFun;
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < bodyFun.size(); i++) {
			if (bodyFun.get(i) == null) {
				s += "\n\tnull";
			} else {
				s += "\n\t" + bodyFun.get(i).toString();
			}
		}
		return "( CONSTRUCTOR (" + argumentosFun.toString() + ", " + s + ")\n)";
	}

	@Override
	public void binding() {
		Programa.abrirBloque();
		for (Parametro arg : argumentosFun) {
			arg.binding();
		}
		for (Inicializacion instr : bodyFun) {
			instr.binding();
		}
		Programa.cerrarBloque();
	}

	@Override
	public void checkType() {
		for (Parametro arg : argumentosFun) {
			arg.checkType();
		}
		for (Inicializacion instr : bodyFun) {
			instr.checkType();
		}
	}

	@Override
	public void setPos(int delta) {
		int current = delta;
		for (Inicializacion instr : bodyFun) {
			instr.setPos(current);
			current += instr.getTamanyo();
		}
	}

	@Override
	public int maxMemory() {
		int total = 0;
		for (Inicializacion instr : bodyFun) {
			total += instr.getTamanyo();
		}
		return total;
	}

	@Override
	public boolean isBlock() {
		return true;
	}

	@Override
	public void generaCodigo() {
		for (Inicializacion instr : bodyFun) {
			instr.generaCodigo();
		}
	}
}
