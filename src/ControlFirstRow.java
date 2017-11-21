import javax.swing.*;
import java.util.Random;

public class ControlFirstRow extends JPanel {
    private final int RANDOM_COORDINATE_BOUNDS = 350;
    private final int RANDOM_SIZE_BOUNDS = 200;
    public ControlFirstRow(Canvas canvas) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        Random rand = new Random();

        JLabel addLabel = new JLabel("Add");
        JButton addRectButton = new JButton("Rect");
        addRectButton.addActionListener(e -> {
            DRectModel dShapeModel = new DRectModel();
            int x = rand.nextInt(RANDOM_COORDINATE_BOUNDS);
            int y = rand.nextInt(RANDOM_COORDINATE_BOUNDS);
            int width = rand.nextInt(RANDOM_SIZE_BOUNDS);
            int height = rand.nextInt(RANDOM_SIZE_BOUNDS);
            dShapeModel.setX(x);
            dShapeModel.setY(y);
            dShapeModel.setWidth(width);
            dShapeModel.setHeight(height);
            canvas.addShape(dShapeModel);
        });

        JButton addOvalButton = new JButton("Oval");
        addOvalButton.addActionListener(e -> {
            DOvalModel dShapeModel = new DOvalModel();
            int x = rand.nextInt(RANDOM_COORDINATE_BOUNDS);
            int y = rand.nextInt(RANDOM_COORDINATE_BOUNDS);
            int width = rand.nextInt(RANDOM_SIZE_BOUNDS);
            int height = rand.nextInt(RANDOM_SIZE_BOUNDS);
            dShapeModel.setX(x);
            dShapeModel.setY(y);
            dShapeModel.setWidth(width);
            dShapeModel.setHeight(height);
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
