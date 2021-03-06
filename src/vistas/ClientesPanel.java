package vistas;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.MainPanel;
import modelos.Cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientesPanel extends VistaGeneral{

	private static final long serialVersionUID = -8239045406423479266L;

	public ClientesPanel() {
		super(
				"Clientes", 
				new String[]{ "Cedula", "Nombres", "Apellidos", "Editar", "Eliminar"}, 
				"clientes",
				new int[] {3, 4}
		);
	}
	
	@Override
	protected void agregaItem() {
		AgregarClienteForm form = new AgregarClienteForm();
		
		JOptionPane.showMessageDialog(null, form, "Ingreso", JOptionPane.INFORMATION_MESSAGE);
		Object [] validator = form.validar();
		
		if(!(boolean) validator[0]) {
			JOptionPane.showMessageDialog(null, validator[1]);
			return;
		}
		
		Cliente cliente = form.guardar();
		
		if(cliente != null) {	
			// para despues poder buscar
			MainPanel.arbolCliente.insertar(cliente);	
	        this.itemsTabla.modelo.addRow(new Object[] {
	        		cliente.getID(),
	        		cliente.getNombre(),
	        		cliente.getApellido()
	        });
	        this.itemsTabla.updateUI();
		}
	}

	@Override
	protected void insertaItemTabla(ObjectInputStream stream)
			throws IOException, FileNotFoundException, ClassNotFoundException {
		Cliente cliente = (Cliente) stream.readObject();
		stream.close();
        
        if(cliente == null) return;

		// para despues poder buscar
		MainPanel.arbolCliente.insertar(cliente);

        this.itemsTabla.modelo.addRow(new Object[] {
        		cliente.getID(),
        		cliente.getNombre(),
        		cliente.getApellido()
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

	@Override
	protected void buscarItem(String ID) {
		// TODO Auto-generated method stub
		
	}

}

class AgregarClienteForm extends JPanel {
    private static final long serialVersionUID = 1L;

    private final BoxLayout layout;
    private final JTextField nombre;
    private final JTextField apellidos;
    private final JTextField cedula;

    public AgregarClienteForm() {
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

    public Cliente guardar() {
        if ((boolean) validar()[0]) {
            Cliente cliente = new Cliente(
            		this.cedula.getText(), 
            		this.nombre.getText(), 
            		this.apellidos.getText()
            );

            try {
            	cliente.guardar();
                JOptionPane.showMessageDialog(null, "Cliente Guardado");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error\n" + e.getMessage());
            }
            return cliente;
        }
        return null;
    }
}
