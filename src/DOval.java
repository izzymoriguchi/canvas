import java.awt.*;

public class DOval extends DShape {
    public DOval() {
        super();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.fillOval(dShapeModel.getX(), dShapeModel.getY(), dShapeModel.getWidth(), dShapeModel.getHeight());
    }
}
