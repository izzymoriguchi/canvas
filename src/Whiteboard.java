import javax.swing.*;
import java.awt.*;

public class Whiteboard extends JFrame {
    public Whiteboard(String title) {
        super(title);
        setLayout(new BorderLayout());
        Canvas canvas = new Canvas();
        add(canvas, BorderLayout.CENTER);

        Controls controls = new Controls(canvas);
        add(controls, BorderLayout.WEST);
        for (Component comp : controls.getComponents()) {
            ((JComponent) comp).setAlignmentX(Box.LEFT_ALIGNMENT);
        }

        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new Whiteboard("Whiteboard");
        new Whiteboard("Whiteboard");
    }
}
