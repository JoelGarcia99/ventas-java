package vistas;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import estructuras.ModeloGenerico;
import main.MainPanel;
import modelos.Cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

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
		AgregarClienteForm form = new AgregarClienteForm(null);
		
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
			MainPanel.listaCliente.insertar(cliente);	
	        this.itemsTabla.modelo.addRow(new Object[] {
	        		cliente.getID(),
	        		cliente.getNombre(),
	        		cliente.getApellido()
	        });
	        this.itemsTabla.updateUI();
	        
	        generaBotones(cliente);
	        MainPanel.clientesArray.add(cliente);
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
		MainPanel.listaCliente.insertar(cliente);

        this.itemsTabla.modelo.addRow(new Object[] {
        		cliente.getID(),
        		cliente.getNombre(),
        		cliente.getApellido()
        });
        generaBotones(cliente);	
        MainPanel.clientesArray.add(cliente);        
	}

	@Override
	protected void buscarItem(String criterio) {
		if(criterio == null) criterio = "";
		
		ArrayList<ModeloGenerico> pro = MainPanel.listaCliente.buscar(criterio);
		
		if(pro.size() == 0) {
			JOptionPane.showMessageDialog(null, "Cliente no hallado");
			return;
		}
		
		JTable table = new JTable();
		JScrollPane scroll = new JScrollPane(table);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		model.setColumnIdentifiers(new Object[] {"Cedula", "Nombre", "Apellidos"});
		
		for(ModeloGenerico mo:pro) {
			Cliente p = (Cliente) mo;
			
			model.addRow(new Object[] {
					p.getID(),
					p.getNombre(),
					p.getApellido(),
			});
		}
		
		JOptionPane.showMessageDialog(null, scroll);
	}

	
	private void generaBotones(Cliente cliente) {
		JButton btn = new JButton("Editar");
		JButton btnEliminar = new JButton("Eliminar");

		final int rowIndex = itemsTabla.modelo.getRowCount() - 1;
		
		ActionListener evento = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch(e.getActionCommand()) {
					case "Editar": {
						Cliente new_ = editarCliente(cliente);
						
						if(new_ == null) return;
																		
						itemsTabla.modelo.setValueAt(new_.getID(), rowIndex, 0);
						itemsTabla.modelo.setValueAt(new_.getNombre(), rowIndex, 1);
						itemsTabla.modelo.setValueAt(new_.getApellido(), rowIndex, 2);
					}; break;
					case "Eliminar": {
						eliminarCliente(cliente);
						redibujarTabla();
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
	
	private Cliente editarCliente(Cliente cliente) {
		MainPanel.clientesArray.remove(cliente);
		AgregarClienteForm form = new AgregarClienteForm(cliente);
		JOptionPane.showMessageDialog(null, form);
		
		if(!(boolean) form.validar()[0]) {
			JOptionPane.showMessageDialog(null, form.validar()[1]);
			return cliente;
		}
		
		Cliente new_ = form.guardar();
		
		if(new_ == null) return null;
		
		MainPanel.arbolCliente.eliminar(new_.getID());
		MainPanel.arbolCliente.insertar(new_);
		MainPanel.clientesArray.add(new_);
		JOptionPane.showMessageDialog(null, "Cliente actualizado");
		
		return new_;
	}
	

	private void eliminarCliente(Cliente cliente) {
		MainPanel.arbolCliente.eliminar(cliente.getID());
		MainPanel.listaCliente.eliminar(cliente);
		try {
			cliente.eliminar();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se pudo eliminar\n"+e.getMessage());
			return;
		}
		MainPanel.clientesArray.remove(cliente);
		JOptionPane.showMessageDialog(null, "Cliente eliminado");
	}
}


class AgregarClienteForm extends JPanel {
    private static final long serialVersionUID = 1L;

    private final BoxLayout layout;
    private final JTextField nombre;
    private final JTextField apellidos;
    private final JTextField cedula;
    private Cliente cliente;

    public AgregarClienteForm(Cliente cliente) {
    	this.cliente = cliente;
        this.layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.nombre = new JTextField();
        this.apellidos = new JTextField();
        this.cedula = new JTextField();

        if(cliente != null) {
        	this.nombre.setText(cliente.getNombre());
        	this.apellidos.setText(cliente.getApellido());
        }
        
        this.setLayout(this.layout);

        // Formulario
        this.add(new JLabel("Nombre"));
        this.add(this.nombre);
        this.add(new JLabel("Apellidos"));
        this.add(this.apellidos);
        
        if(cliente == null) {
        	this.add(new JLabel("Cedula"));
        	this.add(this.cedula);        	
        }
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
        if (cliente==null && !this.cedula.getText().matches("[0-9]{10}")) {
            correcto = false;
            mensaje = "La cedula no es correcta";
        }
        return new Object[] { correcto, mensaje };
    }

    
    public Cliente guardar() {
        if ((boolean) validar()[0]) {
        	
        	if(this.cliente != null) {
        		this.cliente.setNombre(this.nombre.getText());
        		this.cliente.setApellido(this.apellidos.getText());
        		
        		try {
					this.cliente.actualizar();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "No se pudo actualizar.\n"+e.getMessage());
				}
        		
        		return this.cliente;
        	}
        	
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
