package ast.tipo;

import java.util.List;
import ast.instrucciones.*;
import ast.expresiones.*;
import ast.ASTNode;
import ast.NodeKind;
import java.util.List;
import java.util.ArrayList;

public class TipoArray extends Tipo{
	
	private Tipo tipoBasico;
	private List<Num> tams;

	public TipoArray (Tipo tipoBasico, List<Num> tams){
		this.tipoBasico = tipoBasico;
		this.tams = tams;
		this.modificable = true;
	}

	@Override
	public String toString(){
		if(modificable) return "ARRAY " + tipoBasico.toString() + tams.toString();
		else return "FINAL ARRAY " + tipoBasico.toString() + tams.toString();
	}

	public List<Num> getTams(){
		return this.tams;
	}

	@Override
	public void binding(){
		tipoBasico.binding();
	}

	public Tipo reduceAlias(){
		this.tipo=tipoBasico.reduceAlias();
		if(this.tipo instanceof TipoArray){
			TipoArray aux = (TipoArray) tipo;
			this.tams.addAll(aux.getTams());
			this.tipo = aux.getTipoBasico();
		}
		return this;
	}

	public Tipo getTipoBasico(){
		if(tipoBasico instanceof TipoArray) return ((TipoArray) tipoBasico).getTipoBasico();
		else return tipoBasico.reduceAlias();
		
	}

	@Override
	public void checkType(){
	}

	public Tipo getTipo(){
		return tipo;
	}

	@Override
	public boolean equals(Object o){
		if(this==null||o==null||!(o instanceof TipoArray)) return false;
		else{
			TipoArray a = (TipoArray) o;
			if(!tipoBasico.equals(a.getTipoBasico())) return false;
			else{
				List<Num> tams2 = a.getTams();
				if(tams2.size()!= tams.size()) return false;
				else{
					for(int i=0;i<tams.size();++i){
						if(tams.get(i).getInt()!= tams2.get(i).getInt()) return false; 
					}
				}	
			}
		}
		return true;
	}

	public int getDim(){
		return tams.size();
	}

	
	public int getNumElems(){
		int n = 1;
		for(Expresion e : tams){
			Num numerito = (Num) e;
			n *= numerito.getInt();
		}
		return n;
	}

	public int getTam(){
		return tipo.getTam() * getNumElems();
	}
}