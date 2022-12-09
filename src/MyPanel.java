import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.Timer;

public class MyPanel extends JPanel implements ActionListener {
    private BufferedImage appleImage, snakeImage;
    private Random appleLocGenerator;
    private int speed;
    private int score, preScore;
    private final Coordinate SCORE_LOC;
    private final String GAME_PAUSED = "GAME PAUSED";
    private final Coordinate GAME_PAUSED_LOC;
    private final int SNAKE_START_X = 100, SNAKE_START_Y = 100;
    private final static int PAR = 20;
    private javax.swing.Timer tmr;
    private Direction direction;
    private Coordinate appleLoc;
    private java.util.List<Coordinate> snakeLoc;
    private boolean isGameOver = false;
    private boolean isGamePaused = false;

    public MyPanel(){
        // Load apple image
        try {
            // read the image from the url and save it as a bufferedImage
            appleImage = ImageIO.read(new File(
                    "image/AppleImage.jpeg"));
        } catch (IOException e) { //IOException includes MalformedURLException
            System.out.println("Cannot open the apple file" + e.toString());
        }

        // load snake image
        try {
            // read the image from the url and save it as a bufferedImage
            snakeImage = ImageIO.read(new File(
                    "image/SnakeImage.png"));
        } catch (IOException e) { //IOException includes MalformedURLException
            System.out.println("Cannot open the snake file" + e.toString());
        }
        appleLocGenerator = new Random(System.currentTimeMillis());
        appleLoc = new Coordinate(SnakeGame.WIDTH / 2, SnakeGame.HEIGHT / 2);
        snakeLoc = new ArrayList<>();
        snakeLoc.add(new Coordinate(SNAKE_START_X, SNAKE_START_Y));
        snakeLoc.add(new Coordinate(SNAKE_START_X, SNAKE_START_Y));
        score = 0;
        preScore = 0;
        SCORE_LOC = new Coordinate(PAR, PAR);
        GAME_PAUSED_LOC = new Coordinate(SnakeGame.WIDTH / 2, SnakeGame.HEIGHT - PAR);
        speed = 10;

        tmr = new javax.swing.Timer(160, this);
        tmr.start();

        // register a keyboard listener to this panel
        this.addKeyListener(new KeyBoardListener(this));
        this.setFocusable(true);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // paint the apple at the middle of the canvas
        // and paint the snake if it is not game over
        if (!this.isGameOver) {
            if (isGamePaused) {
                g.drawString(GAME_PAUSED, GAME_PAUSED_LOC.x, GAME_PAUSED_LOC.y);
            }
            g.drawString(Integer.toString(score), SCORE_LOC.x, SCORE_LOC.y);
            g.drawImage(appleImage, appleLoc.x, appleLoc.y, PAR, PAR, null);
            for (Coordinate cur : snakeLoc) {
                g.drawImage(snakeImage, cur.x, cur.y, speed, speed, null);
            }
        } else {
            g.drawString("GAME OVER", SnakeGame.WIDTH / 2, SnakeGame.HEIGHT / 2);
        }
    }
    void setGamePaused() {this.isGamePaused = true;}
    void setGameResume() {this.isGamePaused = false;}

    boolean getGamePaused() {return this.isGamePaused;}
    void setDirection(Direction dir) {
        this.direction = dir;
    }

    Direction getDirection() {return this.direction;}

    void addSpeed() {
        if (score - preScore == 3) {
            preScore = score;
            speed += 3;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // tmr was initiated and started in the constructor
        // every time the timer fires (16ms defined by us)
        // it will call the paintComponent to repaint the snake according to the keyboard direction

        // add a new head to the snake, the new head's coordinate is determined by the snake direction
        int oldX = snakeLoc.get(0).x;
        int oldY = snakeLoc.get(0).y;

        int newX, newY;
        if (!isGamePaused) {
            if (direction == Direction.UP) {
                newX = oldX;
                newY = oldY - speed;
            } else if (direction == Direction.DOWN) {
                newX = oldX;
                newY = oldY + speed;
            } else if (direction == Direction.LEFT) {
                newX = oldX - speed;
                newY = oldY;
            } else {
                newX = oldX + speed;
                newY = oldY;
            }
            Coordinate newHead = new Coordinate(newX, newY);
            if (snakeLoc.contains(newHead)) this.isGameOver = true;

            snakeLoc.add(0, newHead);

            if (newHead.x <= 0 || newHead.x >= SnakeGame.HEIGHT ||
                    newHead.y <= 0 || newHead.y >= SnakeGame.WIDTH) {
                // snake is hitting the boundary of the panel
                this.isGameOver = true;
            }

            if (!(newHead.isIntersect(appleLoc))) {
                // chop off the tail if the snake did NOT get the apple
                snakeLoc.remove(snakeLoc.size() - 1);
            } else {
                // Apple is eaten, re-generate it at a random location
                int newAppleX =
                        Math.abs((Math.abs(appleLocGenerator.nextInt()) % SnakeGame.WIDTH) / 10 * 10 - PAR);
                int newAppleY =
                        Math.abs((Math.abs(appleLocGenerator.nextInt()) % SnakeGame.HEIGHT) / 10 * 10 - PAR);
                appleLoc = new Coordinate(newAppleX, newAppleY);
                score += 1;
            }
            addSpeed();
        }
        repaint();
    }
}
