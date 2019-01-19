package Bodies;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Geometry.Coordinate;

public class WaypointList {
	private ArrayList<Waypoint> list;
	
	public WaypointList() {
		this.list = new ArrayList<Waypoint>();
	}
	
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
	
	public void readWaypointsFromJson() {
		
		File f = new File("Waypoints.json");
		Scanner sc = null;
		try {
			sc = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sc.nextLine();
		sc.nextLine();
		while(sc.hasNextLine()) {
			String currLine = sc.nextLine();
			if(currLine.contains("{")) {
				
				Waypoint newWaypoint = new Waypoint();
				Coordinate newCoordinate = new Coordinate();
				
				newCoordinate.setX(Integer.parseInt(
						sc.nextLine()
						.replaceAll("\"long\":","")
						.replaceAll(",","")
						.replace(" ","")
						.replaceAll("\t","")));
				newCoordinate.setX(Integer.parseInt(
						sc.nextLine()
						.replaceAll("\"lat\":","")
						.replaceAll(",","")
						.replace(" ","")
						.replaceAll("\t","")));
				
				newWaypoint.setCoordinate(newCoordinate);
				list.add(newWaypoint);
			}
		}
		
		
		
	}
	
}

