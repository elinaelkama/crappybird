import javax.swing.*;
import java.awt.*;

public class LevelCompleteScreen extends JLabel {

    private String text;

    public LevelCompleteScreen(int initialX, int initialY, int width, int height){
        setFont(new Font("Serif", Font.BOLD, 40));
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setBounds(initialX,initialY,width,height);
        setText(" ");
        text = "Level ";
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setOpaque(false);
    }

    public void setText(String text){
        super.setText("<html><center>" + text + "</center></html>");
    }
    public void show(int level, int score){
        setText(text + level + "<br>Score: " + Integer.toString(score) + "<br>Press any key");
        setOpaque(true);
    }

    public void hide(){
        setText(" ");
        setOpaque(false);
    }
}
