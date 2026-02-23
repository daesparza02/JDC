package ast.instrucciones.declaraciones;

import ast.instrucciones.*;
import ast.Programa;

public abstract class Declaracion extends Instruccion{

    @Override
	public abstract String toString();

	@Override
	public void binding(){

	}
	@Override 
	public void checkType(){
	}
	
	public void setDelta() {
    	this.delta = Programa.pila.usarEspacio(this.tipo.getTam());
	}

	public void setDelta(int offset){
		this.delta=offset;
	}


}