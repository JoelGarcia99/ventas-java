package main;

import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Observer.Observador;
import vistas.ClientesPanel;
import vistas.ProductoPanel;
import vistas.Vendedores;

/**
 * 
 * Este es el panel principal, todos los modulos de la interfaz
 * recaeran aqui
 *
 */

public class MainPanel extends JPanel implements Observador{

	private static final long serialVersionUID = -892554275836568837L;
	
	private final MainFrame frame;
	private final BorderLayout layout;
	private JPanel [] paneles = {
		new JPanel(),
		new ProductoPanel(),
		new JPanel(),
		new Vendedores(),
		new ClientesPanel(),
	};
	private JPanel panel;

	public MainPanel() {

		// Suscribiendose a un observable para ser notificado
		LateralPanel.observadores.add(this);


		this.frame = new MainFrame();
		this.layout = new BorderLayout();
		this.panel = paneles[0];

		// Configuraciones de este panel
		this.setLayout(this.layout);

		this.add(new LateralPanel(this.getSize()), BorderLayout.WEST);
		this.add(panel, BorderLayout.CENTER);

		// configuraciones del frame
		frame.add(this);
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

