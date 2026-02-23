package ast;

import java.util.Stack;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


public class Pila {
    
    private int delta;
    private List<HashMap<String,ASTNode>> pila;

    public Pila() {
        this.delta = 0;
        this.pila = new ArrayList<HashMap<String,ASTNode>>();
    }


    public void insertar (String id, ASTNode puntero){
        if(pila.size() != 0){
	    System.out.println("SE INSERTA " + puntero);
            HashMap<String,ASTNode> top = pila.get(pila.size() - 1);
            top.put(id, puntero);
        }
    }
    
    public void abrirBloque(){
        pila.add(new HashMap<String,ASTNode>());
    }

    public void cerrarBloque(){
        if (pila.size() != 0){ 
            pila.remove(pila.size()-1); 
        }
    }


    public ASTNode searchId (String id){
        ASTNode node = null;
	if(pila.size() != 0){
            HashMap<String,ASTNode> last = pila.get(pila.size()-1);
            node = last.get(id);
        }
	for (int i = pila.size() - 2; i >= 0 && node == null; i--){
   	    HashMap<String,ASTNode> last = pila.get(i);
            node = last.get(id);
	} 
        return node;
    }

    public ASTNode searchIdLastFun(String id){
	ASTNode node = null;
	if(pila.size() != 0){
            HashMap<String,ASTNode> last = pila.get(pila.size()-1);
            node = last.get(id);
        }
	return node;
   }

    public int getSize(){
    return pila.size();
    }

    public int getDelta(){
        return delta;
    }

    public void updateDelta(int size) {
        System.out.println("Actualizando delta global: +"+size);

        delta += size;
    }
    public void reset(){
        delta=0;
    }
    private void printAmbito(HashMap<String,ASTNode> ambito){
        for (Map.Entry<String, ASTNode> entry : ambito.entrySet()) {
            String key = entry.getKey();
            ASTNode value = entry.getValue();
            System.out.println(key + " -> " + value);
        }
    }

    public void print(){
	if (pila.size() != 0){
		System.out.println("INICIO AMBITO");
		HashMap<String,ASTNode> last = pila.get(pila.size() - 1);
		printAmbito(last);
		System.out.println("FIN AMBITO");

	}
        else {
		System.out.println("Pila Vacia");
	}
   }

   public void printAll(){
        for(int i = 0; i < pila.size(); i++){
            System.out.println("INICIO AMBITO");
            HashMap<String,ASTNode> last = pila.get(i);
            printAmbito(last);
            System.out.println("FIN AMBITO");

        }
    }

    public int usarEspacio(int tam) {
        int actual = delta;
        delta += tam;
        return actual;
    }

    public void reset(int desde) {
        delta = desde;
    }
        
	
}