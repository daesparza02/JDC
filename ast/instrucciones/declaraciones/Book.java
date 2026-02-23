package ast.instrucciones.declaraciones;
import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;

public class Book extends Creacion{

	public Book(Tipo t, String iden){
		super(t,iden);
	}
	@Override
	public String toString(){
		return "(BOOK " + tipo.toString() + " " + name + ")";
	}

	@Override
	public void binding(){
		tipo.binding();
		ASTNode node = Programa.searchIdLastFun(name);
		if (node == null){
			Programa.insertar(name, this);
		}
		else{
			System.out.println("ERROR: identificador en Book " + name + " no se puede utilizar en " + this);
			Programa.setFin();
		}
	}

	@Override
	public void generaCodigo() {
		if (tipo instanceof TipoRegistro) {
			calcularDirRelativa();
			Programa.codigo.println("\ti32.const " + tipo.getTam() / 4);
			Programa.codigo.println("\tcall $zeron");
		}

		else if (tipo instanceof TipoArray && ((TipoArray) tipo).getTipoBasico() instanceof TipoRegistro) {
			TipoRegistro base = (TipoRegistro) ((TipoArray) tipo).getTipoBasico();
			int numElems = ((TipoArray) tipo).getNumElems();
			for (int i = 0; i < numElems; i++) {
				calcularDirRelativa();
				Programa.codigo.println("\ti32.const " + base.getTam() * i);
				Programa.codigo.println("\ti32.add");
				Programa.codigo.println("\ti32.const " + base.getTam() / 4);
				Programa.codigo.println("\tcall $zeron");
			}
		}
	}

}