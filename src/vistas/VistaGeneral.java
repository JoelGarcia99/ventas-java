package vistas;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import design.Buttons;
import helper.ButtonTableRender;
import helper.LectorDirectorio;
import main.MainPanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public abstract class VistaGeneral extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected final BorderLayout layout;
    protected final SearchBar searchBar;
    protected final JPanel footer;
    protected final Buttons btnAgregar;
    /*protected final Buttons btnActualizar;
    protected final Buttons btnEliminar;*/
    protected ItemList itemsTabla;
    
    // Esto se rellena en la clase hija
    protected String nombre = ""; // nombre del modulo
    protected String [] columnas;
    protected int[] editable;
    protected String rutaArchivos;
    protected Buttons btnSearch;
    
    public VistaGeneral(String nombre, String [] columnas, String ruta, int [] editable) {
    	
    	this.nombre = nombre;
    	this.columnas = columnas;
    	this.editable = editable;
    	this.rutaArchivos = ruta;
    	
    	this.btnSearch = new Buttons("Buscar");    	
        this.btnAgregar = new Buttons("Agregar");
        /*this.btnActualizar = new Buttons("Actualizar");
        this.btnEliminar = new Buttons("Eliminar");*/
        this.footer = new JPanel();
        this.searchBar = new SearchBar(nombre, btnSearch);
        this.itemsTabla = new ItemList(columnas, editable);
        this.layout = new BorderLayout();
        
        // Tabla
        leeProductos();

        this.btnAgregar.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.btnAgregar.addActionListener(event);
        
        this.btnSearch.addActionListener(event);

        // Footer
        this.footer.setAlignmentX(Component.CENTER_ALIGNMENT);
        /*this.footer.setLayout(new BoxLayout(this.footer, BoxLayout.X_AXIS));
        this.footer.add(this.btnActualizar);
        this.footer.add(Box.createHorizontalStrut(10));*/
        this.footer.add(this.btnAgregar);
        /*this.footer.add(Box.createHorizontalStrut(10));
        this.footer.add(this.btnEliminar);*/

        // configuracion del panel
        this.setBorder(BorderFactory.createEmptyBorder(0, 15, 30, 15));
        this.setLayout(this.layout);
        this.add(this.searchBar, BorderLayout.NORTH);
        this.add(this.itemsTabla, BorderLayout.CENTER);
        this.add(this.footer, BorderLayout.SOUTH);
        
    }
    
    public void redibujarTabla() {
    	if(nombre == "Clientes") {
	    	MainPanel.clientesArray.clear();
	    }else if(nombre == "Vendedores") {
	    	MainPanel.vendedoresArray.clear();
	    }
    	
    	this.remove(this.itemsTabla);
    	this.itemsTabla = new ItemList(columnas, editable);
        leeProductos();
        this.add(this.itemsTabla, BorderLayout.CENTER);
        
 
        
        this.updateUI();
    }

    protected ActionListener event = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Agregar")) {
            	agregaItem();
            }
            if(e.getActionCommand().equals("Buscar")) {  
            	buscarItem(searchBar.searchBar.getText());
            }
        }

    };
    
    protected abstract void agregaItem(); // agrega un nuevo item a los archivos
    protected abstract void insertaItemTabla(ObjectInputStream stream) throws IOException, FileNotFoundException, ClassNotFoundException ; // agrega un nuevo item a la tabla
    
    public void leeProductos() {

        ArrayList<String> files = LectorDirectorio.leeArhivosEnCarpeta(new File(this.rutaArchivos));

        
        try {
        	for (String file : files) {
                ObjectInputStream lector = new ObjectInputStream(new FileInputStream(file));
				insertaItemTabla(lector);
        	}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    protected abstract void buscarItem(String ID);
  

}

/**
 * 
 * Barra de busqueda
 *
 */
class SearchBar extends JPanel {

    private static final long serialVersionUID = -493443701311276889L;
    public JTextField searchBar;
    private JLabel title;

    public SearchBar(String titulo, Buttons btnSearch) {
        this.searchBar = new JTextField();
        this.title = new JLabel(
                "<html><body><center><h1 style=\"color: blue; text-align: center\">"+titulo+"</h1></center></body></html>");

        this.title.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSearch.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.searchBar.setMargin(new Insets(5, 1, 5, 1));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 50, 0, 50));
        this.add(this.title);
        this.add(Box.createVerticalStrut(10));
        this.add(searchBar);
        this.add(Box.createVerticalStrut(10));
        this.add(btnSearch);
        this.add(Box.createVerticalStrut(20));
    }
    
}

/**
 * 
 * Tabla de elementos
 *
 */
class ItemList extends JPanel {
    private static final long serialVersionUID = 1L;

    public JTable productosTabla;
    public DefaultTableModel modelo;
    private JScrollPane scrollPanel;

    public ItemList(String[] columnas, int[] editable) {
        this.productosTabla = new JTable()
        {
			private static final long serialVersionUID = -4796854934713342244L;

			public boolean isCellEditable(int rowIndex, int vColIndex) {
                
				for (int i : editable) {
					if(i == vColIndex) return true;
				}
				
				return false;
            }
			
        };
        
        
        productosTabla.setDefaultRenderer(Object.class, new ButtonTableRender(productosTabla));
        productosTabla.setDefaultEditor(Object.class, new ButtonTableRender(productosTabla));
        
        productosTabla.setCellSelectionEnabled(true);
        
        this.modelo = (DefaultTableModel) this.productosTabla.getModel();        
        this.modelo.setColumnIdentifiers(columnas);

        this.scrollPanel = new JScrollPane(this.productosTabla);

        // Scroll
        this.scrollPanel.setPreferredSize(new Dimension(900, 400));

        // Panel
        this.add(this.scrollPanel);
    }

}