package Bodies;

import Geometry.Coordinate;

public class Waypoint {
	
	private Coordinate coordinate;
	
	private boolean hasVistied;
	
	//getters
	public Coordinate getCoordinate() {
		return this.coordinate;
	}
	public boolean hasVistited() {
		return this.hasVistied;
	}
	
	//setters
	public void setCoordinate(Coordinate c){
		this.coordinate = c;
	}
	public void setVisited(boolean v) {
		this.hasVistied = v;
	}
	
}
