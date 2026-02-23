package ast.instrucciones.declaraciones;

import java.util.List;
import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;

public class Typedef extends Declaracion{
	
	private String nombre;
	private Tipo tipoP;


	public Typedef(String nombre, Tipo tipoP){
		this.nombre = nombre;
        this.tipoP = tipoP;
	}

	@Override
	public String toString(){
		return "(TYPEDEF (" + nombre + ", " + tipoP.toString() + "))";
	}

	@Override
	public void binding(){
		setTipo(new TipoPersonalizado(nombre));
		tipoP.binding();
		ASTNode node = Programa.searchIdLastFun(nombre);
		if (node == null){
			Programa.insertar(nombre, this);
		}
		else{
			System.out.println("ERROR: identificador en Typedef " + nombre + " no se puede utilizar en " + this);
			Programa.setFin();
		}
	}

	@Override
	public void checkType(){
		tipoP.checkType();
	}
}