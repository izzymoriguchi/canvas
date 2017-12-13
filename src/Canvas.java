import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Canvas extends JPanel implements ModelListener {
    private ArrayList<DShape> shapes;
    private Point p;
    private JTextField textField;
    private ModelListener tableListener;
    private ModelListener serverListener;
    private boolean isNetworkingOn;
    public Canvas() {
        super();
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.white);
        shapes = new ArrayList<>();
        MouseListeners listeners = new MouseListeners();
        addMouseListener(listeners);
        addMouseMotionListener(listeners);
    }

    public void attachModelListener(ModelListener listener) {
        this.tableListener = listener;
    }

    public void attachModelListenerForServer(ModelListener serverListener) {
        this.serverListener = serverListener;
    }

    public void notifyTableListener(DShapeModel model) {
        tableListener.modelChanged(model);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (DShape shape : shapes) {
            shape.draw(g);
            if (!shape.isBeingSaved()) {
                shape.drawKnobs(g);
            }
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
        } else if (model instanceof DLineModel) {
            DLine line = new DLine();
            model.addListener(this);
            line.setdShapeModel(model);
            shapes.add(line);
        } else if (model instanceof DTextModel) {
            DText text = new DText();
            model.addListener(this);
            text.setdShapeModel(model);
            shapes.add(text);
        }
        notifyTableListener(model);
        repaint();
    }

    public void addFirst(DShapeModel model) {
        if (model instanceof DRectModel) {
            DRect rect = new DRect();
            model.addListener(this);
            rect.setdShapeModel(model);
            shapes.add(0, rect);
        } else if (model instanceof DOvalModel) {
            DOval oval = new DOval();
            model.addListener(this);
            oval.setdShapeModel(model);
            shapes.add(0, oval);
        } else if (model instanceof DLineModel) {
            DLine line = new DLine();
            model.addListener(this);
            line.setdShapeModel(model);
            shapes.add(0, line);
        } else if (model instanceof DTextModel) {
            DText text = new DText();
            model.addListener(this);
            text.setdShapeModel(model);
            shapes.add(0, text);
        }
        notifyTableListener(model);
        repaint();
    }

    public DShape removeShape() {
        DShape shapeToBeRemoved = null;
        for (DShape shape : shapes) {
            if (shape.isSelected()) {
                shapeToBeRemoved = shape;
                shape.getdShapeModel().removeListener(this);
                shapes.remove(shape);
                notifyTableListener(shape.dShapeModel);
                break;
            }
        }
        repaint();
        return shapeToBeRemoved;
    }

    public void removeSpecificShape(DShapeModel model) {
        for (DShape shape : shapes) {
            boolean isSame = shape.getdShapeModel().getId() == model.getId();
            if (isSame) {
                shape.getdShapeModel().removeListener(this);
                shapes.remove(shape);
                notifyTableListener(shape.getdShapeModel());
                break;
            }
        }
        repaint();
    }

    public void saveImage(File file) {
        for (DShape shape : shapes) {
            shape.setBeingSaved(true);
        }
        BufferedImage image = (BufferedImage) createImage(getWidth(), getHeight());
        Graphics g = image.getGraphics();

        paintAll(g);
        g.dispose();
        try {
            javax.imageio.ImageIO.write(image, "PNG", file);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        for (DShape shape : shapes) {
            shape.setBeingSaved(false);
        }
    }

    public void clearAll() {
        shapes.clear();
        repaint();
    }

    public DShape moveFront() {
        DShape shapeToBeFront = null;
        for (DShape shape : shapes) {
            if (shape.isSelected()) {
                shapeToBeFront = shape;
                shapes.remove(shape);
                shapes.add(shape);
                notifyTableListener(shape.getdShapeModel());
                break;
            }
        }
        repaint();
        return shapeToBeFront;
    }

    public DShape moveBack() {
        DShape shapeToBeBack = null;
        for (DShape shape : shapes) {
            if (shape.isSelected()) {
                shapeToBeBack = shape;
                shapes.remove(shape);
                shapes.add(0, shape);
                notifyTableListener(shape.getdShapeModel());
                break;
            }
        }
        repaint();
        return shapeToBeBack;
    }

    public void mimicShape(DShapeModel encodedModel) {
        for (DShape shape : shapes) {
            if (shape.getdShapeModel().getId() == encodedModel.getId()) {
                shape.getdShapeModel().mimic(encodedModel);
                break;
            }
        }
    }

    public boolean isWithinBounds(int clickedX, int clickedY, int x1, int y1, int x2, int y2) {
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);
        return minX - 4.5 <= clickedX && clickedX <= maxX + 4.5 && minY - 4.5 <= clickedY && clickedY <= maxY + 4.5;
    }

    public void setNetworkingOn(boolean networkingOn) {
        isNetworkingOn = networkingOn;
    }

    public void setSelectedShape(int x, int y) {
        for (DShape shape : shapes) {
            if (shape.isSelected()) {
                shape.setSelected(false);
                if (shape.getdShapeModel() instanceof DTextModel) {
                    ((DTextModel) shape.getdShapeModel()).obtainTextField().setEnabled(false);
                    ((DTextModel) shape.getdShapeModel()).obtainFontList().setEnabled(false);
                }
            }
        }

        for (int i = shapes.size() - 1; i >= 0; i--) {
            DShape shape = shapes.get(i);
            Integer[] b = shape.getBounds();
            if (isWithinBounds(x, y, b[0], b[1], b[2], b[3])) {
                shape.setSelected(true);
                if (shape.getdShapeModel() instanceof DTextModel) {
                    ((DTextModel) shape.getdShapeModel()).obtainTextField().setEnabled(true);
                    ((DTextModel) shape.getdShapeModel()).obtainFontList().setEnabled(true);
                }
                break;
            }
        }
    }

    public ArrayList<DShape> getShapes() {
        return shapes;
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
        notifyTableListener(model);
        if (serverListener != null) {
            serverListener.modelChanged(model);
        }
    }

    private class MouseListeners extends MouseAdapter {
        int diffX = 0;
        int diffY = 0;
        Point tHat = null;
        Point anchor = null;
        Point[] tHatAndAnchor = new Point[2];

        @Override
        public void mousePressed(MouseEvent e) {
            if (!isNetworkingOn || serverListener != null) {
                setSelectedShape(e.getX(), e.getY());
                tHatAndAnchor = getSelectedKnob(e.getX(), e.getY());
                tHat = tHatAndAnchor[0];
                anchor = tHatAndAnchor[1];
                if (tHat == null) {
                    if (getSelectedShape() != null) {
                        int[] upperLeftCordinates = getSelectedShape().getdShapeModel().getUpperLeftCorderInfo();
                        diffX = e.getX() - upperLeftCordinates[0];
                        diffY = e.getY() - upperLeftCordinates[1];
                    }
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (!isNetworkingOn || serverListener != null) {
                if (tHat == null) {
                    if (getSelectedShape() != null) {
                        getSelectedShape().getdShapeModel().updateLocation(e.getX() - diffX, e.getY() - diffY);
                    }
                } else {
                    tHat.x = e.getX();
                    tHat.y = e.getY();

                    getSelectedShape().getdShapeModel().updateBounds(tHat, anchor);
                }
            }
        }
    }
}
