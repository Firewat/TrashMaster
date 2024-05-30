import javax.swing.*;
import java.awt.*;

public class Trash {
    private int x;
    private int y;
    private Image image;
    private String type;
    private String category; // New field for category

    public Trash(int x, int y, String imagePath, String type, String category) {
        this.x = x;
        this.y = y;
        this.image = new ImageIcon(imagePath).getImage();
        this.type = type;
        this.category = category; // Initialize category
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category; // Getter for category
    }

    public void setCategory(String category) {
        this.category = category; // Setter for category
    }
}
