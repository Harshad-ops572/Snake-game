import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main application launcher for the 2D Classic Snake Game.
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Launch on the Swing Event Dispatch Thread (standard best practice)
        SwingUtilities.invokeLater(() -> new GameFrame());
    }
}
