import javax.swing.*;
import java.awt.*;

public class ControlSecondRow extends JPanel {
    public ControlSecondRow(Canvas canvas) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JButton setColorButton = new JButton("Set Color");
        setColorButton.addActionListener(e -> {
            if (canvas.getSelectedShape()!= null) {
                Color initialColor = canvas.getSelectedShape().dShapeModel.getColor();
                Color newColor = JColorChooser.showDialog(this, "Select a color", initialColor);
                canvas.getSelectedShape().dShapeModel.setColor(newColor);
            }
        });
        add(setColorButton);
        setVisible(true);
    }
}
