package Bodies;

import java.util.ArrayList;

public class WaypointList {
	private ArrayList<Waypoint> list;
	
	//getters
	public ArrayList<Waypoint> getWaypointList(){
		return this.list;
	}
	
	//setters
	public void setWaypointList(ArrayList<Waypoint> l) {
		this.list = l;
	}
	
	//public methods 
	public void insertWaypoint(Waypoint newWaypoint, int newIndex) {
		list.add(newIndex, newWaypoint);
	}
	
}

