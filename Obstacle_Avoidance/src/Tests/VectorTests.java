package Tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Geometry.Coordinate;
import Geometry.Vector;

public class VectorTests {
	
	@Test
	public void ConstructorTests() {
		Vector a = new Vector();
		assertEquals(a.getX(), 0, 0.001);
		assertEquals(a.getY(), 0, 0.001);
		assertEquals(a.getMagnitude(), 0, 0.001);
		assertEquals(a.getDirection(), 0, 0.001);
		
		Coordinate c = new Coordinate(0,0);
		Coordinate d = new Coordinate(3,2);
		Vector b = new Vector(c,d);
		assertEquals(b.getX(), 3, 0.001);
		assertEquals(b.getY(), 2, 0.001);
		
		Vector e = b.copy();
		assertEquals(e.getX(), 3, 0.001);
		assertEquals(e.getY(), 2, 0.001);
		
		Vector f = a.add(b);
		assertEquals(f.getX(), 3, 0.001);
		assertEquals(f.getY(), 2, 0.001);
		
		Vector g = a.subtract(b);
		assertEquals(g.getX(), -3, 0.001);
		assertEquals(g.getY(), -2, 0.001);
		assertEquals(g.getMagnitude(), 3.6055, 0.0001);
		assertEquals(g.getDirection(), 3.7296, 0.0001);
		
		g.setX(0);
		g.setY(-5);
		assertEquals(g.getX(), 0, 0.001);
		assertEquals(g.getY(), -5, 0.001);
		assertEquals(g.getMagnitude(), 5, 0.001);
		assertEquals(g.getDirection(), -1*Math.PI / 2, 0.001);
		
		Vector h = b.scale(2);
		assertEquals(h.getX(), 6, 0.001);
		assertEquals(h.getY(), 4, 0.001);
		assertEquals(h.getMagnitude(), 7.2111, 0.0001);
		assertEquals(h.getDirection(), 0.588, 0.001);
		
		Vector i = g.scale(-1);
		assertEquals(i.getX(), 0, 0.001);
		assertEquals(i.getY(), 5, 0.001);
		assertEquals(i.getMagnitude(), 5, 0.001);
		assertEquals(i.getDirection(), Math.PI/2, 0.001);
		
		assertEquals(b.dotProduct(h), 26, 0.001);
		assertEquals(b.determinant(h), 0, 0.001);
		assertEquals(b.angleBetween(h), 0, 0.001);
		assertEquals(h.angleBetween(b), 0, 0.001);
		
		Vector j = new Vector(1,0);
		assertEquals(j.dotProduct(b), 3, 0.001);
		assertEquals(j.determinant(b), 2, 0.001);
		assertEquals(j.angleBetween(b), 0.588, 0.001);
		assertEquals(b.angleBetween(j), -0.588, 0.001);
		
		Vector k = new Vector(2,-4);
		assertEquals(k.dotProduct(b), -2, 0.001);
		assertEquals(k.determinant(b), 16, 0.001);
		assertEquals(k.angleBetween(b), 1.695, 0.001);
		
		Vector l = new Vector(1,2);
		Vector m = new Vector(2,1);
		assertEquals(l.dotProduct(m), 4, 0.001);
		assertEquals(l.determinant(m), -3, 0.001);
		assertEquals(l.angleBetween(m), -0.6435, 0.0001);
		
		Vector n = new Vector(1,0);
		Vector o = new Vector(0,1);
		assertEquals(n.dotProduct(o), 0, 0.001);
		assertEquals(n.determinant(o), 1, 0.001);
		assertEquals(n.angleBetween(o), Math.PI/2, 0.001);
		
		Vector p = new Vector(-1,1);
		assertEquals(p.angleBetween(o), -Math.PI/4, 0.001);
	}
}
