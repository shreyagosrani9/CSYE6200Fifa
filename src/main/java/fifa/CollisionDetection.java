package fifa;

import java.util.Collection;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class CollisionDetection {

    private final int collisionBoundary = 5;
    private HashMap<Integer, Ball> dynamicObj = new HashMap<Integer, Ball>();
    private HashMap<Integer, Rectangle> staticObj = new HashMap<Integer, Rectangle>();
    private Rectangle bottomFieldBorder;
    private int dynamicSize = 0;
    private int staticSize = 0;
    private final double shootingStrength = 5.0;
    private final double bounceEffectBall = 1.05;
    private final double ballShootableOffset = 10.00;
    private final double goalGateAngles[] = { 0, -2 * Math.PI / 3, 2 * Math.PI / 3 };
    private final double borderAngles[] = { Math.PI / 3, Math.PI, 5 * Math.PI / 3 };
    public boolean playKickSound = false;
    private Logic gameLogic;
    private Hitboxes hitboxes;

    public CollisionDetection(Rectangle border, Logic gameLogic, Hitboxes hitboxes) {
        bottomFieldBorder = border;
        this.gameLogic = gameLogic;
        this.hitboxes = hitboxes;
    }

    public void addDynamic(Ball ball) {
        dynamicObj.put(dynamicSize++, ball);
    }

    public void addStatic(Collection<Node> walls) {
        for (Node w : walls) {
            staticObj.put(staticSize++, (Rectangle) w);
        }
    }

    private boolean checkStaticCollision(Ball b, Rectangle wall, double angle) {
        double radius = b.ball.getRadius();

        Vector position = rotateCoordinates(b.position, angle, new Vector(App.WIDTH / 2, App.HEIGHT / 2));

        double x = position.xaxis;
        double y = position.yaxis;
        double minX = wall.getX() - radius;
        double maxX = minX + wall.getWidth() + radius;
        double minY = wall.getY() - radius;
        double maxY = minY + wall.getHeight() + radius;

        return y >= minY && y <= maxY && x >= minX && x <= maxX;
    }

    private void resolveStaticCollision(Ball b, double angle) {
        Vector newVelocity = Utils.rotate(b.velocity, (Math.PI / 4) - 2 * angle);
        newVelocity.xaxis *= bounceEffectBall;
        newVelocity.yaxis *= bounceEffectBall;
        b.velocity = newVelocity;
    }

    private void resolveSimpleStaticCollision(Ball b) {
        b.velocity.xaxis *= -bounceEffectBall;
        b.velocity.yaxis *= -bounceEffectBall;
    }

    private Vector rotateCoordinates(Vector vec, double angle, Vector anchor) {
        double sinus = Math.sin(angle);
        double cosinus = Math.cos(angle);
        Vector temp = new Vector(0, 0);
        temp.xaxis = (vec.xaxis - anchor.xaxis) * cosinus - (vec.yaxis - anchor.yaxis) * sinus + anchor.xaxis;
        temp.yaxis = (vec.yaxis - anchor.yaxis) * cosinus + (vec.xaxis - anchor.xaxis) * sinus + anchor.yaxis;
        return temp;
    }

    public boolean checkBallCollision(Ball b1, Ball b2) {
        double distance = Utils.getDistance(b1.position, b2.position);
        return distance <= (b1.size + b2.size + collisionBoundary);
    }

	// Code resource from
	// https://gist.github.com/christopher4lis/f9ccb589ee8ecf751481f05a8e59b1dc

	private void resolveBallCollision(Ball particle, Ball otherParticle) {
		double xVelocityDiff = particle.velocity.xaxis - otherParticle.velocity.xaxis;
		double yVelocityDiff = particle.velocity.yaxis - otherParticle.velocity.yaxis;
		double xDist = otherParticle.position.xaxis - particle.position.xaxis;
		double yDist = otherParticle.position.yaxis - particle.position.yaxis;

		// Prevent accidental overlap of particles
		if (xVelocityDiff * xDist + yVelocityDiff * yDist >= 0) {

			// Grab angle between the two colliding particles
			double angle = -Math.atan2(otherParticle.position.yaxis - particle.position.yaxis,
					otherParticle.position.xaxis - particle.position.xaxis);

			// Store mass in var for better readability in collision equation
			double m1 = particle.mass;
			double m2 = otherParticle.mass;

			// Velocity before equation
			Vector u1 = Utils.rotate(particle.velocity, angle);
			Vector u2 = Utils.rotate(otherParticle.velocity, angle);

			// Velocity after 1d collision equation
			Vector v1 = new Vector(0, 0);
			Vector v2 = new Vector(0, 0);

			v1.xaxis = u1.xaxis * (m1 - m2) / (m1 + m2) + u2.xaxis * 2 * m2 / (m1 + m2);
			v1.yaxis = u1.yaxis;

			v2.xaxis = u2.xaxis * (m2 - m1) / (m1 + m2) + u1.xaxis * 2 * m1 / (m1 + m2);
			v2.yaxis = u2.yaxis;

			// Final velocity after rotating axis back to original location
			Vector vFinal1 = Utils.rotate(v1, -angle);
			Vector vFinal2 = Utils.rotate(v2, -angle);

			// Swap particle velocities for realistic bounce effect
			particle.velocity.xaxis = vFinal1.xaxis;
			particle.velocity.yaxis = vFinal1.yaxis;

			otherParticle.velocity.xaxis = vFinal2.xaxis;
			otherParticle.velocity.yaxis = vFinal2.yaxis;
		}
	}

	// ---------- Timers and events ----------

	public void collisionChecks() {
		// Resolve collisions between dynamic objects
		for (int i = 0; i < dynamicSize; i++) {
			for (int j = i + 1; j < dynamicSize; j++) {
				Ball b1 = dynamicObj.get(i);
				Ball b2 = dynamicObj.get(j);
				if (checkBallCollision(b1, b2) == true) {
					resolveBallCollision(b1, b2);
					playKickSound = true;
				}
				// Check if player can shoot the ball
				if (b1.IS_BALL && !b2.IS_BALL) {
					double d = Utils.getDistance(b1.position, b2.position);
					if (d < (b1.size + b2.size + 2 * ballShootableOffset)) {
						b2.makeShootable(true);
						if (b2.isShooting()) {
							b1.shoot(b2.position, shootingStrength);
							b2.makeShootable(false);
						}
					} else {
						b2.makeShootable(false);
					}
				}
			}
		}

		// Resolve collisions between ball and static objects
		for (int i = 0; i < dynamicSize; i++) {
			Ball b = dynamicObj.get(i);
			if (b.IS_BALL) {
				for (int s = 0; s < 3; s++) {
					if (checkStaticCollision(b, bottomFieldBorder, goalGateAngles[s])) {
						if (s == 0)
							resolveSimpleStaticCollision(b);
						else
							resolveStaticCollision(b, goalGateAngles[s]);
					}
					if (checkStaticCollision(b, bottomFieldBorder, borderAngles[s])) {
						if (s == 1)
							resolveSimpleStaticCollision(b);
						else
							resolveStaticCollision(b, borderAngles[s]);
					}
				}

				// Check for ball object and for all 3 angles
				for (int j = 0; j < staticSize; j++) {
					Rectangle wall = staticObj.get(j);
					for (int s = 0; s < 3; s++) {
						if (checkStaticCollision(b, wall, goalGateAngles[s])) {
							// Check for a score
							if (wall.getWidth() == hitboxes.getGoalHitbox().getWidth()) {
								gameLogic.goalDetected(s);
							}
							resolveSimpleStaticCollision(b);
						}
					}
				}
			}
		}
	}
}
