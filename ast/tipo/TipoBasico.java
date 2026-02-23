package ast.tipo;

import ast.instrucciones.*;
import ast.expresiones.*;
import ast.ASTNode;
import ast.NodeKind;

public class TipoBasico extends Tipo {

	private TipoDeTipo tipoD;

	public TipoBasico(TipoDeTipo tipo){
		this.tipoD = tipo;
		this.modificable = true;
	}	

	public String toString(){
		return tipoD.toString();
	}

	public int getTam(){
		int tam = 0;
		if(tipoD==TipoDeTipo.NUM||tipoD==TipoDeTipo.DEC||tipoD==TipoDeTipo.TOF) tam = 4;
		return tam;
	}
}
