import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Obstacle extends JPanel {
    private int gapSize;
    private float gapPosition;

    private JLabel upper;

    private JLabel lower;

    private boolean passed = false;

    private static final int DRAW_THRESHOLD = 10;

    public Obstacle(int initialX, int initialY, int width, int height, int gapSize, float gapPosition) {
        this.gapSize = gapSize;
        this.gapPosition = gapPosition;

        this.setBounds(initialX, initialY, width, height);
        this.setLayout(null);
        setVisible(true);

        upper = new JLabel();
        lower = new JLabel();

        this.setBackground(new Color(173, 216, 230));

        int gapStart = Math.round((height - gapSize) * gapPosition);

        upper.setBounds(0, 0, width, gapStart);
        lower.setBounds(0, gapStart + gapSize, width, height - upper.getHeight() - gapSize);

        ImageIcon vine = new ImageIcon(getClass().getResource("./vine500.png"));
        ImageIcon pillar = new ImageIcon(getClass().getResource("./pillar500.png"));

        int pillarHeight = height - upper.getHeight() - gapSize;
        int vineHeight = gapStart;

        if (pillarHeight > DRAW_THRESHOLD) {
            Image pillarImage = pillar.getImage();
            BufferedImage bufferedPillarImage = new BufferedImage(pillarImage.getWidth(null), pillarImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedPillarImage.createGraphics();
            g2d.setComposite(AlphaComposite.Src);
            g2d.drawImage(pillarImage, 0, 0, null);
            g2d.dispose();

            BufferedImage croppedPillarImage = bufferedPillarImage.getSubimage(0, 0, width, pillarHeight);
            pillar = new ImageIcon(croppedPillarImage);
            lower.setIcon(pillar);
        }

        if (vineHeight > DRAW_THRESHOLD) {
            Image vineImage = vine.getImage();
            BufferedImage bufferedVineImage = new BufferedImage(vineImage.getWidth(null), vineImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedVineImage.createGraphics();
            g2d.setComposite(AlphaComposite.Src);
            g2d.drawImage(vineImage, 0, 0, null);
            g2d.dispose();

            BufferedImage croppedVineImage = bufferedVineImage.getSubimage(0, bufferedVineImage.getHeight() - vineHeight, width, vineHeight);
            vine = new ImageIcon(croppedVineImage);
            upper.setIcon(vine);
        }


        this.add(upper);
        this.add(lower);

        this.setOpaque(true);
    }

    public ArrayList<Rectangle> getCollisionBoxes(){
        ArrayList<Rectangle> rectangles = new ArrayList<>();
        if (upper.getHeight() > DRAW_THRESHOLD) {
            rectangles.add(new Rectangle(this.getX() + 10, 0, upper.getWidth() - 20, upper.getHeight() - 20));
        }

        if (lower.getHeight() > DRAW_THRESHOLD) {
            rectangles.add(new Rectangle(this.getX() + 20, lower.getY(), lower.getWidth() - 40, lower.getHeight()));
        }
        return rectangles;
    }

    public ArrayList<Rectangle> getScoreBoxes(){
        ArrayList<Rectangle> boxes = new ArrayList<>();
        boxes.add(new Rectangle(this.getX(), lower.getY() - gapSize, this.getWidth(), gapSize));
        return boxes;
    }

    public void setPassed(){
        passed = true;
    }

    public boolean getPassed(){
        return passed;
    }
}
