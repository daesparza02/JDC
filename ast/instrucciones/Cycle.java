package ast.instrucciones;

import java.util.List;
import java.util.ArrayList;

import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.instrucciones.*;
import ast.instrucciones.declaraciones.*;
import ast.Programa;

public class Cycle extends Bucle{
	private Declaracion dec;
	private Expresion condicion;
    	private Expresion salto;
	private List<Instruccion> body;
	private Inicializacion paso;
	
	public Cycle(Declaracion dec, Expresion condicion, Inicializacion paso, List<Instruccion> body) {
		this.dec = dec;
		this.condicion = condicion;
        this.paso = paso;
        this.salto=null;
		this.body = body;
	}

    public Cycle(Declaracion dec, Expresion condicion, Inicializacion paso, Expresion salto, List<Instruccion> body) {
        this.dec = dec;
        this.condicion = condicion;
        this.paso = paso;
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
        if(this.salto==null) return "(CYCLE ((" + dec.toString() + ", " + condicion.toString() + ", " + paso.toString() + "), (" + s + ")))";
		else return "(CYCLE ((" + dec.toString() + ", " + condicion.toString() + ", " + paso.toString() + ", " + salto.toString() + "), (" + s + ")))";
	}

	@Override
	public void binding(){
		Programa.abrirBloque();
		dec.binding();
		condicion.binding();
		if(salto!=null) salto.binding();
		paso.binding();
		for(Instruccion i : body){
			i.binding();
		}
		Programa.print();
		Programa.cerrarBloque();
		
	}

	@Override
	public void checkType(){
		dec.checkType();
		condicion.checkType();
		if(salto!=null) salto.checkType();
		paso.checkType();
		if(!dec.getTipo().equals(paso.getTipo())||  !condicion.getTipo().equals("TOF")||(salto!=null&& !salto.getTipo().equals("TOF"))){
			System.out.println("ERROR: mal tipado en Cycle " + this);
			Programa.setFin();
		}
		else{
			for(Instruccion i: body){
				i.checkType();
			}
		}
	}
	
	@Override
	public void setPos(){

	}

	public void setPos(int delta) {
		int forDelta = delta;
		dec.setPos(forDelta);
		if (dec instanceof Creacion){
			forDelta += dec.getTamanyo();
		}
		for(Instruccion ins : body){			
			ins.setPos(forDelta);
			forDelta += ins.getTamanyo();
		}

	}

	public void generaCodigo(){

        	dec.generaCodigo();

        	Programa.codigo.println("\tblock");
        	Programa.codigo.println("\t loop");

        	condicion.generaCodigo();
        
        	Programa.codigo.println(" i32.eqz");
        	Programa.codigo.println(" br_if 1");

			if(salto!=null){
				salto.generaCodigo();
    		Programa.codigo.println(" i32.const 0");
   		 Programa.codigo.println(" i32.ne");  
    		Programa.codigo.println(" br_if 0");
			}
		
		
        
        	for (Instruccion ins : body){
         	   ins.generaCodigo();
        	}

        	paso.generaCodigo();

        	Programa.codigo.println("\t br 0");
        	Programa.codigo.println("\t end");
        	Programa.codigo.println("\tend");

	}

	public int maxMemory(){
		int max = 0;
		int c = 0;
		max += dec.getTamanyo();
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