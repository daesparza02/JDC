package ast.expresiones;
import ast.instrucciones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;
import ast.KindAsig;
import ast.instrucciones.declaraciones.*;

public class ExpBinaria extends Expresion {
	private Expresion exp1;
	private Instruccion exp2;
	private KindAsig kind;


	public ExpBinaria(Expresion exp1, Expresion exp2, KindAsig kind){
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.kind = kind;
		if(kind.equals(KindAsig.ACCESO)) this.modificable=true;
		else this.modificable=false;
	}

	@Override
	public String toString(){
		return "(" + kind.toString() + " (" + exp1.toString() + ", " + exp2.toString() + "))"; 
	}

	@Override
	public void binding() {
	exp1.binding();

	if (!kind.equals(KindAsig.ACCESO)) {
		exp2.binding();
		return;
	}

	Tipo tipoIzq = exp1.getTipo();
	if (tipoIzq == null || !(tipoIzq instanceof TipoPersonalizado)) {
		System.out.println("ERROR: acceso sobre tipo no válido o no enlazado en: " + this);
		Programa.setFin();
		return;
	}

	ASTNode defEntidad = tipoIzq.getLink();
	if (defEntidad == null || !(defEntidad.getTipo() instanceof TipoEntity)) {
		System.out.println("ERROR: tipo personalizado no enlaza a entidad válida: " + this);
		Programa.setFin();
		return;
	}

	TipoEntity tipoEntidad = (TipoEntity) defEntidad.getTipo();

	if (exp2 instanceof Unitario) {
		Unitario u = (Unitario) exp2;
		String nombre = u.getIden();
		Call llamada = u.getCall();

		if (llamada != null) {
			if (tipoEntidad.tieneFuncion(llamada.getNombre())) {
				Funcion f = tipoEntidad.getFuncion(llamada.getNombre());
				llamada.setLink(f);
				llamada.setObjeto(exp1); 
				llamada.binding();        
			} else {
				System.out.println("ERROR: función '" + llamada.getNombre() + "' no existe en entidad");
				Programa.setFin();
			}
		} else if (nombre != null) {
			// Enlace a un campo
			if (tipoEntidad.tieneCampo(nombre)) {
				u.setLink(tipoEntidad.getCampo(nombre));
			} else if (tipoEntidad.tieneFuncion(nombre)) {
				u.setLink(tipoEntidad.getFuncion(nombre));
			} else {
				System.out.println("ERROR: campo o función '" + nombre + "' no existe en entidad");
				Programa.setFin();
			}
		} else {
			System.out.println("ERROR: Unitario sin identificador ni call");
			Programa.setFin();
		}
	} else {
		System.out.println("ERROR: exp2 no es un Unitario en binding() de ExpBinaria: " + this);
		Programa.setFin();
	}
	}

	@Override
	public void checkType() {
		exp1.checkType();

		if (!kind.equals(KindAsig.ACCESO)) {
			exp2.checkType();
			setTipo(exp2.getTipo());
		} else {
			Tipo t = exp1.getTipo();
			if (t == null) {
				System.out.println("ERROR: tipo null en ExpBinaria (lado izquierdo): " + this);
				Programa.setFin();
				return;
			}

			if (t instanceof TipoPersonalizado) {
				ASTNode entidad = t.getLink();
				if (entidad == null) {
					System.out.println("ERROR: tipo personalizado no enlazado: " + this);
					Programa.setFin();
					return;
				}

				Tipo tipoReal = entidad.getTipo();
				if (!(tipoReal instanceof TipoEntity)) {
					System.out.println("ERROR: se esperaba tipo TipoEntity en ExpBinaria: " + this);
					Programa.setFin();
					return;
				}

				TipoEntity tipoEntidad = (TipoEntity) tipoReal;

				if (exp2 instanceof Unitario) {
					Call llamada = ((Unitario) exp2).getCall();
					String nombreCampo = ((Unitario) exp2).getIden();

					if (llamada!=null) {
						llamada.setObjeto(exp1); 
						llamada.checkType();
						setTipo(llamada.getTipo());
						return;
					} else if(nombreCampo!=null){
						if (tipoEntidad.tieneCampo(nombreCampo)) {
							Creacion campo = tipoEntidad.getCampo(nombreCampo);
							setTipo(campo.getTipo());
							return;
						} else if (tipoEntidad.tieneFuncion(nombreCampo)) {
							Funcion f = tipoEntidad.getFuncion(nombreCampo);
							setTipo(f.getTipo());
							return;
						} else {
							System.out.println("ERROR: campo o función no encontrado: " + nombreCampo);
							Programa.setFin();
							return;
						}
					} else {
						System.out.println("ERROR: contenido de Unitario no esperado: " + this);
						Programa.setFin();
						return;
					}
				} else {
					System.out.println("ERROR: exp2 no es un Unitario en ACCESO: " + this);
					Programa.setFin();
					return;
				}
			} else {
				System.out.println("ERROR: tipo izquierdo no es TipoPersonalizado en ACCESO: " + this);
				Programa.setFin();
				return;
			}
		}

		switch (kind) {
			case POR: case MAS: case MENOS: case DIV:
				if (!exp1.getTipo().equals(exp2.getTipo()) ||
					!(exp1.getTipo().equals("NUM") || exp1.getTipo().equals("DEC"))) {
					System.out.println("ERROR: tipos incompatibles en operación aritmética: " + this);
					Programa.setFin();
				}
				break;

			case MOD:
				if (!exp1.getTipo().equals(exp2.getTipo()) || !exp1.getTipo().equals("NUM")) {
					System.out.println("ERROR: MOD solo admite tipo NUM: " + this);
					Programa.setFin();
				}
				break;

			case MAYOR_IGUAL: case MENOR_IGUAL: case MAYOR: case MENOR:
				if (!exp1.getTipo().equals(exp2.getTipo()) || exp1.getTipo().equals("TOF")) {
					System.out.println("ERROR: comparación no válida: " + this);
					Programa.setFin();
				}
				setTipo(new TipoBasico(TipoDeTipo.TOF));
				break;

			case EQ: case NEQ:
				if (!exp1.getTipo().equals(exp2.getTipo())) {
					System.out.println("ERROR: igualdad entre tipos distintos: " + this);
					Programa.setFin();
				}
				setTipo(new TipoBasico(TipoDeTipo.TOF));
				break;

			case AND: case OR:
				if (!exp1.getTipo().equals(exp2.getTipo()) || !exp1.getTipo().equals("TOF")) {
					System.out.println("ERROR: operación lógica no válida: " + this);
					Programa.setFin();
				}
				setTipo(new TipoBasico(TipoDeTipo.TOF));
				break;

			default:
				break;
		}
	}


	public void generaCodigo() {
		exp1.generaCodigo();
		exp2.generaCodigo();
		switch (kind) {
		  case MAS:
			Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".add");
			break;
		  case MENOS:
			Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".sub");
			break;
		  case POR:
			Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".mul");
			break;
		  case DIV:
			if(exp1.getTipo().equals(new TipoBasico(TipoDeTipo.DEC))){
				Programa.codigo.println("\tf32.div");
			}
			else Programa.codigo.println("\ti32.div_s");
			/*if (exp1.getTipo().equals(new TipoBasico(TipoDeTipo.DEC))) {
				// verificar divisor ≠ 0 para f32
				Programa.codigo.println("\tlocal.get $divisor");
				Programa.codigo.println("\tf32.const 0.0");
				Programa.codigo.println("\tf32.eq");
				Programa.codigo.println("\tif");
				Programa.codigo.println("\t\ti32.const 3");
				Programa.codigo.println("\t\tcall $exception");
				Programa.codigo.println("\tend");

				Programa.codigo.println("\tlocal.get $dividendo");
				Programa.codigo.println("\tlocal.get $divisor");
				Programa.codigo.println("\tf32.div");
			} else {
				// verificar divisor ≠ 0 para i32
				Programa.codigo.println("\tlocal.get $divisor");
				Programa.codigo.println("\ti32.eqz");
				Programa.codigo.println("\tif");
				Programa.codigo.println("\t\ti32.const 3");
				Programa.codigo.println("\t\tcall $exception");
				Programa.codigo.println("\tend");

				Programa.codigo.println("\tlocal.get $dividendo");
				Programa.codigo.println("\tlocal.get $divisor");
				Programa.codigo.println("\ti32.div_s");
			}*/

			break;
		  case MOD:
			Programa.codigo.println("\ti32.rem_s");
			break;
		  case MENOR:
			if (exp1.getTipo().equals(new TipoBasico(TipoDeTipo.DEC))) Programa.codigo.println("\tf32.lt");
			else Programa.codigo.println("\ti32.lt_s");
			break;
		  case MAYOR:
			if (exp1.getTipo().equals(new TipoBasico(TipoDeTipo.DEC))) Programa.codigo.println("\tf32.gt");
			else Programa.codigo.println("\ti32.gt_s");
			break;
		  case AND:
			Programa.codigo.println("\ti32.and");
			break;
		  case OR:
			Programa.codigo.println("\ti32.or");
			break;
		  case EQ:
			Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".eq");
			break;
		  case NEQ:
			Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".ne");
			break;
		  case MAYOR_IGUAL:
			if (exp1.getTipo().equals(new TipoBasico(TipoDeTipo.DEC))) Programa.codigo.println("\tf32.ge");
			else Programa.codigo.println("\ti32.ge_s");
			break;
		  case MENOR_IGUAL:
			if (exp1.getTipo().equals(new TipoBasico(TipoDeTipo.DEC))) Programa.codigo.println("\tf32.le");
			else Programa.codigo.println("\ti32.le_s");
			break;
		  default:
		}
	  }

}