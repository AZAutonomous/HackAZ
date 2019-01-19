package Tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import Bodies.Obstacle;
import Geometry.Coordinate;
import Geometry.Line;
import Geometry.Vector;

public class ObstacleTests {
	
	@Test
	public void dangerLinesTest1() {
		Coordinate pos = new Coordinate(0,0);
		Vector vel = new Vector(1,1);
		Obstacle obs = new Obstacle(pos, vel);
		
		assertEquals(vel.getDirection(), Math.PI/4, 0.001);
		
		List<Line> list = obs.getDangerLines();
		
		assertEquals(list.get(0).getEnd().getX(), 0, 0.001);
		assertEquals(list.get(0).getEnd().getY(), Math.sqrt(2), 0.001);
		assertEquals(list.get(1).getEnd().getX(), 1, 0.001);
		assertEquals(list.get(1).getEnd().getY(), 1, 0.001);
		assertEquals(list.get(2).getEnd().getX(), Math.sqrt(2), 0.001);
		assertEquals(list.get(2).getEnd().getY(), 0, 0.001);
	}
	
	@Test
	public void dangerLinesTest2() {
		Coordinate pos = new Coordinate(1,1);
		Vector vel = new Vector(-1,0);
		Obstacle obs = new Obstacle(pos, vel);
		
		assertEquals(vel.getDirection(), Math.PI, 0.001);
		
		List<Line> list = obs.getDangerLines();
		
		assertEquals(list.get(0).getEnd().getX(), 0.293, 0.001);
		assertEquals(list.get(0).getEnd().getY(), 0.293, 0.001);
		assertEquals(list.get(1).getEnd().getX(), 0, 0.001);
		assertEquals(list.get(1).getEnd().getY(), 1, 0.001);
		assertEquals(list.get(2).getEnd().getX(), 0.293, 0.001);
		assertEquals(list.get(2).getEnd().getY(), 1.707, 0.001);
	}
}
