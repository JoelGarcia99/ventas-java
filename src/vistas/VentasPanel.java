package vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import estructuras.ModeloGenerico;
import helper.ButtonTableRender;
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
		MainPanel.arbolVenta.insertar(venta);
		MainPanel.listaVenta.insertar(venta);

        this.itemsTabla.modelo.addRow(new Object[] {
        		venta.getID(),
        		venta.getCliente().getID(),
        		venta.getVendedor().getID(),
        		venta.getFecha(),
        		"",
        		venta.calcularPrecio()
        });
        
        generarBotones(venta);
        
		
	}
	
	private void generarBotones(Venta venta) {
		JButton btn = new JButton("Ver");
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, new JScrollPane(
						new JTextArea(venta.formatProductos())
				));
			}
		});
		
		int row = this.itemsTabla.modelo.getRowCount() - 1;
		this.itemsTabla.modelo.setValueAt(btn, row, 4);
	}

	@Override
	protected void buscarItem(String criterio) {
		if(criterio == null) criterio = "";
		
		ArrayList<ModeloGenerico> pro = MainPanel.listaVenta.buscar(criterio);
		
		if(pro.size() == 0) {
			JOptionPane.showMessageDialog(null, "Venta no hallado");
			return;
		}
		
		JTable table = new JTable();
        ButtonTableRender render = new ButtonTableRender(table);
        
		table.setDefaultRenderer(Object.class, render);
		table.setDefaultEditor(Object.class, render);
       
		JScrollPane scroll = new JScrollPane(table);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		model.setColumnIdentifiers(new Object[] {"ID", "Cliente", "Vendedor", "Fecha", "Productos", "Precio"});
		
		for(ModeloGenerico mo:pro) {
			Venta p = (Venta) mo;
			
			model.addRow(new Object[] {
					p.getID(),
	        		p.getCliente().getID(),
	        		p.getVendedor().getID(),
					p.getFecha(),
	        		p.formatProductos(),
	        		p.calcularPrecio()
			});

			JButton btn = new JButton("Ver");
			
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, new JScrollPane(
							new JLabel(p.formatProductos())
					));
				}
			});
			
			int row = table.getModel().getRowCount() - 1;
			table.getModel().setValueAt(btn, row, 4);
		}
		
		JOptionPane.showMessageDialog(null, scroll);
	}

}

