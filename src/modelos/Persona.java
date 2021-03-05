package modelos;

import java.io.IOException;
import java.io.Serializable;

public abstract class Persona implements Serializable{

    private static final long serialVersionUID = -6292698498613458250L;
    protected String cedula;
    protected String nombre;
    protected String apellido;

    public Persona(String cedula, String nombre, String apellido) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;

    }

    

    public String getCedula() {
		return cedula;
	}



	public void setCedula(String cedula) {
		this.cedula = cedula;
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
