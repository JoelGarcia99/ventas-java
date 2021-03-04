package productos;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

import design.Buttons;

import java.awt.Component;
import java.awt.BorderLayout;

public class ProductoPanel extends JPanel{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final BorderLayout layout;
    private final SearchBarProducto searchBar;

    public ProductoPanel() {

        this.layout = new BorderLayout();
        this.searchBar = new SearchBarProducto();

        // configuracion del panel
        this.setLayout(this.layout);
        this.add(this.searchBar, BorderLayout.NORTH);
    }
    
}

class SearchBarProducto extends JPanel {
    
    /**
     *
     */
    private static final long serialVersionUID = -493443701311276889L;
    private JTextField searchBar;
    private Buttons btnSearch;

    public SearchBarProducto() {
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

class ProductList extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ProductList() {
        
    }

}