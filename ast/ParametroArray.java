package ast;

import ast.instrucciones.*;
import ast.expresiones.*;
import ast.tipo.*;

public class ParametroArray extends Parametro{

	public ParametroArray(TipoArray tipoArg, String nombreArg, Boolean referencia){
		super(tipoArg, nombreArg, referencia);
		
	}
}