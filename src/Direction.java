/**
 * Direction enum representing the 4 cardinal directions the snake can travel.
 */
public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    /**
     * Checks if moving in the new direction would cause an instant 180-degree reversal.
     */
    public boolean isOpposite(Direction other) {
        return (this == UP && other == DOWN) ||
               (this == DOWN && other == UP) ||
               (this == LEFT && other == RIGHT) ||
               (this == RIGHT && other == LEFT);
    }
}
