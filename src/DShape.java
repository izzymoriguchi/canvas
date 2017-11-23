import java.awt.*;

public class DShape {
    protected DShapeModel dShapeModel;
    public DShape() {
    }

    public void draw(Graphics g) {
        g.setColor(dShapeModel.getColor());
    }

    public int[] getBounds() {
        return dShapeModel.getBounds();
    }

    public void setdShapeModel(DShapeModel dShapeModel) {
        this.dShapeModel = dShapeModel;
    }

    public void setSelected(boolean isSelected) {
        dShapeModel.setSelected(isSelected);
    }

    public boolean isSelected() {
        return dShapeModel.isSelected();
    }
}
