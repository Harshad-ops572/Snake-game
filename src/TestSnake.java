import java.awt.Point;

/**
 * Automated test suite for Snake game physics and collision logic.
 */
public class TestSnake {
    public static void main(String[] args) {
        System.out.println("Starting Snake Game Automated Verification...\n");

        testSnakeMovementAndDirection();
        testGrowthMechanics();
        testSelfCollision();
        testFoodSpawning();

        System.out.println("\nALL SNAKE ENGINE TESTS PASSED!");
    }

    private static void testSnakeMovementAndDirection() {
        System.out.print("1. Testing Snake Movement & 180 Reversal Guard... ");

        Snake snake = new Snake(5, 5, 3);
        assert snake.getBody().size() == 3 : "Initial size should be 3";
        assert snake.getHead().equals(new Point(5, 5)) : "Initial head should be (5, 5)";

        // Move RIGHT
        snake.move();
        assert snake.getHead().equals(new Point(6, 5)) : "Head should be at (6, 5)";

        // Try to reverse directly LEFT while moving RIGHT (should be ignored)
        snake.setDirection(Direction.LEFT);
        snake.move();
        assert snake.getHead().equals(new Point(7, 5)) : "Snake should continue RIGHT, not reverse into itself";

        // Turn UP
        snake.setDirection(Direction.UP);
        snake.move();
        assert snake.getHead().equals(new Point(7, 4)) : "Head should be at (7, 4) after moving UP";

        System.out.println("PASSED");
    }

    private static void testGrowthMechanics() {
        System.out.print("2. Testing Snake Growth Mechanics... ");

        Snake snake = new Snake(5, 5, 3);
        int initialSize = snake.getBody().size();

        snake.grow();
        snake.move();

        assert snake.getBody().size() == initialSize + 1 : "Snake length should have increased by 1";
        System.out.println("PASSED");
    }

    private static void testSelfCollision() {
        System.out.print("3. Testing Self Collision Detection... ");

        // Create a longer snake: length 5
        Snake snake = new Snake(10, 10, 5);
        assert !snake.checkSelfCollision() : "Fresh snake should not collide with itself";

        // Perform loop: UP -> LEFT -> DOWN (looping back into body)
        snake.setDirection(Direction.UP);
        snake.move();
        snake.setDirection(Direction.LEFT);
        snake.move();
        snake.setDirection(Direction.DOWN);
        snake.move();

        assert snake.checkSelfCollision() : "Snake should have collided with its own segment";
        System.out.println("PASSED");
    }

    private static void testFoodSpawning() {
        System.out.print("4. Testing Food Spawning Logic... ");

        Snake snake = new Snake(10, 10, 5);
        Food food = new Food();

        for (int i = 0; i < 50; i++) {
            food.respawn(24, 24, snake);
            Point p = food.getPosition();
            assert p.x >= 0 && p.x < 24 : "Food X out of bounds";
            assert p.y >= 0 && p.y < 24 : "Food Y out of bounds";
            assert !snake.occupies(p) : "Food spawned inside snake body!";
        }

        System.out.println("PASSED");
    }
}
