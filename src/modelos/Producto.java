package modelos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import helper.Generador;

public class Producto implements Serializable{
    private static final long serialVersionUID = -2516111676303890116L;
    private String id;
    private String nombre;
    private String descripcion;
    private float precio;
    
    public Producto(String nombre, String descripcion, float precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;

        this.id = Generador.generarID();
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

    public String getID() {
        return this.id;
    }

    public void guardar() throws IOException {
        ObjectOutputStream escribiendoFichero = new ObjectOutputStream( 
            new FileOutputStream("productos/"+this.id+"-producto.utm") 
        );
        escribiendoFichero.writeObject(this);
        escribiendoFichero.close();
    }

}
