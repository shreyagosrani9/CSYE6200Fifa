package fifa;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Player extends Ball {

    // Object behavior attributes
    int maximumSpeed = 3;
    double airResistance = 0.07;
    double acceleration = 0.2;
    double mass = 10;

    public boolean none, shooting, goNorth, goSouth, goEast, goWest;

    public double size = 27;
    public int score = 0;

    public Player(Elements list, Vector startPos, Paint color, Circle field, String name) {
        IS_BALL = false;

        this.name = name;

        position = new Vector(startPos.xaxis, startPos.yaxis);
        velocity = new Vector(0, 0);
        this.startPosition = startPos;

        ball = new Circle(0f, 0f, size);
        ball.setFill(color);
        ball.setStroke(Color.BLACK);
        ball.setStrokeWidth(2.0);
        list.add(ball);

        setCenter(field);
    }

    // --------- Single-use methods ---------

    /**
     * Update method that handles player movement and behavior.
     */
    public void update() {

        // Update vertical velocity (y-axis)
        if (Math.abs(velocity.yaxis) < maximumSpeed) {
            if (goNorth)
                velocity.yaxis -= acceleration;
            if (goSouth)
                velocity.yaxis += acceleration;
        }

        // Update horizontal velocity (x-axis)
        if (Math.abs(velocity.xaxis) < maximumSpeed) {
            if (goEast)
                velocity.xaxis += acceleration;
            if (goWest)
                velocity.xaxis -= acceleration;
        }

        // Apply air resistance when player is not moving
        if (none) {
            if (velocity.xaxis > 0)
                velocity.xaxis -= airResistance;
            if (velocity.xaxis < 0)
                velocity.xaxis += airResistance;
            if (velocity.yaxis > 0)
                velocity.yaxis -= airResistance;
            if (velocity.yaxis < 0)
                velocity.yaxis += airResistance;
        }

        // Update stroke color based on shooting state
        if (shooting) {
            ball.setStroke(Color.DARKORANGE);
        } else if (!shooting) {
            ball.setStroke(Color.BLACK);
        } else {
            ball.setStroke(Color.WHITE);
        }

        // Move player based on velocity and redraw
        move(velocity.xaxis, velocity.yaxis);
        draw();
    }

    /**
     * Check if the player is in shooting state.
     *
     * @return true if the player is in shooting state, false otherwise.
     */
    public boolean isShooting() {
        return shooting;
    }
}
