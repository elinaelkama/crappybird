import javax.swing.*;
import java.awt.*;

public class Bird extends JLabel {

    private float momentumY = 0f;

    private static final float TERMINAL_VELOCITY = 6f;

    private int lowerBoundary;

    public Bird(int initialX, int initialY, int width, int height, int lowerBoundary) {
        ImageIcon birdUp = new ImageIcon(getClass().getResource("./up.png"));
        ImageIcon birdDown = new ImageIcon(getClass().getResource("down.png"));

        birdUp = new ImageIcon(birdUp.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
        birdDown = new ImageIcon(birdDown.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));

        //-10 to compensate png not being as big as bounds
        this.setBounds(initialX, initialY, width - 10, height -10);
        //this.setBackground(Color.red);
        this.setIcon(birdUp);
        //this.setOpaque(true);

        this.lowerBoundary = lowerBoundary - height;
    }

    public void applyMomentum(){
        setLocation(getX(), getY() + Math.round(momentumY));
        if (getY() < 0 && momentumY < 0){
            momentumY = 0f;
        }else if(getY() > lowerBoundary && momentumY > 0){
            momentumY = 0f;
        }
    }

    public void applyGravity(float strength) {
        if (momentumY < TERMINAL_VELOCITY && getY() < lowerBoundary) {
            momentumY += strength;
        }
    }

    public void flap(float strength) {
        if (momentumY > -TERMINAL_VELOCITY && getY() > 0) {
            float flapStrength = strength;
            if (momentumY < 0) {
                float attenuationFactor = 0.5f; // Adjust the attenuation factor as needed
                flapStrength *= attenuationFactor;
            }
            momentumY -= flapStrength;
        }
    }

    public void iconUp(){
        ImageIcon birdUp = new ImageIcon(getClass().getResource("./up.png"));
        birdUp = new ImageIcon(birdUp.getImage().getScaledInstance(getWidth(), getHeight(), java.awt.Image.SCALE_SMOOTH));
        setIcon(birdUp);
    }

    public void iconDown(){
        ImageIcon birdDown = new ImageIcon(getClass().getResource("down.png"));
        birdDown = new ImageIcon(birdDown.getImage().getScaledInstance(getWidth(), getHeight(), java.awt.Image.SCALE_SMOOTH));
        setIcon(birdDown);
    }

    private float calculatePower(float value) {
        float powerBase = 0.5f;  // Adjust this value to control the curve shape
        return (float) Math.pow(powerBase, Math.abs(value));
    }

    public boolean collidesWith(Obstacle obstacle){
        for (Rectangle rect : obstacle.getRectangles()) {
            if(getBounds().intersects(rect)){
                return true;
            }
        }
        return false;
    }
}
