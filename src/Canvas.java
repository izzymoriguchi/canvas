import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Canvas extends JPanel implements ModelListener{
    ArrayList<DShape> shapes;
    Point p;
    public Canvas() {
        super();
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.white);
        shapes = new ArrayList<>();

        MouseListeners listeners = new MouseListeners();
        addMouseListener(listeners);
        addMouseMotionListener(listeners);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (DShape shape : shapes) {
            shape.draw(g);
            shape.drawKnobs(g);
        }
    }

    public void addShape(DShapeModel model) {
        if (model instanceof DRectModel) {
            DRect rect = new DRect();
            model.addListener(this);
            rect.setdShapeModel(model);
            shapes.add(rect);
        } else if (model instanceof DOvalModel) {
            DOval oval = new DOval();
            model.addListener(this);
            oval.setdShapeModel(model);
            shapes.add(oval);
        }
        repaint();
    }

    public void removeShape() {
        for (DShape shape : shapes) {
            if (shape.isSelected()) {
                shape.dShapeModel.removeListener(this);
                shapes.remove(shape);
                break;
            }
        }
        repaint();
    }

    public boolean isWithinBounds(int clickedX, int clickedY, int x1, int y1, int x2, int y2) {
        return x1 - 4.5 <= clickedX && clickedX <= x2 + 4.5 && y1 - 4.5 <= clickedY && clickedY <= y2 + 4.5;
    }


    public void setSelectedShape(int x, int y) {
        for (DShape shape : shapes) {
            if (shape.isSelected()) {
                shape.setSelected(false);
            }
        }
        // look for the last shape in the list --> means that shape is on top of the other
        for (int i = shapes.size() - 1; i >= 0; i--) {
            DShape shape = shapes.get(i);
            int[] b = shape.getBounds();
            if (isWithinBounds(x, y, b[0], b[1], b[2], b[3])) {
                shape.setSelected(true);
                break;
            }
        }
    }

    public Point[] getSelectedKnob(int x, int y) {
        Point tHat = null;
        Point anchor = null;
        for (DShape shape : shapes) {
            if (shape.isSelected()) {
                tHat = shape.getKnobSelected(x, y);
                if (tHat != null) {
                    anchor = shape.getAnchor(tHat);
                }

            }
        }
        return new Point[] {tHat, anchor};
    }

    public DShape getSelectedShape() {
        DShape selectedShape = null;
        for (DShape shape : shapes) {
            if (shape.isSelected()) {
                selectedShape = shape;
                break;
            }
        }
        return selectedShape;
    }

    @Override
    public void modelChanged(DShapeModel model) {
        repaint();
    }

    private class MouseListeners extends MouseAdapter {
        int diffX = 0;
        int diffY = 0;
        Point tHat = null;
        Point anchor = null;
        Point[] tHatAndAnchor = new Point[2];

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            setSelectedShape(e.getX(), e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            setSelectedShape(e.getX(), e.getY());
            tHatAndAnchor = getSelectedKnob(e.getX(), e.getY());
            tHat = tHatAndAnchor[0];
            anchor = tHatAndAnchor[1];
            if (tHat == null) {
                diffX = e.getX() - getSelectedShape().dShapeModel.getX();
                diffY = e.getY() - getSelectedShape().dShapeModel.getY();
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (tHat == null) {
                getSelectedShape().dShapeModel.setX(e.getX() - diffX);
                getSelectedShape().dShapeModel.setY(e.getY() - diffY);
            } else {
                tHat.x = e.getX();
                tHat.y = e.getY();
                int newWidth = Math.abs(tHat.x - anchor.x);
                int newHeight = Math.abs(tHat.y - anchor.y);
                if (tHat.x < anchor.x) {
                    getSelectedShape().dShapeModel.setX(tHat.x + 4); // ToDo: add 4.5 - half of knob size
                } else {
                    getSelectedShape().dShapeModel.setX(anchor.x + 4);
                }

                if (tHat.y < anchor.y) {
                    getSelectedShape().dShapeModel.setY(tHat.y + 4);
                } else {
                    getSelectedShape().dShapeModel.setY(anchor.y + 4);
                }
                getSelectedShape().dShapeModel.setWidth(newWidth);
                getSelectedShape().dShapeModel.setHeight(newHeight);
            }
        }
    }
}
