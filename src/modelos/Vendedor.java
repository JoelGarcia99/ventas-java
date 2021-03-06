package modelos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Vendedor extends Persona {

    /**
     *
     */
    private static final long serialVersionUID = 5381970758044958318L;

    public Vendedor(String cedula, String nombre, String apellido) {
        super(cedula, nombre, apellido);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void guardar() throws IOException{
        ObjectOutputStream escribiendoFichero = new ObjectOutputStream( 
            new FileOutputStream("vendedores/"+this.ID+"-vendedor.utm") 
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
    
}
