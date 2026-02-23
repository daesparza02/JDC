package ast.instrucciones.declaraciones;

import java.util.List;
import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;
import java.util.ArrayList;
import java.util.List;


public class Registro extends Creacion{
	
	private String nombreReg;
	private List<Creacion> camposReg;


	public Registro(String nombreReg, List<Creacion> camposReg){
		this.nombreReg = nombreReg;
		this.camposReg = camposReg;
	}

	@Override
	public String toString(){
		return "(" + nombreReg + ", " + camposReg.toString() + ")";
	}

	@Override
	public void binding(){
		ASTNode node = Programa.searchId(nombreReg);
		if (node == null){
			Programa.insertar(nombreReg, this);
			Programa.abrirBloque();
			for(Creacion c : camposReg){	
				c.binding();
			}
			Programa.cerrarBloque();
			
		}
		else{
			System.out.println("ERROR: identificador en Registro " + nombreReg + " no se puede utilizar en " + this);
			Programa.setFin();
		}
	} 

	@Override
	public void checkType(){
		List<Tipo> t = new ArrayList<Tipo>();
		 for(Creacion c: camposReg){
			 c.checkType();
			 t.add(c.getTipo());
		  }
		  setTipo(new TipoRegistro(t));
	}

	public Tipo getTipoCampo(String s){
		for(Creacion c : camposReg){
			if (c.getNombre().equals(s)){
				return c.getTipo();
			}
		}
		return null;
	}

	@Override
	public void generaCodigo() {
		for (Creacion campo : camposReg) {
			campo.setDeltaReg(delta);
			campo.generaCodigo();
		}
	}
	@Override
	public int getTamanyo() {
		int total = 0;
		for (Creacion campo : camposReg) {
			total += campo.getTipo().getTam();
		}
		return total;
	}

}