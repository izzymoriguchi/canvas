import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlFourthRow extends JPanel {
    public ControlFourthRow(Canvas canvas) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JButton moveToFrontButton = new JButton("Move To Front");
        moveToFrontButton.addActionListener(e -> {
            canvas.moveFront();
        });
        JButton moveToBackButton = new JButton("Move To Back");
        moveToBackButton.addActionListener(e -> {
            canvas.moveBack();
        });
        JButton removeShapeButton = new JButton("Remove Shape");
        removeShapeButton.addActionListener(e -> {
            canvas.removeShape();
        });
        add(moveToFrontButton);
        add(moveToBackButton);
        add(removeShapeButton);
        setVisible(true);
    }
}
