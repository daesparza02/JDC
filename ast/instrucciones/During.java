package ast.instrucciones;

import java.util.List;
import java.util.ArrayList;

import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;
import ast.instrucciones.declaraciones.*;
import ast.instrucciones.*;


public class During extends Bucle{
	private Expresion condicion;
    private Expresion salto;
	private List<Instruccion> body;
	
	public During(Expresion condicion, List<Instruccion> body) {
		this.condicion = condicion;
        this.salto=null;
		this.body = body;
	}

    public During(Expresion condicion, Expresion salto, List<Instruccion> body) {
        this.condicion = condicion;
        this.salto=salto;
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
        if(this.salto==null) return "(DURING (" + condicion.toString() + ", (" + s + ")))";
		else return "(DURING ((" + condicion.toString() + ", SALTO " + salto.toString() + "), (" + s + ")))";
	}

	@Override
	public void binding(){
		Programa.abrirBloque();
		condicion.binding();
		if(salto!=null) salto.binding();
		for(Instruccion i: body){
			i.binding();
		}	
		Programa.print();
		Programa.cerrarBloque();
	}

	@Override
	public void checkType(){
		condicion.checkType();
		if(salto!=null) salto.checkType();
		if(!condicion.getTipo().equals("TOF")||(salto!=null&&!salto.getTipo().equals("TOF"))){
			System.out.println("ERROR: mal tipado en During " + this);
			Programa.setFin();
		}
		else{
			for(Instruccion i: body){
				i.checkType();
			}
		}
	}

	public void setPos(int delta) {
		int whileDelta = delta;
		for(Instruccion ins : body){
			ins.delta = whileDelta;
			ins.setPos(whileDelta);
			whileDelta += ins.getTamanyo();
		}

	}

	public void generaCodigo(){
       
        	Programa.codigo.println("\tblock");
        	Programa.codigo.println("\t  loop");

        	condicion.generaCodigo();
        
        	Programa.codigo.println("\t i32.eqz");
        	Programa.codigo.println("\t br_if 1");
		if (salto!= null){
			salto.generaCodigo();
   		 	Programa.codigo.println(" i32.const 0");
    		Programa.codigo.println(" i32.ne");  
    		Programa.codigo.println(" br_if 0");
		}
		
        
        	for (Instruccion ins : body){
            		ins.generaCodigo();
        	}

       		Programa.codigo.println("\t br 0");
        	Programa.codigo.println("\t end");
        	Programa.codigo.println("\tend");
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
	

}