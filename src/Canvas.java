import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Canvas extends JPanel {
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
            rect.setdShapeModel(model);
            shapes.add(rect);
        } else if (model instanceof DOvalModel) {
            DOval oval = new DOval();
            oval.setdShapeModel(model);
            shapes.add(oval);
        }
        repaint();
    }

    public boolean isWithinBounds(int clickedX, int clickedY, int shapeX, int shapeY, int shapeWidth, int shapeHeight) {
        return shapeX <= clickedX && clickedX <= shapeX + shapeWidth
                && shapeY <= clickedY && clickedY <= shapeY + shapeHeight;
    }

    public void setSelectedShape(int x, int y) {
        for (DShape shape : shapes) {
            if (shape.isSelected) {
                shape.setSelected(false);
            }
        }
        for (DShape shape : shapes) {
            int[] b = shape.getBounds();
            if (isWithinBounds(x, y, b[0], b[1], b[2], b[3])) {
                shape.setSelected(true);
                repaint();
            }
        }
    }
}
