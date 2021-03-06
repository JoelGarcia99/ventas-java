package estructuras;

/**
 * @param <T> es el objeto a guardar
 */
public class NodoProducto {
	private ModeloGenerico dato;
	private NodoProducto izq;
	private NodoProducto der;
	private int balance; // para rotacion AVL
	
	public NodoProducto(ModeloGenerico dato) {
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

	public NodoProducto getIzq() {
		return izq;
	}

	public void setIzq(NodoProducto izq) {
		this.izq = izq;
	}

	public NodoProducto getDer() {
		return der;
	}

	public void setDer(NodoProducto der) {
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
