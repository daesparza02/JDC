package ast.tipo;
import java.util.List;

import ast.ASTNode;
import ast.NodeKind;
import ast.instrucciones.*;
import ast.instrucciones.declaraciones.Creacion;
import ast.tipo.Tipo;
import ast.expresiones.*;

public class TipoEntity extends Tipo{
    
    private List<Creacion> campos;
    private Constructo constructor;
    private List<Funcion> listaFunciones;

    public TipoEntity(List<Creacion> campos, Constructo constructor, List<Funcion> listaFunciones){
        this.campos=campos;
        this.constructor=constructor;
        this.listaFunciones=listaFunciones;
    }
   
    @Override
	public String toString(){
        String c="";
        String f = "";
        for(Creacion s:campos){
            c+=s.toString();
            c+=" ";
        }
        for(Funcion s:listaFunciones){
            f+=s.toString();
            f+=" ";
        }
		return "(ENTITY (CAMPOS " + c.toString() + ", CONSTRUCTOR " + constructor.toString() +", FUNCIONES "+f.toString() +"))";
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

    @Override
	public void binding(){
		for(Creacion c:campos) c.binding();
        constructor.binding();
        for(Funcion f:listaFunciones) f.binding();

	} 

	@Override 
	public void checkType(){
        for(Creacion c:campos) c.checkType();
        constructor.checkType();
        for(Funcion f:listaFunciones) f.checkType();	
    }

	@Override
	public int getTam() {
		int total = 0;
		for (Creacion campo : campos) {
			total += campo.getTipo().getTam();
		}
		return total;
	}


}
