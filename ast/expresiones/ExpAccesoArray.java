package ast.expresiones;

import ast.expresiones.*;
import ast.instrucciones.*;
import ast.instrucciones.declaraciones.*;
import ast.tipo.*;

import java.util.ArrayList;
import java.util.List;

import ast.NodeKind;
import ast.Programa;

public class ExpAccesoArray extends Expresion {
    private Expresion array;
    private Expresion indice;

    public ExpAccesoArray(Expresion array, Expresion indice) {
        this.array = array;
        this.indice = indice;
    }

    public Expresion getArray() {
        return array;
    }

    public Expresion getIndice() {
        return indice;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.EXPRESION_ACCESO_ARRAY;
    }

    @Override
    public String toString() {
        return "(" + array.toString() + "[" + indice.toString() + "])";
    }

    @Override
    public void binding() {
        array.binding();
        this.link = array;
        indice.binding();
    }

    @Override
    public void checkType() {
        array.checkType();
        indice.checkType();

        if (!(indice.getTipo() instanceof TipoBasico)) {
            System.out.println("ERROR: fallo en puntero ExpAccesoArray " + this);
            Programa.setFin();
        }

        Tipo type = getTipoArray();

        if (!(type instanceof TipoArray)) {
            System.out.println("ERROR: fallo en tipo ExpAccesoArray " + this);
            Programa.setFin();
        } else {
            TipoArray tipoA = (TipoArray) type;
            System.out.println(tipoA.getTipoBasico());
            int dimension = tipoA.getDim();
            int prof = profundidad(1, this);
            System.out.println("Dimension " + dimension + " profundidad " + prof);
            System.out.println("El tipo de " + array.toString() + " es " + array.getTipo());

            if (prof > dimension) {
                System.out.println("ERROR: fallo en tipo ExpAccesoArray " + this);
                Programa.setFin();
            } else if (prof == dimension) {
                System.out.println("Caso iguales " + tipoA.getTipoBasico());
                setTipo(tipoA.getTipoBasico());
            } else {
                List<Num> tams = tipoA.getTams();
                int i = dimension - 1;
                List<Num> aux = new ArrayList<Num>();
                while (i >= prof) {
                    aux.add(0, tams.get(i));
                    i--;
                }
                setTipo(new TipoArray(tipoA.getTipoBasico(), aux));
            }
        }
    }

    private Tipo getTipoArray() {
        Tipo t = array.getTipo();
        if (array instanceof ExpAccesoArray) {
            ExpAccesoArray array2 = (ExpAccesoArray) array;
            t = array2.getTipoArray();
        }
        return t;
    }

    private int profundidad(int n, ExpAccesoArray a) {
        if (a.array instanceof ExpAccesoArray)
            return profundidad(n + 1, (ExpAccesoArray) a.array);
        else
            return n;
    }

    public void comprobarRangos() {
        Tipo tipoArray = getTipoArray();
        if (tipoArray instanceof TipoArray) {
            TipoArray ta = (TipoArray) tipoArray;
            int dimensionActual = profundidad(1, this) - 1;
            int max = ta.getTams().get(dimensionActual).getInt();

            indice.generaCodigo();
            Programa.codigo.println("\ti32.const 0");
            Programa.codigo.println("\ti32.lt_s");
            Programa.codigo.println("\tif");
            Programa.codigo.println("\t\tcall $error_rango");
            Programa.codigo.println("\tend");

            indice.generaCodigo();
            Programa.codigo.println("\ti32.const " + max);
            Programa.codigo.println("\ti32.ge_u");
            Programa.codigo.println("\tif");
            Programa.codigo.println("\t\tcall $error_rango");
            Programa.codigo.println("\tend");
        }
    }

    public void calcularDirRelativa() {
        array.generaCodigo();
        comprobarRangos();
        indice.generaCodigo();
        Programa.codigo.println("\ti32.const 4");
        Programa.codigo.println("\ti32.mul");
        Programa.codigo.println("\ti32.add");
    }

    @Override
    public void generaCodigo() {
        this.calcularDirRelativa();
        if (!this.getModificable()) {
            Programa.codigo.println("\t" + this.getTipo().convertWasm() + ".load");
        }
    }
}
