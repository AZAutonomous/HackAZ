package Geometry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Coordinate {
	private double x, y;
	
	public Coordinate() {
		this(0,0);
	}
	
	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Coordinate copy() {
		return new Coordinate(this.x, this.y);
	}
	
	public Coordinate add(Vector that) {
		return new Coordinate(this.x + that.getX(), this.y + that.getY());
	}
	
	public Coordinate subtract(Vector that) {
		return new Coordinate(this.x - that.getX(), this.y - that.getY());
	}
	
	public boolean longitudeSort(Coordinate that) {	
		if (this.x == that.x) { // if the longitude is the same
			return (this.y < that.y); // the one with the smaller latitude is less
		} else {
			return (this.x < that.x); // the smallest longitude is the smaller one
		}
	}
	
	public boolean latitudeSort(Coordinate that) {
		if (this.y == that.y) { // if latitude is the same
			return (this.x < that.x); // the one with the smaller longitude is less than the other
		} else {
			return (this.y < that.y); // the smallest latitude is the smaller one
		}
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getX() {
		return this.x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getY() {
		return this.y;
	}
	
	
	public static List<Coordinate> getCircleLineIntersectionPoint(Coordinate pointA,
			Coordinate pointB, Coordinate center, double radius) {
        double baX = pointB.x - pointA.x;
        double baY = pointB.y - pointA.y;
        double caX = center.x - pointA.x;
        double caY = center.y - pointA.y;

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - radius * radius;

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return Collections.emptyList();
        }
        // if disc == 0 ... dealt with later
        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;
        double abScalingFactor2 = -pBy2 - tmpSqrt;

        Coordinate p1 = new Coordinate(pointA.x - baX * abScalingFactor1, pointA.y
                - baY * abScalingFactor1);
        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            return Collections.singletonList(p1);
        }
        Coordinate p2 = new Coordinate(pointA.x - baX * abScalingFactor2, pointA.y
                - baY * abScalingFactor2);
        return Arrays.asList(p1, p2);
    }
}
