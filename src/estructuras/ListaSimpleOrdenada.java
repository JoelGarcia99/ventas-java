package estructuras;

import java.util.ArrayList;

public class ListaSimpleOrdenada {
	private NodoLista root;
	
	public ListaSimpleOrdenada() {
		this.root = null;
	}
	
	// Insertando en orden alfabetico
	public void insertar(ModeloGenerico dato) {
		
		NodoLista nodo = new NodoLista(dato);
		
		if(this.root == null) {
			this.root = nodo;
			return;
		}
		
		// Inserta el nuevo nodo al inicio del todo si es el caso
		if(dato.getCriterio().toUpperCase().compareTo(this.root.getDato().getCriterio().toUpperCase()) < 0) {
			nodo.setSiguiente(this.root);
			this.root = nodo;
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
			return;
		}
		
		NodoLista aux = this.root;
		
		while(aux.getSiguiente() != null) {
			raizDato = aux.getSiguiente().getDato().getCriterio().toUpperCase();
			
			if(raizDato.equals(eliminarDato)) {
				aux.setSiguiente(aux.getSiguiente().getSiguiente());
				break;
			}
		}
		
		
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


}
