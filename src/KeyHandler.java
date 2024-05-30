//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//
//public class KeyHandler implements KeyListener {
//
//    private boolean upPressed, downPressed, leftPressed, rightPressed;
//    private final GamePanel gamePanel;
//
//    public KeyHandler(GamePanel gamePanel) {
//        this.gamePanel = gamePanel;
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//        int code = e.getKeyCode();
//
//        // Handle arrow keys for movement
//        if (code == KeyEvent.VK_UP) {
//            upPressed = true;
//        }
//
//        if (code == KeyEvent.VK_DOWN) {
//            downPressed = true;
//        }
//
//        if (code == KeyEvent.VK_LEFT) {
//            leftPressed = true;
//        }
//
//        if (code == KeyEvent.VK_RIGHT) {
//            rightPressed = true;
//        }
//
//        // Handle menu interactions
//        if (code == KeyEvent.VK_ENTER) {
//            // Add your code here for when the Enter key is pressed
//        }
//
//        if (code == KeyEvent.VK_ESCAPE) {
//            // Add your code here for when the Escape key is pressed
//        }
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//        int code = e.getKeyCode();
//
//        if (code == KeyEvent.VK_UP) {
//            upPressed = false;
//        }
//
//        if (code == KeyEvent.VK_DOWN) {
//            downPressed = false;
//        }
//
//        if (code == KeyEvent.VK_LEFT) {
//            leftPressed = false;
//        }
//
//        if (code == KeyEvent.VK_RIGHT) {
//            rightPressed = false;
//        }
//    }
//
//    @Override
//    public void keyTyped(KeyEvent e) {
//        // Not used
//    }
//
//    public boolean isUpPressed() {
//        return upPressed;
//    }
//
//    public KeyHandler setUpPressed(boolean upPressed) {
//        this.upPressed = upPressed;
//        return this;
//    }
//
//    public boolean isDownPressed() {
//        return downPressed;
//    }
//
//    public KeyHandler setDownPressed(boolean downPressed) {
//        this.downPressed = downPressed;
//        return this;
//    }
//
//    public boolean isLeftPressed() {
//        return leftPressed;
//    }
//
//    public KeyHandler setLeftPressed(boolean leftPressed) {
//        this.leftPressed = leftPressed;
//        return this;
//    }
//
//    public boolean isRightPressed() {
//        return rightPressed;
//    }
//
//    public KeyHandler setRightPressed(boolean rightPressed) {
//        this.rightPressed = rightPressed;
//        return this;
//    }
//}