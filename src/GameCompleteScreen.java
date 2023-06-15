import javax.swing.*;
import java.awt.*;

public class GameCompleteScreen extends JLabel {
    private String text;



    public GameCompleteScreen(int initialX, int initialY, int width, int height){
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setFont(new Font("Serif", Font.BOLD, 40));
        setBounds(initialX,initialY,width,height);
        setText(" ");
        text = "<html><center>CONGRATULATIONS!</center></html>";
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setOpaque(false);
    }

    public void show(){
        setText(text);
        setOpaque(true);
    }

    public void hide(){
        setText(" ");
        setOpaque(false);
    }
}
