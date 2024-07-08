import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ControlsScreen extends JPanel {
    private final JFrame window;
    private final JButton backButton;

    public ControlsScreen(JFrame window) {
        this.window = window;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // JTextArea erstellen
        JTextArea controlsScreen = new JTextArea();
        controlsScreen.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        controlsScreen.setEditable(false);
        controlsScreen.setOpaque(false); // Make text area non-opaque

        // einlesen der controls Text-Datei
        try (BufferedReader reader = new BufferedReader(new FileReader("src/res/controls/controls.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                controlsScreen.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        controlsScreen.setForeground(Color.black);

        // durchsichtiges panel
        JScrollPane scrollPane = new JScrollPane(controlsScreen);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.9;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        // backbutton hinzufügen
        backButton = new JButton(new ImageIcon("src/res/button/back.png"));
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.addActionListener(e -> HighScoreScreen.navigateToStartScreen());
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        gbc.gridy = 1;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(backButton, gbc);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon backgroundImage = new ImageIcon("src/res/background/bgCS.jpg");
        Image image = backgroundImage.getImage();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
