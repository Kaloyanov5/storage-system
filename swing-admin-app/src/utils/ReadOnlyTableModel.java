package utils;

import javax.swing.table.DefaultTableModel;

public class ReadOnlyTableModel extends DefaultTableModel {
    public ReadOnlyTableModel(String[] data, int columnNames) {
        super(data, columnNames);
    }

    public ReadOnlyTableModel(Object[][] data, String[] strings) {
        super(data, strings);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // All cells are non-editable
    }
}
