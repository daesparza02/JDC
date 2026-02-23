package ast.instrucciones;

import java.util.List;
import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;
import ast.instrucciones.declaraciones.*;
import ast.instrucciones.*;


public class Othercheck extends Instruccion{

	private Expresion condicion;
	private List<Instruccion> body;

	public Othercheck(Expresion condicion, List<Instruccion> body){
		this.condicion = condicion;
		this.body = body;

	}
	public String toString(){
		String s = "";
		for(int i = 0; i < body.size(); i++){
			if (body.get(i) == null){
					s += "null";
			}
			else {
				s += body.get(i).toString();
			}

			if (i != body.size() - 1){
				s += ", ";
			}
		}
		return "(OTHERCHECK " + condicion.toString() + " THEN (" + s + "))";
	}

	@Override
	public void binding(){
		condicion.binding();
		Programa.abrirBloque();
		for(Instruccion i: body){
			i.binding();
		}
		Programa.print();
		Programa.cerrarBloque();	
	}

	@Override
	public void checkType(){
		condicion.checkType();
		if (!condicion.getTipo().equals("TOF")){
			System.out.println("ERROR: fallo en tipo Othercheck" + this);
			Programa.setFin();
		}
		else{
			for(Instruccion i: body){
				i.checkType();
			}
		}
	}
public void setPos(int delta) {
		int elseifDelta = delta;
		for(Instruccion ins: body){
			ins.setPos(elseifDelta);
			elseifDelta += ins.getTamanyo();
		}

	}

	public int maxMemory(){
		int max = 0;
		int c = 0;
		for(Instruccion ins : body){
			if(ins instanceof Creacion){
				c += ins.getTamanyo();
				max += ins.getTamanyo();
			}
			else if(ins.isBlock()){
				int max1 = ins.maxMemory();
				if(c+max1 > max){
					max = c + max1;
				}
			}
		}
		return max;
	}

	public boolean isBlock(){
		return true;
	}

	public void generaCodigo(){
		condicion.generaCodigo(); 
		Programa.codigo.println("\tif");
		for(Instruccion ins : body){
			System.out.println("Ejecutando instrucción dentro de Othercheck: " + ins);
			ins.generaCodigo();
		}		Programa.codigo.println("\tbr $finCondicional");
		Programa.codigo.println("\tend");
	}


	public Expresion getCondicion(){
		return condicion;
	}
}