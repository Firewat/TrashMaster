import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ControlsScreen extends JPanel {
    private JButton backButton;
    public JFrame window;
    public ControlsScreen(JFrame window) {
        //setLayout(null);
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

        JLabel title = new JLabel("Controls", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 24));

        gbc.gridx = 360;
        gbc.gridy = -1;
        gbc.weightx = 360;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        background.add(title, gbc);

        // Load high scores from file
        JTextArea controlsScreen = new JTextArea();
        controlsScreen.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        controlsScreen.setEditable(false);
        controlsScreen.setOpaque(false);
        try (BufferedReader reader = new BufferedReader(new FileReader("res/highscore/Controls.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                controlsScreen.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        gbc.gridx = 360;
        gbc.gridy = 0;
        gbc.weighty = 0.9;
        background.add(controlsScreen, gbc);

        backButton = new JButton(new ImageIcon("res/button/back.png"));
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

        // Add back button to panel
        background.add(backButton);
    }
}

