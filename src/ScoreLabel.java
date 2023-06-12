import javax.swing.*;
import java.awt.*;

public class ScoreLabel  extends JLabel {
    private int score;

    public ScoreLabel(int initialX, int initialY, int width, int height){
        setForeground(Color.BLACK);
        setFont(new Font("Serif", Font.BOLD, 40));
        setBounds(initialX,initialY,width,height);
        setText(Integer.toString(score));
    }

    public void incrementScore(){
        score += 1;
        setText(Integer.toString(score));
    }

    public int getScore(){
        return score;
    }

    public void reset(){
        score = 0;
        setText(Integer.toString(score));
    }
}
