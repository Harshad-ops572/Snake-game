import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Teammate 1 & Integration: Game Panel Canvas & Game Loop
 * Renders the 2D grid, snake, food, score banner, and runs the animation timer loop.
 */
public class GamePanel extends JPanel implements ActionListener {

    // Grid Dimensions
    public static final int TILE_SIZE = 25;
    public static final int GRID_WIDTH = 24;   // 24 * 25 = 600 px
    public static final int GRID_HEIGHT = 24;  // 24 * 25 = 600 px
    public static final int SCREEN_WIDTH = GRID_WIDTH * TILE_SIZE;
    public static final int SCREEN_HEIGHT = GRID_HEIGHT * TILE_SIZE;
    private static final int INITIAL_DELAY = 120; // Milliseconds per tick

    // Entities & State
    private Snake snake;
    private Food food;
    private Timer timer;
    private int score;
    private int highScore;
    private boolean running;
    private boolean gameOver;
    private boolean paused;

    public GamePanel() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(new Color(24, 30, 36)); // Sleek dark slate
        setFocusable(true);
        addKeyListener(new GameKeyAdapter());

        this.highScore = 0;
        initGame();
    }

    /**
     * Initializes or resets the game to the starting state.
     */
    public void initGame() {
        snake = new Snake(10, 10, 3);
        food = new Food();
        food.respawn(GRID_WIDTH, GRID_HEIGHT, snake);

        score = 0;
        running = true;
        gameOver = false;
        paused = false;

        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        timer = new Timer(INITIAL_DELAY, this);
        timer.start();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && !paused) {
            updateGame();
        }
        repaint();
    }

    /**
     * Advances game physics by one frame.
     */
    private void updateGame() {
        snake.move();
        Point head = snake.getHead();

        // 1. Check if snake ate food
        if (food.isEatenBy(head)) {
            snake.grow();
            score += 10;
            if (score > highScore) {
                highScore = score;
            }
            food.respawn(GRID_WIDTH, GRID_HEIGHT, snake);

            // Dynamically speed up as snake grows (down to 65ms minimum)
            int currentDelay = timer.getDelay();
            if (currentDelay > 65) {
                timer.setDelay(currentDelay - 2);
            }
        }

        // 2. Check wall collisions
        if (head.x < 0 || head.x >= GRID_WIDTH || head.y < 0 || head.y >= GRID_HEIGHT) {
            triggerGameOver();
            return;
        }

        // 3. Check self collisions
        if (snake.checkSelfCollision()) {
            triggerGameOver();
        }
    }

    private void triggerGameOver() {
        running = false;
        gameOver = true;
        timer.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Smooth antialiased rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawGridLines(g2d);
        drawFood(g2d);
        drawSnake(g2d);
        drawScoreHUD(g2d);

        if (gameOver) {
            drawGameOverScreen(g2d);
        } else if (paused) {
            drawPausedScreen(g2d);
        }
    }

    private void drawGridLines(Graphics2D g) {
        g.setColor(new Color(35, 43, 51));
        for (int x = 0; x <= SCREEN_WIDTH; x += TILE_SIZE) {
            g.drawLine(x, 0, x, SCREEN_HEIGHT);
        }
        for (int y = 0; y <= SCREEN_HEIGHT; y += TILE_SIZE) {
            g.drawLine(0, y, SCREEN_WIDTH, y);
        }
    }

    private void drawFood(Graphics2D g) {
        Point p = food.getPosition();
        int x = p.x * TILE_SIZE;
        int y = p.y * TILE_SIZE;

        // Shiny red apple
        g.setColor(new Color(231, 76, 60));
        g.fillOval(x + 2, y + 2, TILE_SIZE - 4, TILE_SIZE - 4);

        // Apple highlight reflection
        g.setColor(new Color(241, 148, 138));
        g.fillOval(x + 6, y + 5, 5, 5);

        // Green leaf
        g.setColor(new Color(46, 204, 113));
        g.fillRect(x + (TILE_SIZE / 2) - 1, y, 3, 4);
    }

    private void drawSnake(Graphics2D g) {
        java.util.List<Point> body = snake.getBody();

        for (int i = 0; i < body.size(); i++) {
            Point p = body.get(i);
            int x = p.x * TILE_SIZE;
            int y = p.y * TILE_SIZE;

            if (i == 0) {
                // Head (Bright Emerald Green)
                g.setColor(new Color(46, 204, 113));
                g.fillRoundRect(x + 1, y + 1, TILE_SIZE - 2, TILE_SIZE - 2, 8, 8);

                // Draw Eyes on Head based on direction
                drawEyes(g, x, y, snake.getDirection());
            } else {
                // Body segments (Forest Green with gradient depth)
                g.setColor(new Color(39, 174, 96));
                g.fillRoundRect(x + 2, y + 2, TILE_SIZE - 4, TILE_SIZE - 4, 6, 6);

                // Inner detail
                g.setColor(new Color(30, 132, 73));
                g.drawRoundRect(x + 2, y + 2, TILE_SIZE - 4, TILE_SIZE - 4, 6, 6);
            }
        }
    }

    private void drawEyes(Graphics2D g, int x, int y, Direction dir) {
        g.setColor(Color.WHITE);
        int eyeSize = 4;
        int pupilSize = 2;

        int eye1X = 0, eye1Y = 0, eye2X = 0, eye2Y = 0;

        switch (dir) {
            case RIGHT:
                eye1X = x + 16; eye1Y = y + 5;
                eye2X = x + 16; eye2Y = y + 15;
                break;
            case LEFT:
                eye1X = x + 5; eye1Y = y + 5;
                eye2X = x + 5; eye2Y = y + 15;
                break;
            case UP:
                eye1X = x + 5; eye1Y = y + 5;
                eye2X = x + 15; eye2Y = y + 5;
                break;
            case DOWN:
                eye1X = x + 5; eye1Y = y + 16;
                eye2X = x + 15; eye2Y = y + 16;
                break;
        }

        g.fillOval(eye1X, eye1Y, eyeSize, eyeSize);
        g.fillOval(eye2X, eye2Y, eyeSize, eyeSize);

        g.setColor(Color.BLACK);
        g.fillOval(eye1X + 1, eye1Y + 1, pupilSize, pupilSize);
        g.fillOval(eye2X + 1, eye2Y + 1, pupilSize, pupilSize);
    }

    private void drawScoreHUD(Graphics2D g) {
        g.setFont(new Font("Segoe UI", Font.BOLD, 15));
        g.setColor(new Color(236, 240, 241));
        g.drawString("Score: " + score, 15, 25);

        String highStr = "High Score: " + highScore;
        int highWidth = g.getFontMetrics().stringWidth(highStr);
        g.drawString(highStr, SCREEN_WIDTH - highWidth - 15, 25);
    }

    private void drawGameOverScreen(Graphics2D g) {
        // Semi-transparent dark overlay
        g.setColor(new Color(0, 0, 0, 190));
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // Game Over Banner
        g.setColor(new Color(231, 76, 60));
        g.setFont(new Font("Segoe UI", Font.BOLD, 42));
        String overText = "GAME OVER";
        int textWidth = g.getFontMetrics().stringWidth(overText);
        g.drawString(overText, (SCREEN_WIDTH - textWidth) / 2, SCREEN_HEIGHT / 2 - 40);

        // Final Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        String scoreText = "Final Score: " + score;
        int scoreWidth = g.getFontMetrics().stringWidth(scoreText);
        g.drawString(scoreText, (SCREEN_WIDTH - scoreWidth) / 2, SCREEN_HEIGHT / 2 + 5);

        // Restart Prompt
        g.setColor(new Color(241, 196, 15));
        g.setFont(new Font("Segoe UI", Font.BOLD, 16));
        String restartText = "Press [SPACE] or [R] to Play Again";
        int restartWidth = g.getFontMetrics().stringWidth(restartText);
        g.drawString(restartText, (SCREEN_WIDTH - restartWidth) / 2, SCREEN_HEIGHT / 2 + 50);
    }

    private void drawPausedScreen(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 140));
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Segoe UI", Font.BOLD, 36));
        String text = "PAUSED";
        int w = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (SCREEN_WIDTH - w) / 2, SCREEN_HEIGHT / 2);

        g.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        String sub = "Press [P] to Resume";
        int sw = g.getFontMetrics().stringWidth(sub);
        g.drawString(sub, (SCREEN_WIDTH - sw) / 2, SCREEN_HEIGHT / 2 + 40);
    }

    /**
     * Keyboard input handler supporting Arrow Keys, WASD, Pause (P), and Restart (Space/R).
     */
    private class GameKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (gameOver) {
                if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_R) {
                    initGame();
                }
                return;
            }

            if (key == KeyEvent.VK_P) {
                paused = !paused;
                return;
            }

            if (paused) return;

            // Direction inputs
            if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)) {
                snake.setDirection(Direction.LEFT);
            } else if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)) {
                snake.setDirection(Direction.RIGHT);
            } else if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W)) {
                snake.setDirection(Direction.UP);
            } else if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)) {
                snake.setDirection(Direction.DOWN);
            }
        }
    }
}
