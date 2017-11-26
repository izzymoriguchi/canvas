import java.awt.*;

public class DShape {
    protected DShapeModel dShapeModel;
    protected static final int KNOB_SIZE = 9;
    protected Point[] knobs;
    public DShape() {
    }

    public void draw(Graphics g) {
        g.setColor(dShapeModel.getColor());
    }

    public void drawKnobs(Graphics g) {
        if (isSelected()) {
            g.setColor(Color.black);
            knobs = getKnobs();
            for (Point point : knobs) {
                g.fillRect(point.x, point.y, KNOB_SIZE, KNOB_SIZE);
            }
        }
    }

    public int[] getBounds() {
        return dShapeModel.getBounds();
    }

//    public int[] getBigBounds() {
//        int[] bigBounds = new int[dShapeModel.getBounds().length];
//        int[] smallBounds = dShapeModel.getBounds();
//        for (int i = 0; i < bigBounds.length; i++) {
//            bigBounds[i] = smallBounds[i] +
//        }
//        bigBounds[0] =
//    }

    public Point[] getKnobs() {
        int x = dShapeModel.getX();
        int y = dShapeModel.getY();
        int w = dShapeModel.getWidth();
        int h = dShapeModel.getHeight();
        Point upperLeft = new Point(x - KNOB_SIZE / 2, y - KNOB_SIZE / 2);
        Point upperRight = new Point(x + w - KNOB_SIZE / 2, y - KNOB_SIZE / 2);
        Point lowerLeft = new Point(x - KNOB_SIZE / 2, y + h - KNOB_SIZE / 2);
        Point lowerRight = new Point(x + w - KNOB_SIZE / 2, y + h - KNOB_SIZE / 2);
        return new Point[] {upperLeft, upperRight, lowerLeft, lowerRight};
    }

    public Point getKnobSelected(int x, int y) {
        Point knobSelected = null;
        for (Point p : getKnobs()) {
            if (p.x <= x && x <= p.x + KNOB_SIZE && p.y <= y && y <= p.y + KNOB_SIZE) {
                knobSelected = p;
            }
        }
        return knobSelected;
    }

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
            anchorIndex = 3;
        } else if (index == 1) {
            anchorIndex = 2;
        } else if (index == 2) {
            anchorIndex = 1;
        } else if (index == 3) {
            anchorIndex = 0;
        }
        return knobs[anchorIndex];
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
