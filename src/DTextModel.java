import javax.swing.*;
import java.awt.*;

public class DTextModel extends DShapeModel {
    private String text;
    private String font;
    private JTextField textField;
    private JComboBox<String> fontList;

    public DTextModel() {
        super();
        text = "Hello";
        font = "Dialog";
    }

    public String getText() {
        return text;
    }


    public String getFont() {
        return font;
    }

    public JTextField obtainTextField() {
        return textField;
    }

    public JComboBox<String> obtainFontList() {
        return fontList;
    }

    public void setText(String text) {
        this.text = text;
        notifyListeners();
    }

    public void setFont(String font) {
        this.font = font;
        notifyListeners();
    }

    public void attachTextField(JTextField text) {
        textField = text;
    }

    public void attachFontList(JComboBox<String> fontList) {
        this.fontList = fontList;
    }

    public Font computeFont(Graphics g) {
        double size = 1.0;
        Font font = new Font(getFont(), Font.PLAIN, (int) size);
        FontMetrics metrics = g.getFontMetrics(font);
        int fontHeight = metrics.getHeight();
        while (fontHeight < getHeight()) {
            size = (size * 1.10) + 1;
            font = new Font(getFont(), Font.PLAIN, (int) size);
            metrics = g.getFontMetrics(font);
            fontHeight = metrics.getHeight();
        }
        font = new Font(getFont(), Font.PLAIN, ((int) size) - 1);
        return font;
    }

    @Override
    public void mimic(DShapeModel other) {
        super.mimic(other);
        this.setFont(((DTextModel) other).getFont());
        this.setText(((DTextModel) other).getText());
    }
}
