package ast.expresiones;

import java.util.Arrays;
import java.util.List;
import ast.instrucciones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;

public class Dec extends Constante{
	
	private float n;
	
	public Dec(String n){
		List<String> partes = Arrays.asList(n.split("'"));
		String num = partes.get(0) + "." + partes.get(1);
		this.n = Float.parseFloat(num);
	}
	
	public String toString(){
		return "" + n;
	}

	@Override
	public void binding(){
	}

	@Override
	public void checkType(){
		setTipo(new TipoBasico(TipoDeTipo.DEC));
	}

	public void generaCodigo(){
		Programa.codigo.println("\tf32.const " + n);
	}
	
}