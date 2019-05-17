package Bodies;

import Geometry.Coordinate;

public class Waypoint {
	private Coordinate coordinate;
	public boolean isObjective;
	
	public Waypoint() {
		this.coordinate = null;
	}
	
	public Waypoint(Coordinate coordinate, boolean o) {
		this.coordinate = coordinate;
		this.isObjective = o;
	}
	
	//getters
	public Coordinate getCoordinate() {
		return this.coordinate;
	}
	
	//setters
	public void setCoordinate(Coordinate c){
		this.coordinate = c;
	}
}
