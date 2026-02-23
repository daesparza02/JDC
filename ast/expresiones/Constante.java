package ast.expresiones;

import java.util.Arrays;
import java.util.List;
import ast.instrucciones.*;
import ast.tipo.*;
import ast.ASTNode;
import ast.NodeKind;

public abstract class Constante extends Expresion {
    public abstract void binding();
    public abstract void checkType();
}