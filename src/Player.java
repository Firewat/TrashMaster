import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private double x;
    private double y;
    private double dx;
    private double dy;
    private GamePanel gamePanel;
    private List<Trash> inventory;
    private Image imageLeft1;
    private Image imageLeft2;
    private Image imageRight1;
    private Image imageRight2;
    private Image imageUp1;
    private Image imageUp2;
    private Image imageDown1;
    private Image imageDown2;
    private int moveCounter = 0;
    private Image image;
    private Direction direction;

    public void resetMovement() {
        dx = 0;
        dy = 0;
    }

    private enum Direction {
        LEFT, RIGHT, UP, DOWN, NONE
    }

    public Player(double x, double y, String imagePath, GamePanel gamePanel) {
        this.x = x;
        this.y = y;
        this.gamePanel = gamePanel;
        this.inventory = new ArrayList<>();

        int tileSize = gamePanel.getTileSize(); // Assuming you have a method to get the tile size

        this.imageLeft1 = new ImageIcon("res/player/player5.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        this.imageLeft2 = new ImageIcon("res/player/player6.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        this.imageRight1 = new ImageIcon("res/player/player3.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        this.imageRight2 = new ImageIcon("res/player/player4.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        this.imageUp1 = new ImageIcon("res/player/player7.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        this.imageUp2 = new ImageIcon("res/player/player8.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        this.imageDown1 = new ImageIcon("res/player/player1.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        this.imageDown2 = new ImageIcon("res/player/player2.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        this.image = imageRight1;
        this.direction = Direction.NONE;
    }

    public Image getImage() {
        return image;
    }

    public void move(double dx, double dy) {
        double newX = x + dx;
        double newY = y + dy;

        // Check boundaries
        if (newX >= 0 && newX < gamePanel.MAP_WIDTH) {
            x = newX;
        }
        if (newY >= 0 && newY < gamePanel.MAP_HEIGHT) {
            y = newY;
        }

        // Update the move counter
        moveCounter++;

        // Update the direction and image based on movement
        if (dx < 0) { // Moving left
            direction = Direction.LEFT;
            image = (moveCounter / 10) % 2 == 0 ? imageLeft1 : imageLeft2;
        } else if (dx > 0) { // Moving right
            direction = Direction.RIGHT;
            image = (moveCounter / 10) % 2 == 0 ? imageRight1 : imageRight2;
        } else if (dy < 0) { // Moving up
            direction = Direction.UP;
            image = (moveCounter / 10) % 2 == 0 ? imageLeft1  : imageLeft2;
        } else if (dy > 0) { // Moving down
            direction = Direction.DOWN;
            image = (moveCounter / 10) % 2 == 0 ? imageRight1 : imageRight2;
        } else {
            // Maintain the current direction
            switch (direction) {
                case LEFT:
                    image = imageLeft1;
                    break;
                case RIGHT:
                    image = imageRight1;
                    break;
                case UP:
                    image = imageLeft1;
                    break;
                case DOWN:
                    image = imageRight1;
                    break;
                default:
                    image = imageRight1;
                    break;
            }
        }

        for (Trash trash : gamePanel.getTrashItems()) { // Assuming you have a method to get all trash items
            if (isOnSameTile(trash)) {
                addToInventory(trash);
               // gamePanel.removeTrashItem(trash); // Assuming you have a method to remove a trash item
                break;
            }
        }
    }
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
            dy = 0;
        } else if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
            dy = 0;
        } else if (key == KeyEvent.VK_UP) {
            dx = 0;
            dy = -1;
        } else if (key == KeyEvent.VK_DOWN) {
            dx = 0;
            dy = 1;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            dx = 0;
        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }

    public void addToInventory(Trash trash) {
        inventory.add(trash);
    }

    public List<Trash> getInventory() {
        return inventory;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    private boolean isOnSameTile(Trash trash) {
        // Calculate the tile size
        int tileSize = gamePanel.getTileSize();

        // Calculate the player's tile
        int playerTileX = (int) x / tileSize;
        int playerTileY = (int) y / tileSize;

        // Calculate the trash item's tile
        int trashTileX = (int) trash.getX() / tileSize;
        int trashTileY = (int) trash.getY() / tileSize;

        // Check if more than 50% of the player's tile overlaps with the trash item's tile
        return Math.abs(playerTileX - trashTileX) * 3 < tileSize && Math.abs(playerTileY - trashTileY) * 2 < tileSize;
    }
}
