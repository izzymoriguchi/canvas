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
        Rectangle rect = new Rectangle(dShapeModel.getX(), dShapeModel.getY(),
                dShapeModel.getWidth(), dShapeModel.getHeight());
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }
}
