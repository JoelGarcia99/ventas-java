package modelos;

import java.io.IOException;
import java.io.Serializable;

import estructuras.ModeloGenerico;

public abstract class Persona extends ModeloGenerico implements Serializable{

    private static final long serialVersionUID = -6292698498613458250L;
    protected String nombre;
    protected String apellido;

    public Persona(String cedula, String nombre, String apellido) {
    	
    	super(cedula);
    	
        this.nombre = nombre;
        this.apellido = apellido;

    }



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getApellido() {
		return apellido;
	}



	public void setApellido(String apellido) {
		this.apellido = apellido;
	}



	public abstract void guardar() throws IOException;
    public abstract void actualizar();
    public abstract void eliminar();
}
