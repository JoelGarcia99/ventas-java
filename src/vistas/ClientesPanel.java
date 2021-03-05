package vistas;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modelos.Cliente;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientesPanel extends VistaGeneral{

	private static final long serialVersionUID = -8239045406423479266L;

	public ClientesPanel() {
		super(
				"Clientes", 
				new String[]{ "Cedula", "Nombres", "Apellidos"},
				"clientes"
		);
	}
	
	@Override
	protected void agregaItem() {
		AgregarClienteForm form = new AgregarClienteForm();
		
		JOptionPane.showMessageDialog(null, form, "Ingreso", JOptionPane.INFORMATION_MESSAGE);
		
		Cliente cliente = form.guardar();
        this.itemsTabla.modelo.addRow(new Object[] {
        		cliente.getCedula(),
        		cliente.getNombre(),
        		cliente.getApellido()
        });
        this.itemsTabla.updateUI();
	}

	@Override
	protected void insertaItemTabla(ObjectInputStream stream)
			throws IOException, FileNotFoundException, ClassNotFoundException {
		Cliente cliente = (Cliente) stream.readObject();
		stream.close();
        
        if(cliente == null) return;

        this.itemsTabla.modelo.addRow(new Object[] {
        		cliente.getCedula(),
        		cliente.getNombre(),
        		cliente.getApellido()
        });
		
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
