package vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import main.MainPanel;
import modelos.Venta;

public class VentasPanel extends VistaGeneral {

	private static final long serialVersionUID = 568755134161884199L;

	public VentasPanel() {
		super(
				"Ventas", 
				new String[]{"ID", "Cliente", "Vendedor", "Fecha", "Productos", "Precio"},
				"ventas",
				new int[] {4}
		);
	}

	@Override
	protected void agregaItem() {
		JOptionPane.showMessageDialog(null, "La venta debe ser agregada desde el carrito");
	}

	@Override
	protected void insertaItemTabla(ObjectInputStream stream)
			throws IOException, FileNotFoundException, ClassNotFoundException {
		Venta venta = (Venta) stream.readObject();
		stream.close();
        
        if(venta == null) return;
		// para despues poder buscar
		MainPanel.arbolPro.insertar(venta);

        this.itemsTabla.modelo.addRow(new Object[] {
        		venta.getID(),
        		venta.getCliente(),
        		venta.getVendedor(),
        		venta.getFecha(),
        		venta.formatProductos(),
        		venta.calcularPrecio()
        });
        
		
		JButton btn = new JButton("Ver");
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, new JScrollPane(
						new JLabel(venta.formatProductos())
				));
			}
		});
		
		int row = this.itemsTabla.modelo.getRowCount() - 1;
		this.itemsTabla.modelo.setValueAt(btn, row, 4);
	}

	@Override
	protected void buscarItem(String ID) {
		// TODO Auto-generated method stub
		
	}

}

