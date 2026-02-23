package asint;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import alex.*;

import ast.instrucciones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;
import ast.expresiones.*;

import asint.*;

import ast.Programa;

public class Main {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(args[0]));
	 AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
	 ConstructorASTExp constructorast = new ConstructorASTExp(alex);
	 try{
		Programa p = (Programa) constructorast.parse().value;
		System.out.println("\n\n----------AST----------\n");		
		System.out.println(p);
		System.out.println("\n\n----------BINDING----------\n");
		p.binding();
		if (p.getFin() == 1){
			System.out.println("ERROR EN BINDING.");
			System.exit(1);
		}

		System.out.println("\n\n----------TYPING----------\n");
		p.checkType();
		if (p.getFin() == 1){
			System.out.println("ERROR EN TIPADO.");
			System.exit(1);
		}
		System.out.println("\n\n----------CODE GENERATION----------\n");
		p.generaCodigo();
		if (p.getFin() == 1){
			System.out.println("ERROR EN GENERACION CODIGO");
			System.exit(1);
		}
	 }catch(Exception e){
		System.out.println("Error while parsing..." + e.getMessage());
		e.printStackTrace(System.out);
	 }
  }
}   
   