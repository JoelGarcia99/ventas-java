package vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

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
import modelos.Vendedor;

public class VendedoresPanel extends VistaGeneral{

	private static final long serialVersionUID = 1849483027521228507L;

	public VendedoresPanel() {
		super(
				"Vendedores", 
				new String[]{ "Cedula", "Nombres", "Apellidos", "Editar", "Eliminar"},
				"vendedores",
				new int[] {3, 4}
		);
	}

	@Override
	protected void agregaItem() {
		AgregarVendedorForm form = new AgregarVendedorForm(null);
		
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
			MainPanel.listaVendedor.insertar(vendedor);

	        this.itemsTabla.modelo.addRow(new Object[] {
	        		vendedor.getID(),
	        		vendedor.getNombre(),
	        		vendedor.getApellido()
	        });
	        this.itemsTabla.updateUI();
	        generaBotones(vendedor);	
	        MainPanel.vendedoresArray.add(vendedor);
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
		MainPanel.listaVendedor.insertar(vendedor);
		
        this.itemsTabla.modelo.addRow(new Object[] {
        		vendedor.getID(),
        		vendedor.getNombre(),
        		vendedor.getApellido()
        });

        MainPanel.vendedoresArray.add(vendedor);
        generaBotones(vendedor);	
		
	}

	@Override
	protected void buscarItem(String criterio) {
		if(criterio == null) criterio = "";
		
		ArrayList<ModeloGenerico> pro = MainPanel.listaVendedor.buscar(criterio);
		
		if(pro.size() == 0) {
			JOptionPane.showMessageDialog(null, "Vendedor no hallado");
			return;
		}
		
		JTable table = new JTable();
		JScrollPane scroll = new JScrollPane(table);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		model.setColumnIdentifiers(new Object[] {"Cedula", "Nombre", "Apellidos"});
		
		for(ModeloGenerico mo:pro) {
			Vendedor p = (Vendedor) mo;
			
			model.addRow(new Object[] {
					p.getID(),
					p.getNombre(),
					p.getApellido(),
			});
		}
		
		JOptionPane.showMessageDialog(null, scroll);
	}

	private void generaBotones(Vendedor vendedor) {
		JButton btn = new JButton("Editar");
		JButton btnEliminar = new JButton("Eliminar");

		final int rowIndex = itemsTabla.modelo.getRowCount() - 1;
		
		ActionListener evento = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch(e.getActionCommand()) {
					case "Editar": {
						Vendedor new_ = editarCliente(vendedor);
						
						if(new_ == null) return;
																		
						itemsTabla.modelo.setValueAt(new_.getID(), rowIndex, 0);
						itemsTabla.modelo.setValueAt(new_.getNombre(), rowIndex, 1);
						itemsTabla.modelo.setValueAt(new_.getApellido(), rowIndex, 2);
					}; break;
					case "Eliminar": {
						eliminarVendedor(vendedor);
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
	
	private Vendedor editarCliente(Vendedor vendedor) {
		MainPanel.vendedoresArray.remove(vendedor);
		AgregarVendedorForm form = new AgregarVendedorForm(vendedor);
		JOptionPane.showMessageDialog(null, form);
		
		if(!(boolean) form.validar()[0]) {
			JOptionPane.showMessageDialog(null, form.validar()[1]);
			return vendedor;
		}
		
		Vendedor new_ = form.guardar();
		
		if(new_ == null) return null;
		
		MainPanel.arbolVendedor.eliminar(new_.getID());
		MainPanel.arbolVendedor.insertar(new_);
		MainPanel.vendedoresArray.add(new_);
		JOptionPane.showMessageDialog(null, "Vendedor actualizado");
		return new_;
	}
	
	private void eliminarVendedor(Vendedor vendedor) {
		MainPanel.arbolVendedor.eliminar(vendedor.getID());
		MainPanel.listaVendedor.eliminar(vendedor);
		MainPanel.vendedoresArray.remove(vendedor);
		try {
			vendedor.eliminar();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se pudo eliminar\n"+e.getMessage());
			return;
		}
		JOptionPane.showMessageDialog(null, "Vendedor eliminado");
	}
	
}

class AgregarVendedorForm extends JPanel {
    private static final long serialVersionUID = 1L;

    private final BoxLayout layout;
    private final JTextField nombre;
    private final JTextField apellidos;
    private final JTextField cedula;
    private final Vendedor vendedor;

    public AgregarVendedorForm(Vendedor vendedor) {
    	this.vendedor = vendedor;
        this.layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.nombre = new JTextField();
        this.apellidos = new JTextField();
        this.cedula = new JTextField();

        if(vendedor != null) {
        	this.nombre.setText(vendedor.getNombre());
        	this.apellidos.setText(vendedor.getApellido());
        }
        
        this.setLayout(this.layout);

        // Formulario
        this.add(new JLabel("Nombre"));
        this.add(this.nombre);
        this.add(new JLabel("Apellidos"));
        this.add(this.apellidos);
        
        if(vendedor == null) {
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
        if (vendedor==null && !this.cedula.getText().matches("[0-9]{10}")) {
            correcto = false;
            mensaje = "La cedula no es correcta";
        }
        return new Object[] { correcto, mensaje };
    }

    public Vendedor guardar() {
        if ((boolean) validar()[0]) {
            
           	if(this.vendedor != null) {
        		this.vendedor.setNombre(this.nombre.getText());
        		this.vendedor.setApellido(this.apellidos.getText());
        		
        		try {
					this.vendedor.actualizar();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "No se pudo actualizar.\n"+e.getMessage());
				}
        		
        		return this.vendedor;
        	}
        	
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
