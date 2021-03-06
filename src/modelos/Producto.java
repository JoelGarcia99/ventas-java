package modelos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import estructuras.ModeloGenerico;
import helper.Generador;

public class Producto extends ModeloGenerico implements Serializable{
    private static final long serialVersionUID = -2516111676303890116L;
    private String nombre;
    private String descripcion;
    private float precio;
    
    public Producto(String nombre, String descripcion, float precio) {
    	super(Generador.generarID());
    	
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }


    public void guardar() throws IOException {
        ObjectOutputStream escribiendoFichero = new ObjectOutputStream( 
            new FileOutputStream("productos/"+this.ID+"-producto.utm") 
        );
        escribiendoFichero.writeObject(this);
        escribiendoFichero.close();
    }

}
