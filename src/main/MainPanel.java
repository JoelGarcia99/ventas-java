package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Observer.Observador;
import estructuras.ArbolAVLProducto;
import modelos.Carrito;
import modelos.Producto;
import modelos.Venta;
import vistas.ClientesPanel;
import vistas.ProductoPanel;
import vistas.VendedoresPanel;
import vistas.VentasPanel;

/**
 * 
 * Este es el panel principal, todos los modulos de la interfaz
 * recaeran aqui
 *
 */

public class MainPanel extends JPanel implements Observador{

	private static final long serialVersionUID = -892554275836568837L;
	public static Carrito carrito = new Carrito();
	public static ArbolAVLProducto arbolPro = new ArbolAVLProducto();
	public static ArbolAVLProducto arbolCliente = new ArbolAVLProducto();
	public static ArbolAVLProducto arbolVendedor = new ArbolAVLProducto();
	
	private final MainFrame frame;
	private final BorderLayout layout;
	private final JMenuBar menuBar;
		
	private final JMenu menuCarrito;
	private final JMenuItem verCarrito;
	private final JMenuItem agregarAlCarrito;
	private final JMenuItem borrarDelCarrito;
	private final JMenuItem pagarCarrito;	
	private final JMenuItem descartarCarrito;	
	
	private final JMenu menuArchivo;
	private final JMenuItem salirArchivo;
	
	private JPanel [] paneles = {
		new ProductoPanel(),
		new VendedoresPanel(),
		new ClientesPanel(),
		new VentasPanel(),
		new JPanel(),
	};
	private JPanel panel;

	public MainPanel() {

		// Suscribiendose a un observable para ser notificado
		LateralPanel.observadores.add(this);
		
		this.menuBar = new JMenuBar();
		this.menuCarrito = new JMenu("Carrito");
		this.menuArchivo = new JMenu("Archivo");
		this.verCarrito = new JMenuItem("Ver carrito");
		this.agregarAlCarrito = new JMenuItem("Agregar producto");
		this.borrarDelCarrito = new JMenuItem("Borrar producto");
		this.pagarCarrito = new JMenuItem("Pagar");
		this.descartarCarrito = new JMenuItem("Reiniciar");
		this.salirArchivo = new JMenuItem("Salir");

		this.frame = new MainFrame();
		this.layout = new BorderLayout();
		this.panel = paneles[0];

		// Configuraciones de este panel
		this.loadMenu();
		this.setLayout(this.layout);

		this.add(this.menuBar, BorderLayout.NORTH);
		this.add(new LateralPanel(this.getSize()), BorderLayout.WEST);
		this.add(panel, BorderLayout.CENTER);

		// configuraciones del frame
		frame.add(this);
	}
	
	private void loadMenu() {
		this.menuBar.add(menuArchivo);
		this.menuBar.add(menuCarrito);
		
		menuCarrito.add(verCarrito);
		menuCarrito.add(agregarAlCarrito);
		menuCarrito.add(borrarDelCarrito);
		menuCarrito.add(pagarCarrito);
		menuCarrito.add(descartarCarrito);
		
		menuArchivo.add(salirArchivo);
		
		verCarrito.addActionListener(event);
		agregarAlCarrito.addActionListener(event);
		descartarCarrito.addActionListener(event);
		borrarDelCarrito.addActionListener(event);
		pagarCarrito.addActionListener(event);
		
	}
	
	private ActionListener event = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()) {
				case "Ver carrito": {
					muestraCarrito();
				}; break;
				case "Agregar producto": {
					agregarCarrito();
				}; break;
				case "Borrar producto": {
					eliminaItem();
				}; break;
				case "Pagar": {
					pagarCarrito();
				}; break;
				case "Reiniciar": {
					carrito.getProductos().clear();
					carrito.getProductosCantidad().clear();
					JOptionPane.showMessageDialog(null, "Carrito reiniciado");
				}; break;
				case "Salir": System.exit(0); break;
			}
		}
	};
	
	private void muestraCarrito() {
		JTable table = new JTable();
		JScrollPane pane = new JScrollPane(table);
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		model.addColumn("ID Producto");
		model.addColumn("Producto");
		model.addColumn("Precio");
		model.addColumn("Cantidad");
		model.addColumn("Total");
		
		int cantidadIndex = 0;
		for(Producto producto:carrito.getProductos()) {
			model.addRow(
					new Object[] {
							producto.getID(),
							producto.getNombre(),
							producto.getPrecio(),
							carrito.getProductosCantidad().get(cantidadIndex),
							carrito.getProductosCantidad().get(cantidadIndex) * producto.getPrecio()
					}
			);
			++cantidadIndex;
		}
		
		model.addRow(
				new Object[] {
						"",
						"",
						"",
						"Total",
						"$ "+carrito.calcularPrecio()
				}
		);
		JOptionPane.showMessageDialog(null, pane, "Carrito de compras", JOptionPane.NO_OPTION);
	}

	private void agregarCarrito() {
		JTextField producto = new JTextField();
		JTextField cantidad = new JTextField();
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		panel.add(new JLabel("Producto ID"));
		panel.add(producto);
		panel.add(new JLabel("Cantidad"));
		panel.add(cantidad);
		
		JOptionPane.showMessageDialog(null, panel, "Agregar al carrito", JOptionPane.INFORMATION_MESSAGE);
	
		// agregando al carrito
		if(!producto.getText().matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null, "El ID del producto no es correcto");
			return;
		}
		try {
			Integer.parseInt(cantidad.getText());
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "La cantidad no es correcto, ingrese un entero");
			return;
		}
		
		// validando informacion
		if(!arbolPro.existe(producto.getText())) {
			JOptionPane.showMessageDialog(null, "El producto no existe. Verifique el ID");
			return;
		}
		
		carrito.agregarProducto((Producto) arbolPro.buscar(producto.getText()), Integer.parseInt(cantidad.getText()));
		JOptionPane.showMessageDialog(null, "Producto agregado al carrito");
	}
	
	private void eliminaItem() {
		JTextField itemID = new JTextField();
		
		JOptionPane.showMessageDialog(null, itemID, "Ingrese el ID del producto", JOptionPane.INFORMATION_MESSAGE);
	
		if(!arbolPro.existe(itemID.getText())) {
			JOptionPane.showMessageDialog(null, "El producto no existe");
			return;
		}
		
		carrito.borrarProducto((Producto) arbolPro.buscar(itemID.getText()));
		JOptionPane.showMessageDialog(null, "Producto borrado del carrito");
	}
	
	private void pagarCarrito() {
		
		JTextField clienteID = new JTextField();
		JTextField vendedorID = new JTextField();
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		panel.add(new JLabel("Cliente ID"));
		panel.add(clienteID);
		panel.add(new JLabel("Vendedor ID"));
		panel.add(vendedorID);
		
		JOptionPane.showMessageDialog(null, panel, "Pagar carrito", JOptionPane.INFORMATION_MESSAGE);
		
		if(!arbolCliente.existe(clienteID.getText())) {
			JOptionPane.showMessageDialog(null, "El cliente no existe");
			return;
		}
		if(!arbolVendedor.existe(vendedorID.getText())) {
			JOptionPane.showMessageDialog(null, "El vendedor no existe");
			return;
		}
		if(carrito.getProductos().size() < 1) {
			JOptionPane.showMessageDialog(null, "No hay productos en el carrito");
			return;
		}
		
		try {
			Venta venta = new Venta();
			venta.setProductos(carrito.getProductos());
			venta.setProductosCantidad(carrito.getProductosCantidad());
			venta.guardar(clienteID.getText(), vendedorID.getText());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		carrito.reiniciar();
		JOptionPane.showMessageDialog(null, "Carrito pagado");

	}

	private class MainFrame extends JFrame {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 733782331767299264L;

		public MainFrame() {
			this.setTitle("Ventas");
			this.setResizable(false);
			this.setUndecorated(true);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
			this.setMinimumSize(new Dimension(800, 600));
			this.setPreferredSize(new Dimension(800, 600));
			
			this.setVisible(true);
			this.setLocationRelativeTo(null);
		}
		
	}

	@Override
	public void notifyAction(int selected) {
		this.remove(panel);
		panel = paneles[selected];
		this.add(panel, BorderLayout.CENTER);
		this.updateUI();
	}
	
}

