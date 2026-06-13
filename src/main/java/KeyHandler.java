/*
 * Author: Cary
 * */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * <a href="https://www.youtube.com/watch?v=oPzPpUcDiYY&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&index=8">Reference</a>
 * */
public class KeyHandler implements KeyListener {
    public boolean downPressed, leftPressed, rightPressed, escPressed;
    public boolean downPressedOnce;  // Activate only once per press
    public boolean escPressedOnce;   // Activate only once per ESC press (for toggles)

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Manage key press events
     *
     * @param e the KeyEvent object representing the key press event
     * */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            // Only triggers once
            downPressedOnce = !downPressed;
            downPressed = true;
        }
        if (key == KeyEvent.VK_ESCAPE) {
            // Toggle trigger: mark escPressed true and set one-shot flag so the game loop
            // can consume the event exactly once for toggling pause/menu
            escPressedOnce = !escPressed;
            escPressed = true;
        }
    }

    /**
     * Manage key release events
     *
     * @param e the KeyEvent object representing the key release event
     * */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            downPressed = false;
            downPressedOnce = false;
        }
        if (key == KeyEvent.VK_ESCAPE) {
            escPressed = false;
            escPressedOnce = false;
        }
    }

    public void resetTiggerKey() {
        downPressedOnce = false;
        escPressedOnce = false;
    }
}
