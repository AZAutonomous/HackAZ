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
	private boolean StaticCollides(Line l, Obstacle o1) {
		// Finding the distance of line from center. 
		double a = l.getStart().getX() - l.getEnd().getX();
		double b = l.getStart().getY() - l.getEnd().getY();
		double x = Math.sqrt(a*a + b*b);
       
		return (Math.abs((o1.getCoordinate().getY() - l.getStart().getY()) * (l.getEnd().getX() - l.getStart().getX()) - 
		           (o1.getCoordinate().getX() -  l.getStart().getX()) * (l.getEnd().getY() - l.getStart().getY())) / x <= o1.getRadius());
		
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
		coors.add(plane.getPosition());
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
			//get list of Coordinats starting at the planes current location
			List<Coordinate> coors = getXNextObjectiveCoordinates(LOOKAHEAD_AMOUNT);
			
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
			System.out.println("collision");
		}
		
		//step 3: generate prediction equations for dynamic obstacles
		
		//step 4: generate avoidance path
		
		//step 5: evaluate risk of each avoidance paths
		
		//step 6: select dodge points
		
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
		
		String currLine = sc.nextLine();
		
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
						if(newObstacle.getCoordinate().getX() == 150) {
							int a = 0;
							a = 1;
						}
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
