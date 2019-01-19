package Bodies;

import Geometry.Coordinate;
import Geometry.Vector;

public class Obstacle {
	private Coordinate coordinate;
	private Vector direction;
	private int radius;
	public int turnRadius;
	
	private double theta1;
	private double theta2;
	private double theta3;
	private int addedThetas;
	
	public Obstacle() {
		this.theta1 = 0.0;
		this.theta2 = 0.0;
		this.theta3 = 0.0;	
		addedThetas = 0;
	}
	
	
	//getters
	public Coordinate getCoordinate() {
		return this.coordinate;
	}
	public Vector getDirection() {
		return this.direction;
	}
	public int getRadius() {
		return this.radius;
	}
	
	
	//setters
	public void setCoordinate(Coordinate c){
		this.coordinate = c;
	}
	public void setDirection(Vector v) {
		this.direction = v;
	}
	public void setRadius(int r) {
		this.radius = r;
	}
	
	public void addTheta(double newTheta) {
		this.theta3 = this.theta2;
		this.theta2 = this.theta1;
		this.theta1 = newTheta;
		if(addedThetas < 3) {
			addedThetas++;
		}
	}
	
	public double getAverageTheta() {
		if(addedThetas == 0) {
			return 0.0;
		}
		else if(addedThetas == 1) {
			return this.theta1;
		}
		else if(addedThetas == 2) {
			return ((this.theta1 + this.theta2) / 2.0);
		}
		else {
			return ((this.theta1 + this.theta2 + this.theta3) / 3.0);
		}
		
	}
	
	
	
	
}
