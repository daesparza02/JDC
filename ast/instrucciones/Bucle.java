
package ast.instrucciones;

import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;

public abstract class Bucle extends Instruccion{

	public abstract String toString();

	@Override
	public void checkType(){
	}
	public boolean isBlock(){
		return true;
	}


}	