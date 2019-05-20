package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Bodies.Obstacle;
import Bodies.Plane;
import Bodies.Waypoint;
import Bodies.WaypointList;
import Geometry.Coordinate;
import Geometry.Line;
import Geometry.Vector;

public class PlaneThread extends Thread{
	
	public final static int MILISECONDS_DELAY = 1000;
	//the amount of objective waypoint to look ahead when calc collisions
	public final static int LOOKAHEAD_AMOUNT = 2;
	//the radius for wich a "hit" can be counted in feet
	public final static double PLANE_HIT_RADIUS = 100;
	
	private Plane plane;
	private ArrayList<Obstacle> obstacleList;
	private WaypointList waypointList;
	private boolean listIsEmpty;
	public boolean dynamic;
	
	public PlaneThread(Plane p) {
		this.plane = p;
		this.listIsEmpty = true;
	}

	public PlaneThread(Plane p, ArrayList<Obstacle> ol, WaypointList w) {
		this.plane = p;
		this.obstacleList = ol;
		this.waypointList = w;
		this.listIsEmpty = true;
	}
	
	public void runOnce() {
		int it = 0;
		
		updatePlane();
		updateObstacleList();
			
		//print stuff (for debugging)
		System.out.println(it + "  plane lat:" + plane.getPosition().getY() 
							  + "  lon:" + plane.getPosition().getX());
		System.out.println(it + "  obst 1: lat:" + obstacleList.get(0).getCoordinate().getY() 
				 + " lon:" + obstacleList.get(0).getCoordinate().getX()
				 + " vector: " + obstacleList.get(0).getDirection().getX() + "-" + obstacleList.get(0).getDirection().getY()
				 + " thetas: " + obstacleList.get(0).theatasToString());
		it++;
		
		RunAlgorithm();
		
	}

	@Override
	public void run() {
		int it = 0;
		while(true) {
			updatePlane();
			updateObstacleList();
			
			//print stuff (for debugging)
			System.out.println(it + "  plane lat:" + plane.getPosition().getY() 
								  + "  lon:" + plane.getPosition().getX());
			System.out.println(it + "  obst 1: lat:" + obstacleList.get(0).getCoordinate().getY() 
					 + " lon:" + obstacleList.get(0).getCoordinate().getX()
					 + " vector: " + obstacleList.get(0).getDirection().getX() + "-" + obstacleList.get(0).getDirection().getY()
					 + " thetas: " + obstacleList.get(0).theatasToString());
			it++;
			
			RunAlgorithm();
			
			try {
				Thread.sleep(PlaneThread.MILISECONDS_DELAY);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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
	
	private void RunAlgorithm() {

		//repeat these steps until no collisions
		
		//step 1: check for collision
		int collisionsIndex = findDangerousObstacle();
		
		//step 2: if collision create anchor points
		if(collisionsIndex != -1) {
			System.out.println("collisions detected...");
		}
		collisionsIndex = findDangerousObstacle();
		while(collisionsIndex != -1) {
			
			//set current obstacle trying to avoid
			Obstacle currObstacle = obstacleList.get(collisionsIndex);
			
			//go into the first waypoint that the obstacle collides with
			for(int j = 0; j < waypointList.getWaypointList().size()-1; j++) {
				Waypoint a1 = waypointList.getWaypointList().get(j);
				Waypoint a2 = waypointList.getWaypointList().get(j+1);
				if(ObstacleCollides(a1.getCoordinate(),a2.getCoordinate(), currObstacle)) {
					
					avoidObstacle(j,a1.getCoordinate(),a2.getCoordinate(),currObstacle);
					break;
				}
			}
			
			
			collisionsIndex = findDangerousObstacle();
		}
		
		//step 3: generate prediction equations for dynamic obstacles
		
		//step 4: generate avoidance path
		
		//step 5: evaluate risk of each avoidance paths
		
		//step 6: select dodge points
		System.out.println("done.");
	}
	
	private void avoidInside(int j) {
		// TODO Auto-generated method stub
		
	}
	
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

	//removes the soonest waypoint if the planes current position is a "hit"
	//TODO: implement me
	public void updateWaypoints() {
		
	}
	
	public void updatePlane() {
		File f = new File("Plane.json");
		Scanner sc = null;
		try {
			sc = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sc.nextLine();
		sc.nextLine();
		
		Coordinate newCoordinate = new Coordinate();
		
		
		newCoordinate.setY(Integer.parseInt(
				sc.nextLine()
				.replaceAll("\"lat\":","")
				.replaceAll(",","")
				.replace(" ","")
				.replaceAll("\t","")));
		
		newCoordinate.setX(Integer.parseInt(
				sc.nextLine()
				.replaceAll("\"long\":","")
				.replaceAll(",","")
				.replace(" ","")
				.replaceAll("\t","")));
		plane.setPosition(newCoordinate);
		
	}
	
	//returns 0 if success
		//else
		public int updateObstacleList() {
			//obstacleList.clear();
			int listIndex = 0;
			
			File f = new File("Obstacles.json");
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
					//make a new obstacle
					Coordinate newCoordinate = new Coordinate();
					Obstacle newObstacle = new Obstacle();
					String tempNum = sc.nextLine()
							.replaceAll("\"lat\":","")
							.replaceAll(",","")
							.replace(" ","")
							.replaceAll("\t","");
						
					newCoordinate.setY(Integer.parseInt(tempNum));
							
					newCoordinate.setX(Integer.parseInt(
							sc.nextLine()
							.replaceAll("\"long\":","")
							.replaceAll(",","")
							.replace(" ","")
							.replaceAll("\t","")));
					
					newObstacle.setRadius(Integer.parseInt(
							sc.nextLine()
							.replaceAll("\"radius\":","")
							.replaceAll(",","")
							.replace(" ","")
							.replaceAll("\t","")));
					newObstacle.setCoordinate(newCoordinate);
					//add obstacle
					if(listIsEmpty) {
						newObstacle.setDirection(new Vector());
						obstacleList.add(newObstacle);
					}
					//update obstacle values
					else {
						Vector newVector = new Vector(
								newObstacle.getCoordinate().getX() 
								- obstacleList.get(listIndex).getCoordinate().getX(),
								newObstacle.getCoordinate().getY() 
								- obstacleList.get(listIndex).getCoordinate().getY());
						//set the new direction
						newObstacle.setDirection(newVector);
						//update the change in angle
						newObstacle.setThetas(obstacleList.get(listIndex).getThetas());
						Double newTheta = obstacleList.get(listIndex).getDirection()
						.angleBetween(newObstacle.getDirection());
						newObstacle.addTheta(newTheta);
						//update with the new obstacle
						obstacleList.set(listIndex,newObstacle);
						
					}
					
					
					listIndex++;
					
				}
			}
			
			listIsEmpty = false;
			return 0;
		}
	
	//getters
	public Plane getPlane() {
		return this.plane;
	}
	
	
	//setters
	public void setPlane(Plane p) {
		this.plane = p;
	}

}
