package Bodies;
import java.util.ArrayList;
import java.util.List;

import Geometry.Vector;
import Geometry.Coordinate;

public class Map {
	private List<Obstacle> obstacles; 
	private Plane plane;
	
	public Map() {
		obstacles = new ArrayList<Obstacle>();
		obstacles.add(new Obstacle(new Coordinate(0,0), new Vector(1,0)));
		//obstacles.add(new Plane(new Coordinate(5,5), new Vector(0,-1)));
	}
	
	
	
}
