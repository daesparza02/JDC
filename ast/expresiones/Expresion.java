package ast.expresiones;
import ast.instrucciones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.KindAsig;
import ast.NodeKind;
import java.util.List;
import java.util.ArrayList;

public abstract class Expresion extends Instruccion {

	public boolean modificable = false;

	public abstract String toString();

	@Override
	public NodeKind nodeKind(){
		return NodeKind.EXPRESION;
	}

	public String getName(){
		return "";
	}

	public void setSize(List<Num> tamanyos){

	}

	public boolean checkModificable(){
		return true;
	}
	
	public abstract void binding();
	public abstract void checkType();


	public void generaCodigo(){

	}

	public void calcularDirRelativa(){

	}

	public boolean getModificable(){
		return modificable;
	}

}