import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Player {
    // Position des Spielers auf der x und Y-Achse
    private double x;
    private double y;

    // Bewegung des Spielers in x/y-Richtung
    private double dx;
    private double dy;

    // Referenz zum GamePanel
    private final GamePanel gamePanel;

    // Inventar für gesammelten Müll
    //private final List<Trash> inventory;

    // Bilder für die Animation der Spielerbewegung
    private final Image imageLeft1;
    private final Image imageLeft2;
    private final Image imageRight1;
    private final Image imageRight2;
    // Aktuelles Bild des Spielers
    private Image image;

    //BewgungsCounter
    private int moveCounter = 0;

    // Enum zur Darstellung der Bewegungsrichtung
    private enum Direction {
        LEFT, RIGHT, UP, DOWN, NONE
    }

    private Direction direction;



    // Konstruktor zur Initialisierung der Spielerposition, Bilder und GamePanel-Referenz
    public Player(double x, double y, String imagePath, GamePanel gamePanel) {
        this.x = x;
        this.y = y;

        this.gamePanel = gamePanel;
        //this.inventory = new ArrayList<>();
        int tileSize = gamePanel.getTileSize();

        // Laden und Skalieren der Bilder für die verschiedenen Animationen des Spielers
        this.imageLeft1 = new ImageIcon("res/player/player5.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        this.imageLeft2 = new ImageIcon("res/player/player6.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        this.imageRight1 = new ImageIcon("res/player/player3.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        this.imageRight2 = new ImageIcon("res/player/player4.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        Image imageUp1 = new ImageIcon("res/player/player7.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        Image imageUp2 = new ImageIcon("res/player/player8.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        Image imageDown1 = new ImageIcon("res/player/player1.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        Image imageDown2 = new ImageIcon("res/player/player2.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        // Init Bild und Bewegung
        this.image = imageRight1;
        this.direction = Direction.NONE;
    }

    // Getter für das aktuelle Spielerbild
    public Image getImage() {
        return image;
    }


    // Methode zum Zurücksetzen der Bewegung des Spielers
    public void resetMovement() {
        dx = 0;
        dy = 0;
    }


    // Methode zur Bewegung des Spielers um dx und dy
    public void move(double dx, double dy) {
        double newX = x + dx;
        double newY = y + dy;

        // Verhindern, dass der Spieler die mapgrenzen verlässt
        if (newX >= 0 && newX < GamePanel.MAP_WIDTH) {
            x = newX;
        }
        if (newY >= 0 && newY < GamePanel.MAP_HEIGHT) {
            y = newY;
        }
        // Aktualisieren des Bewegungscounters
        moveCounter++;

        // Aktualisieren der Richtung und des Bildes basierend auf der Bewegung
        if (dx < 0) {
            direction = Direction.LEFT;
            image = (moveCounter / 10) % 2 == 0 ? imageLeft1 : imageLeft2;
        } else if (dx > 0) {
            direction = Direction.RIGHT;
            image = (moveCounter / 10) % 2 == 0 ? imageRight1 : imageRight2;
        } else if (dy < 0) {
            direction = Direction.UP;
            image = (moveCounter / 10) % 2 == 0 ? imageLeft1  : imageLeft2;
        } else if (dy > 0) {
            direction = Direction.DOWN;
            image = (moveCounter / 10) % 2 == 0 ? imageRight1 : imageRight2;
        } else {
            // Beibehalten der aktuellen Richtung bei keiner neuen Bewegung
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

        // Überprüfen, ob der Spieler sich auf derselben Kachel wie ein Müllitem befindet
        for (Trash trash : gamePanel.getTrashItems()) {
            if (isOnSameTile(trash)) {
                //addToInventory(trash);
                // gamePanel.removeTrashItem(trash); // Annahme: Methode zum Entfernen eines Müllitems
                break;
            }
        }
    }

    // Getter und Setter für die xy-Position und dxy (richtung)
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


    // KeyPresssed zur Aktualisierung der Bewegung des Spielers -> GamePanel
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            dx = -1;
            dy = 0;
        } else if (key == KeyEvent.VK_D) {
            dx = 1;
            dy = 0;
        } else if (key == KeyEvent.VK_W) {
            dx = 0;
            dy = -1;
        } else if (key == KeyEvent.VK_S) {
            dx = 0;
            dy = 1;
        }
    }

    // KeyReleased zum beenden der Bewegung des Spielers -> GamePanel
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A || key == KeyEvent.VK_D) {
            dx = 0;
        } else if (key == KeyEvent.VK_W || key == KeyEvent.VK_S) {
            dy = 0;
        }
    }

    // Hinzufügen eines Müllitems zum Inventar des Spielers
    public void addToInventory(Trash trash) {
       // inventory.add(trash);
    }

    // Getter für das Inventar
    public List<Trash> getInventory() {
       // return inventory;
        return null;
    }

    // Setzen der Spielerposition
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Überprüfen, ob sich der Spieler auf selbe Tile wie ein Müllitem befindet
    private boolean isOnSameTile(Trash trash) {
        int tileSize = gamePanel.getTileSize();

        // Berechnung der Kachel des Spielers
        int playerTileX = (int) x / tileSize;
        int playerTileY = (int) y / tileSize;

        // Berechnung der Kachel des Müllitems
        int trashTileX =  trash.getX() / tileSize;
        int trashTileY =  trash.getY() / tileSize;

        // Überprüfen, ob mehr als 50% des Spielers mit der Tiles des Müllitems überlappt
        return Math.abs(playerTileX - trashTileX) * 3 < tileSize && Math.abs(playerTileY - trashTileY) * 2 < tileSize;
    }
}
