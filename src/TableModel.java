import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel implements ModelListener {
    Canvas canvas;
    private final String[] COLUMN_NAMES = new String[] {"X", "Y", "Width", "Height"};
    public TableModel(Canvas canvas) {
        this.canvas = canvas;
        canvas.attachModelListener(this);
    }
    @Override
    public int getRowCount() {
        return canvas.getShapes().size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DShapeModel model = canvas.getShapes().get(rowIndex).getdShapeModel();
        Object valueAt = null;
        if (columnIndex == 0) { // means return X
            if (model instanceof  DLineModel) {
                valueAt = "Start X " + ((DLineModel) model).getUpperLeft().x;
            } else {
                valueAt = model.getX();
            }
        } else if (columnIndex == 1) { // means return Y
            if (model instanceof  DLineModel) {
                valueAt = "Start Y " + ((DLineModel) model).getUpperLeft().y;
            } else {
                valueAt = model.getY();
            }
        } else if (columnIndex == 2) { // means return Width
            if (model instanceof  DLineModel) {
                valueAt = "End X " + ((DLineModel) model).getLowerRight().x;
            } else {
                valueAt = model.getWidth();
            }
        } else if (columnIndex == 3) { // means return Height
            if (model instanceof DLineModel) {
                valueAt = "End Y " + ((DLineModel) model).getLowerRight().y;
            } else {
                valueAt = model.getHeight();
            }
        }
        return valueAt;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public void modelChanged(DShapeModel model) {
        fireTableDataChanged();
    }
}
