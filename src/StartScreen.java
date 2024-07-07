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

        // Background hinzufügen
        JLabel background = new JLabel(new ImageIcon("src/res/background/background2.jpg"));
        add(background);
        background.setLayout(new GridBagLayout());

        // Buttons mit Bildern
        playButton = new JButton(new ImageIcon("src/res/button/start_game.png"));
        controlsButton = new JButton(new ImageIcon("src/res/button/controlss.png"));
        highscoreButton = new JButton(new ImageIcon("src/res/button/highscore.png"));

        // default styling entfernen
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        controlsButton.setBorderPainted(false);
        controlsButton.setContentAreaFilled(false);
        highscoreButton.setBorderPainted(false);
        highscoreButton.setContentAreaFilled(false);

        // Action Listerner GamePanel
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
        //ActionListener ControlsScreen
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
        //ActionListener HighScoreScreen
        highscoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.getContentPane().removeAll();
                //JLabel background = new JLabel(new ImageIcon("res/background/background2.jpg"));

                HighScoreScreen highScoreScreen = new HighScoreScreen(window);
                highScoreScreen.setPreferredSize(new Dimension(720, 540));
                window.add(highScoreScreen);
                window.revalidate();
                window.repaint();

            }
        });

        // buttons zum panel hinzufügen
        background.add(playButton);
        background.add(controlsButton);
        background.add(highscoreButton);
    }
}
