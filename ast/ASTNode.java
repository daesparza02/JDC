package ast;

import java.util.List;
import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;


public abstract class ASTNode {

    protected Tipo tipo;
	public ASTNode link;
	public int delta;


	public abstract String toString();
	public abstract NodeKind nodeKind();
	
	public abstract void binding(); 

	public Tipo getTipo(){
		return tipo;
	}

	public void setTipo(Tipo tipo){
		this.tipo = tipo;
	}	

	public abstract void checkType();
	public abstract String getNombre();
	public ASTNode getLink(){
		return this.link;
	}

	public void setLink(ASTNode node){
		this.link = node;
	}

	public void setDelta(){
        	delta = Programa.pila.getDelta();
	        int tam = tipo.getTam();
     	   	Programa.pila.updateDelta(tam);
System.out.println("Variable " + this.getNombre() + " tiene delta " + this.delta + (this.getGlobal() ? " (global)" : " (local)"));
   System.out.println("Asignando delta " + this.delta + " con tamanyo " + tam + " a " + this.getNombre());

}

        public int getDelta() {
             return delta;
        }

	public void setPos(){
	}
	public void setPos(int deltaFun) {
		delta = deltaFun;
	}

	public int getTamanyo(){
		if (tipo != null){
			return tipo.getTam();
		}
		return 0;
	}

	public void getMemory(){

	}

	public int maxMemory(){
		return 0;
	} 
	
	public boolean isBlock(){
		return false;
	}

	public boolean getGlobal(){
		return false;
	}

	public void calcularDirRelativa(){
	}
	
}
