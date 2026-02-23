package ast.instrucciones.declaraciones;
import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import java.util.List;
import java.util.ArrayList;
import ast.Programa;

public class BookAsig extends CreacionAsig {

	public BookAsig(Tipo t, String iden, Expresion exp){
		super(t,iden,exp);
	}

	@Override
	public String toString(){
		return "(BOOK " + tipo.toString() + name + " ("+ exp.toString() + "))";
	}

	@Override
	public void binding(){
		tipo.binding();
	
		ASTNode node = Programa.searchIdLastFun(name);
		if (node == null){
			Programa.insertar(name, this);
		}
		else{
			System.out.println("ERROR: identificador en BookAsig " + name + " no se puede utilizar en " + this);
			Programa.setFin();
		}
		
		exp.binding();
		
	}

}