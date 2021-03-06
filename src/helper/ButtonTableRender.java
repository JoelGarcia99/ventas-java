package helper;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class ButtonTableRender extends AbstractCellEditor implements 
	TableCellRenderer, TableCellEditor {

	private static final long serialVersionUID = 5489565178260042621L;

	private JTable table;
	private int col, row;
	
	public ButtonTableRender(JTable table) {
		this.table = table;
	}
	
	@Override
	public Object getCellEditorValue() {
		col = table.getSelectedColumn();
		row = table.getSelectedRow();
		
		return table.getValueAt(row, col);
	}

	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		if(value instanceof JButton) {
			return (JButton) value;
		}
		
		if(value != null) return new JLabel(value.toString());
		return new JLabel();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		if(value instanceof JButton) {
			return (JButton) value;
		}
		
		if(value != null) return new JLabel(value.toString());
		
		return new JLabel();
	}


}
