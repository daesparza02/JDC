package ast.instrucciones;

import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;

public class Return extends Instruccion{

	private Expresion exp;
	
	public Return(Expresion exp){
		this.exp = exp;
	}

	public String toString(){
		return "(RET " + exp.toString() + ")";
	}

	@Override
	public void binding(){
		exp.binding();
	}

	@Override 
	public void checkType(){
		exp.checkType();
		setTipo(exp.getTipo());
	}

	@Override
public void generaCodigo() {
    if (exp.getTipo() instanceof TipoBasico) {
        exp.generaCodigo(); 
    } else {
        exp.calcularDirRelativa();
    }
    Programa.codigo.println("\tcall $freeStack"); 
    Programa.codigo.println("\treturn");
}



	}