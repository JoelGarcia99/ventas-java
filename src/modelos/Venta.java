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
    private String cliente;
    private String vendedor;

    public void guardar(String cliente, String vendedor) throws IOException{

        this.cliente = cliente;
        this.vendedor = vendedor;

        ObjectOutputStream escribiendoFichero = new ObjectOutputStream( 
            new FileOutputStream("ventas/"+this.ID+"-venta.utm") 
        );
        escribiendoFichero.writeObject(this);
        escribiendoFichero.close();
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

}
