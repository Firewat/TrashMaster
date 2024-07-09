import javax.swing.*;
import java.awt.*;

public class Tile {
    private Image image;
    private int type;

    public Tile(String imagePath, int type) {
        this.image = new ImageIcon(getClass().getResource(imagePath)).getImage();
        this.type = type;
    }

    public Image getImage() {
        return image;
    }

    public int getType() {
        return type;
    }
}
