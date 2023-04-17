package fifa;

public class Vector {
	public double xaxis;
	public double yaxis;

	//Constructor of vector 
	public Vector(double x, double y) {
		this.xaxis = x;
		this.yaxis = y;
	}

	// Method to add another vector to this vector
	public Vector add(Vector n) {
		xaxis += n.xaxis;
		yaxis += n.yaxis;
		return this;
	}

	// Method to extend the length of this vector by a scalar factor
	public Vector extend(double a) {
		xaxis *= a;
		yaxis *= a;
		return this;
	}

	// Method to calculate the length of this vector
	public double getLength() {
		return Math.sqrt(xaxis * xaxis + yaxis * yaxis);
	}
}
