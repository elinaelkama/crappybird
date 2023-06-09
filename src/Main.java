import javax.swing.*;
import java.awt.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        GameConfig config = new GameConfig();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("CrappyBird");
        frame.setResizable(false);

        Game game = new Game(config.getWindowWidth(), config.getWindowHeight(), config);
        frame.addKeyListener(game);

        frame.add(game);
        frame.pack();
        frame.setVisible(true);
        game.run();
    }
}