import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class SnakeGame {
    public final static int WIDTH = 500, HEIGHT = 500;
    public static void main(String[] args) {
        // if the frame doesn't have anything, it will just be invisible
        // need to set the panel, since we are drawing on the panel instead of the frame
        JFrame window = new JFrame("My snake game");
//        JPanel panel = new JPanel(); // set the panel
        JPanel panel = new MyPanel(); // MyPanel override the paintComponent method
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        window.add(panel);
        window.pack(); // pack the panel and the frame together

        window.setVisible(true); // set it visible

    }
}
