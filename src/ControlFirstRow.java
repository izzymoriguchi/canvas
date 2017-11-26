import javax.swing.*;
import java.util.Random;

public class ControlFirstRow extends JPanel {
    private final int RANDOM_COORDINATE_BOUNDS = 350;
    private final int RANDOM_SIZE_BOUNDS = 200;
    private final int X = 10;
    private final int Y = 10;
    private final int WIDTH = 20;
    private final int HEIGHT = 20;
    public ControlFirstRow(Canvas canvas) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JLabel addLabel = new JLabel("Add");
        JButton addRectButton = new JButton("Rect");
        addRectButton.addActionListener(e -> {
            DRectModel dShapeModel = new DRectModel();
            dShapeModel.setX(X);
            dShapeModel.setY(Y);
            dShapeModel.setWidth(WIDTH);
            dShapeModel.setHeight(HEIGHT);
            canvas.addShape(dShapeModel);
        });

        JButton addOvalButton = new JButton("Oval");
        addOvalButton.addActionListener(e -> {
            DOvalModel dShapeModel = new DOvalModel();
            dShapeModel.setX(X);
            dShapeModel.setY(Y);
            dShapeModel.setWidth(WIDTH);
            dShapeModel.setHeight(HEIGHT);
            canvas.addShape(dShapeModel);
        });

        JButton addLineButton = new JButton("Line");
        JButton addTextButton = new JButton("text");

        add(addLabel);
        add(addRectButton);
        add(addOvalButton);
        add(addLineButton);
        add(addTextButton);
        setVisible(true);
    }
}
