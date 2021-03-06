package estructuras;

/**
 * @param <T> es el objeto a guardar
 */
public class NodoArbol {
	private ModeloGenerico dato;
	private NodoArbol izq;
	private NodoArbol der;
	private int balance; // para rotacion AVL
	
	public NodoArbol(ModeloGenerico dato) {
		this.dato = dato;
		this.balance = 1;
		this.izq = null;
		this.der = null;
	}

	public ModeloGenerico getDato() {
		return dato;
	}

	public void setDato(ModeloGenerico dato) {
		this.dato = dato;
	}

	public NodoArbol getIzq() {
		return izq;
	}

	public void setIzq(NodoArbol izq) {
		this.izq = izq;
	}

	public NodoArbol getDer() {
		return der;
	}

	public void setDer(NodoArbol der) {
		this.der = der;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return "codigo: "+dato.getID()+
				"\nBalance: "+balance+
				"\nHijo derecho: "+ (der != null)+
				"\nHijo izquierdo: "+ (izq != null);
	}
}
