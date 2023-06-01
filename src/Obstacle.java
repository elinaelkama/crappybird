import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Obstacle extends JPanel {
    private int gapSize;
    private float gapPosition;

    private JLabel upper;

    private JLabel lower;

    public Obstacle(int initialX, int initialY, int width, int height, int gapSize, float gapPosition) {
        this.gapSize = gapSize;
        this.gapPosition = gapPosition;

        this.setBounds(initialX, initialY, width, height);
        setVisible(true);

        upper = new JLabel();
        lower = new JLabel();

        int gapStart = Math.round((height - gapSize) * gapPosition);

        upper.setBounds(0, 0, width, gapStart);
        lower.setBounds(0, gapStart + gapSize, width, height - upper.getHeight() - gapSize);
        upper.setBackground(new Color(22, 60, 39));
        lower.setBackground(new Color(22, 60, 39));
        upper.setOpaque(true);
        lower.setOpaque(true);

        this.add(upper);
        this.add(lower);

        this.setOpaque(true);
    }


    public List<Rectangle> getRectangles(){
        List<Rectangle> rectangles = new ArrayList<>();
        rectangles.add(new Rectangle(this.getX(), 0, upper.getWidth(), upper.getHeight()));
        rectangles.add(new Rectangle(this.getX(), lower.getY(), lower.getWidth(), lower.getHeight()));
        return rectangles;
    }

}
