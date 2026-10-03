import javax.swing.JFrame;

/**
 * Teammate 1: Window Frame Setup
 * Configures the desktop window properties: title, sizing, centering, and close operations.
 */
public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("2D Classic Snake - Java Swing Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Add the custom GamePanel
        GamePanel panel = new GamePanel();
        add(panel);

        // pack() sizes the frame so that the GamePanel's preferred size (600x600) is respected
        pack();
        setLocationRelativeTo(null); // Center window on the screen
        setVisible(true);
    }
}
