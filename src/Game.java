import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

public class Game extends JFrame implements KeyListener {
    private Bird bird;

    private List<Obstacle> obstacles;
    private List<Integer> obstacleRemoveList;
    private Timer timer;

    private boolean isKeyDown = false;
    private boolean gameOver = false;

    private JLabel gameOverText;

    private final int FRAME_RATE = 120;  // Desired frames per second
    private final int DELAY = 1000 / FRAME_RATE;  // Delay between frames in milliseconds

    public Game(int windowWidth, int windowHeight){

        this.setTitle("CrappyBird");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(windowWidth, windowHeight);

        this.setLocationRelativeTo(null);
        this.setLayout(null);

        this.setVisible(true);

        this.addKeyListener(this);
        this.getContentPane().setBackground(new Color(173, 216, 230));

        this.obstacleRemoveList = new ArrayList<>();
        obstacles = new ArrayList<>();

        bird = new Bird(100, windowHeight/2, 80, 80, windowHeight);
        this.add(bird);

        gameOverText = new GameOverScreen(200, 200, 400, 200, "Game Over");
        this.add(gameOverText);

        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
    }

    private void createNewObstacle() {
        int gapSize = 250;

        Random random = new Random();
        float newPos = (random.nextFloat());

        float gapPos = newPos;

        // Create a new obstacle and add it to the list
        Obstacle obstacle = new Obstacle(getWidth(),0, 100, getHeight(), gapSize, gapPos);
        obstacles.add(obstacle);
        this.add(obstacle);
    }

    private void checkCollision() {
        for (Obstacle o : obstacles){
            if (bird.collidesWith(o)){
                gameOver = true;
            }
        }
    }

    public void update(){
        //update game logic move, score, time etc

        checkCollision();

        if (obstacles.size() < 1 || obstacles.get(obstacles.size() -1).getX() < getWidth() - 350){
            createNewObstacle();
        }

        for (int i = 0; i < obstacles.size(); i++){
            obstacles.get(i).setLocation(obstacles.get(i).getX()-3, obstacles.get(i).getY());
            if(obstacles.get(i).getX() <= -obstacles.get(i).getWidth()){
                obstacleRemoveList.add(i);
                this.remove(obstacles.get(i));
            }
        }
        bird.iconUp();
        bird.applyMomentum();
        bird.applyGravity(0.25f);

        if (isKeyDown){
            bird.flap(1.5f);
            bird.iconDown();
        }

        if(obstacleRemoveList.size() > 0){
            for (int obstacleIndex : obstacleRemoveList){
                obstacles.remove(obstacleIndex);
            }
            obstacleRemoveList.clear();
        }

        // Check for game-over condition
        if (gameOver) {
            gameOverText.show();
            timer.stop();
            System.out.println("YOU ARE DEAD~ DEAD~ DEAD~");
        }
    }

    public void run() {
        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        isKeyDown = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        isKeyDown = false;
    }
}
