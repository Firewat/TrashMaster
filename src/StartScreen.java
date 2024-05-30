import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel {
    private JButton playButton;
    private JButton helpButton;
    private JButton highscoreButton;
    public JFrame window;

    public StartScreen(JFrame window) {
        this.window = window;
        setLayout(new BorderLayout()); // Set layout to border layout

        // Create background label
        JLabel background = new JLabel(new ImageIcon("res/background/background2.jpg"));
        add(background);
        background.setLayout(new GridBagLayout());

        // Create buttons with images
        playButton = new JButton(new ImageIcon("res/button/start_game.png"));
        helpButton = new JButton(new ImageIcon("res/button/controlss.png"));
        highscoreButton = new JButton(new ImageIcon("res/button/highscore.png"));

        // Remove default button styling
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        helpButton.setBorderPainted(false);
        helpButton.setContentAreaFilled(false);
        highscoreButton.setBorderPainted(false);
        highscoreButton.setContentAreaFilled(false);

        // Add action listeners to buttons
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove StartScreen and add GamePanel
                window.getContentPane().removeAll();
                GamePanel gamePanel = new GamePanel(window);
                gamePanel.setPreferredSize(new Dimension(720, 540));
                window.add(gamePanel);
                window.revalidate();
                window.repaint();
                gamePanel.startGame(); // Start the game
            }
        });

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement action for help button
            }
        });

        highscoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.getContentPane().removeAll();
                //JLabel background = new JLabel(new ImageIcon("res/background/background2.jpg"));

                HighScoreScreen highScoreScreen = new HighScoreScreen();
                highScoreScreen.setPreferredSize(new Dimension(720, 540));
                window.add(highScoreScreen);
                window.revalidate();
                window.repaint();

            }
        });

        // Add buttons to panel
        background.add(playButton);
        background.add(helpButton);
        background.add(highscoreButton);
    }
}
