import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Teammate 2: Snake Body & Movement Model
 * Manages the snake's segments, direction, growth, and self-collision.
 */
public class Snake {
    private final List<Point> body;
    private Direction direction;
    private Direction nextDirection;
    private boolean shouldGrow;

    public Snake(int startX, int startY, int initialLength) {
        this.body = new ArrayList<>();
        this.direction = Direction.RIGHT;
        this.nextDirection = Direction.RIGHT;
        this.shouldGrow = false;

        // Initialize snake starting from head moving leftward
        for (int i = 0; i < initialLength; i++) {
            body.add(new Point(startX - i, startY));
        }
    }

    public List<Point> getBody() {
        return body;
    }

    public Point getHead() {
        return body.get(0);
    }

    public Direction getDirection() {
        return direction;
    }

    /**
     * Queues a direction change while preventing instant 180-degree self-reversals.
     */
    public void setDirection(Direction newDir) {
        if (!direction.isOpposite(newDir)) {
            nextDirection = newDir;
        }
    }

    /**
     * Advances the snake forward by one grid tile in the current direction.
     */
    public void move() {
        direction = nextDirection; // Apply the queued direction for this tick
        Point currentHead = getHead();
        Point newHead = new Point(currentHead.x, currentHead.y);

        switch (direction) {
            case UP:    newHead.y--; break;
            case DOWN:  newHead.y++; break;
            case LEFT:  newHead.x--; break;
            case RIGHT: newHead.x++; break;
        }

        // Add the new head at the beginning of the list
        body.add(0, newHead);

        // If not growing, remove the old tail
        if (!shouldGrow) {
            body.remove(body.size() - 1);
        } else {
            shouldGrow = false; // Reset grow flag after eating
        }
    }

    /**
     * Instructs the snake to grow on its next move.
     */
    public void grow() {
        shouldGrow = true;
    }

    /**
     * Checks if the snake's head has collided with any part of its own body.
     */
    public boolean checkSelfCollision() {
        Point head = getHead();
        // Check from index 1 to the end (ignore head colliding with itself)
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a point occupies any part of the snake.
     */
    public boolean occupies(Point p) {
        for (Point segment : body) {
            if (segment.equals(p)) {
                return true;
            }
        }
        return false;
    }
}
