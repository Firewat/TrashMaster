import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;

public class HighScoreScreen extends JPanel {
    private final JFrame window;
    private final JTextArea highScores;

    public HighScoreScreen(JFrame window) {
        this.window = window;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("High Scores", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        highScores = new JTextArea();
        highScores.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        highScores.setEditable(false);
        loadHighScores();
        add(new JScrollPane(highScores), BorderLayout.CENTER);

        JButton backButton = new JButton(new ImageIcon("src/res/button/back.png"));
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.addActionListener(e -> navigateToStartScreen());
        add(backButton, BorderLayout.SOUTH);
    }

    private void loadHighScores() {
        highScores.setText("");
        try (BufferedReader reader = new BufferedReader(new FileReader("src/res/highscore/highscores.txt"))) {
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
        try (BufferedReader reader = new BufferedReader(new FileReader("src/res/highscore/highscores.txt"))) {
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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/res/highscore/highscores.txt"))) {
            int count = 0;
            for (Map.Entry<Integer, String> entry : scoresMap.entrySet()) {
                if (count >= 10) break;
                writer.write(entry.getValue() + ": " + entry.getKey());
                writer.newLine();
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadHighScores();
    }

    private void navigateToStartScreen() {
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
}
