import javax.swing.*;
import java.awt.*;

public class ControlSecondRow extends JPanel {
    public ControlSecondRow(Canvas canvas) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JButton setColorButton = new JButton("Set Color");
        setColorButton.addActionListener(e -> {
            Color initialColor = Color.GRAY;
            Color newColor = JColorChooser.showDialog(this, "Select a color", initialColor);
        });
        add(setColorButton);
        setVisible(true);
    }
}
