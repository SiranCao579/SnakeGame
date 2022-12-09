import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

public class KeyBoardListener implements KeyListener {

    private MyPanel panel;

    public KeyBoardListener(MyPanel panel) {
        this.panel = panel;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == VK_DOWN && panel.getDirection() != Direction.UP) {
            panel.setDirection(Direction.DOWN);
        } else if (code == VK_UP && panel.getDirection() != Direction.DOWN) {
            panel.setDirection(Direction.UP);
        } else if (code == VK_LEFT && panel.getDirection() != Direction.RIGHT) {
            panel.setDirection(Direction.LEFT);
        } else if (code == VK_RIGHT && panel.getDirection() != Direction.LEFT) {
            panel.setDirection(Direction.RIGHT);
        } else if (code == VK_SPACE) {
            if (panel.getGamePaused()) panel.setGameResume();
            else panel.setGamePaused();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // do nothing
    }
}
