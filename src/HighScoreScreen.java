import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class HighScoreScreen extends JPanel {
    private JFrame window;
    private JTextArea highScores;

    public HighScoreScreen(JFrame window) {
        this.window = window;
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

        highScores = new JTextArea();
        highScores.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        highScores.setEditable(false);
        highScores.setOpaque(false);
        loadHighScores();
        gbc.gridx = 360;
        gbc.gridy = 0;
        gbc.weighty = 0.9;
        background.add(highScores, gbc);

        JButton backButton = new JButton(new ImageIcon("res/button/back.png"));
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (window != null) {
                    window.getContentPane().removeAll();
                    StartScreen startScreen = new StartScreen(window);
                    startScreen.setPreferredSize(new Dimension(720, 540));
                    window.add(startScreen);
                    window.revalidate();
                    window.repaint();
                } else {
                    System.out.println("Window is null");
                }
            }
        });

        gbc.gridx = 360;
        gbc.gridy = 1;
        gbc.weighty = 0.1;
        background.add(backButton, gbc);
    }

    private void loadHighScores() {
        highScores.setText("");
        try (BufferedReader reader = new BufferedReader(new FileReader("res/highscore/highscores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                highScores.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateHighScores(String name, int score) {
        TreeMap<Integer, String> scoresMap = new TreeMap<>(Collections.reverseOrder());
        try (BufferedReader reader = new BufferedReader(new FileReader("res/highscore/highscores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    scoresMap.put(Integer.parseInt(parts[1].trim()), parts[0].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        scoresMap.put(score, name);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("res/highscore/highscores.txt"))) {
            int count = 0;
            for (Map.Entry<Integer, String> entry : scoresMap.entrySet()) {
                if (count >= 10) break; // Keep only top 10 scores
                writer.write(entry.getValue() + ": " + entry.getKey());
                writer.newLine();
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadHighScores();
    }
}
