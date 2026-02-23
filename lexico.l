package alex;

import errors.GestionErroresTiny;

%%
%cup
%line
%column
%class AnalizadorLexicoTiny
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErroresTiny errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErroresTiny errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

separador = [ \t\r\b\n]
letra  = ([A-Z]|[a-z])
digito = [0-9]
comentario = \#[^\n]*
comentarioMulti = {operadorDivision}{operadorMultiplicacion}~{operadorMultiplicacion}{operadorDivision}
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
identificador = {letra}({letra}|{digito})*
numeroEntero = [\+\-]?{digito}+
parteEntera={digito}+
parteDecimal={digito}+
numeroDecimal = [\+\-]?{parteEntera}+{coma}{parteDecimal}+

operadorSuma = \+
operadorResta = \-
operadorMultiplicacion = \*
operadorDivision = \/
modulo = \%
igual = eq
diferente = neq
menorIgual = \<\=
mayorIgual = \>\=
menor = \<
mayor = \>
and = and
or = or
not = not
asignacion= {menor}{operadorResta}
puntero = {operadorResta}{mayor}
accesocampo = {operadorResta}{operadorResta}{mayor}

llaveApertura = \{
llaveCierre = \}
parentesisApertura = \(
parentesisCierre = \)
corcheteApertura = \[
corcheteCierre = \]
punto = \.
coma = \,
apostrofe = '
puntocoma = ;
dospuntosigual = :\=

tof = tof
fun = fun
tru = tru
fols = fols
final = FINAL
dev = dev
ret = ret
check = check
otherCheck = othercheck
other = other
cycle = cycle
during = during
entity = entity
ref = ref
book = book
unbook = unbook
num = num
dec = dec
array = array
reg = reg

nothing = nothing
startPls = startPls
readNum = readNum
printNum = printNum
readDec = readDec
printDec = printDec
readTof = readTof
printTof = printTof
toDec = toDec
toNum = toNum
typedef = typedef
constructor = constructor


digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
identificador = {letra}({letra}|{digito}|\_)*
numeroEntero = [\+\-]?{digito}+
parteEntera={digito}+
parteDecimal={digito}+
numeroDecimal = [\+\-]?{parteEntera}+{apostrofe}{parteDecimal}+



%%
{separador}		{}
{comentario}             {}
{comentarioMulti}        {}

{operadorSuma}           { return ops.unidadSuma(); }
{operadorResta}          { return ops.unidadResta(); }
{operadorMultiplicacion} { return ops.unidadMul(); }
{operadorDivision}       { return ops.unidadDiv(); }
{modulo}                 { return ops.unidadMod(); }
{igual}                  { return ops.unidadIgual(); }
{diferente}              { return ops.unidadDiferente(); }
{menorIgual}             { return ops.unidadMenorIgual(); }
{mayorIgual}             { return ops.unidadMayorIgual(); }
{menor}                  { return ops.unidadMenor(); }
{mayor}                  { return ops.unidadMayor(); }
{and}                    { return ops.unidadAnd(); }
{or}                     { return ops.unidadOr(); }
{not}                    { return ops.unidadNot(); }
{asignacion}             { return ops.unidadAsignacion(); }
{puntero}                { return ops.unidadPuntero(); }
{accesocampo}		 { return ops.unidadAccesocampo(); }

{llaveApertura}          { return ops.unidadLlaveApertura(); }
{llaveCierre}            { return ops.unidadLlaveCierre(); }
{parentesisApertura}     { return ops.unidadPApertura(); }
{parentesisCierre}       { return ops.unidadPCierre(); }
{corcheteApertura}       { return ops.unidadCApertura(); }
{corcheteCierre}         { return ops.unidadCCierre(); }
{punto}                  { return ops.unidadPunto(); }
{puntocoma}                  { return ops.unidadPuntocoma(); }
{coma}                   { return ops.unidadComa(); }
{dospuntosigual}	 { return ops.unidadDospuntosigual(); }

{fun}           { return ops.unidadFun(); }

{tru}           { return ops.unidadTru(); }
{fols}          { return ops.unidadFols(); }
{final}         { return ops.unidadFinal(); }

{dev}           { return ops.unidadDev(); }
{ret}           { return ops.unidadRet(); }
{check}         { return ops.unidadCheck(); }
{otherCheck}    { return ops.unidadOtherCheck(); }
{other}         { return ops.unidadOther(); }
{cycle}         { return ops.unidadCycle(); }
{during}        { return ops.unidadDuring(); }
{entity}        { return ops.unidadEntity(); }
{ref}           { return ops.unidadRef(); }
{book}          { return ops.unidadBook(); }
{unbook}        { return ops.unidadUnbook(); }


{nothing}           { return ops.unidadNothing(); }
{startPls}           { return ops.unidadStartPls(); }
{readNum}         { return ops.unidadReadNum(); }
{printNum}    { return ops.unidadPrintNum(); }
{readDec}         { return ops.unidadReadDec(); }
{printDec}         { return ops.unidadPrintDec(); }
{readTof}        { return ops.unidadReadTof(); }
{printTof}        { return ops.unidadPrintTof(); }
{toDec}           { return ops.unidadToDec(); }
{toNum}          { return ops.unidadToNum(); }
{typedef}        { return ops.unidadTypedef(); }
{constructor}	 { return ops.unidadConstructor(); }
{num}          { return ops.unidadNum(); }
{dec}          { return ops.unidadDec(); }
{tof}          { return ops.unidadTof(); }
{array}          { return ops.unidadArray(); }
{reg}          { return ops.unidadReg(); }

{identificador}          { return ops.unidadIden(); }
{numeroEntero}           { return ops.unidadNumEntero(); }
{numeroDecimal}          { return ops.unidadNumDecimal(); }

[^]                       {errores.errorLexico(fila(),columna(),lexema());}  
