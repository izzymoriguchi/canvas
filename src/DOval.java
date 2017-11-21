import java.awt.*;

public class DOval extends DShape {
    public DOval() {
        super();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (isSelected) {
            g.drawString("x", dShapeModel.getX(), dShapeModel.getY());
        }
        g.drawOval(dShapeModel.getX(), dShapeModel.getY(), dShapeModel.getWidth(), dShapeModel.getHeight());
    }
}
