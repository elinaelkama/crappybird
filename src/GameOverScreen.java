import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends JLabel {

    private String gameOverText;
    public GameOverScreen(int initialX, int initialY, int width, int height, String text){
        this.setBounds(initialX, initialY, width, height);
        setText(" ");
        setFont(new Font("Serif", Font.BOLD, 40));
        gameOverText = text;
    }

    public void show(){
        setText(gameOverText);
    }

    public void hide(){
        setText(" ");
    }
}
