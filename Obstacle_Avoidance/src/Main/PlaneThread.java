package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Bodies.Obstacle;
import Bodies.Plane;
import Bodies.WaypointList;
import Geometry.Coordinate;
import Geometry.Vector;

public class PlaneThread extends Thread{
	
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
					 + " vector: " + obstacleList.get(0).getDirection().getMagnitude());
			it++;
			
			//TODO: add method to check for collisions here
			
			try {
				Thread.sleep(ObstacleThread.MILISECONDS_DELAY);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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
						newObstacle.addTheta(obstacleList.get(listIndex).getDirection()
								.angleBetween(newObstacle.getDirection()));
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
