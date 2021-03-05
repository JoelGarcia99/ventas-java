package vistas;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import design.Buttons;
import design.TableButton;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;

public class Vendedores extends JPanel{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final BorderLayout layout;
    private final SearchBarVendedor searchBar;
    private final VendedoresList VendedoresTabla;

    public Vendedores() {
        this.VendedoresTabla = new VendedoresList();
        this.layout = new BorderLayout();
        this.searchBar = new SearchBarVendedor();

        // configuracion del panel
        this.setLayout(this.layout);
        this.add(this.searchBar, BorderLayout.NORTH);
        this.add(this.VendedoresTabla, BorderLayout.CENTER);
    }
    
}

class SearchBarVendedor extends JPanel {
    
    /**
     *
     */
    private static final long serialVersionUID = -493443701311276889L;
    private JTextField searchBar;
    private Buttons btnSearch;

    public SearchBarVendedor() {
        this.searchBar = new JTextField();
        this.btnSearch = new Buttons("Buscar");

        this.btnSearch.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 50, 0, 50));
        this.add(Box.createVerticalStrut(10));
        this.add(searchBar);
        this.add(Box.createVerticalStrut(10));
        this.add(btnSearch);
        this.add(Box.createVerticalStrut(20));
    }
}

class VendedoresList extends JPanel {
    private static final long serialVersionUID = 1L;
    private final String[] columnas = {
        "Cédula", "Nombre", "Apellido", "Usuario", "Editar", "Eliminar"};

    private JTable VendedoresTabla;
    private JScrollPane scrollPanel;

    Object[][] data = {
        {"1234567890", "Nombre1", "Apellido1", "nomape1", null, null},
        {"1234567890", "Nombre1", "Apellido1", "nomape1", null, null},
        {"1234567890", "Nombre2", "Apellido2", "nomape2", null, null},
        {"1234567890", "Nombre3", "Apellido3", "nomape3", null, null},
    };
    

    public VendedoresList() {
        this.VendedoresTabla = new JTable(data, columnas){
			private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {                
                return false;               
            };

        };

        this.scrollPanel = new JScrollPane(this.VendedoresTabla);


        // configuracion de la tabla
        this.VendedoresTabla.getColumn("Editar").setCellRenderer(new TableButton("Editar"));
        this.VendedoresTabla.getColumn("Eliminar").setCellRenderer(new TableButton("Eliminar"));

        // Scroll
        this.scrollPanel.setPreferredSize(new Dimension(800, 600));

        // Panel
        this.add(this.scrollPanel);
    }

}
