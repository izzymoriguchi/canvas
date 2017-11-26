import java.awt.*;

public class DRect extends DShape {
    public DRect() {
        super();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.fillRect(dShapeModel.getX(), dShapeModel.getY(), dShapeModel.getWidth(), dShapeModel.getHeight());
    }
}
