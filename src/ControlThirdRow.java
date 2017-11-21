import javax.swing.*;
import java.awt.*;

public class ControlThirdRow extends JPanel {
    public ControlThirdRow() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JTextField textField = new JTextField();
        JComboBox<String> fonts = new JComboBox<>(new String[] {"Arial", "Calibri", "Consolas"});
        add(textField);
        add(fonts);
        setVisible(true);
    }
}
