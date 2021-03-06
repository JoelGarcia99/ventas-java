package modelos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Cliente extends Persona {

    /**
     *
     */
    private static final long serialVersionUID = -7929753657420812965L;

    public Cliente(String cedula, String nombre, String apellido) {
        super(cedula, nombre, apellido);
    }

    @Override
    public void guardar() throws IOException{
        ObjectOutputStream escribiendoFichero = new ObjectOutputStream( 
            new FileOutputStream("clientes/"+this.ID+"-cliente.utm") 
        );
        escribiendoFichero.writeObject(this);
        escribiendoFichero.close();
    }

    @Override
    public void actualizar() {
        // TODO Auto-generated method stub

    }

    @Override
    public void eliminar() {
        // TODO Auto-generated method stub

    }

	@Override
	public String getCriterio() {
		// TODO Auto-generated method stub
		return null;
	}

}
