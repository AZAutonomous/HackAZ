package Main;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import Bodies.Obstacle;
import Geometry.Coordinate;
import Geometry.Vector;


public class ObstacleThread implements Runnable{

	boolean listIsEmpty;
	ArrayList<Obstacle> obstacleList;
	public final static int MILISECONDS_DELAY = 1000;
	
	public ObstacleThread() {
		obstacleList = new ArrayList<Obstacle>();
		listIsEmpty = true;
	}
	public ObstacleThread(ArrayList<Obstacle> list) {
		obstacleList = list;
		listIsEmpty = true;
	}
	
	@Override
	public void run() {
		int it = 0;
		while(true) {
			updateObstacleList();
			
			System.out.println(it + "  obst 1: lat:" + obstacleList.get(0).getCoordinate().getY() 
									 + " lon:" + obstacleList.get(0).getCoordinate().getX()
									 + " vector: " + obstacleList.get(0).getDirection().getMagnitude());
			
			it++;
			
			try {
				Thread.sleep(MILISECONDS_DELAY);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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

}
