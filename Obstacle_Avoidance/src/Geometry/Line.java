package Geometry;

public class Line {
	private Coordinate a, b;
	
	public Line(Coordinate a, Coordinate b) {
		this.a = a;
		this.b = b;
	}
	
	public Line(double x1, double y1, double x2, double y2) {
		this.a = new Coordinate(x1, y1);
		this.b = new Coordinate(x2, y2);
	}
	
	public static boolean onSegment(Coordinate p, Coordinate q, Coordinate r) {
		if (q.getX() <= Math.max(p.getX(), q.getX()) && q.getX() >= Math.min(p.getX(), r.getX()) &&
				q.getY() <= Math.max(p.getY(), q.getY()) && q.getY() >= Math.min(p.getY(), r.getY())) {
			return true;
		} else {
			return false;
		}
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
	
	public boolean intersect(Coordinate p1, Coordinate p2, Coordinate q1, Coordinate q2) {
		int o1 = orientation(p1, q1, p2);
		int o2 = orientation(p1, q1, q2);
		int o3 = orientation(p2, q2, p1);
		int o4 = orientation(p2, q2, q1);
		
		if (o1 != o2 && o3 != o4) {
			return true;
		}
		
		if (o1 == 0 && onSegment(p1, p2, q1)) {
			return true;
		}
		
		if (o2 == 0 && onSegment(p1, q2, q1)) {
			return true;
		}
		
		if (o3 == 0 && onSegment(p2, p1, q2)) {
			return true;
		}
		
		if (o4 == 0 && onSegment(p2, q1, q2)) {
			return true;
		}
		
		return false;
	}
	
}
