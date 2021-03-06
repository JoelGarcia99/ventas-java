package vistas;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
		AgregarProductoForm form = new AgregarProductoForm(null);
		
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
	        generaBotones(producto);
	        itemsTabla.productosTabla.updateUI();
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
        		
        generaBotones(producto);

	}
	
	private void generaBotones(Producto producto) {
		JButton btn = new JButton("Editar");
		JButton btnEliminar = new JButton("Eliminar");
		JButton btnCarrito = new JButton("Al carrito");
		
		final int rowIndex = itemsTabla.modelo.getRowCount() - 1;
		
		ActionListener evento = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch(e.getActionCommand()) {
					case "Editar": {
						Producto new_ = editarProducto(producto);
						
						if(new_ == null) return;
																		
						itemsTabla.modelo.setValueAt(new_.getID(), rowIndex, 0);
						itemsTabla.modelo.setValueAt(new_.getNombre(), rowIndex, 1);
						itemsTabla.modelo.setValueAt(new_.getDescripcion(), rowIndex, 2);
						itemsTabla.modelo.setValueAt(new_.getPrecio(), rowIndex, 3);
						
					}; break;
					case "Eliminar": {
						eliminarProducto(producto);
						redibujarTabla();
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
	
	private Producto editarProducto(Producto producto) {
		AgregarProductoForm form = new AgregarProductoForm(producto);
		JOptionPane.showMessageDialog(null, form);
		
		if(!(boolean) form.validar()[0]) {
			JOptionPane.showMessageDialog(null, form.validar()[1]);
			return producto;
		}
		
		Producto new_ = form.guardar();
		
		if(new_ == null) return null;
		
		MainPanel.arbolPro.eliminar(new_.getID());
		MainPanel.arbolPro.insertar(new_);
		JOptionPane.showMessageDialog(null, "Producto actualizado");
		return new_;
	}
	
	private void eliminarProducto(Producto producto) {
		MainPanel.arbolPro.eliminar(producto.getID());
		try {
			producto.eliminar();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se pudo eliminar\n"+e.getMessage());
			return;
		}
		JOptionPane.showMessageDialog(null, "Producto eliminado");
	}

	@Override
	protected void buscarItem(String ID) {
		if(ID == null) ID = "";
		
		Producto pro = (Producto) MainPanel.arbolPro.buscar(ID);
		
		if(pro == null) {
			JOptionPane.showMessageDialog(null, "Producto no hallado");
			return;
		}
		
		JOptionPane.showMessageDialog(null, pro.getID());
	}
}

// 290652500357500

class AgregarProductoForm extends JPanel {
    private static final long serialVersionUID = 1L;

    private final BoxLayout layout;
    private final JTextField nombre;
    private final JTextField precio;
    private final JTextField descripcion;
    private final Producto producto;

    public AgregarProductoForm(Producto producto) {
    	this.producto = producto;
        this.layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.nombre = new JTextField();
        this.precio = new JTextField();
        this.descripcion = new JTextField();

        if(producto != null) {
        	this.nombre.setText(producto.getNombre());
        	this.precio.setText(producto.getPrecio()+"");
        	this.descripcion.setText(producto.getDescripcion());
        }
        
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

        	// Si esto se cumple quiere decir que se va a actualizar
        	if(this.producto != null) {
        		this.producto.setNombre(nombre.getText());
        		this.producto.setDescripcion(descripcion.getText());
        		this.producto.setPrecio(Float.parseFloat(precio.getText()));
        		
        		try {
					this.producto.actualizar();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "No se pudo actualizar.\n"+e.getMessage());
				}
        		
        		return this.producto;
        	}
        	else {

        		// Si se llega hasta aqui es porque es un producto nuevo
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
        }
        return null;
    }
}
