package ast.expresiones;
import ast.instrucciones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;

public class Num extends Constante{
	private int n;
	
	public Num(){
		this.n=0;
	}
	public Num(String n){
		this.n = Integer.parseInt(n);
	}
	
	public String toString(){
		return "" + this.n;
	}

	@Override
	public void binding(){
	}

	@Override
	public void checkType(){
		setTipo(new TipoBasico(TipoDeTipo.NUM));
	}

	public int getInt(){
		return this.n;
	}
	
	public void addOne(){
		n++;
	}

	public void set0(){
		n=0;
	}
	
	public void generaCodigo(){
		System.out.println("valor del argumento "+n);
		Programa.codigo.println("\ti32.const " + n);
	}

}	