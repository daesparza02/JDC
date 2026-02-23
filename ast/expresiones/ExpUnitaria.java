package ast.expresiones;
import ast.instrucciones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;
import ast.KindAsig;


public class ExpUnitaria extends Expresion {
	private Expresion exp1;
	private KindAsig kind;


	public ExpUnitaria(Expresion exp1, KindAsig kind){
		this.exp1 = exp1;
		this.kind = kind;
	}

	@Override
	public String toString(){
		return "(" + kind.toString() + " (" + exp1.toString() + "))";
	}

	@Override
	public void binding(){
		exp1.binding();
	}

	@Override
	public void checkType(){
		exp1.checkType();
		Tipo t = exp1.getTipo();
		switch(kind){				
			case PUNTERO: 	
				if (t == null){
					System.out.println("ERROR: fallo en puntero ExpUnitaria" + this);
					Programa.setFin();
				}
				else{
					if (!(t instanceof TipoPuntero)){
						System.out.println("ERROR: fallo en tipo ExpUnitaria" + this);
						Programa.setFin();
					}	
					else{
						TipoPuntero tipoP = (TipoPuntero) t;
						setTipo(tipoP.getTipoBasico());
					}
				}
				break;
			case INTERROGACION: 		
				if (t == null){
					System.out.println("ERROR: fallo en puntero ExpUnitaria" + this);
					Programa.setFin();
				}
				else{
					setTipo(new TipoPuntero(t));	
				}

				break;


			case NOT:
				if (!(t.equals("TOF"))){
					System.out.println("ERROR: fallo en tipo ExpUnitaria" + this);
					Programa.setFin();
				}
				else{
					setTipo(exp1.getTipo());
				}
				break;

			case PARENTESIS:
				setTipo(t);
				break;

			default:
				System.out.println("DEFAULT DE ExpUnitaria " + kind);
				break;
			
		}


	}

	public void generaCodigo(){
		switch (kind) {
	        case NOT:
        	 	exp1.generaCodigo();
				Programa.codigo.println("\ti32.load");
          		Programa.codigo.println("\ti32.eqz");
				Programa.codigo.println("\tif (result i32)");
				Programa.codigo.println("\ti32.const 1");
				Programa.codigo.println("\telse");
				Programa.codigo.println("\ti32.const 0");
				Programa.codigo.println("\tend");
         		break;
			case INTERROGACION:
				exp1.calcularDirRelativa();
				break;
			case PARENTESIS:
				exp1.generaCodigo();
				break;
			default:
		}
    }

	public void calcularDirRelativa(){
		switch (kind) {
	        case NOT:
        	 	exp1.calcularDirRelativa();
         		break;
			case INTERROGACION:
				break;
			case PARENTESIS:
				exp1.calcularDirRelativa();
				break;
				default:
		}
    }

}