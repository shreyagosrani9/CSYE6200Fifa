package fifa;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball {

    // Object behaviour attributes
    public int maximumspeed = 3;
    public double airResistance = 0.02;
    public double acceleration = 0.2;
    public double mass = 10;
    //

    protected Vector center;
    protected double maxDistance;

    public boolean IS_BALL;
    public boolean shooting = false;
    public boolean lockMovement = false;

    public Vector position;
    public Vector vel;

    protected int startX, startY;

    final int size = 20;
    private final double centerOffset = 6d;

    public Circle ball;

    public String name = "ball";
    protected boolean canShoot;

    public boolean makeShootable = false;
    protected Vector startPosition;

    public Ball() {
    };

    public Ball(Elements list, Vector startPosition, Circle field) {
        IS_BALL = true;

        position = new Vector(startPosition.x, startPosition.y);
        vel = new Vector(0, 0);
        this.startPosition = startPosition;

        ball = new Circle(0f, 0f, size);
        ball.setFill(Color.WHITE);
        list.add(ball);

        setCenter(field);
    }

    public void draw() {
        ball.setCenterX(position.x);
        ball.setCenterY(position.y);
    }

    protected void move(double dx, double dy) {

        if (lockMovement)
            return;

        Vector newPosX = new Vector(position.x + dx, position.y);
        Vector newPosY = new Vector(position.x, position.y + dy);

        double distX = Math.abs(Utils.getDistance(center, newPosX));
        double distY = Math.abs(Utils.getDistance(center, newPosY));

        double effect = 0.7;

        if (distX < maxDistance)
            position.x = newPosX.x;
        else
            vel.x *= -effect;

        if (distY < maxDistance)
            position.y = newPosY.y;
        else
            vel.y *= -effect;
    }

    public void shoot(Vector from, double strength) {

        shooting = true;

        // Create a new normalized vector from 'from' to ball 'pos'
        double Xcomponent = from.x - position.x;
        double Ycomponent = from.y - position.y;

        double length = Math.sqrt(Xcomponent * Xcomponent + Ycomponent * Ycomponent);

        Xcomponent /= length;
        Ycomponent /= length;

        vel.x = -Xcomponent * strength;
        vel.y = -Ycomponent * strength;
    }

    public boolean isShooting() {
        return shooting;
    }

    public void resetPos() {
        position = new Vector(startPosition.x, startPosition.y);
        vel = new Vector(0, 0);
    }

    // --------- Single use methods ---------

    public void setCenter(Circle field) {
        center = new Vector(field.getCenterX(), field.getCenterY());
        maxDistance = field.getRadius() - ball.getRadius() - centerOffset;
    }

    public void update() {
        if (vel.x > 0)
            vel.x -= airResistance;
        if (vel.x < 0)
            vel.x += airResistance;
        if (vel.y > 0)
            vel.y -= airResistance;
        if (vel.y < 0)
            vel.y += airResistance;

        move(vel.x, vel.y);
        draw();
    }

    public void makeShootable(boolean value) {
        makeShootable = value;
    }
}