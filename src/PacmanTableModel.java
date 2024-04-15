import javax.swing.table.AbstractTableModel;

public class PacmanTableModel extends AbstractTableModel {

    private int wiersze;
    private int kolumny;
    private Object[][] board;

    public PacmanTableModel(int wiersze, int kolumny) {
        this.wiersze = wiersze;
        this.kolumny = kolumny;
        board = new Object[wiersze][kolumny];
    }

    @Override
    public int getRowCount() {

        return wiersze;
    }

    @Override
    public int getColumnCount() {

        return kolumny;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return board[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        board[rowIndex][columnIndex] = aValue;
        fireTableCellUpdated(rowIndex,columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

}
