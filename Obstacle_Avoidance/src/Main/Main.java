package Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Bodies.Obstacle;
import Bodies.Plane;
import Geometry.Coordinate;
import Geometry.Vector;

public class Main {
	public static void main(String args[]) {
		
		
		ArrayList<Obstacle> obstacleList = new ArrayList<Obstacle>();
		Plane plane = new Plane();
		
		ObstacleThread obstacleThread = new ObstacleThread(obstacleList);
		//obstacleThread.updateObstacleList();
		obstacleThread.run();
		
		//test-------------------------------------------------
		/*
		System.out.println("test");
		Coordinate c = new Coordinate(1,2);
		Vector v = new Vector(1,2);
		Obstacle o = new Obstacle();
		o.setCoordinate(c);
		o.setDirection(v);
		o.setRadius(5);
		o.turnRadius = 21000;
		Obstacle o2 = new Obstacle();
		o.setCoordinate(c);
		o.setDirection(v);
		o.setRadius(4);
		ArrayList<Obstacle> list1 = new ArrayList<Obstacle>();
		list1.add(o);
		list1.add(o2);
		ObstacleThread n = new ObstacleThread();
		n.obstacleList = list1;
		//n.delEndOfList();
		//end of test*/
		
	}
}
