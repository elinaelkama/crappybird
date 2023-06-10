import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends JLabel {

    private String gameOverText;

    private Rectangle initBounds;

    public GameOverScreen(int initialX, int initialY, int width, int height,String text){
        super(text);
        setFont(new Font("Serif", Font.BOLD, 40));
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setBounds(initialX,initialY,width,height);
        setText(" ");
        gameOverText = "<html> <div style=\"text-align: center;\">"+ text + "<br>Press any key to restart</dív> </html>";
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.BOTTOM);
        setOpaque(false);
        setVerticalAlignment(SwingConstants.CENTER);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void show(){
        setText(gameOverText);
        setOpaque(true);
    }

    public void hide(){
        setText(" ");
        setOpaque(false);
    }
}
