package Bodies;

import Geometry.Coordinate;
import Geometry.Vector;

public class Plane {

	Coordinate position;
	double speed;
	
	public Plane(Coordinate p, double s) {
		this.position = p;
		this.speed = s;
	}
	
	public Plane() {
		this.position = null;
		this.speed = 0;
	}
}
