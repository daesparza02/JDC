package ast.instrucciones;

import java.util.List;
import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;
import ast.instrucciones.declaraciones.*;


public class Other extends Instruccion{

	private List<Instruccion> body;
	
	public Other(List<Instruccion> body){
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
		return "(OTHER (" +  s + "))";
		
	}

	@Override
	public void binding(){
		Programa.abrirBloque();
		for(Instruccion i: body){
			i.binding();
		}
		Programa.print();	
		Programa.cerrarBloque();	
	}

	@Override
	public void checkType(){
		for(Instruccion i: body){
			i.checkType();
		}
		
	}

	
	public void setPos(int delta) {
		int elseDelta = delta;
		for(Instruccion ins: body){
			ins.setPos(elseDelta);
			elseDelta += ins.getTamanyo();
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
		for(Instruccion ins : body){
			ins.generaCodigo();
		}
	}
}