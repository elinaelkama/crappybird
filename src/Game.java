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

    private ScoreLabel scoreLabel;
    private boolean isKeyDown = false;
    private boolean gameOver = false;

    private LocalTime keysDisabledUntil;

    private LocalTime obstaclesDisabledUntil;

    private StartScreen startScreen;
    private GameOverScreen gameOverScreen;

    private boolean gameStarted = false;

    private Random obstacleRandomizer;

    private int scoreRequiredForLevelComplete;
    private int levelsToBeatTheGame;

    private long[] levelSeeds = {13, 8, 33, 21, 14};
    private int currentLevel = 0;

    private LevelCompleteScreen levelCompleteScreen;
    private GameCompleteScreen gameCompleteScreen;
    private boolean levelComplete = false;
    private boolean gameWon = false;

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

        startScreen = new StartScreen(0, 0, windowWidth, windowHeight);
        this.add(startScreen);

        scoreLabel = new ScoreLabel(windowWidth - 5 - 50, 5, 50, 50);
        this.add(scoreLabel);

        levelCompleteScreen = new LevelCompleteScreen((windowWidth - 400) / 2, (windowHeight - 200) / 2, 400, 200);
        this.add(levelCompleteScreen);

        gameCompleteScreen = new GameCompleteScreen(0, 0, windowWidth, windowHeight);
        this.add(gameCompleteScreen);

        setVisible(true);

        gameOverScreen = new GameOverScreen((windowWidth - 400) / 2, (windowHeight - 200) / 2, 400, 200, "Game Over");
        this.add(gameOverScreen);
        setComponentZOrder(gameOverScreen, 0);

        this.obstacleRemoveList = new ArrayList<>();
        obstacles = new ArrayList<>();

        this.scoreRequiredForLevelComplete = 15;
        this.levelsToBeatTheGame = levelSeeds.length;
        initStage(0);

        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
    }

    private void initStage(int levelNumber) {
        levelCompleteScreen.hide();
        long seed = levelSeeds[levelNumber];

        obstacleRandomizer = new Random(seed);
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

        float newPos = obstacleRandomizer.nextFloat();
        float gapPos = newPos;

        // Create a new obstacle and add it to the list
        Obstacle obstacle = new Obstacle(getWidth(),0, 100, getHeight(), gapSize, gapPos);
        obstacles.add(obstacle);
        this.add(obstacle);
    }

    private boolean checkCollision() {
        for (Obstacle o : obstacles){
            if (bird.collidesWith(o.getCollisionBoxes())){
                gameOver = true;
                gameOverScreen.show(scoreLabel.getScore());
            }

            if(!o.getPassed() && bird.collidesWith(o.getScoreBoxes())){
                o.setPassed();
                scoreLabel.incrementScore();
            }
        }
        return gameOver;
    }

    public void update(){
        if(!gameStarted) {
            startScreen.show();
            if(isKeyDown) {
                gameStarted = true;
                startScreen.hide();
                obstaclesDisabledUntil = LocalTime.now().plusSeconds(1);
            }
            return;
        }

        if (gameOver || levelComplete) {
            if(isKeyDown && isKeysEnabled()){
                restart();
            }
            return;
        }

        if(scoreLabel.getScore() == scoreRequiredForLevelComplete){
            levelComplete = true;
            currentLevel++;
            scoreRequiredForLevelComplete += 5;
            levelCompleteScreen.show(this.currentLevel, scoreLabel.getScore());


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
        for(Obstacle o : obstacles){
            this.remove(o);
        }
        obstacles.clear();
        obstacleRemoveList.clear();
        keysDisabledUntil = LocalTime.now().plusSeconds(1);

        if(levelComplete){
            levelCompleteScreen.hide();
        }

        if(gameOver){
            gameOverScreen.hide();
        }

        if(currentLevel == levelsToBeatTheGame){
            gameWon = true;
            gameCompleteScreen.show();
            timer.stop();
            return;
        }
        levelComplete = false;
        gameOver = false;
        scoreLabel.reset();
        if(currentLevel != levelsToBeatTheGame){
            initStage(currentLevel);
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
