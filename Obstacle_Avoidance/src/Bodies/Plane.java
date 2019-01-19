package Bodies;

import Geometry.Coordinate;
import Geometry.Vector;

public class Plane {

	private Coordinate position;
	private double speed;
	
	public Plane(Coordinate p, double s) {
		this.position = p;
		this.speed = s;
	}
	
	public Plane() {
		this.position = null;
		this.speed = 0;
	}
	
	//getters
	public Coordinate getPosition() {
		return this.position;
	}
	public double getSpeed() {
		return this.speed;
	}
	
	//setters
	public void setPosition(Coordinate p) {
		this.position = p;
	}
	public void setSpeed(double s) {
		this.speed = s;
	}
}
