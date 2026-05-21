/*
* Author: Cary
* Description:
* */

import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class Kingdom{
    public static void main(String[] args) {
        // Create the main screen
        JFrame mainScreen = new JFrame("Kingdom");
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.setResizable(false);
        // Apply the game panel layout
        GamePanel gamePanel = new GamePanel();
        mainScreen.add(gamePanel);
        mainScreen.pack();  // Adjust the window size to fit the preferred size of the panel
        // Display the window
        mainScreen.setVisible(true);
        mainScreen.setLocationRelativeTo(null);  // Center the window on the screen
        // Icon setup
        ImageIcon icon = new ImageIcon("src/main/resources/crown icon.jpg");
        mainScreen.setIconImage(icon.getImage());
    }
}
