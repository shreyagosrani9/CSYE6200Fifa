package fifa;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball {

    // Object behaviour attributes
    public int maximumspeed = 3;
    public double AIR_RESISTANCE = 0.02;
    public double acceleration = 0.2;
    public double mass = 10;
    protected Vector center;
    protected double maxDistance;
    public boolean IS_BALL;
    public boolean shooting = false;
    public boolean lockMovement = false;
    public Vector position;
    public Vector velocity;
    protected int startX, startY;
    final int size = 20;
    private final double centerOffset = 6d;
    public Circle ball;
    public String name = "ball";
    protected boolean canShoot;
    public boolean makeShootable = false;
    protected Vector startPosition;

    public Ball() {
    }

    // Constructor for Ball class
    public Ball(Elements list, Vector startPosition, Circle field) {
        IS_BALL = true;

        position = new Vector(startPosition.xaxis, startPosition.yaxis);
        velocity = new Vector(0, 0);
        this.startPosition = startPosition;

        ball = new Circle(0f, 0f, size);
        ball.setFill(Color.WHITE);
        list.add(ball);

        setCenter(field);
    }

    // Update the position of the ball on the screen
    public void draw() {
        ball.setCenterX(position.xaxis);
        ball.setCenterY(position.yaxis);
    }

    // Move the ball by a given amount in the x and y direction
    protected void move(double dx, double dy) {

        if (lockMovement)
            return;

        Vector newPosX = new Vector(position.xaxis + dx, position.yaxis);
        Vector newPosY = new Vector(position.xaxis, position.yaxis + dy);

        double distX = Math.abs(Utils.getDistance(center, newPosX));
        double distY = Math.abs(Utils.getDistance(center, newPosY));

        double effect = 0.7;

        if (distX < maxDistance)
            position.xaxis = newPosX.xaxis;
        else
            velocity.xaxis *= -effect;

        if (distY < maxDistance)
            position.yaxis = newPosY.yaxis;
        else
            velocity.yaxis *= -effect;
    }

    // Shoot the ball from a given position with a given strength
    public void shoot(Vector from, double strength) {

        shooting = true;

        // Create a new normalized vector from 'from' to ball 'pos'
        double Xcomponent = from.xaxis - position.xaxis;
        double Ycomponent = from.yaxis - position.yaxis;
        double length = Math.sqrt(Xcomponent * Xcomponent + Ycomponent * Ycomponent);

        Xcomponent /= length;
        Ycomponent /= length;

        velocity.xaxis = -Xcomponent * strength;
        velocity.yaxis = -Ycomponent * strength;
    }

    // Check if the ball is currently being shot
    public boolean isShooting() {
        return shooting;
    }

    // Reset the position and velocity of the ball to its starting position
    public void resetPos() {
        position = new Vector(startPosition.xaxis, startPosition.yaxis);
        velocity = new Vector(0, 0);
    }

    // Set the center of the ball and calculate the maximum distance the ball can move from the center
    public void setCenter(Circle field) {
        center = new Vector(field.getCenterX(), field.getCenterY());
        maxDistance = field.getRadius() - ball.getRadius() - centerOffset;
    }

    // Update the position and velocity of the ball based on air resistance and call the draw method to update the display	
	public void update() {
		if (velocity.xaxis > 0)
			velocity.xaxis -= AIR_RESISTANCE;
		if (velocity.xaxis < 0)
			velocity.xaxis += AIR_RESISTANCE;
		if (velocity.yaxis > 0)
			velocity.yaxis -= AIR_RESISTANCE;
		if (velocity.yaxis < 0)
			velocity.yaxis += AIR_RESISTANCE;
		
		move(velocity.xaxis, velocity.yaxis);
		draw();
	}

	public void makeShootable(boolean value) {
		makeShootable = value;
	}
}
