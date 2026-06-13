/*
* Author: Cary
* */

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Create the main screen
        JFrame mainScreen = new JFrame("Kingdom");
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.setResizable(false);
        // Icon setup
        ImageIcon icon = new ImageIcon("src/main/resources/raw images/crown icon.jpg");
        mainScreen.setIconImage(icon.getImage());
        // Apply the game panel layout
        GamePanel gamePanel = new GamePanel();
        gamePanel.startGameThread();
        mainScreen.add(gamePanel);
        mainScreen.pack();  // Adjust the window size to fit the preferred size of the panel
        // Display the window
        mainScreen.setVisible(true);
        mainScreen.setLocationRelativeTo(null);  // Center the window on the screen
    }
}
