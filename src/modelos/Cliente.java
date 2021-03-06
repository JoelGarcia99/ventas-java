package modelos;

import java.io.File;
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
    public void actualizar() throws IOException{
    	File file = new File("clientes/"+this.ID+"-cliente.utm");
    	
    	if(file.exists()) {
    		file.delete();
    	}
    	
    	ObjectOutputStream escribiendoFichero = new ObjectOutputStream( 
                new FileOutputStream(file)
        );
        escribiendoFichero.writeObject(this);
        escribiendoFichero.close();
    }

    @Override
    public void eliminar() throws IOException{
    	File file = new File("clientes/"+this.ID+"-cliente.utm");
    	file.delete();
    }

	@Override
	public String getCriterio() {
		return this.getNombre() + " "+this.getApellido()+this.getID();
	}

}
