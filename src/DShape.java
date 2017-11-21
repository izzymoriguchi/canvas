import java.awt.*;

public class DShape {
    protected DShapeModel dShapeModel; // should hold a pointer to a DShapeModel
    protected boolean isSelected;
    public DShape() {
        isSelected = false;
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
        this.isSelected = isSelected;
    }
}
