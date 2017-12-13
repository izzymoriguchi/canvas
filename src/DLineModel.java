import java.awt.*;

public class DLineModel extends DShapeModel {
    private Point upperLeft;
    private Point lowerRight;
    private boolean isAnchorAtLowerRight;

    public DLineModel() {
        super();
        upperLeft = new Point(0, 0);
        lowerRight = new Point(0, 0);
    }

    public Point getUpperLeft() {
        return upperLeft;
    }

    public Point getLowerRight() {
        return lowerRight;
    }

    public Integer[] getBounds() {
        bounds[0] = upperLeft.x;
        bounds[1] = upperLeft.y;
        bounds[2] = lowerRight.x;
        bounds[3] = lowerRight.y;
        return bounds;
    }

    public void updateBounds(Point tHat, Point anchor) {
        if (getUpperLeft().x == getLowerRight().x && getUpperLeft().y == getLowerRight().y) {
            if (isAnchorAtLowerRight) {
                setUpperLeft(tHat);
            } else {
                setLowerRight(tHat);
            }
        } else if (anchor.x + 4 == getUpperLeft().x && anchor.y + 4 == getUpperLeft().y) {
            isAnchorAtLowerRight = false;
            setLowerRight(tHat);
        } else if (anchor.x + 4 == getLowerRight().x && anchor.y + 4 == getLowerRight().y) {
            isAnchorAtLowerRight = true;
            setUpperLeft(tHat);
        }
    }

    public void updateLocation(int newX, int newY) {
        int origWidth = Math.abs(lowerRight.x - upperLeft.x);
        int origHeight = Math.abs(lowerRight.y - upperLeft.y);
        setUpperLeft(new Point(newX, newY));
        setLowerRight(new Point(newX + origWidth, newY + origHeight));
    }

    public int[] getUpperLeftCorderInfo() {
        return new int[] {upperLeft.x, upperLeft.y};
    }

    public void setUpperLeft(Point upperLeft) {
        this.upperLeft = upperLeft;
        notifyListeners();
    }

    public void setLowerRight(Point lowerRight) {
        this.lowerRight = lowerRight;
        notifyListeners();
    }

    public void mimic(DShapeModel other) {
        this.setUpperLeft(((DLineModel) other).getUpperLeft());
        this.setLowerRight(((DLineModel) other).getLowerRight());
        this.setColor(other.getColor());
        this.setSelected(other.isSelected());
        this.setBounds(other.getBounds());
    }
}
