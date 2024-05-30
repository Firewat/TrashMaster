import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HighScoreScreen extends JPanel {
    public HighScoreScreen() {
        //setLayout(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel background = new JLabel(new ImageIcon("res/background/background2.jpg"));
        gbc.gridx = -1; // Move background to the left by one cell
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(background, gbc);
        background.setLayout(new GridBagLayout());

        JLabel title = new JLabel("High Scores", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 24));

        gbc.gridx = 360;
        gbc.gridy = -1;
        gbc.weightx = 360;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        background.add(title, gbc);

        // Load high scores from file
        JTextArea highScores = new JTextArea();
        highScores.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        highScores.setEditable(false);
        highScores.setOpaque(false);
        try (BufferedReader reader = new BufferedReader(new FileReader("res/highscore/highscores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                highScores.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        gbc.gridx = 360;
        gbc.gridy = 0;
        gbc.weighty = 0.9;
        background.add(highScores, gbc);
    }
}