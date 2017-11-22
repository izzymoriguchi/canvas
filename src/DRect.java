import java.awt.*;

public class DRect extends DShape {
    public DRect() {
        super();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (isSelected()) {
            g.drawString("x", dShapeModel.getX(), dShapeModel.getY());

        }
        Rectangle test = new Rectangle();
        g.drawRect(dShapeModel.getX(), dShapeModel.getY(), dShapeModel.getWidth(), dShapeModel.getHeight());

    }
}
