import javax.swing.*;

public class ControlSecondRow extends JPanel {
    public ControlSecondRow() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JButton setColorButton = new JButton("Set Color");
        add(setColorButton);
        setVisible(true);
    }
}
