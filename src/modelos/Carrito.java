package modelos;

public class Carrito extends AbsVenta{

	private static final long serialVersionUID = -6078384026712177048L;

	public void agregarProducto(Producto producto, int cantidad) {
		this.productos.add(producto);
		this.productosCantidad.add(cantidad);
	}
	
	public void borrarProducto(Producto producto) {
		int index = this.productos.indexOf(producto);
		
		this.productosCantidad.remove(index);
		this.productos.remove(producto);
		
	}
	
	public void reiniciar() {
		this.productos.clear();
		this.productosCantidad.clear();
	}
}
