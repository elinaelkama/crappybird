import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameCompleteScreen extends JLabel {
    private String baseText;

    public GameCompleteScreen(int initialX, int initialY, int width, int height){
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setFont(new Font("Serif", Font.BOLD, 40));
        setBounds(initialX,initialY,width,height);
        setText(" ");
        baseText = "CONGRATULATIONS!";
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setOpaque(false);
    }

    public void setText(String text){
        super.setText("<html><center>" + text + "</center></html>");
    }

    public String getJoke(){
        String[] jokes = {
                "Why did the seagull fly over the sea?<br>Because if it flew over the bay, it would be a bagel!",
                "What do you call a bird that's afraid of heights?<br>>A chicken!",
                "Why don't birds use cell phones?<br>They already have tweet-er!",
                "What do you call a bird that's good at telling jokes?<br>A comedi-hen!",
                "How do crows stick together in a flock?<br>Velcrow!",
                "Why did the pigeon go to school?<br>To improve its cooing skills!",
                "What kind of bird can write?<br>A penguin! They always have a pen-guin their flippers!",
                "How does a bird with a broken wing manage to land safely?<br>With its \"sparrow-chute\"!",
                "What do you get if you cross a parrot with a shark?<br>A bird that talks your ear off and then eats you!",
                "Why don't you ever see birds in hospitals?<br>Because they can't afford the \"bill\"!",
                "What did one duck say to the other duck at the party?<br>\"Let's quack up the dance floor!\"",
                "What's a bird's favorite type of math?<br>Owl-gebra!"
        };
        Random random = new Random();
        return jokes[random.nextInt(0, jokes.length -1)];
    }

    public void show(){
        setText(baseText + "<br>" + getJoke());
        setOpaque(true);
    }

    public void hide(){
        setText(" ");
        setOpaque(false);
    }
}
