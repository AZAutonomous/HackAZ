package Bodies;

import Geometry.Coordinate;

public class Waypoint {
	private Coordinate coordinate;
	
	public Waypoint() {
		this.coordinate = null;
	}
	
	public Waypoint(Coordinate coordinate) {
		this.coordinate = coordinate;
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
