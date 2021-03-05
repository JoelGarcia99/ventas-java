package modelos;

import java.util.ArrayList;
import java.util.Date;

import helper.Generador;

public abstract class AbsVenta {
    protected ArrayList<Producto> productos;
    protected Date fecha;
    protected String id;

    public AbsVenta() {
        this.id = Generador.generarID();
    }

    public float calcularPrecio() {
        float total = 0.0f;

        for (Producto producto : productos) {
            total += producto.getPrecio();
        }

        return total;
    }
}
