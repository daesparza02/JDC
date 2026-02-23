package alex;

import asint.ClaseLexica;


public class ALexOperations {
  private AnalizadorLexicoTiny alex;

  public ALexOperations(AnalizadorLexicoTiny alex) {
    this.alex = alex;
  }


  public UnidadLexica unidadIden() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IDEN, alex.lexema());
  }

  public UnidadLexica unidadNumEntero() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NUMERO_ENTERO, alex.lexema());
  }

  public UnidadLexica unidadNumDecimal() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NUMERO_DECIMAL, alex.lexema());
  }

  public UnidadLexica unidadNum() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NUM, alex.lexema());
  }

  public UnidadLexica unidadDec() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DEC, alex.lexema());
  }

  public UnidadLexica unidadSuma() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.SUMA);
  }

  public UnidadLexica unidadResta() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.RESTA);
  }

  public UnidadLexica unidadMul() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MULTIPLICACION);
  }

  public UnidadLexica unidadDiv() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DIVISION);
  }

  public UnidadLexica unidadMod() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MODULO);
  }

  public UnidadLexica unidadIgual() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IGUAL);
  }

  public UnidadLexica unidadDiferente() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DIFERENTE);
  }

  public UnidadLexica unidadMenorIgual() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENOR_IGUAL);
  }

  public UnidadLexica unidadMayorIgual() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAYOR_IGUAL);
  }

  public UnidadLexica unidadMenor() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENOR);
  }

  public UnidadLexica unidadMayor() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAYOR);
  }

  public UnidadLexica unidadAnd() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.AND);
  }

  public UnidadLexica unidadOr() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OR);
  }

  public UnidadLexica unidadNot() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NOT);
  }

  public UnidadLexica unidadAsignacion() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ASIGNACION);
  }

  public UnidadLexica unidadPuntero() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PUNTERO);
  }

  public UnidadLexica unidadAccesocampo() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ACCESOCAMPO);
  }

  public UnidadLexica unidadLlaveApertura() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LLAVE_APERTURA);
  }

  public UnidadLexica unidadLlaveCierre() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LLAVE_CIERRE);
  }

  public UnidadLexica unidadPApertura() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PARENTESIS_APERTURA);
  }

  public UnidadLexica unidadPCierre() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PARENTESIS_CIERRE);
  }

  public UnidadLexica unidadCApertura() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CORCHETE_APERTURA);
  }

  public UnidadLexica unidadCCierre() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CORCHETE_CIERRE);
  }

  public UnidadLexica unidadPunto() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PUNTO);
  }

  public UnidadLexica unidadPuntocoma() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PUNTOCOMA);
  }

  public UnidadLexica unidadComa() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.COMA);
  }

  public UnidadLexica unidadDospuntosigual() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DOSPUNTOSIGUAL);
  }

  public UnidadLexica unidadTru() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.TRU);
  }

  public UnidadLexica unidadFols() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FOLS);
  }

  public UnidadLexica unidadFinal() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FINAL);
  }

 

  public UnidadLexica unidadDev() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DEV);
  }

  public UnidadLexica unidadRet() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.RET);
  }

  public UnidadLexica unidadCheck() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CHECK);
  }

  public UnidadLexica unidadOtherCheck() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OTHER_CHECK);
  }

  public UnidadLexica unidadOther() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OTHER);
  }

  public UnidadLexica unidadCycle() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CYCLE);
  }

  public UnidadLexica unidadDuring() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DURING);
  }

  public UnidadLexica unidadEntity() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ENTITY);
  }

  public UnidadLexica unidadRef() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.REF);
  }

  public UnidadLexica unidadBook() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.BOOK);
  }

  public UnidadLexica unidadUnbook() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.UNBOOK);
  }

  public UnidadLexica unidadNothing() {
	    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NOTHING);
  }
  
  public UnidadLexica unidadStartPls() {
	    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.STARTPLS);
  }
  
  public UnidadLexica unidadReadNum() {
	    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.READNUM);
  }

  public UnidadLexica unidadPrintNum() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PRINTNUM);
  }

  public UnidadLexica unidadReadDec() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.READDEC);
  }

  public UnidadLexica unidadPrintDec() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PRINTDEC);
  }

  public UnidadLexica unidadReadTof() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.READTOF);
  }

  public UnidadLexica unidadPrintTof() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PRINTTOF);
  }

  public UnidadLexica unidadToDec() {
	    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.TODEC);
  }
  
  public UnidadLexica unidadToNum() {
	    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.TONUM);
  }
  
  
  public UnidadLexica unidadArray() {
	    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ARRAY);
  }
  public UnidadLexica unidadReg() {
	    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.REG);
  }
  public UnidadLexica unidadTypedef() {
	    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.TYPEDEF);
  }
  
  public UnidadLexica unidadConstructor() {
	    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CONSTRUCTOR);
  }

  public UnidadLexica unidadTof() {
	    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.TOF);
  }

  public UnidadLexica unidadFun() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FUN);
  }

  public UnidadLexica unidadEof() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.EOF);
  }

  public void error() {
    System.err.println("***" + alex.fila() + ", " + alex.columna() + " Caracter inesperado: " + alex.lexema());
  }
}
