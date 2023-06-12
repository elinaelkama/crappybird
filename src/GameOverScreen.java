import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends JLabel {

    private String gameOverText;

    public GameOverScreen(int initialX, int initialY, int width, int height, String text){
        setFont(new Font("Serif", Font.BOLD, 40));
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setBounds(initialX, initialY, width, height);
        setText(" ");
        gameOverText = text + "<br>Press any key<br>";
        setOpaque(false);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    public void setText(String text){
        super.setText("<html><center>" + text + "</center></html>");
    }
    public void show(int score){
        setText(gameOverText + "Score: " + Integer.toString(score));
        setOpaque(true);
    }

    public void hide(){
        setText(" ");
        setOpaque(false);
    }
}
