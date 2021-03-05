package vistas;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modelos.Producto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ProductoPanel extends VistaGeneral{
	
	private static final long serialVersionUID = -1640790027685054739L;

    
	public ProductoPanel() {
		super(
				"Productos", 
				new String[]{ "ID", "Nombre", "Descripcion", "Precio" }, 
				"productos"
		);
	}
	
	@Override
	protected void agregaItem() {
		AgregarProductoForm form = new AgregarProductoForm();
		
		JOptionPane.showMessageDialog(null, form, "Ingreso", JOptionPane.INFORMATION_MESSAGE);
		
		Producto producto = form.guardar();
        this.itemsTabla.modelo.addRow(new Object[] {
        		producto.getID(),
        		producto.getNombre(),
        		producto.getDescripcion(),
        		producto.getPrecio()
        });
        this.itemsTabla.updateUI();
	}

	@Override
	protected void insertaItemTabla(ObjectInputStream stream) throws IOException, FileNotFoundException, ClassNotFoundException {
		Producto producto = (Producto) stream.readObject();
		stream.close();
        
        if(producto == null) return;

        this.itemsTabla.modelo.addRow(new Object[] {
        		producto.getID(),
        		producto.getNombre(),
        		producto.getDescripcion(),
        		producto.getPrecio()
        });
	}
	
}

class AgregarProductoForm extends JPanel {
    private static final long serialVersionUID = 1L;

    private final BoxLayout layout;
    private final JTextField nombre;
    private final JTextField precio;
    private final JTextField descripcion;

    public AgregarProductoForm() {
        this.layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.nombre = new JTextField();
        this.precio = new JTextField();
        this.descripcion = new JTextField();

        this.setLayout(this.layout);

        // Formulario
        this.add(new JLabel("Nombre"));
        this.add(this.nombre);
        this.add(new JLabel("Precio"));
        this.add(this.precio);
        this.add(new JLabel("Descripción"));
        this.add(this.descripcion);
    }

    public Object[] validar() {
        boolean correcto = true;
        String mensaje = "Todo OK";

        if (!this.nombre.getText().matches("^[A-Za-z][A-Za-z ]+")) {
            mensaje = "El nombre no es correcto";
            correcto = false;
        }
        if (this.descripcion.getText().length() < 5) {
            correcto = false;
            mensaje = "La descripción es muy corta";
        }
        try {
            Float.parseFloat(this.precio.getText());
        } catch (Exception e) {
            correcto = false;
            mensaje = "El precio no es válido";
        }

        return new Object[] { correcto, mensaje };
    }

    public Producto guardar() {
        if ((boolean) validar()[0]) {
            Producto producto = new Producto(this.nombre.getText(), this.descripcion.getText(),
                    Float.parseFloat(this.precio.getText()));

            try {
                producto.guardar();
                JOptionPane.showMessageDialog(null, "Producto Guardado");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error\n" + e.getMessage());
            }
            return producto;
        }
        return null;
    }
}
