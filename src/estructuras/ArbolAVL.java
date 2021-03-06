package estructuras;


public class ArbolAVL extends Arbol{
	
	@Override
	protected NodoArbol insertar(NodoArbol producto, NodoArbol root) {
		
		if(root == null) {
			
			return producto;
		}
		
		int comparacion = producto.getDato().getID().compareToIgnoreCase(root.getDato().getID());

		if(comparacion < 0) {
			root.setIzq(insertar(producto, root.getIzq()));
		}
		else if(comparacion > 0) {
			root.setDer(insertar(producto, root.getDer()));
		}
		
		
		return evaluaBalance(root);
	}
	
	@Override
	public void eliminar(String codigo) {
		this.root = eliminar(codigo, this.root);
	}
	
	
	private NodoArbol eliminar(String codigo, NodoArbol root) {
		
		if(root == null) {
			System.err.println("No se encontro el producto");
			return null;
		}
		
		int comparacion = codigo.compareToIgnoreCase(root.getDato().getID());
		
		if(comparacion < 0) {
			root.setIzq(eliminar(codigo, root.getIzq()));
		}else if(comparacion > 0) {
			root.setDer(eliminar(codigo, root.getDer()));
		}else {
			if(root.getIzq() != null) {
				NodoArbol mayor_subarbol = root.getIzq().getDer();
				root.getIzq().setDer(root.getDer());
				root = root.getIzq();
				
				if(mayor_subarbol != null)
				{
					root = insertar(mayor_subarbol, root);
				}
				
			}else {
				root = root.getDer();
			}
			
			if(root != null) {
				root = evaluaBalance(root);
			}
			System.out.println("Producto eliminado");
			--this.size;
		}		

		return root;
		
	}
	
	private NodoArbol evaluaBalance(NodoArbol root) {
	
		int balanceIzquierdo = root.getIzq() != null? root.getIzq().getBalance():0;
		int balanceDerecho = root.getDer() != null? root.getDer().getBalance():0;
		
		// desbalance del subarbol derecho
		if(balanceIzquierdo - balanceDerecho < -1) {
			if(root.getDer().getIzq() == null) {
				root = rotacionSimpleIzquierda(root);
			}
			else {
				root = rotacionDobleIzquierda(root);
			}
		}
		// desbalance del subarbol izquierdo
		else if(balanceIzquierdo - balanceDerecho > 1) {
			if(root.getIzq().getDer() == null) { // si no tiene hijo derecho
				root = rotacionSimpleDerecha(root);
			}
			else {
				root = rotacionDobleDerecha(root);
			}
		}
		
		return actualizaBalance(root);
	}

	private NodoArbol rotacionSimpleDerecha(NodoArbol root) {
		NodoArbol nueva_raiz = root.getIzq();
		
		root.setIzq(nueva_raiz.getDer());
		nueva_raiz.setDer(root);
		
		return actualizaBalance(nueva_raiz);
	}
	
	private NodoArbol rotacionSimpleIzquierda(NodoArbol root) {
		NodoArbol nueva_raiz = root.getDer();
		
		root.setDer(nueva_raiz.getIzq());
		nueva_raiz.setIzq(root);
		
		return nueva_raiz;
	}
	
	private NodoArbol rotacionDobleDerecha(NodoArbol root) {
		root.setIzq(rotacionSimpleIzquierda(root.getIzq()));
		return rotacionSimpleDerecha(root);
	}
	
	private NodoArbol rotacionDobleIzquierda(NodoArbol root) {
		root.setDer(rotacionSimpleDerecha(root.getDer()));
		return rotacionSimpleIzquierda(root);
	}
	
	private NodoArbol actualizaBalance(NodoArbol root) {
		NodoArbol hijoIzq = root.getIzq();
		NodoArbol hijoDer = root.getDer();
		
		int hi = hijoIzq != null? hijoIzq.getBalance():0;
		int hd = hijoDer != null? hijoDer.getBalance():0;
		
		root.setBalance(Math.max(hi, hd) + 1);
		return root;
	}

}
