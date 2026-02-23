package ast.expresiones;
import ast.instrucciones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;

import java.util.List;
import java.util.ArrayList;

public class ExpresionArray extends Expresion {

    private List<Expresion> elementos;

    public ExpresionArray(List<Expresion> elementos) {
        this.elementos = elementos;
    }

    public ExpresionArray(){
        this.elementos= new ArrayList<Expresion>();
    }

    public void add(int pos,Expresion e){
        this.elementos.add(pos, e);
    }

    public void add(Expresion e){
        this.elementos.add(e);
    }

    @Override
    public String toString(){
        String s = "";
        Boolean primero = true;
        for(Expresion e : elementos){
            if(primero) primero = false;
            else s += ", ";
            s += e.toString();
        }
        return "["+s+"]";
    }

   @Override
   public void binding(){
    for(Expresion e: elementos){
        e.binding();
    }
   } 

   @Override 
	public void checkType(){
        Tipo tipoArray = null;
        Boolean primerElem = true;
        List<Num> t = new ArrayList<Num>();
        for(Expresion e: elementos){
            e.checkType();
           if(primerElem){
                tipoArray=e.getTipo();
                primerElem=false;
            }
            else if(!tipoArray.equals(e.getTipo())){
                System.out.println("ERROR: mal tipado en Array " + this);
			    Programa.setFin();
                break;
            }
        }
        List<Num> tams = getTams();
		setTipo(new TipoArray(tipoArray, tams));
	}

    private List<Num> getTams(){
        List<Num> tams = new ArrayList<Num>();
        if(elementos!=null)tams.add(new Num(""+ elementos.size()));
        if(elementos!=null&&elementos.size()>0){
            if(elementos.get(0) instanceof ExpresionArray){
                ExpresionArray e = (ExpresionArray) elementos.get(0);
                tams.addAll(e.getTams());
            }
        }
        return tams;
    }

    private int getNElems(){
        return elementos.size();
    }

    @Override
    public void generaCodigo() {
        generaCodigo(0); 
    }

    public int generaCodigo(int offsetInicial) {
        System.out.println("Entrando en generacodigo de Expresionarray");
        int i = 0;
        int offset=offsetInicial;
        for (Expresion e : elementos) {
            if (e instanceof ExpresionArray) {
                int num= ((ExpresionArray) e).generaCodigo(offset);  
                offset = num;
            } else {
                System.out.println("Tipo de " + e.toString()+ " "+ e.getTipo());
                Programa.codigo.println("\tlocal.get $localsStart");
                Programa.codigo.println("\ti32.const " + offset);
                Programa.codigo.println("\ti32.add");
                e.generaCodigo();

                if (e.getModificable()) {
                    Programa.codigo.println("\t" + e.getTipo().convertWasm() + ".load");
                }

                Programa.codigo.println("\t" + e.getTipo().convertWasm() + ".store");
                offset+=4;

            }

            i++;
        }
        return offset;
    }

}
