import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Whiteboard extends JFrame {
    public Whiteboard(String title) {
        super(title);
        setLayout(new BorderLayout());
        Canvas canvas = new Canvas();
        add(canvas, BorderLayout.CENTER);
//        canvas.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//            }
//        });

        ControlFirstRow firstRow = new ControlFirstRow(canvas);
        ControlSecondRow secondRow = new ControlSecondRow();
        ControlThirdRow thirdRow = new ControlThirdRow();
        ControlFourthRow fourthRow = new ControlFourthRow();

        JPanel allControls = new JPanel();
        allControls.setLayout(new BoxLayout(allControls, BoxLayout.Y_AXIS));
        allControls.add(firstRow);
        allControls.add(secondRow);
        allControls.add(thirdRow);
        allControls.add(fourthRow);
        add(allControls, BorderLayout.WEST);
        for (Component comp : allControls.getComponents()) {
            ((JComponent) comp).setAlignmentX(Box.LEFT_ALIGNMENT);
        }

        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public static void main(String[] args) {
        Whiteboard whiteboard = new Whiteboard("Whiteboard");
    }


}
