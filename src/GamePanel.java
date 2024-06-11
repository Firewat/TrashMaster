import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GamePanel extends JPanel {
    private Map<String, String> trashCategories;
    private JFrame window;
    public static final int TILE_SIZE = 32;
    public static int MAP_WIDTH = 20;
    public static int MAP_HEIGHT = 15;
    private double movementSpeed = 0.33;
    private String playerName;
    private HighScoreScreen highScoreScreen;
    private Player player;
    private List<Trash> trashItems;
    private Tile[][] worldMap;
    private int cameraX;
    private int cameraY;
    private int lives = 3;
    private int collectedItems = 0;
    private String trashTypeToCollect;
    private int roundTime = 30;
    private int round = 1;
    private Timer gameTimer;
    private boolean displayNextRound = false;


    public GamePanel(JFrame window) {

        this.highScoreScreen = new HighScoreScreen(window);
        this.window = window;
        promptForName();
        int mapWidth = 0;
        int mapHeight = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("res/map/worldmap.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                mapWidth = Math.max(mapWidth, line.length());
                mapHeight++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        MAP_WIDTH = mapWidth;
        MAP_HEIGHT = mapHeight;

        setPreferredSize(new Dimension(MAP_WIDTH * TILE_SIZE, MAP_HEIGHT * TILE_SIZE));
        player = new Player((double) MAP_WIDTH / 2, (double) MAP_HEIGHT / 2, "res/player/player3.png", this);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                player.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                player.keyReleased(e);
            }
        });

        setFocusable(true);

        trashCategories = new HashMap<>(); // Initialize trashCategories here
        initializeTrashItems();
        initializeWorldMap();

        cameraX = (int) (player.getX() * TILE_SIZE - window.getWidth() / 2);
        cameraY = (int) (player.getY() * TILE_SIZE - window.getHeight() / 2);
        new Timer(1000 / 60, e -> update()).start();
    }

    private void promptForName() {
        playerName = JOptionPane.showInputDialog(this, "Enter your name:", "Player Name", JOptionPane.PLAIN_MESSAGE);
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Anonymous";
        }
    }

    private void initializeTrashItems() {
        trashItems = new ArrayList<>();
        Map<String, String> trashCategories = new HashMap<>();
        trashCategories.put("aludose", "gelber müll");
        trashCategories.put("aludose_gross", "gelber müll");
        trashCategories.put("bierflasche", "glas müll");
        trashCategories.put("coladose", "gelber müll");
        trashCategories.put("glasbehälter", "glas müll");
        trashCategories.put("glasflasche", "glas müll");
        trashCategories.put("karton", "papier müll");
        trashCategories.put("papbecher", "papier müll");
        trashCategories.put("paptüte", "papier müll");
        trashCategories.put("pizzakarton", "papier müll");
        trashCategories.put("plastikflasche", "gelber müll");
        trashCategories.put("plastikflasche_gross", "gelber müll");
        trashCategories.put("spraydose", "sondermüll");
        trashCategories.put("Tasse", "sondermüll");
        trashCategories.put("waschmittel", "gelber müll");
        trashCategories.put("zeitung", "papier müll");

// Define trash types
        String[] trashTypes = {"aludose", "aludose_gross", "bierflasche", "coladose", "glasbehälter", "glasflasche", "karton", "papbecher", "paptüte", "pizzakarton", "plastikflasche", "plastikflasche_gross", "spraydose", "Tasse", "waschmittel", "zeitung"};

// Define collectible categories
        List<String> collectibleCategories = Arrays.asList("gelber müll", "sondermüll", "papier müll", "glas müll");
        trashTypeToCollect = collectibleCategories.get((int) (Math.random() * collectibleCategories.size()));

/// Initialize 5 trash items of the current category
        for (int i = 0; i < 5; i++) {
            int x = (int) (Math.random() * MAP_WIDTH);
            int y = (int) (Math.random() * MAP_HEIGHT);
            String itemName = getRandomItemNameOfCategory(trashTypeToCollect);
            trashItems.add(new Trash(x, y, "res/items/" + itemName + ".png", itemName, trashTypeToCollect));
        }

        // Initialize remaining trash items randomly
        for (int i = 0; i < 50; i++) {
            int x = (int) (Math.random() * MAP_WIDTH);
            int y = (int) (Math.random() * MAP_HEIGHT);
            String itemName = trashTypes[(int) (Math.random() * trashTypes.length)];
            String category = trashCategories.get(itemName);
            trashItems.add(new Trash(x, y, "res/items/" + itemName + ".png", itemName, category));
        }
// Randomly select a trash type to collect from collectible categories
        trashTypeToCollect = collectibleCategories.get((int) (Math.random() * collectibleCategories.size()));
    }
    private String getRandomItemNameOfCategory(String category) {
        List<String> itemsOfCategory = new ArrayList<>();
        for (Map.Entry<String, String> entry : trashCategories.entrySet()) {
            if (entry.getValue().equals(category)) {
                itemsOfCategory.add(entry.getKey());
            }
        }
        if (!itemsOfCategory.isEmpty()) {
            return itemsOfCategory.get((int) (Math.random() * itemsOfCategory.size()));
        } else {
            return null; // Return null or some default value if there are no items of the current category
        }
    }
    private void initializeWorldMap() {
        worldMap = new Tile[MAP_HEIGHT][MAP_WIDTH];
        try (BufferedReader reader = new BufferedReader(new FileReader("res/map/worldmap.txt"))) {
            String[] tileTypes = {"type1", "type2", "type3", "type4", "type5", "type6", "type7", "type8", "type9", "type10", "type11", "type12", "type13", "type14", "type15", "type16", "type17"};
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null && row < MAP_HEIGHT) {
                for (int col = 0; col < line.length() && col < MAP_WIDTH; col++) {
                    int type = Character.getNumericValue(line.charAt(col));
                    if (type > 0) {
                        worldMap[row][col] = new Tile("res/worldtiles/" + tileTypes[type - 1] + ".png", type);
                    } else {
                        worldMap[row][col] = new Tile("res/worldtiles/" + tileTypes[1] + ".png", 2);
                    }
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderWorld(g);
        renderTrash(g);
        if (displayNextRound) {
            renderNextRound(g);
        }
        renderHUD(g);
        renderCat(g);
        renderPlayer(g);
    }

    private void renderTrash(Graphics g) {
        for (Trash trash : trashItems) {
            int x = trash.getX() * TILE_SIZE - cameraX;
            int y = trash.getY() * TILE_SIZE - cameraY;
            g.drawImage(trash.getImage(), x, y, TILE_SIZE, TILE_SIZE, this);
        }
    }

    private void renderPlayer(Graphics g) {
        int x = (int) (player.getX() * TILE_SIZE - cameraX);
        int y = (int) (player.getY() * TILE_SIZE - cameraY);
        g.drawImage(player.getImage(), x, y, this);
    }

    private void renderWorld(Graphics g) {
        int startRow = Math.max(0, cameraY / TILE_SIZE);
        int endRow = Math.min(MAP_HEIGHT, (cameraY + getHeight()) / TILE_SIZE + 1);
        int startCol = Math.max(0, cameraX / TILE_SIZE);
        int endCol = Math.min(MAP_WIDTH, (cameraX + getWidth()) / TILE_SIZE + 1);
        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                int x = col * TILE_SIZE - cameraX;
                int y = row * TILE_SIZE - cameraY;
                g.drawImage(worldMap[row][col].getImage(), x, y, TILE_SIZE, TILE_SIZE, this);
            }
        }
    }

    private void renderHUD(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.BOLD, 18));
        g.drawString("Leben: " + lives, 10, 20);
        g.drawString("Runde: " + round, 10, 40);
        g.drawString("Eingesammelt: " + collectedItems, 10, 60);
        //g.drawString("Kategorie: " + trashTypeToCollect, 10, 80);
        g.drawString("Zeit: " + roundTime, 10, 100);
    }
    private void renderCat(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Times New Roman", Font.BOLD, 18));
        g.drawString("Kategorie: " + trashTypeToCollect, 10, 80);
    }
    private void renderNextRound(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.BOLD, 18));
        g.drawString("Next Round", 360, 20);
    }

    public void setCameraPosition(int cameraX, int cameraY) {
        int maxCameraX = Math.max(0, MAP_WIDTH * TILE_SIZE - window.getWidth());
        int maxCameraY = Math.max(0, MAP_HEIGHT * TILE_SIZE - window.getHeight());

        this.cameraX = Math.max(0, Math.min(cameraX, maxCameraX));
        this.cameraY = Math.max(0, Math.min(cameraY, maxCameraY));}

    public void startGame() {
        gameTimer = new Timer(1000, e -> {
            roundTime--;
            if (roundTime <= 0) {
                lives--;
                resetGameTime();
                if (lives <= 0) {
                    gameOver();
                } else {
                    nextRound();
                }
            }
            repaint();
        });
        gameTimer.start();
    }

    private void resetGameTime() {
        lives--;
        player.resetMovement();
        roundTime = 30;
        round = 0;
        collectedItems = 0;
        //lives = 3; // Reset lives to initial value
        trashItems.clear(); // Clear all trash items
        player.getInventory().clear(); // Clear player's inventory
        player.setPosition(MAP_WIDTH / 2, MAP_HEIGHT / 2); // Reset player's position
        initializeTrashItems(); // Initialize trash items for the new game
    }


    public void nextRound() {
        //this.displayNextRound = displayNextRound ;
        displayNextRound = true;
        new Timer(3000, e -> displayNextRound = false).start(); // Hide the image after 1 second
        round++;
        collectedItems = 0;
        roundTime = 30 - round;
        trashTypeToCollect = trashItems.get((int) (Math.random() * trashItems.size())).getType();
        initializeTrashItems();
        player.getInventory().clear();
       // player.setPosition(MAP_WIDTH / 2, MAP_HEIGHT / 2);
        repaint();
    }
    public void resetGame() {
        player.resetMovement();
        roundTime = 30;
        round = 0;
        collectedItems = 0;
        lives = 3; // Reset lives to initial value
        trashItems.clear(); // Clear all trash items
        player.getInventory().clear(); // Clear player's inventory
        player.setPosition(MAP_WIDTH / 2, MAP_HEIGHT / 2); // Reset player's position
        initializeTrashItems(); // Initialize trash items for the new game
    }
    public void gameOver() {
        gameTimer.stop();
        JOptionPane.showMessageDialog(this, "Game Over! Du hast es bis Runde " + round + " geschafft", "Game Over", JOptionPane.INFORMATION_MESSAGE);

        highScoreScreen.updateHighScores(playerName, round);

        int response = JOptionPane.showConfirmDialog(this, "Willst du nochmal spielen?", "Erneut Versuchen", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            resetGame();
            startGame();
        } else {
            window.dispose();
        }
    }

    public void update() {
        player.move(player.getDx() * movementSpeed, player.getDy() * movementSpeed);

        // Clamp player's position within the map boundaries
        player.setX(Math.max(0, Math.min(player.getX(), MAP_WIDTH - 1)));
        player.setY(Math.max(0, Math.min(player.getY(), MAP_HEIGHT - 2)));

        int cameraX = (int) (player.getX() * TILE_SIZE - window.getWidth() / 2 + TILE_SIZE / 2);
        int cameraY = (int) (player.getY() * TILE_SIZE - window.getHeight() / 2 + TILE_SIZE / 2);
        setCameraPosition(cameraX, cameraY);

        for (Trash trash : new ArrayList<>(trashItems)) {
            if ((int) player.getX() == trash.getX() && (int) player.getY() == trash.getY()) {
                player.addToInventory(trash);
                trashItems.remove(trash);
                if (trash.getCategory().equals(trashTypeToCollect)) {
                    collectedItems++;
                    if (collectedItems >= 5) {
                        lives++;
                        nextRound();
                    }
                } else {
                    lives--;
                    if (lives <= 0) {
                        gameOver();
                    }
                }
                break;
            }
        }

        repaint();
    }

    public int getTileSize() {
        return TILE_SIZE;
    }

    public Trash[] getTrashItems() {
        return trashItems.toArray(new Trash[0]);
    }
}
