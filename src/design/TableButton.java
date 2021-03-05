package design;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

public class TableButton extends JButton implements TableCellRenderer {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String nombre;

    public TableButton(String nombre) {
        this.nombre = nombre;
        
        setOpaque(true);
        setText(this.nombre);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
    int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        return this;
    }    
}

