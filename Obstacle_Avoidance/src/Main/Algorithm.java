package Main;

import java.util.ArrayList;
import java.util.List;

import Bodies.Obstacle;
import Bodies.Plane;
import Bodies.Waypoint;
import Bodies.WaypointList;
import Geometry.Coordinate;
import Geometry.Line;

public class Algorithm {
	
	//the amount of time to sleep between each ping between continuous processing
	//(not used in NonDynamic mode)
	public final static int MILISECONDS_DELAY = 1000;
	//the amount of objective waypoint to look ahead when calc collisions
	public final static int LOOKAHEAD_AMOUNT = 2;
	//the radius for which a "hit" can be counted in feet
	public final static double PLANE_HIT_RADIUS = 100;
	
	//Private variables used by the algorithm
	//these variables are references directly passed from this program's data retrieval (PlaneThread)
	private ArrayList<Obstacle> obstacleList;
	private WaypointList waypointList;
	public boolean dynamic;
	private Plane plane;
	
	
	/*Runs the Main Loop for the Algorithm
	 * PARAMS
	 * ol: obstacle List
	 * wl: waypoints List
	 * d: dynamic true/false value
	 * p: plane
	 * 
	 * Returns: Void but updates and inserts new waypoints into wl (waypoints list)
	 * 
	 * TODO: Testing is not thoroughly complete
	 * TODO: please complete and test more edge cases
	 * TODO: does not work and is not tested for "Dynamic mode"
	 * TODO: Waypoint "Smoothing" (inserted waypoints do not have and are not adjusted for altitude)  
	 */
	public void RunAlgorithm(ArrayList<Obstacle> ol, WaypointList wl, boolean d, Plane p) {
		obstacleList = ol;
		waypointList = wl;
		dynamic = d;
		plane = p;
		
		//check for collision
		int collisionsIndex = findDangerousObstacle();
		if(collisionsIndex != -1) {
			System.out.println("collisions detected...");
		}
		collisionsIndex = findDangerousObstacle();
		//run overall algorithm until no collisions are detectable 
		while(collisionsIndex != -1) {
			
			//set current obstacle trying to avoid
			Obstacle dangerousObstacle = obstacleList.get(collisionsIndex);
			
			//go into the first waypoint that the obstacle collides with
			for(int j = 0; j < waypointList.getWaypointList().size()-1; j++) {
				Waypoint a1 = waypointList.getWaypointList().get(j);
				Waypoint a2 = waypointList.getWaypointList().get(j+1);
				//if there is a collision for that obstacle and the current two waypoints being checked
				if(ObstacleCollides(a1.getCoordinate(),a2.getCoordinate(), dangerousObstacle)) {
					//avoid that obstacle
					avoidObstacle(j,a1.getCoordinate(),a2.getCoordinate(),dangerousObstacle);
					break;
				}
			}
			//recheck for collisions
			collisionsIndex = findDangerousObstacle();
		}
		
		System.out.println("done.");
		
	}
	
	//returns the index of an obstacle that has a collision between current path and next objective waypoint
	//returns -1 if none are found
	//returns the first obstacle that collides with the soonest waypoint path segment
	private int findDangerousObstacle() {
		int i = 0;
		for(Obstacle currObstacle : obstacleList) {
			List<Coordinate> coors;
			if(this.dynamic) {
				//get list of Coordinats starting at the planes current location
				coors = getXNextObjectiveCoordinates(LOOKAHEAD_AMOUNT);
			}
			else {
				coors = new ArrayList<Coordinate>();;
				for(Waypoint w : this.waypointList.getWaypointList()) {
					coors.add(w.getCoordinate());
				}
			}
			
			//calc if the current predictive obstacles vector intersects over each line segment
			for(int j = 0; j < coors.size()-1; j++) {
				Coordinate a1 = coors.get(j);
				Coordinate a2 = coors.get(j+1);
				if(ObstacleCollides(a1,a2, currObstacle)) {
					//if so return the index of that obstacle
				return i;
				}
			}
				
				
				
			i++;
		}
		return -1;
	}
		
	/*Calculates if an obstacle collides with a line segment
	 * 
	 * PARAMS:
	 * al: point 1 of the line segment
	 * a2: point 2 of the line segment
	 * o1: the obstalce
	 * 
	 * Returns: True if the given linesegment intersects the circle formed by the obstacle
	 * 	otherwise return False
	 * 
	 */
	private boolean ObstacleCollides(Coordinate a1, Coordinate a2, Obstacle o1) {
		Line w1 = new Line(a1, a2);
		double dist1 = w1.getLength();
		
		//if obstacle is static check for a regular collision
		if(o1.getDirection().getMagnitude() == 0) {
			return StaticCollides(w1,o1);
		}
		
		//calculates "danger lines" that are extensions of the 
		//we do dist1 times 3 because an obstacle should move no faster than our plane
		//so we do not worry about any distance covered after we move past these two points
		//we multiply by 3 as an added buffer just to be safe
		//this is not nessessary and shouldn't matter if the program runs in non static mode
		List<Line> dangerLines = o1.getDangerLines(dist1*3);
		for(Line l : dangerLines) {
			if(Line.intersect(w1, l)) {
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * gives the next x waypoint counting only "objectives"
	 * 
	 * PARAMS: x num
	 * 
	 * Returns: List of waypoint where the line segment intersects
	 * 	Range of the size of this list is 0 - 2 inclusive
	 * 
	 */
	private List<Coordinate> getXNextObjectiveCoordinates(int x) {
		List<Coordinate> coors = new ArrayList<Coordinate>();
		int i = 0;
		if(dynamic) {
			coors.add(plane.getPosition());
		}
		for(Waypoint w : waypointList.getWaypointList()) {
			
			coors.add(w.getCoordinate());
			
			if(w.isObjective) {
				i++;
			}
			if(i >= x) {
				break;
			}
		}
		return coors;
	}
	
	//returns true if the line l intersects the circle made by o1
	//returns false otherwise
	//TODO: test this function
	// TESTED!!!, could use a few more cases tho
	private boolean StaticCollides(Line l, Obstacle o1) {
		List<Coordinate> intersections = Coordinate.getCircleLineIntersectionPoint(l.getStart(),l.getEnd(),o1.getCoordinate(),o1.getRadius());
		if(intersections.size() == 0) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	//TODO implement and test avoidance for waypoints resting inside of an obstacle
	private void avoidInside(int j) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * runs the main avoidance calculation and insertion of a new waypoint
	 * this calculation is ran over the linesegment made by points a1 a2 
	 * and over the obstacle currObstacle
	 * 
	 * PARAMS: 
	 * j: the index of the waypoint (a1) in the waypoints list
	 * a1: the coordinate of the first point of the line segment 
	 * a2: the coordinate of the second point of the line segment
	 * currObstacle: the obstacle to be avoided
	 *  
	 * Returns: void but edits and inserts new waypoints into waypointslist
	 */
	//avoids an obstacle colliding with the path created by the waypoints of coordinates a1 and a2
	//these avoidance points will be inserted at indexes j and j+1
	private void avoidObstacle(int j, Coordinate a1, Coordinate a2, Obstacle currObstacle) {
		System.out.println("collision detected at " + j +  " : avoiding...");
		
		
		List<Coordinate> intersections = Coordinate.getCircleLineIntersectionPoint(a1,a2,currObstacle.getCoordinate(),currObstacle.getRadius());
		//if size == 1 then the segment ends in the obstacle so run a different avoidance method
		if(intersections.size() == 1) {
			avoidInside(j);
			return;
		}
		//if it is a tangent or not actually an intersection then done
		if(intersections.size() < 2) {
			return;
		}
		//calculate slope and perpendicular slope
		double slope = Line.slope(a1,a2);
		double perpSlope;
		if(slope == Double.MAX_VALUE) {
			perpSlope = 0;
		}
		if(slope == 0) {
			perpSlope = Double.MAX_VALUE;
		}
		perpSlope = -1 * (1 / slope);
		
		Coordinate b1 = new Coordinate();
		Coordinate b2 = new Coordinate();
		Coordinate c1 = new Coordinate();
		Coordinate c2 = new Coordinate();
		
		double l = currObstacle.getRadius();
		
		if(perpSlope == 0) {
			b1.setX(intersections.get(0).getX() + l);
			b1.setY(intersections.get(0).getY());
	  
			b2.setX(intersections.get(0).getX() - l);
			b2.setY(intersections.get(0).getY());
			
			
			c1.setX(intersections.get(1).getX() + l);
			c1.setY(intersections.get(1).getY());
	  
			c2.setX(intersections.get(1).getX() - l);
			c2.setY(intersections.get(1).getY());
		}
		
		// if slope is infinte 
	    else if (perpSlope == Double.MAX_VALUE) { 
	    	
	    	b1.setX(intersections.get(0).getX());
			b1.setY(intersections.get(0).getY() + l);
	  
			b2.setX(intersections.get(0).getX());
			b2.setY(intersections.get(0).getY() - l);	
		
			c1.setX(intersections.get(1).getX());
			c1.setY(intersections.get(1).getY() + l);
		  
			c2.setX(intersections.get(1).getX());
			c2.setY(intersections.get(1).getY() - l);	
	    } 
		
	    else {
	    	double dx = (l / Math.sqrt(1 + (perpSlope * perpSlope)) ); 
	        double dy = perpSlope * dx; 
	        
	        b1.setX(intersections.get(0).getX() + dx);
			b1.setY(intersections.get(0).getY() + dy);
	  
			b2.setX(intersections.get(0).getX() - dx);
			b2.setY(intersections.get(0).getY() - dy);
			
			
			c1.setX(intersections.get(1).getX() + dx);
			c1.setY(intersections.get(1).getY() + dy);
	  
			c2.setX(intersections.get(1).getX() - dx);
			c2.setY(intersections.get(1).getY() - dy);
	    }
		//pairs are then pair 1(b1, c1) and pair 2(b2, c2)
		//calculate pair distances from center
		double distanceb1 = new Line(b1,currObstacle.getCoordinate()).getLength();
		double distanceb2 = new Line(b2,currObstacle.getCoordinate()).getLength();
		double distancec1 = new Line(c1,currObstacle.getCoordinate()).getLength();
		double distancec2 = new Line(c2,currObstacle.getCoordinate()).getLength();
		
		Waypoint w1;
		Waypoint w2;
		
		if(distanceb1 + distancec1 >= distanceb2 + distancec2) {
			w1 = new Waypoint(b1,false);
			w2 = new Waypoint(c1,false);
		}
		else {
			w1 = new Waypoint(b2,false);
			w2 = new Waypoint(c2,false);
		}
		
		waypointList.getWaypointList().add(j+1,w1);
		waypointList.getWaypointList().add(j+2,w2);
		
	}	

	
}
