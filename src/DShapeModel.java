import java.awt.*;

/**
 * Doesn't do any drawing, but stores the coordinate information of shape
 */
public class DShapeModel {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    int[] bounds;

    public DShapeModel() {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        color = Color.GRAY;
        bounds = new int[4];
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Color getColor() {
        return color;
    }

    public int[] getBounds() {
        bounds[0] = getX();
        bounds[1] = getY();
        bounds[2] = getWidth();
        bounds[3] = getHeight();
        return bounds;
    }
}
