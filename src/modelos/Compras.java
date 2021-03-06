package modelos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

import estructuras.ModeloGenerico;
import helper.Generador;

public class Compras extends ModeloGenerico{

	private static final long serialVersionUID = 1L;
	private String proveedor;
	private Date fecha;
	private String producto;
	private int cantidad;
	
	
	public Compras(String proveedor, Date fecha, String producto, int cantidad) {
		super(Generador.generarID());
		
		this.proveedor = proveedor;
		this.fecha = fecha;
		this.producto = producto;
		this.cantidad = cantidad;
	}


	public String getProveedor() {
		return proveedor;
	}


	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public String getProducto() {
		return producto;
	}


	public void setProducto(String producto) {
		this.producto = producto;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
    public void guardar() throws IOException {
        ObjectOutputStream escribiendoFichero = new ObjectOutputStream( 
            new FileOutputStream("compras/"+this.ID+"-compra.utm") 
        );
        escribiendoFichero.writeObject(this);
        escribiendoFichero.close();
    }


	@Override
	public String getCriterio() {
		return this.getProducto()+" "+this.getID();
	}
	
	

}
