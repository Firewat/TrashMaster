import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private static Player player;

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Trash Meister");

        StartScreen startScreen = new StartScreen(window); // Pass window to StartScreen
        startScreen.setPreferredSize(new Dimension(720, 540)); // Set size of the start screen
        window.add(startScreen);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);


    }
}
