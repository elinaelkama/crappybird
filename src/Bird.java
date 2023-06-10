import javax.swing.*;
import java.awt.*;

public class Bird {

    private float momentumY = 0f;

    private static final float TERMINAL_VELOCITY = 6f;

    private int lowerBoundary;

    private JLabel label;
    private ImageIcon birdUp;
    private ImageIcon birdDown;

    private Rectangle initLocation;

    public Bird(int initialX, int initialY, int width, int height, int lowerBoundary) {
        ImageIcon birdUp = new ImageIcon(getClass().getResource("./up.png"));
        this.birdUp = new ImageIcon(birdUp.getImage().getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH));

        ImageIcon birdDown = new ImageIcon(getClass().getResource("down.png"));
        this.birdDown = new ImageIcon(birdDown.getImage().getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH));

        label = new JLabel(birdUp);
        initLocation = new Rectangle(initialX, initialY, width, height);
        //-10 to compensate png not being as big as bounds
        label.setBounds(initLocation);
        //this.setBackground(Color.red);
        //label.setIcon(birdUp);
        //this.setOpaque(true);

        this.lowerBoundary = lowerBoundary - height;
    }

    public void applyMomentum(){
        label.setLocation(label.getX(), label.getY() + Math.round(momentumY));
        if (label.getY() < 0 && momentumY < 0){
            momentumY = 0f;
        }else if(label.getY() > lowerBoundary && momentumY > 0){
            momentumY = 0f;
        }
    }

    public void resetMomentum(){
        momentumY = 0;
    }

    public void applyGravity(float strength) {
        if (momentumY < TERMINAL_VELOCITY && label.getY() < lowerBoundary) {
            momentumY += strength;
        }
    }

    public void flap(float strength) {
        if (momentumY > -TERMINAL_VELOCITY && label.getY() > 0) {
            float flapStrength = strength;
            if (momentumY < 0) {
                float attenuationFactor = 0.5f; // Adjust the attenuation factor as needed
                flapStrength *= attenuationFactor;
            }
            momentumY -= flapStrength;
        }
    }

    public void iconUp(){
        label.setIcon(birdUp);
    }

    public void iconDown(){
        label.setIcon(birdDown);
    }

    private float calculatePower(float value) {
        float powerBase = 0.5f;  // Adjust this value to control the curve shape
        return (float) Math.pow(powerBase, Math.abs(value));
    }

    public boolean collidesWith(Obstacle obstacle){
        for (Rectangle rect : obstacle.getRectangles()) {
            if(label.getBounds().intersects(rect)){
                return true;
            }
        }
        return false;
    }

    public JLabel getLabel(){
        return label;
    }

    public void resetPosition(){
        label.setLocation(initLocation.x, initLocation.y);
    }
}
