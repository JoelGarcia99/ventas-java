package vistas;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import main.MainPanel;
import modelos.Producto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ProductoPanel extends VistaGeneral{
	
	private static final long serialVersionUID = -1640790027685054739L;

    
	public ProductoPanel() {
		super(
				"Productos", 
				new String[]{ "ID", "Nombre", "Descripcion", "Precio", "Editar", "Eliminar", "Al carrito"}, 
				"productos",
				new int[] {4, 5, 6}
		);

	}
	
	@Override
	protected void agregaItem() {
		AgregarProductoForm form = new AgregarProductoForm();
		
		JOptionPane.showMessageDialog(null, form, "Ingreso", JOptionPane.INFORMATION_MESSAGE);
		
		
		Object [] validator = form.validar();
		
		if(!(boolean) validator[0]) {
			JOptionPane.showMessageDialog(null, validator[1]);
			return;
		}
		
		Producto producto = form.guardar();
		
		if(producto != null) {
			// para despues poder buscar
			MainPanel.arbolPro.insertar(producto);
		
	        this.itemsTabla.modelo.addRow(new Object[] {
	        		producto.getID(),
	        		producto.getNombre(),
	        		producto.getDescripcion(),
	        		producto.getPrecio()
	        });
	        this.itemsTabla.updateUI();
		}
	}

	@Override
	protected void insertaItemTabla(ObjectInputStream stream) throws IOException, FileNotFoundException, ClassNotFoundException {
		Producto producto = (Producto) stream.readObject();
		stream.close();
        
        if(producto == null) return;

		// para despues poder buscar
		MainPanel.arbolPro.insertar(producto);
		
        this.itemsTabla.modelo.addRow(new Object[] {
        		producto.getID(),
        		producto.getNombre(),
        		producto.getDescripcion(),
        		producto.getPrecio()
        });
        		
		JButton btn = new JButton("Editar");
		JButton btnEliminar = new JButton("Eliminar");
		JButton btnCarrito = new JButton("Al carrito");
		
		ActionListener evento = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch(e.getActionCommand()) {
					case "Editar": {
						JOptionPane.showMessageDialog(null, "Editar");
					}; break;
					case "Eliminar": {
						JOptionPane.showMessageDialog(null, "Eliminar");
					}; break;
					case "Al carrito": {
						int cantidad = 0;
						try {
							cantidad = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la cantidad"));
						}catch(Exception exce) {
							JOptionPane.showMessageDialog(null, "La cantidad no es correcta");
							return;
						}

						MainPanel.carrito.agregarProducto(producto, cantidad);
						JOptionPane.showMessageDialog(null, "Producto Agregado");
					}; break;
				}
			}
		};
		
		btn.addActionListener(evento);
		btnEliminar.addActionListener(evento);
		btnCarrito.addActionListener(evento);		
		
		int row = this.itemsTabla.modelo.getRowCount() - 1;
		this.itemsTabla.modelo.setValueAt(btn, row, 4);
		this.itemsTabla.modelo.setValueAt(btnEliminar, row, 5);
		this.itemsTabla.modelo.setValueAt(btnCarrito, row, 6);

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
        this.add(new JLabel("Descripcion"));
        this.add(this.descripcion);
    }

    public Object[] validar() {
        boolean correcto = true;
        String mensaje = "Todo OK";

        if (!this.nombre.getText().matches("^[A-Za-z0-9][A-Za-z0-9 ]+")) {
            mensaje = "El nombre no es correcto";
            correcto = false;
        }
        if (this.descripcion.getText().length() < 5) {
            correcto = false;
            mensaje = "La descripcion es muy corta";
        }
        try {
            Float.parseFloat(this.precio.getText());
        } catch (Exception e) {
            correcto = false;
            mensaje = "El precio no es valido";
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
                JOptionPane.showMessageDialog(null, "Ocurrio un error\n" + e.getMessage());
            }
            return producto;
        }
        return null;
    }
}
