package Geometry;

public class Vector {
	private double x, y, magnitude;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
		this.calculateMagnitude();
	}
	
	public Vector() {
		this(0,0);
	}
	
	public Vector add(Vector that) {
		return new Vector(this.x + that.x, this.y + that.y);
	}
	
	public Vector subtract(Vector that) {
		return new Vector(this.x - that.x, this.y - that.y);
	}
	
	private void calculateMagnitude() {
		this.magnitude = Math.sqrt(Math.pow(this.x,2) + Math.pow(this.y,2));
	}
	
	public void setX(double x) {
		this.x = x;
		this.calculateMagnitude();
	}

	public double getX() {
		return this.x;
	}
	
	public void setY(double y) {
		this.y = y;
		this.calculateMagnitude();
	}
	
	public double getY() {
		return this.y;
	}
	
	public Vector scale(double mult) {
		return new Vector(this.x * mult, this.y * mult);
	}
	
	public double getDirection() {
		if (this.x > 0) {
			return Math.atan(this.y / this.x);
		} else if (this.x < 0) {
			return Math.atan(this.y / this.x) + Math.PI;
		} else if (this.x == 0) {
			if (this.y > 0) {
				return Math.PI / 2;
			} else if (this.y < 0) {
				return -1 * Math.PI / 2;
			} else if (this.y == 0) {
				return 0;
			}
		}
		
		return 0;
	}
	
	public double dotProduct(Vector that) {
		return (this.x * that.x) + (this.y * that.y); 
	}
	
	public double determinant(Vector that) {
		return (this.x * that.y)- (this.y * that.x); 
	}
	
	/*public double angleBetween(Vector that) {
		double result, dot, deter;
		dot = this.dotProduct(that);
	}*/
}
