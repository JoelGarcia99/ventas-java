package main;

import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * Este es el panel principal, todos los modulos de la interfaz
 * recaeran aqui
 *
 */

public class MainPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -892554275836568837L;
	
	private final MainFrame frame;
	private final BorderLayout layout;
	
	public MainPanel() {
		this.frame = new MainFrame();
		this.layout = new BorderLayout();

		// Configuraciones de este panel
		this.setLayout(this.layout);

		this.add(new LateralPanel(this.getSize()), BorderLayout.WEST);


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
	
}

