package Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Bodies.Obstacle;
import Geometry.Coordinate;
import Geometry.Vector;

public class Main {
	public static void main(String args[]) {
		System.out.println("test");
		Coordinate c = new Coordinate(1,2);
		Vector v = new Vector(1,2);
		Obstacle o = new Obstacle();
		o.setCoordinate(c);
		o.setDirection(v);
		o.setRadius(5);
		o.turnRadius = 21000;
		
		FileWriter file = null;
		try {
			file = new FileWriter("test.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
