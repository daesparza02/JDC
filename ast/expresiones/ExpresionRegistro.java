package ast.expresiones;
import ast.instrucciones.*;
import ast.instrucciones.declaraciones.Creacion;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;

import java.util.List;
import java.util.ArrayList;

public class ExpresionRegistro extends Expresion {

    private List<Expresion> elementos;

    public ExpresionRegistro(List<Expresion> elementos) {
        this.elementos = elementos;
    }

    public ExpresionRegistro(){
        this.elementos= new ArrayList<Expresion>();
    }

    public void add(int pos, Expresion e){
        this.elementos.add(pos, e);
    }

    @Override
    public String toString(){
        String s = "";
        for(Expresion e : elementos){
            s += e.toString();
            s += " ";
        }
        return "(REGISTRO "+s+")";
    }

    @Override 
    public void binding(){
		for(Expresion e : elementos){
            e.binding();
        }
	}

   	@Override 
	public void checkType(){
		List<Tipo> t = new ArrayList<Tipo>();
		for(Expresion e: elementos){
			e.checkType();
			t.add(e.getTipo().reduceAlias());
		}
		setTipo(new TipoRegistro(t));
	}

	public void generaCodigo(){
		int i=0;
        for(Expresion e : elementos){
            Programa.codigo.println("");
			Programa.codigo.println("\tcall $repeat");
			Programa.codigo.println("\ti32.const " + i*4);
			Programa.codigo.println("\ti32.add");
			e.generaCodigo();
			Programa.codigo.println("\t" + e.getTipo().convertWasm() + ".store");
			Programa.codigo.println("");
			i++;
        }
		Programa.codigo.println("\tdrop");
	}


	public int getSize(){
		return elementos.size();
	}

}