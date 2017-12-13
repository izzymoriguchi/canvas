import java.awt.*;
import java.awt.geom.Rectangle2D;

public class DText extends DShape {

    public DText() {
        super();
    }

    @Override
    public void draw(Graphics g) {
        DTextModel dTextModel = ((DTextModel) dShapeModel);
        Font font = dTextModel.computeFont(g);
        g.setFont(font);
        g.setColor(dShapeModel.getColor());
        FontMetrics metrics = g.getFontMetrics(font);
        int y = dShapeModel.getY() + ((dShapeModel.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        Shape clip = g.getClip();
        g.setClip(clip.getBounds().createIntersection(new Rectangle2D.Double(dTextModel.getX(), dTextModel.getY(),
                dTextModel.getWidth(), dTextModel.getHeight())));

        g.drawString(dTextModel.getText(), dShapeModel.getX(), y);
        g.setClip(clip);
    }
}
