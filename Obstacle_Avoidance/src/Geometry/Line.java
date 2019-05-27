package Geometry;

import java.math.BigDecimal;

public class Line {
	private Coordinate a, b;
	private double length;
	
	public Line(Coordinate a, Coordinate b) {
		this.a = a;
		this.b = b;
		this.calculateLength();
	}
	
	public Line(double x1, double y1, double x2, double y2) {
		this.a = new Coordinate(x1, y1);
		this.b = new Coordinate(x2, y2);
	}
	
	private void calculateLength() {
		this.length = Math.sqrt(Math.pow(this.a.getX() - this.b.getX(), 2) + Math.pow(this.a.getY() - this.b.getY(), 2));
	}
	
	public double getLength() {
		return this.length;
	}
	
	public static double distanceBetween(Coordinate a, Coordinate b) {
		return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
	}
	public static BigDecimal distanceBetweenBD(Coordinate a, Coordinate b) {
		
		BigDecimal ax = new BigDecimal(a.getX());
		BigDecimal bx = new BigDecimal(b.getX());
		BigDecimal ay = new BigDecimal(a.getY());
		BigDecimal by = new BigDecimal(b.getY());
		
		if(ax.equals(bx) && ay.equals(by)) {
			return new BigDecimal(0);
		}
		
		BigDecimal retval = sqrt2((ax.subtract(bx)).pow(2).add(ay.subtract(by).pow(2)),2);
		BigDecimal check = new BigDecimal(Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2)));
		
		
		return retval;
	}
	
	public static BigDecimal sqrt2(BigDecimal A, final int SCALE) {
	    BigDecimal x0 = new BigDecimal("0");
	    BigDecimal x1 = new BigDecimal(Math.sqrt(A.doubleValue()));
	    while (!x0.equals(x1)) {
	        x0 = x1;
	        x1 = A.divide(x0, SCALE, BigDecimal.ROUND_HALF_UP);
	        x1 = x1.add(x0);
	        x1 = x1.divide(BigDecimal.valueOf(2), SCALE, BigDecimal.ROUND_HALF_UP);

	    }
	    return x1;
	}
	
	
	public static double slope(Coordinate a, Coordinate b) {
		double retval = 0;
		//then max slope
		if(a.getY() == b.getY()) {
			return Double.MAX_VALUE;
		}
		retval = (b.getY() - a.getY()) / (b.getX() - a.getX());
		return retval;
	}
	
	public static boolean onSegment(Coordinate a, Coordinate b, Coordinate c) { // return true if b is on segment ac
		if (distanceBetween(a,c) == distanceBetween(a,b) + distanceBetween(b,c)) {
			return true;
		} else {
			return false;
		}
	}
	
	public Coordinate getStart() {
		return this.a;
	}
	
	public Coordinate getEnd() {
		return this.b;
	}
	
	public static int orientation(Coordinate p, Coordinate q, Coordinate r) {
		double val = (q.getY() - p.getY()) * (r.getX() - q.getX()) -
				     (q.getX() - p.getX()) * (r.getY() - q.getY());
		
		if (val == 0) { 
			return 0; // colinear
		} else if (val > 0) {
			return 1;
		} else {
			return 2;
		}
	}
	
	public static boolean intersect(Coordinate p1, Coordinate p2, Coordinate q1, Coordinate q2) {
		int o1 = orientation(p1, q1, p2);
		int o2 = orientation(p1, q1, q2);
		int o3 = orientation(p2, q2, p1);
		int o4 = orientation(p2, q2, q1);
		
		if (o1 != o2 && o3 != o4) {
			return true;
		} else if (o1 == 0 && onSegment(p1, p2, q1)) {
			return true;
		} else if (o2 == 0 && onSegment(p1, q2, q1)) {
			return true;
		} else if (o3 == 0 && onSegment(p2, p1, q2)) {
			return true;
		} else if (o4 == 0 && onSegment(p2, q1, q2)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean intersect(Line a, Line b) {
		return intersect(a.getStart(), b.getStart(), a.getEnd(), b.getEnd());
	}
	
	public static Coordinate findIntersection(Line a, Line b) {
		return findIntersection(a.getStart(), b.getStart(), a.getEnd(), b.getEnd());
	}
	
	public static Coordinate findIntersection(Coordinate p0, Coordinate q0, Coordinate p1, Coordinate q1) {
		Coordinate s1 = new Coordinate(p1.getX() - p0.getX(), p1.getY() - p0.getY());
		Coordinate s2 = new Coordinate(q1.getX() - q0.getX(), q1.getY() - q0.getY());
		Coordinate intersection = null;
		
		double s, t;
		s = (-1 * s1.getY() * (p0.getX() - q0.getX()) + s1.getX() * (p0.getY() - q0.getY())) / (-1 * s2.getX() * s1.getY() + s1.getX() * s2.getY());  
		t = (s2.getX() * (p0.getY() - q0.getY()) - s2.getY() * (p0.getX() - q0.getX())) / (-1 * s2.getX() * s1.getY() + s1.getX() * s2.getY());
		
		if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
			intersection = new Coordinate();
			intersection.setX(p0.getX() + (t * s1.getX()));
			intersection.setY(p0.getY() + (t * s1.getY()));
		}
		
		return intersection;
	}
	
	public static Coordinate findMidpoint(Coordinate a, Coordinate b) {
		Coordinate midpoint = new Coordinate();
		midpoint.setX((a.getX() + b.getX()) / 2);
		midpoint.setY((a.getY() + b.getY()) / 2);
		
		return midpoint;
	}
}
