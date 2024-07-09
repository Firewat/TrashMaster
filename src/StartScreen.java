import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel {
    private JButton playButton;
    private JButton controlsButton;
    private JButton highscoreButton;
    public JFrame window;

    public StartScreen(JFrame window) {
        this.window = window;
        setLayout(new BorderLayout());

        // Bilder laden
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/res/background/background2.jpg"));
        ImageIcon startBIcon = new ImageIcon(getClass().getResource("/res/start_game.png"));
        ImageIcon controlsBIcon = new ImageIcon(getClass().getResource("/res/controlss.png"));
        ImageIcon highscoreBIcon = new ImageIcon(getClass().getResource("/res/highscore.png"));

        // Background hinzufügen
        JLabel background = new JLabel(bgIcon);
        add(background);
        background.setLayout(new GridBagLayout());

        // Buttons mit Bildern
        playButton = new JButton(startBIcon);
        controlsButton = new JButton(controlsBIcon);
        highscoreButton = new JButton(highscoreBIcon);

        // default styling entfernen
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        controlsButton.setBorderPainted(false);
        controlsButton.setContentAreaFilled(false);
        highscoreButton.setBorderPainted(false);
        highscoreButton.setContentAreaFilled(false);

        // Action Listener für GamePanel
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
                gamePanel.requestFocusInWindow();
                gamePanel.startGame(); // Start the game
            }
        });

        // ActionListener für ControlsScreen
        controlsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.getContentPane().removeAll();
                ControlsScreen controlsScreen = new ControlsScreen(window);
                controlsScreen.setPreferredSize(new Dimension(720, 540));
                window.add(controlsScreen);
                window.revalidate();
                window.repaint();
            }
        });

        // ActionListener für HighScoreScreen
        highscoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.getContentPane().removeAll();
                HighScoreScreen highScoreScreen = new HighScoreScreen(window);
                highScoreScreen.setPreferredSize(new Dimension(720, 540));
                window.add(highScoreScreen);
                window.revalidate();
                window.repaint();
            }
        });

        // buttons zum panel hinzufügen
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        background.add(playButton, gbc);

        gbc.gridy = 1;
        background.add(controlsButton, gbc);

        gbc.gridy = 2;
        background.add(highscoreButton, gbc);
    }
}
