package Geometry;

public class Coordinate {
	private double x, y, z;
	
	public Coordinate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Coordinate add(Vector that) {
		return new Coordinate(this.x + that.x, this.y + that.y, this.z + that.z);
	}
	
	public Coordinate subtract(Vector that) {
		return new Coordinate(this.x - that.x, this.y - that.y, this.z - that.z);
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
}
