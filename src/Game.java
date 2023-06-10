import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel implements KeyListener {
    private Bird bird;

    private List<Obstacle> obstacles;
    private List<Integer> obstacleRemoveList;
    private Timer timer;

    private boolean isKeyDown = false;
    private boolean gameOver = false;

    private LocalTime keysDisabledUntil;

    private LocalTime obstaclesDisabledUntil;


    private JLabel gameOverText;

    private int windowWidth;
    private int windowHeight;

    private final int FRAME_RATE = 120;  // Desired frames per second
    private final int DELAY = 1000 / FRAME_RATE;  // Delay between frames in milliseconds

    public Game(int windowWidth, int windowHeight){
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.setPreferredSize(new Dimension(windowWidth, windowHeight));
        this.setBackground(new Color(173, 216, 230));
        this.setLayout(null);




        bird = new Bird(100, windowHeight/2, 80, 80, windowHeight);
        this.add(bird.getLabel());

        setVisible(true);


        gameOverText = new GameOverScreen((windowWidth - 400) / 2, (windowHeight - 200) / 2, 400, 200, "Game Over");
        this.add(gameOverText);
        setComponentZOrder(gameOverText, 0);

        this.obstacleRemoveList = new ArrayList<>();
        obstacles = new ArrayList<>();

        initStage();

        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
    }

    private void initStage() {
        bird.resetPosition();
        bird.resetMomentum();

    }

    private boolean isObstaclesEnabled(){
        if(obstaclesDisabledUntil != null && obstaclesDisabledUntil.isAfter(LocalTime.now())){
            return false;
        }
        return true;
    }

    private boolean isKeysEnabled(){
        if(keysDisabledUntil != null && keysDisabledUntil.isAfter(LocalTime.now())){
            return false;
        }
        return true;
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

    private boolean checkCollision() {
        for (Obstacle o : obstacles){
            if (bird.collidesWith(o)){
                gameOver = true;
                gameOverText.show();
            }
        }
        if(gameOver){
            for(Obstacle o : obstacles){
                this.remove(o);
            }
            obstacles.clear();
            obstacleRemoveList.clear();
            keysDisabledUntil = LocalTime.now().plusSeconds(1);
        }
        return gameOver;
    }

    public void update(){
        if (gameOver) {
            if(isKeyDown && isKeysEnabled()){
                restart();
            }
            return;
        }

        if(checkCollision()){
            return;
        }

        if (isObstaclesEnabled() && (obstacles.size() < 1 || obstacles.get(obstacles.size() -1).getX() < getWidth() - 350)){
            createNewObstacle();
        }

        for (int i = 0; i < obstacles.size(); i++){
            obstacles.get(i).setLocation(obstacles.get(i).getX()-3, obstacles.get(i).getY());
            if(obstacles.get(i).getX() <= -obstacles.get(i).getWidth()){
                obstacleRemoveList.add(i);
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
                this.remove(obstacles.get(obstacleIndex));
                obstacles.remove(obstacleIndex);
            }
            obstacleRemoveList.clear();
        }

    }

    public void restart(){
        gameOverText.hide();
        gameOver = false;
        initStage();
    }

    public void run() {
        obstaclesDisabledUntil = LocalTime.now().plusSeconds(1);
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
