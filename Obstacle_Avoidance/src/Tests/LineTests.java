package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Geometry.Coordinate;
import Geometry.Line;

public class LineTests {
	
	@Test
	public void endpointsTest() {
		Coordinate a = new Coordinate(0,0);
		Coordinate b = new Coordinate(3,4);
		Line l1 = new Line(a, b);
		
		assertEquals(l1.getLength(), 5, 0.001);
		
		Coordinate c = new Coordinate(6,8);
		Line l2 = new Line(a, c);
		assertTrue(Line.onSegment(a, b, c));
		assertFalse(Line.onSegment(a, c, b));
		
		assertEquals(l1.getStart().getX(), 0, 0.001);
		assertEquals(l1.getStart().getY(), 0, 0.001);
		assertEquals(l1.getEnd().getX(), 3, 0.001);
		assertEquals(l1.getEnd().getY(), 4, 0.001);
	}
	
	@Test
	public void orientationTests() {
		Coordinate a = new Coordinate(0,0);
		Coordinate b = new Coordinate(1,1);
		Coordinate c = new Coordinate(1,0);
		Coordinate d = new Coordinate(0,1);
		
		Line l1 = new Line(a,b);
		Line l2 = new Line(c,d);
		
		assertTrue(Line.intersect(l1, l2));
		assertFalse(Line.intersect(a, c, d, b));
	}
	
	@Test
	public void findIntersectionTests() {
		Coordinate a = new Coordinate(0,0);
		Coordinate b = new Coordinate(1,1);
		Coordinate c = new Coordinate(1,0);
		Coordinate d = new Coordinate(0,1);
		
		Line l1 = new Line(a,b);
		Line l2 = new Line(c,d);
		
		Coordinate intersect = Line.findIntersection(l1, l2);
		
		assertTrue(intersect != null);
		assertEquals(intersect.getX(), 0.5, 0.001);
		assertEquals(intersect.getY(), 0.5, 0.001);
		
		l1 = new Line(a,c);
		l2 = new Line(b,d);
		intersect = Line.findIntersection(l1,l2);
		assertTrue(intersect == null);
		
	}
}
