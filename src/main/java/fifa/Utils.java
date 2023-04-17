package fifa;

public class Utils {

	// Empty constructor
	public Utils() {
	};

	// Method to rotate a vector by a given angle
	// Uses trigonometry functions (cos, sin) to perform rotation
	// Returns a new vector with the rotated coordinates
	public static Vector rotate(Vector vel, double angle) {

		Vector rotatedVelocities = new Vector(0, 0);

		rotatedVelocities.xaxis = vel.xaxis * Math.cos(angle) - vel.yaxis * Math.sin(angle);
		rotatedVelocities.yaxis = vel.xaxis * Math.sin(angle) + vel.yaxis * Math.cos(angle);

		return rotatedVelocities;
	}

	// Method to calculate the Euclidean distance between two vectors (positions)
	// Uses the distance formula (square root of sum of squares)
	// Returns the calculated distance
	public static double getDistance(Vector pos1, Vector pos2) {
		double xDistance = pos2.xaxis - pos1.xaxis;
		double yDistance = pos2.yaxis - pos1.yaxis;

		return Math.sqrt(xDistance * xDistance + yDistance * yDistance);
	}
}
