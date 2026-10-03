import java.awt.Point;
import java.util.Random;

/**
 * Teammate 3: Food & Spawning Engine
 * Manages spawning food apples at random unoccupied locations on the grid.
 */
public class Food {
    private Point position;
    private final Random random;

    public Food() {
        this.position = new Point(0, 0);
        this.random = new Random();
    }

    public Point getPosition() {
        return position;
    }

    /**
     * Randomly repositions the food onto an empty tile that the snake does not occupy.
     */
    public void respawn(int gridWidth, int gridHeight, Snake snake) {
        Point candidate = new Point();
        boolean valid = false;

        while (!valid) {
            candidate.x = random.nextInt(gridWidth);
            candidate.y = random.nextInt(gridHeight);

            // Ensure food does not spawn inside the snake's body
            if (snake == null || !snake.occupies(candidate)) {
                valid = true;
            }
        }

        this.position = candidate;
    }

    /**
     * Checks if the snake's head occupies the same grid tile as the food.
     */
    public boolean isEatenBy(Point head) {
        return position.equals(head);
    }
}
