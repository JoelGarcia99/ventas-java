package modelos;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import estructuras.ModeloGenerico;
import helper.Generador;

public abstract class AbsVenta extends ModeloGenerico{

	private static final long serialVersionUID = 6784556204547630988L;
	protected ArrayList<Producto> productos; // productos como tal
    protected ArrayList<Integer> productosCantidad; // cantidad de cada producto
    protected Date fecha;

    public AbsVenta() {
    	
    	super(Generador.generarID());
    	
    	this.productos = new ArrayList<Producto>();
    	this.productosCantidad = new ArrayList<Integer>();
        this.fecha = Date.from(Instant.now());
    }

    public float calcularPrecio() {
        float total = 0.0f;

        int cantidadIndex = 0;
        
        for (Producto producto : productos) {
            total += producto.getPrecio() * productosCantidad.get(cantidadIndex);
            ++cantidadIndex;
        }

        return total;
    }

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}
		

	public ArrayList<Integer> getProductosCantidad() {
		return productosCantidad;
	}

	public void setProductosCantidad(ArrayList<Integer> productosCantidad) {
		this.productosCantidad = productosCantidad;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public String formatProductos() {
		
		int cantidadIndex = 0;
		String formato = "";
		
		for(Producto producto:productos) {
			formato += "Producto: ";
			formato += producto.getNombre();
			formato += "\nCantidad: "+productosCantidad.get(cantidadIndex);
			formato += "\n----------------------------------------\n";
			
			++cantidadIndex;
		}
				
		return formato;
	}
    
}
