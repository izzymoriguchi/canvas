import java.awt.*;
import java.util.ArrayList;

/**
 * Doesn't do any drawing, but stores the coordinate information of shape
 */
public class DShapeModel {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private int[] bounds;
    private boolean isSelected;
    private ArrayList<ModelListener> lstOfListeners;

    public DShapeModel() {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        color = Color.GRAY;
        isSelected = false;
        bounds = new int[4];
        lstOfListeners = new ArrayList<>();
    }

    public void setX(int x) {
        this.x = x;
        notifyListeners();
    }

    public void setY(int y) {
        this.y = y;
        notifyListeners();
    }

    public void setWidth(int width) {
        this.width = width;
        notifyListeners();
    }

    public void setHeight(int height) {
        this.height = height;
        notifyListeners();
    }

    public void setColor(Color color) {
        this.color = color;
        notifyListeners();
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
        bounds[2] = getX() + getWidth();
        bounds[3] = getY() + getHeight();
        return bounds;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        notifyListeners();
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void addListener(ModelListener listener) {
        lstOfListeners.add(listener);
    }

    public void removeListener(ModelListener listener) {
        lstOfListeners.remove(listener);
    }

    public void notifyListeners() {
        for (ModelListener m : lstOfListeners) {
            m.modelChanged(this);
        }
    }
}
