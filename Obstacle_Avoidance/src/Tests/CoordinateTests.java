package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Geometry.Coordinate;

public class CoordinateTests {
	@Test
	public void constructorTests() {
		Coordinate a = new Coordinate(0,0);
		assertEquals(a.getX(), 0, 0.001);
		assertEquals(a.getY(), 0, 0.001);
		
		Coordinate b = new Coordinate(5,5);
		
		assertTrue(a.longitudeSort(b));
		assertTrue(a.latitudeSort(b));
		
		Coordinate c = new Coordinate(5,0);
		Coordinate d = new Coordinate(0,5);
		
		assertTrue(c.longitudeSort(a));
		assertTrue(d.latitudeSort(b));
	}
}
