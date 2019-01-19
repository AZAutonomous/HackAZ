package Bodies;

import java.util.ArrayList;
import java.util.List;

import Geometry.Coordinate;
import Geometry.Line;
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
	
	public Waypoint calculateNextAnchor(Waypoint nextDestination, List<Obstacle> obstacles, int length) {
		List<Coordinate> possibleCollisions = new ArrayList<Coordinate>();
		Line currentPath = new Line(this.position, nextDestination.getCoordinate());
		for (Obstacle o : obstacles) {
			List<Line> dangerLines = o.getDangerLines(length);
			
			
		}
	}
}
