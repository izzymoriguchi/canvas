import java.awt.*;

public class DLine extends DShape {
    public DLine() {
        super();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.drawLine(((DLineModel) dShapeModel).getUpperLeft().x, ((DLineModel) dShapeModel).getUpperLeft().y,
                ((DLineModel) dShapeModel).getLowerRight().x, ((DLineModel) dShapeModel).getLowerRight().y);
    }

    @Override
    public Point[] getKnobs() {
        Point upperLeft = new Point(((DLineModel) dShapeModel).getUpperLeft().x - KNOB_SIZE / 2,
                ((DLineModel) dShapeModel).getUpperLeft().y - KNOB_SIZE / 2);
        Point lowerRight = new Point(((DLineModel) dShapeModel).getLowerRight().x - KNOB_SIZE / 2,
                ((DLineModel) dShapeModel).getLowerRight().y - KNOB_SIZE / 2);
        return new Point[] {upperLeft, lowerRight};
    }

    @Override
    public Point getAnchor(Point tHat) {
        int index = 0;
        Point[] tests = getKnobs();
        for (int i = 0; i < tests.length; i++) {
            if (tests[i].x == tHat.x && tests[i].y == tHat.y) {
                index = i;
                break;
            }
        }

        int anchorIndex = 0;
        if (index == 0) {
            anchorIndex = 1;
        }
        return knobs[anchorIndex];
    }
}
