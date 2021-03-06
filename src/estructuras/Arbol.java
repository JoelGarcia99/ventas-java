package estructuras;

public abstract class Arbol {
	protected NodoArbol root;
	protected long size;
	
	public Arbol() {
		this.root = null;
		this.size = 0;
	}
	
	public void insertar(ModeloGenerico x) {
		NodoArbol producto = new NodoArbol(x);
		
		if(existe(x.getID())) {
			System.err.println("El codigo de producto "+x.getID()+" ya existe");
			return;
		}

		if(this.root == null) {
			this.root = producto;
		}else {
			this.root = insertar(producto, this.root);
		}
		++this.size;
	}
	
	protected abstract NodoArbol insertar(NodoArbol producto, NodoArbol root);
	
	public ModeloGenerico buscar(String codigo) {
		
		NodoArbol hallado = buscar(codigo, this.root);
		
		return hallado != null? hallado.getDato():null;
	}
	
	public boolean existe(String codigo) {
		return buscar(codigo, this.root) != null;
	}
	
	protected NodoArbol buscar(String codigo, NodoArbol root) {
		if(root == null) {
			return null;
		}
		
		int comparacion = codigo.compareToIgnoreCase(root.getDato().getID());
		
		if(comparacion < 0) {
			return buscar(codigo, root.getIzq());
		}
		else if(comparacion > 0) {
			return buscar(codigo, root.getDer());
		}
		else {
			return root;
		}
	}
	

	public void printInOrden() {
		System.out.println(inOrden(this.root));
	}
	
	public void printPostOrden() {
		System.out.println(postOrden(this.root));
	}
	
	public void printPreOrden() {
		System.out.println(preOrden(this.root));
	}
	
	private String inOrden(NodoArbol root) {
		String value = "";
		if(root != null) {
			
			value += inOrden(root.getIzq());
			value += "\n"+root.getDato();
			value += inOrden(root.getDer());
		}
		return value;
	}
	
	private String postOrden(NodoArbol root) {
		String value = "";
		if(root != null) {
			value += "\n"+ postOrden(root.getIzq());
			value += "\n"+ postOrden(root.getDer());
			value += "\n"+ root.getDato();
		}
		return value;
	}
	
	private String preOrden(NodoArbol root) {
		String value = "";
		if(root != null) {
			value += "\n"+ root.getDato();
			value += "\n"+ preOrden(root.getIzq());
			value += "\n"+ preOrden(root.getDer());
		}
		return value;
	}
	
	protected NodoArbol getMenor(NodoArbol root) {
		if(root.getIzq() == null) {
			return root;
		}
		return getMenor(root.getIzq());
	}
	
	/**
	 * Se elimina reemplazando el nodo a eliminar
	 * con el nodo menor del subarbol formado por
	 * el nodo a eliminar.
	 * @param codigo
	 */
	public abstract void eliminar(String codigo);
	
	protected void hacerNull(NodoArbol n) {
		n.setDato(null);
		n.setIzq(null);
		n.setDer(null);
		n.setBalance(0);
	}
	
	protected void intercambiarNodos(NodoArbol n1, NodoArbol n2) {
		n1.setDato(n2.getDato());
		n1.setIzq(n2.getIzq());
		n1.setDer(n2.getDer());
		
	}
	
	public long size() {
		return this.size;
	}
	
	@Override
	public String toString() {
		return preOrden(this.root);
	}
}
