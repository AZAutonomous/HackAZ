package Geometry;

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
		
		
		
		return false;
	}
	
	public static boolean intersect(Line a, Line b) {
		return intersect(a.getStart(), a.getEnd(), b.getStart(), b.getEnd());
	}
	
}
