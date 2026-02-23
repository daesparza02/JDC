
import java.util.List;
import ast.expresiones.*;
import ast.instrucciones.declaraciones.Creacion;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;
import java.util.ArrayList;
import java.util.List;

public class Array extends Creacion{
    
    private String nombre;
    private Tipo tipoBasico;
    private List<Num> tams;

    public Array(String nombre, Tipo tipoBasico, List<Num> tams){
        this.nombre=nombre;
        this.tipoBasico = tipoBasico;
        this.tams = tams;
    }

    @Override
	public String toString(){
		return "(ARRAY, [" + nombreReg + ", " + camposReg.toString() + "])";
	}

    @Override
	public void binding(){
		ASTNode node = Programa.searchId(nombreReg);
		if (node == null){
			Programa.insertar(nombreReg, this);
			tipoBasico.binding();
            for(Num t: tams) t.binding();			
		}
		else{
			System.out.println("ERROR: identificador en Array " + nombreReg + " no se puede utilizar en " + this);
			Programa.setFin();
		}
	} 
    @Override
	public void checkType(){
        tipoBasico.checkType();
		 for(Num t: tams){
			 t.checkType();
		  }
		  setTipo(new TipoArray(tipoBasico, tams));
	}

	@Override
	public void generaCodigo() {
		Tipo t = getTipo();

		if (!(t instanceof TipoArray)) return;

		TipoArray tipoArray = (TipoArray) t;
		Tipo tipoElem = tipoArray.getTipoBase();

		int numElems = tipoArray.getNumElems();

		if (tipoElem instanceof TipoRegistro) {
			for (int i = 0; i < numElems; i++) {
				calcularDirRelativa();
				Programa.codigo.println("\ti32.const " + tipoElem.getTam() * i);
				Programa.codigo.println("\ti32.add");
				Programa.codigo.println("\ti32.const " + tipoElem.getTam() / 4);
				Programa.codigo.println("\tcall $zeron");
			}
		} 
	}

}
