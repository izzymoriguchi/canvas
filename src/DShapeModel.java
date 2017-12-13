import java.awt.*;
import java.util.ArrayList;


public class DShapeModel {
    protected int id;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Color color;
    protected Integer[] bounds;
    protected boolean isSelected;
    protected ArrayList<ModelListener> lstOfListeners;

    public DShapeModel() {
        color = Color.GRAY;
        isSelected = false;
        bounds = new Integer[4];
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

    public void setBounds(Integer[] bounds) {
        this.bounds = bounds;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public Integer[] getBounds() {
        bounds[0] = getX();
        bounds[1] = getY();
        bounds[2] = getX() + getWidth();
        bounds[3] = getY() + getHeight();
        return bounds;
    }

    public int[] getUpperLeftCorderInfo() {
        return new int[] {x, y};
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        notifyListeners();
    }

    public void updateBounds(Point tHat, Point anchor) {
        setX(Math.min(tHat.x, anchor.x) + 4);
        setY(Math.min(tHat.y, anchor.y) + 4);
        setWidth(Math.abs(tHat.x - anchor.x));
        setHeight(Math.abs(tHat.y - anchor.y));
    }

    public void updateLocation(int newX, int newY) {
        setX(newX);
        setY(newY);
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

    public void mimic(DShapeModel other) {
        this.setX(other.getX());
        this.setY(other.getY());
        this.setColor(other.getColor());
        this.setWidth(other.getWidth());
        this.setHeight(other.getHeight());
        this.setSelected(other.isSelected);
        this.setBounds(other.getBounds());
    }
}
