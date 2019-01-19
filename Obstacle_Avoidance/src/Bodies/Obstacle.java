package Bodies;

import Geometry.Coordinate;
import Geometry.Vector;

public class Obstacle {
	private Coordinate coordinate;
	private Vector direction;
	private int radius;
	public int turnRadius;
	
	
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
	
	
	
	
}
