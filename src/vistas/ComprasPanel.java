package vistas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import estructuras.ModeloGenerico;
import main.MainPanel;
import modelos.Compras;

public class ComprasPanel extends VistaGeneral{

	private static final long serialVersionUID = -3768111081317991600L;

	public ComprasPanel() {
		super(
				"Compras", 
				new String[]{ "ID", "Proveedor", "Fecha", "Producto", "Cantidad"}, 
				"compras",
				new int[] {}
		);
	}

	@Override
	protected void agregaItem() {
		AgregarComprasForm form = new AgregarComprasForm();
		
		JOptionPane.showMessageDialog(null, form, "Ingreso", JOptionPane.INFORMATION_MESSAGE);
		Object [] validator = form.validar();
		
		if(!(boolean) validator[0]) {
			JOptionPane.showMessageDialog(null, validator[1]);
			return;
		}
		
		Compras compras = form.guardar();
		
		if(compras != null) {		
			// para despues poder buscar
			MainPanel.arbolCompra.insertar(compras);
			MainPanel.listaCompra.insertar(compras);
	        this.itemsTabla.modelo.addRow(new Object[] {
	        		compras.getID(),
	        		compras.getProveedor(),
	        		compras.getFecha(),
	        		compras.getProducto(),
	        		compras.getCantidad()
	        });
	        this.itemsTabla.updateUI();
		}
	}

	@Override
	protected void insertaItemTabla(ObjectInputStream stream)
			throws IOException, FileNotFoundException, ClassNotFoundException {
		Compras compras = (Compras) stream.readObject();
		stream.close();
        
        if(compras == null) return;

		// para despues poder buscar
		MainPanel.arbolCompra.insertar(compras);
		MainPanel.listaCompra.insertar(compras);
		
		
        this.itemsTabla.modelo.addRow(new Object[] {
        		compras.getID(),
        		compras.getProveedor(),
        		compras.getFecha(),
        		compras.getProducto(),
        		compras.getCantidad()
        });
		
	}

	@Override
	protected void buscarItem(String criterio) {
		if(criterio == null) criterio = "";
		
		ArrayList<ModeloGenerico> pro = MainPanel.listaCompra.buscar(criterio);
		
		if(pro.size() == 0) {
			JOptionPane.showMessageDialog(null, "Venta no hallada");
			return;
		}
		
		JTable table = new JTable();
		JScrollPane scroll = new JScrollPane(table);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		model.setColumnIdentifiers(new Object[] {"ID", "Proveedor", "Fecha", "Producto", "Cantidad"});
		
		for(ModeloGenerico mo:pro) {
			Compras p = (Compras) mo;
			
			model.addRow(new Object[] {
					p.getID(),
					p.getProveedor(),
	        		p.getFecha(),
	        		p.getProducto(),
	        		p.getCantidad()
			});
		}
		
		JOptionPane.showMessageDialog(null, scroll);
	}

}


class AgregarComprasForm extends JPanel {
    private static final long serialVersionUID = 1L;

    private final BoxLayout layout;
    private final JTextField proveedor;
    private final JTextField producto;
    private final JTextField cantidad;

    public AgregarComprasForm() {
        this.layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.proveedor = new JTextField();
        this.producto = new JTextField();
        this.cantidad = new JTextField();

        this.setLayout(this.layout);

        // Formulario
        this.add(new JLabel("Proveedor"));
        this.add(this.proveedor);
        this.add(new JLabel("Producto"));
        this.add(this.producto);
        this.add(new JLabel("Cantidad"));
        this.add(this.cantidad);
    }

    public Object[] validar() {
        boolean correcto = true;
        String mensaje = "Todo OK";

        if (!this.proveedor.getText().matches("^[A-Za-z0-9][A-Za-z0-9 ]+")) {
            mensaje = "El proveedor no es correcto";
            correcto = false;
        }
        if (!this.producto.getText().matches("^[A-Za-z0-9][0-9A-Za-z ]+")) {
            correcto = false;
            mensaje = "El nombre del producto no es correcto";
        }        
        if (!this.cantidad.getText().matches("[0-9]+")) {
            correcto = false;
            mensaje = "La cantidad no es correcta";
        }
        return new Object[] { correcto, mensaje };
    }

    public Compras guardar() {
        if ((boolean) validar()[0]) {
        	Compras compras = new Compras(
            		this.proveedor.getText(), 
            		Date.from(Instant.now()),
            		this.producto.getText(), 
            		Integer.parseInt(this.cantidad.getText())
            );

            try {
            	compras.guardar();
                JOptionPane.showMessageDialog(null, "Compra Guardado");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error\n" + e.getMessage());
            }
            return compras;
        }
        return null;
    }
}

