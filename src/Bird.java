import javax.swing.*;
import java.awt.*;

public class Bird extends JLabel {

    private float momentumY = 0f;

    private static final float TERMINAL_VELOCITY = 6f;

    private int lowerBoundary;

    public Bird(int initialX, int initialY, int width, int height, int lowerBoundary) {
        this.setBounds(initialX, initialY, width, height);
        this.setBackground(Color.red);
        this.setOpaque(true);

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
