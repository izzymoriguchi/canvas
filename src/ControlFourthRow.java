import javax.swing.*;

public class ControlFourthRow extends JPanel {
    public ControlFourthRow() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JButton moveToFrontButton = new JButton("Move To Front");
        JButton moveToBackButton = new JButton("Move To Back");
        JButton removeShapeButton = new JButton("Remove DShape");
        add(moveToFrontButton);
        add(moveToBackButton);
        add(removeShapeButton);
        setVisible(true);
    }
}
