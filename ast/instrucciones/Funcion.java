package ast.instrucciones;

import java.util.List;
import ast.expresiones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Parametro;
import ast.Programa;
import ast.instrucciones.declaraciones.*;

public class Funcion extends Instruccion {
	private String nombreFun;
	private Tipo tipoFun;
	private List<Parametro> argumentosFun;
	private List<Instruccion> bodyFun;
	private Entity clase;

	public Funcion(String nombreFun, List<Parametro> argumentosFun, Tipo tipoFun, List<Instruccion> bodyFun){
		this.nombreFun = nombreFun;
		this.tipoFun = tipoFun;
		this.argumentosFun = argumentosFun;
		this.bodyFun = bodyFun;
		this.clase = null;
	}
	
	@Override
	public String toString(){
		String s = "";
		for(int i = 0; i < bodyFun.size();i++){
			if (bodyFun.get(i) == null){
				s += "\n\tnull";
			}
			else {
				s += "\n\t" + bodyFun.get(i).toString();
			}
		}
		return "( FUNCION " + nombreFun.toString() + " DEVUELVE " + tipoFun.toString() + " (" + argumentosFun.toString() + ", " + s + ")\n)";
	}

	@Override
	public void binding(){
		ASTNode node = Programa.searchId(nombreFun);
		int numReturn=0;
		if (node == null && clase == null){
			Programa.insertar(nombreFun, this);
			if(nombreFun.equals("startPls")) Programa.addStartPls();
			Programa.abrirBloque();
			for(Parametro arg: argumentosFun){
				arg.binding();
			}
			for(Instruccion ins: bodyFun){
				if (ins instanceof Return){
					numReturn += 1;
					if (numReturn > 1 || (tipoFun.equals(new TipoBasico(TipoDeTipo.NOTHING)))){
						System.out.println("ERROR no hay los returns adecuados en Funcion " +this.nombreFun);
						Programa.setFin();
					}
					else{
						ins.setLink(this);
					}	
				}
				ins.binding();

			}
			if (numReturn == 0 && !tipoFun.equals(new TipoBasico(TipoDeTipo.NOTHING))){
				System.out.println("ERROR falta return en Funcion " +this.nombreFun);
				Programa.setFin();

			}
			Programa.print();
			Programa.cerrarBloque();
		}
		else if(clase!=null){
			Programa.abrirBloque();
			for(Parametro arg: argumentosFun){
				arg.binding();
			}
			for(Instruccion ins: bodyFun){
				if (ins instanceof Return){
					numReturn += 1;
					if (numReturn > 1 || (tipoFun.equals(new TipoBasico(TipoDeTipo.NOTHING)))){
						System.out.println("ERROR no hay los returns adecuados en Funcion " +this.nombreFun);
						Programa.setFin();
					}
					else{
						ins.setLink(this);
					}	
				}
				else if(ins instanceof Inicializacion){
					Inicializacion inicializa = (Inicializacion) ins;
					Expresion exp1 = inicializa.getExp1();
					Expresion exp2 = inicializa.getExp2();
					if(!clase.tieneCampo(exp1.getName())){
						System.out.println("ERROR no existe el campo "+exp1+" en la clase " + clase.getNombre() + ", error en Funcion " +this.nombreFun);
					Programa.setFin();
					}
					else exp2.binding();
				}
				else ins.binding();

			}
			if (numReturn == 0 && !tipoFun.equals(new TipoBasico(TipoDeTipo.NOTHING))){
				System.out.println("ERROR falta return en Funcion " +this.nombreFun);
				Programa.setFin();

			}
			Programa.print();
			Programa.cerrarBloque();
		}
		else{
			System.out.println("ERROR: identificador en Funcion " + nombreFun + " no se puede utilizar en " + this);
			Programa.setFin();
		}

	}

	@Override
	public void checkType(){
		setTipo(tipoFun);
		for(Parametro p: argumentosFun){
			p.checkType();
		}
		for(Instruccion ins: bodyFun){
			ins.checkType();
			if(ins instanceof Return){
				System.out.println("Tipo de la funcion "+nombreFun+ " es "+tipoFun+", tipo del return "+ins.getTipo());
			}
		}
	}

	public List<Parametro> getArgs(){
		return this.argumentosFun;
	}

	public String getNombre(){
		return this.nombreFun;
	}

	public Tipo getTipo(){
		return this.tipoFun;
	}

	@Override
	public void generaCodigo(){
    setPos(); 

    int tamParams = getParamSize();
    int tamLocales = maxMemory();
    int tamTotal = tamParams + tamLocales + 4; 

    Programa.codigo.print("(func $" + nombreFun);
    if (!tipoFun.equals(new TipoBasico(TipoDeTipo.NOTHING))){
        Programa.codigo.print(" (result i32)");
    }
    Programa.codigo.println("");
    Programa.codigo.println("\t(local $localsStart i32)");
    Programa.codigo.println("\t(local $temp i32)");

    Programa.codigo.println("\ti32.const " + tamTotal); 
    Programa.codigo.println("\tcall $reserveStack"); 
    Programa.codigo.println("\tlocal.set $temp");
    Programa.codigo.println("\tglobal.get $MP");
    Programa.codigo.println("\tlocal.get $temp");
    Programa.codigo.println("\ti32.store"); 

    Programa.codigo.println("\tlocal.get $temp");
    Programa.codigo.println("\ti32.const 4");
    Programa.codigo.println("\ti32.add");
    Programa.codigo.println("\tlocal.set $localsStart\n");

    for (Instruccion instruccion : bodyFun) {
        instruccion.generaCodigo(); 
        if (instruccion instanceof Return) break;
        Programa.codigo.println();
    }

    if (tipoFun.equals(new TipoBasico(TipoDeTipo.NOTHING))) {
        Programa.codigo.println("\tcall $freeStack");
    }

    Programa.codigo.println(")");
}

public int getParamSize() {
    int total = 0;
    for (Parametro p : argumentosFun) total += p.getTam();
    return total;
}

	@Override
public void setPos(){
    int offset = 0;
    for (Parametro p : argumentosFun) {
        p.setDelta(offset);
        System.out.println(p.toString() + " -> delta " + p.getDelta()); 
        offset += p.getTipo().getTam(); 
    }

   
    for(Instruccion ins : bodyFun){
        if(ins instanceof Declaracion){
           ((Declaracion) ins).setDelta(offset);
            System.out.println(ins.toString() + " -> delta " + ((Declaracion)ins).getDelta()); 
            offset += ins.getTamanyo();
        }

    }
}


	public String getName(){
		return nombreFun;
	}

	public int maxMemory(){
		int max = 0;
		int c = 0;

		for(Parametro arg : argumentosFun){
			arg.checkType();
			c += arg.getTam();
			max += arg.getTam();
		}
		for(Instruccion ins : bodyFun){
			if(ins instanceof Creacion){
				c += ins.getTamanyo();
				max += ins.getTamanyo();
			}
			else if(ins.isBlock()){
				int max1 = ins.maxMemory();
				if(c+max1 > max){
					max = c + max1;
				}
			}
		}
		return max;
	}


	public void setClase(Entity e){
		clase=e;
	}

}
