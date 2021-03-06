package estructuras;

public class NodoLista {
	private NodoLista atras;
	private NodoLista siguiente;
	private ModeloGenerico dato;
	
	public NodoLista(ModeloGenerico dato) {
		this.atras = null;
		this.siguiente = null;
		this.dato = dato;		
	}

	public NodoLista getAtras() {
		return atras;
	}

	public void setAtras(NodoLista atras) {
		this.atras = atras;
	}

	public NodoLista getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(NodoLista siguiente) {
		this.siguiente = siguiente;
	}

	public ModeloGenerico getDato() {
		return dato;
	}
	
	
}
