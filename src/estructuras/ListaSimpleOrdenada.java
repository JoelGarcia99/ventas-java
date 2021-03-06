package estructuras;

import java.util.ArrayList;

public class ListaSimpleOrdenada {
	private NodoLista root;
	private long size;
	
	public ListaSimpleOrdenada() {
		this.root = null;
		this.size = 0;
	}
	
	// Insertando en orden alfabetico
	public void insertar(ModeloGenerico dato) {
		
		NodoLista nodo = new NodoLista(dato);
		
		if(this.root == null) {
			this.root = nodo;
			++this.size;
			return;
		}
		
		// Inserta el nuevo nodo al inicio del todo si es el caso
		if(dato.getCriterio().toUpperCase().compareTo(this.root.getDato().getCriterio().toUpperCase()) < 0) {
			nodo.setSiguiente(this.root);
			this.root = nodo;
			++this.size;
			return;
		}
		
		NodoLista aux = this.root;
		
		// buscando la posicion del nuevo nodo
		while(aux.getSiguiente() != null) {
			
			// si ya se ha encontrado la posicion, se sale del bucle
			if(aux.getSiguiente().getDato().getCriterio().toUpperCase().equals(dato.getCriterio().toUpperCase())) break;
			
			aux = aux.getSiguiente();
		}
		
		// insertando el nodo en su posicion
		aux.setSiguiente(nodo);
		++this.size;
	}

	public void eliminar(ModeloGenerico dato) {
		if(this.root == null) {
			System.err.println("Nada que eliminar");
			return;
		}
		
		// Criterios de busqueda
		String raizDato = this.root.getDato().getCriterio().toUpperCase();
		String eliminarDato = dato.getCriterio().toUpperCase();
		
		// eliminando la raiz si es el caso
		if(raizDato.equals(eliminarDato)) {
			this.root = this.root.getSiguiente();
			--this.size;
			return;
		}
		
		NodoLista aux = this.root;
		
		while(aux.getSiguiente() != null) {
			raizDato = aux.getSiguiente().getDato().getCriterio().toUpperCase();
			
			if(raizDato.equals(eliminarDato)) {
				aux.setSiguiente(aux.getSiguiente().getSiguiente());
				--this.size;
				return;
			}
			
			aux = aux.getSiguiente();
		}
		
		System.out.println("No hay nada que eliminar");
		
		
	}

	public ArrayList<ModeloGenerico> buscar(String criterio) {
		
		ArrayList<ModeloGenerico> resultados = new ArrayList<ModeloGenerico>();
		
		if(this.root == null) {
			System.err.println("No hay datos");
			return resultados;
		}
		
		String rootCriterio = this.root.getDato().getCriterio().toUpperCase();
		
		NodoLista aux = this.root;
		criterio = criterio.toUpperCase();
		
		while(aux != null) {
			rootCriterio = aux.getDato().getCriterio().toUpperCase();
			
			if(rootCriterio.regionMatches(true, 0, criterio, 0, criterio.length())) {
				resultados.add(aux.getDato());
			}
			
			aux = aux.getSiguiente();
		}
		
		return resultados;
	}
	
	public long getSize() {
		return this.size;
	}

}
