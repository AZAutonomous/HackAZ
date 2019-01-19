package Tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import Bodies.Obstacle;
import Geometry.Coordinate;
import Geometry.Vector;

public class ObstacleTests {
	
	@Test
	public void dangerLinesTest() {
		Coordinate pos = new Coordinate(0,0);
		Vector vel = new Vector(1,1);
		Obstacle obs = new Obstacle(pos, vel, 0, 0);
		
		List<Coordinate> list = obs.getDangerLines(5);
		
		System.out.println(list);
		assertEquals(list.get(0).getX(), 0, 0.001);
		assertEquals(list.get(0).getY(), 5, 0.001);
		assertEquals(list.get(1).getX(), 2.236, 0.001);
		assertEquals(list.get(1).getY(), 2.236, 0.001);
		assertEquals(list.get(2).getX(), 5, 0.001);
		assertEquals(list.get(2).getY(), 0, 0.001);
		
	}
}
