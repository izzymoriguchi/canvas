import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Canvas extends JPanel implements ModelListener{
    ArrayList<DShape> shapes;
    public Canvas() {
        super();
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.white);
        shapes = new ArrayList<>();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setSelectedShape(e.getX(), e.getY());
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (DShape shape : shapes) {
            shape.draw(g);
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

    public boolean isWithinBounds(int clickedX, int clickedY, int x1, int y1, int x2, int y2) {
        return x1 <= clickedX && clickedX <= x2 && y1 <= clickedY && clickedY <= y2;
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

    @Override
    public void modelChanged(DShapeModel model) {
        repaint();
    }
}
