package ast;

import java.util.List;
import ast.instrucciones.*;
import ast.instrucciones.declaraciones.*;
import ast.expresiones.*;
import ast.tipo.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Programa extends ASTNode {

	private List<Declaracion> listaDeclaraciones;
	private List<Typedef> listaTypedefs;
	private List<Instruccion> listaInstrucciones;
	public static PrintWriter codigo;
	public static Pila pila;
	private static int fin = 0;	
	private static int numStartPls = 0;

	public Programa(List<Declaracion> decIniciales, List<Typedef> listaTypedefs, List<Instruccion> listaInstrucciones){
		this.listaDeclaraciones = decIniciales;
		this.listaTypedefs = listaTypedefs;
		this.listaInstrucciones = listaInstrucciones;
		this.pila = new Pila();
	}

	public void binding(){
		pila.abrirBloque();
		List<Parametro> largs = new ArrayList<Parametro>();
		largs.add(new Parametro(new TipoBasico(TipoDeTipo.NUM), "x", false));
		pila.insertar("printNum", new Funcion( "printNum", largs, new TipoBasico(TipoDeTipo.NOTHING),new ArrayList<Instruccion>()));
		List<Parametro> largs1 = new ArrayList<Parametro>();
		largs1.add(new Parametro(new TipoBasico(TipoDeTipo.TOF), "x", false));
		pila.insertar("printTof", new Funcion("printTof", largs1, new TipoBasico(TipoDeTipo.NOTHING), new ArrayList<Instruccion>()));
		List<Parametro> largs2 = new ArrayList<Parametro>();
		largs2.add(new Parametro(new TipoBasico(TipoDeTipo.DEC), "x", false));
		pila.insertar("printDec", new Funcion("printDec",largs2, new TipoBasico(TipoDeTipo.NOTHING), new ArrayList<Instruccion>()));
		pila.insertar("readNum", new Funcion("readNum", new ArrayList<Parametro>(), new TipoBasico(TipoDeTipo.NUM), new ArrayList<Instruccion>()));
		pila.insertar("readTof", new Funcion("readTof", new ArrayList<Parametro>(), new TipoBasico(TipoDeTipo.TOF), new ArrayList<Instruccion>()));
		pila.insertar("readDec", new Funcion("readDec", new ArrayList<Parametro>(), new TipoBasico(TipoDeTipo.DEC), new ArrayList<Instruccion>()));		
		for (int i = 0; i < listaDeclaraciones.size(); i++){
			listaDeclaraciones.get(i).binding();
		}
		for (int i = 0; i < listaTypedefs.size(); i++){
			listaTypedefs.get(i).binding();
		}
		for (int i = 0; i < listaInstrucciones.size(); i++){
			listaInstrucciones.get(i).binding();
		}
		if (numStartPls == 0){
			System.out.println("ERROR no hay ningun startPls en PROGRAMA "); 
		}
		pila.print();
		pila.cerrarBloque();
	}

	public void checkType(){
		for (int i = 0; i < listaDeclaraciones.size(); i++){
			listaDeclaraciones.get(i).checkType();
		}
		for (int i = 0; i < listaTypedefs.size(); i++){
			listaTypedefs.get(i).checkType();
		}
		for (int i = 0; i < listaInstrucciones.size(); i++){
			listaInstrucciones.get(i).checkType();
		}
	}
	
	public String toString(){
		String instrucciones = "";
		for(int i = 0; i < listaInstrucciones.size(); i++){
			if (listaInstrucciones.get(i) == null){
				instrucciones += "null" + "\n";
			}
			else {
				instrucciones += listaInstrucciones.get(i).toString() + "\n";
			}
		}
		String declaraciones = "";
		for(int i = 0; i < listaDeclaraciones.size(); i++){
			if (listaDeclaraciones.get(i) == null){
				declaraciones += "null" + "\n";
			}
			else {
				declaraciones += listaDeclaraciones.get(i).toString() + "\n";
			}
		}
		String typedefs = "";
		for(int i = 0; i < listaTypedefs.size(); i++){
			if (listaTypedefs.get(i) == null){
				typedefs += "null" + "\n";
			}
			else {
				typedefs += listaTypedefs.get(i).toString() + "\n";
			}
		}
		return "PROGRAMA\nDECLARACIONES INICIALES\n" + declaraciones + "\nDECLARACIONES DE TYPEDEF\n"+typedefs+"\nINSTRUCCIONES\n" + instrucciones;
	}

	@Override
	public NodeKind nodeKind(){
		return NodeKind.PROGRAMA;
	}

	public static void insertar(String name, ASTNode node){
		pila.insertar(name, node);
	}

	public static void abrirBloque(){
		pila.abrirBloque();
	}

	public static void cerrarBloque(){
		pila.cerrarBloque();
	}

	public static ASTNode searchId(String name){
		return pila.searchId(name);
	}
	
	public static ASTNode searchIdLastFun(String name){
		return pila.searchIdLastFun(name);
	}

	public static void print(){
		pila.print();
	}	

	public static void printAll(){
		pila.printAll();
	}

	public int getFin(){
		return this.fin;
	}	

	public static void setFin(){
		fin = 1;
	}

	public static void addStartPls(){
		numStartPls += 1;
	}

	public String getNombre(){
		return "";
	}

	private void calcularDeltas() {
	
		for (Declaracion dec : listaDeclaraciones) {
			System.out.println("Decs");
			dec.setDelta();
		}
	
		for (Instruccion ins : listaInstrucciones) {
			if (ins instanceof Declaracion) {
				System.out.println(ins.toString());
				((Declaracion) ins).setDelta();
			}
		}
	}

	public void generaCodigo() {
		try {
			calcularDeltas();
			Programa.codigo = new PrintWriter(new FileWriter("codigo.wat"));
			FileReader inicio = new FileReader("generacion/inicio.wat");
			inicio.transferTo(Programa.codigo);

			Programa.codigo.println("\ti32.const " + maxMemory());
			Programa.codigo.println("\tcall $reserveStack");
			Programa.codigo.println("\tlocal.set $temp");
			Programa.codigo.println("\tglobal.get $MP");
			Programa.codigo.println("\tlocal.get $temp");
			Programa.codigo.println("\ti32.store");
			Programa.codigo.println("\tglobal.get $MP");
			Programa.codigo.println("\ti32.const 4");
			Programa.codigo.println("\ti32.add");
			Programa.codigo.println("\tlocal.set $localsStart\n");

			Instruccion insMain = null;

			for (Declaracion dec : listaDeclaraciones) {
				dec.generaCodigo();
				Programa.codigo.println();
			}

			for (Instruccion ins : listaInstrucciones) {
				if (ins instanceof Creacion || ins instanceof Inicializacion) {
					ins.generaCodigo();
				} else if (ins instanceof Funcion) {
					Funcion fun = (Funcion) ins;
					if ("startPls".equals(fun.getNombre())) {
						insMain = ins;
					}
				}
				Programa.codigo.println();
			}

			if (insMain == null) {
				throw new RuntimeException("No se encontró la función 'main'.");
			}

			Programa.codigo.println("\tcall $startPls");
			Programa.codigo.println("\tcall $printi32");
			Programa.codigo.println("\tcall $freeStack");
			Programa.codigo.println(")");

			insMain.generaCodigo();
			Programa.codigo.println();

			for (Instruccion ins : listaInstrucciones) {
    if (!(ins instanceof Creacion) && !(ins instanceof Inicializacion) && ins != insMain) {
        try {
            System.out.println("Generando código para clase: " + ins.getClass().getName());
            ins.generaCodigo();
            Programa.codigo.println();
        } catch (Exception e) {
            System.err.println("ERROR EN GENERACION CODIGO para clase: " + ins.getClass().getName());
            System.err.println("Error en generaCodigo: " + e.getMessage());
            e.printStackTrace(); 
            Programa.setFin();
        }
    }
}

			inicio = new FileReader("generacion/final.wat");
			Programa.codigo.println("\n\n\n ;;FUNCIONES DEFINIDAS");
			inicio.transferTo(Programa.codigo);
			inicio.close();
			Programa.codigo.close();
		} catch (Exception e) {
			System.err.println("Error en generaCodigo: " + e.getMessage());
			Programa.setFin();
		}
	}

	public void updateDelta(int tam){
		pila.updateDelta(tam);
	}

	public int maxMemory(){
		int max = 0;
		for(Instruccion ins : listaInstrucciones){
			if(ins instanceof Creacion || ins instanceof Registro || ins instanceof Entity){
				max += ins.getTamanyo();
			}
		}
		return max + 4;
	}

	public static int getSize(){
		return pila.getSize();
	}
}
