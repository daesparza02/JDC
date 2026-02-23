package ast.expresiones;
import ast.instrucciones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;
public class Tof extends Constante{
	private boolean b;

	public Tof(String b){
		if (b.equals("tru")){
			this.b = true;
		}
		else{
			this.b = false;
		}
	}

	@Override
	public String toString(){
		if (b){
			return "tru";
		}
		return "fols";
	}

	@Override
	public void binding(){
	}
	
	@Override
	public void checkType(){
		setTipo(new TipoBasico(TipoDeTipo.TOF));
	}

	public int getValue(){
		if (b) return 1;
		else return 0;
	}

	public void generaCodigo(){
		Programa.codigo.println("\ti32.const " + getValue());
	}
}