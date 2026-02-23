package ast.expresiones;
import ast.instrucciones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;
import ast.Parametro;

public class Unitario extends Expresion{
	
    private String iden;
	private Call call;
	
	public Unitario(){
	}

	public Unitario(String iden){
		this.iden = iden;
		this.modificable=true;
	}

	public Unitario(Call call){
		this.iden = call.toString();
		this.call = call;
		this.modificable=false;
	}

	public String toString(){
		return iden;
	}

	@Override
	public void binding(){
		if (call!= null){
			call.binding();
			this.link = call.getLink();
		}
		else{
			ASTNode node = Programa.searchId(iden);
			if (node != null){
				this.link = node;
			}
			else{	
				System.out.println("ERROR: identificador en Unitario " + iden + " no se puede utilizar en " + this);
				Programa.setFin();
			}

		}
	}
	
	@Override
	public void checkType(){
		if (this.link != null){
			setTipo(this.link.getTipo());
			System.out.println("Tipo de  "+iden+" es "+this.link.getTipo());
		}
		if (call != null){
			call.checkType();
		}	

	}

	@Override
	public String getName(){
		return iden;
	}
	@Override
public void generaCodigo() {
    if (call != null) {
        call.generaCodigo(); 

        if (!call.getAsigned()) {
            Programa.codigo.println("\tdrop");
        }

    } else {
        this.calcularDirRelativa();
        Programa.codigo.println("\t" + this.getTipo().convertWasm() + ".load");
    }
}



	public void calcularDirRelativa(){
		if (this.link == null) {
			System.err.println("ERROR: link no resuelto en Unitario para: " + this);
			Programa.setFin();
			return;
		}
	
		int dir = this.link.getDelta();
		if (this.link.getGlobal()){	
			dir += 4;
			Programa.codigo.println("\ti32.const " + dir);
		}
		else{
			if (this.link instanceof Parametro){
				Parametro arg = (Parametro) this.link;
				if (arg.getRef()){
					Programa.codigo.println("\ti32.const " + dir);
					Programa.codigo.println("\tlocal.get $localsStart");
					Programa.codigo.println("\ti32.add");
					Programa.codigo.println("\ti32.load");

				}
				else{
					Programa.codigo.println("\ti32.const " + dir);
					Programa.codigo.println("\tlocal.get $localsStart");
        				Programa.codigo.println("\ti32.add");
				}

			}
			else{
				Programa.codigo.println("\ti32.const " + dir);
				Programa.codigo.println("\tlocal.get $localsStart");
        			Programa.codigo.println("\ti32.add");
			}

		}
		
	}

	public Call getCall(){
		return call;
	}

	public void setAsigned(){
		if(call!=null) call.setAsigned();
	}

	public String getIden(){
		return iden;
	}

	@Override
	public int getTamanyo(){
		if(call!= null) return call.getTamanyo();
		else return this.link.getTamanyo();
	}
}