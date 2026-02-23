package ast.instrucciones.declaraciones;

import java.util.List;
import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Parametro;
import ast.Programa;
import ast.instrucciones.*;

public class Entity extends Declaracion{
	
	private String nombre;
	private List<Creacion> campos;
    private Constructo constructor;
    private List<Funcion> listaFunciones;


	public Entity(String nombre, List<Creacion> campos, Constructo constructor, List<Funcion> listaFunciones){
		this.nombre = nombre;
		this.campos = campos;
        this.constructor=constructor;
        this.listaFunciones= listaFunciones;
	}

	@Override
	public String toString(){
        String s = "";
        for(Funcion f: listaFunciones){
                s += f.toString();
        }
		return "(ENTITY (" + nombre + ", " + campos.toString()+", " +constructor.toString()+ "FUNCIONES ENTITY " + s + "))";
	}

	@Override
	public void binding(){
		ASTNode node = Programa.searchId(nombre);
		if (node == null){
			Programa.insertar(nombre, this);
			Programa.abrirBloque();
			for (Creacion campo : campos) {
				campo.binding();
			}
			if (constructor != null) {
				constructor.binding();
			}
			
			Programa.cerrarBloque();
			for (Funcion f : listaFunciones) {
				f.setClase(this);
				f.binding();
			}
			
		}
		else{
			System.out.println("ERROR: Entity " + nombre + " ya declarada.");

		}
	}

	@Override
	public void checkType() {
		int offset = 0;
		for (Creacion campo : campos) {
			campo.checkType();
			campo.setDeltaReg(offset);
			offset += campo.getTipo().getTam();
		}
		if (constructor != null) {
			constructor.checkType();
		}
		for (Funcion f : listaFunciones) {
			f.checkType();
		}
		setTipo(new TipoEntity(campos, constructor, listaFunciones));
	}


	@Override
	public int getTamanyo() {
		int total = 0;
		for (Creacion campo : campos) {
			total += campo.getTipo().getTam();
		}
		return total;
	}
	
	@Override
	public void generaCodigo() {
		for (Creacion campo : campos) {
			campo.generaCodigo();
		}
		if (constructor != null) {
			constructor.generaCodigo();
		}
		for (Funcion f : listaFunciones) {
			f.generaCodigo();
		}
	}

	public TipoEntity getTipoEntity() {
    	return new TipoEntity(campos, constructor, listaFunciones);
	}

	public Boolean tieneCampo(String s){
		for(Creacion c:campos){
			if(s.equals(c.getName())) return true;
		}
		return false;
	}

	public Boolean tieneFuncion(String s){
		for(Funcion f:listaFunciones){
			if(s.equals(f.getNombre())) return true;
		}
		return false;
	}

	public Creacion getCampo(String s){
		for(Creacion c:campos){
			if(s.equals(c.getName())) return c;
		}
		return null;
	}

    public Funcion getFuncion(String s){
		for(Funcion f:listaFunciones){
			if(s.equals(f.getNombre())) return f;
		}
		return null;
	}
}