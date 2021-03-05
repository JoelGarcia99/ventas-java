package modelos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Venta extends AbsVenta implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = -2866862816460402591L;
    private Cliente cliente;
    private Vendedor vendedor;

    public void guardar(Cliente cliente, Vendedor vendedor) throws IOException{

        this.cliente = cliente;
        this.vendedor = vendedor;

        ObjectOutputStream escribiendoFichero = new ObjectOutputStream( 
            new FileOutputStream(this.id+"-venta.utm") 
        );
        escribiendoFichero.writeObject(this);
        escribiendoFichero.close();
    }

    public Persona getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Persona getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

}
