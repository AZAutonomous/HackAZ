package Bodies;
import java.util.ArrayList;

import Geometry.Vector;
import Geometry.Coordinate;

public class Map {
	private ArrayList<Body> objects ; 
	
	public Map() {
		objects = new ArrayList<Body>();
		//objects.add(new Obstacle(new Coordinate(0,0), new Vector(1,0), 1, 1));
		objects.add(new Plane(new Coordinate(5,5), new Vector(0,-1)));
	}
	
	
	
}
