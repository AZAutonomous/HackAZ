package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Bodies.Plane;
import Geometry.Coordinate;

public class PlaneThread implements Runnable{
	
	private Plane plane;
	
	public PlaneThread(Plane p) {
		this.plane = p;
	}

	@Override
	public void run() {
		int it = 0;
		while(true) {
			updatePlane();
			
			System.out.println(it + "  plane lat:" + plane.getPosition().getY() 
								  + "  lon:" + plane.getPosition().getX());
			it++;
			
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
		sc.nextLine();
		
		String currLine = sc.nextLine();
		
		Coordinate newCoordinate = new Coordinate();
		
		newCoordinate.setX(Integer.parseInt(
				sc.nextLine()
				.replaceAll("\"long\":","")
				.replaceAll(",","")
				.replace(" ","")
				.replaceAll("\t","")));
		newCoordinate.setY(Integer.parseInt(
				sc.nextLine()
				.replaceAll("\"lat\":","")
				.replaceAll(",","")
				.replace(" ","")
				.replaceAll("\t","")));
		plane.setPosition(newCoordinate);
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
