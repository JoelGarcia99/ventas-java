package vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.MainPanel;
import modelos.Vendedor;

public class ComprasPanel extends VistaGeneral{

	private static final long serialVersionUID = -3768111081317991600L;

	public ComprasPanel() {
		super(
				"Clientes", 
				new String[]{ "ID", "Proveedor", "Fecha", "Productos"}, 
				"clientes",
				new int[] {3}
		);
	}

	@Override
	protected void agregaItem() {
		AgregarVendedorForm form = new AgregarVendedorForm();
		
		JOptionPane.showMessageDialog(null, form, "Ingreso", JOptionPane.INFORMATION_MESSAGE);
		Object [] validator = form.validar();
		
		if(!(boolean) validator[0]) {
			JOptionPane.showMessageDialog(null, validator[1]);
			return;
		}
		
		Vendedor vendedor = form.guardar();
		
		if(vendedor != null) {		
			// para despues poder buscar
			MainPanel.arbolVendedor.insertar(vendedor);
	        this.itemsTabla.modelo.addRow(new Object[] {
	        		vendedor.getID(),
	        		vendedor.getNombre(),
	        		vendedor.getApellido()
	        });
	        this.itemsTabla.updateUI();
		}
	}

	@Override
	protected void insertaItemTabla(ObjectInputStream stream)
			throws IOException, FileNotFoundException, ClassNotFoundException {
		Vendedor vendedor = (Vendedor) stream.readObject();
		stream.close();
        
        if(vendedor == null) return;

		// para despues poder buscar
		MainPanel.arbolVendedor.insertar(vendedor);
		
        this.itemsTabla.modelo.addRow(new Object[] {
        		vendedor.getID(),
        		vendedor.getNombre(),
        		vendedor.getApellido()
        });
        
        JButton btn = new JButton("Editar");
		JButton btnEliminar = new JButton("Eliminar");
		
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
				}
			}
		};
		
		btn.addActionListener(evento);
		btnEliminar.addActionListener(evento);		
		
		int row = this.itemsTabla.modelo.getRowCount() - 1;
		this.itemsTabla.modelo.setValueAt(btn, row, 3);
		this.itemsTabla.modelo.setValueAt(btnEliminar, row, 4);
		
	}

}


class AgregarComprasForm extends JPanel {
    private static final long serialVersionUID = 1L;

    private final BoxLayout layout;
    private final JTextField nombre;
    private final JTextField apellidos;
    private final JTextField cedula;

    public AgregarComprasForm() {
        this.layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.nombre = new JTextField();
        this.apellidos = new JTextField();
        this.cedula = new JTextField();

        this.setLayout(this.layout);

        // Formulario
        this.add(new JLabel("Nombre"));
        this.add(this.nombre);
        this.add(new JLabel("Apellidos"));
        this.add(this.apellidos);
        this.add(new JLabel("Cedula"));
        this.add(this.cedula);
    }

    public Object[] validar() {
        boolean correcto = true;
        String mensaje = "Todo OK";

        if (!this.nombre.getText().matches("^[A-Za-z][A-Za-z ]+")) {
            mensaje = "El nombre no es correcto";
            correcto = false;
        }
        if (!this.apellidos.getText().matches("^[A-Za-z][A-Za-z ]+")) {
            correcto = false;
            mensaje = "El apellido no es correcto";
        }        
        if (!this.cedula.getText().matches("[0-9]{10}")) {
            correcto = false;
            mensaje = "La cedula no es correcta";
        }
        return new Object[] { correcto, mensaje };
    }

    public Vendedor guardar() {
        if ((boolean) validar()[0]) {
            Vendedor vendedor = new Vendedor(
            		this.cedula.getText(), 
            		this.nombre.getText(), 
            		this.apellidos.getText()
            );

            try {
            	vendedor.guardar();
                JOptionPane.showMessageDialog(null, "Vendedor Guardado");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error\n" + e.getMessage());
            }
            return vendedor;
        }
        return null;
    }
}

