package ast;

import ast.instrucciones.*;
import ast.expresiones.*;
import ast.tipo.*;
import ast.tipo.TipoEntity;
import ast.instrucciones.declaraciones.Entity;


public class Parametro extends ASTNode{

	private Tipo tipoArg;
	private String nombreArg;
	private Boolean referencia;

	public Parametro(Tipo tipoArg, String nombreArg, Boolean referencia){
		this.tipoArg = tipoArg;
		this.nombreArg = nombreArg;
		this.referencia = referencia;
		
	}

	public String toString(){
		if (referencia){
            return "(" + tipoArg.toString() + " REF " + nombreArg + ")";
		}
		else{
			return "(" + tipoArg.toString() + " " + nombreArg + ")";
		}
		
	}

	@Override
	public NodeKind nodeKind(){
		return NodeKind.PARAMETRO;
	}

	@Override
	public void binding(){
		ASTNode node = Programa.searchIdLastFun(nombreArg);
		if (node == null){
			Programa.insertar(nombreArg, this);
		}
		else{
			System.out.println("ERROR: identificador PARAM" + nombreArg + " no se puede utilizar en " + this);
			Programa.setFin();
		}
		 if (tipoArg instanceof TipoPersonalizado) {
        ASTNode def = tipoArg.getLink();
        if (def instanceof Entity) {
            this.tipoArg = ((Entity) def).getTipoEntity(); 
        }
    }
	}
	
	@Override
	public String getNombre(){
		return "";
	} 
	
	public void checkType(){
		setTipo(tipoArg);
		System.out.println(this.delta);
	}
	
	public Boolean getRef(){
		return this.referencia;
	}

	public Tipo getTipo(){
		return this.tipoArg;
	}


	public void setDelta(int d) {
    	this.delta = d;
	}

	public int getTam(){
		
		if (!referencia){
			 if (tipo == null) {
            System.err.println("ERROR: tipoArg es null en Parametro '" + nombreArg + "'");
            Programa.setFin();
            return 0;
        	}
			return tipo.getTam();
		}
		return 4;
	}

}