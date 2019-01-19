package Bodies;

import java.util.ArrayList;
import java.util.List;

import Geometry.Coordinate;
import Geometry.Vector;

public class Obstacle extends Body {
	private Coordinate position;
	private Vector velocity;
	private int radius;
	public int turnRadius;
	
	public Obstacle(Coordinate position, Vector velocity, int radius, int turnRadius) {
		super(position, velocity);
		this.radius = radius;
		this.turnRadius = turnRadius;
		
	}
	
	//getters
	public int getRadius() {
		return this.radius;
	}
	public List<Coordinate> getDangerLines(int length) { // TODO: make better name for these (STAGE 1)
		List<Coordinate> list = new ArrayList<Coordinate>(3);
		System.out.println(this.velocity);
		if (this.velocity.getMagnitude() == 0) {
			return null;
		}
		double angle = this.velocity.getDirection();
		Coordinate left_45 = new Coordinate(this.position.getX() + length * Math.acos(angle - Math.PI/4), this.position.getY() + length * Math.asin(angle - Math.PI/4));
		Coordinate straight = new Coordinate(this.position.getX() + length * Math.acos(angle), this.position.getY() + length * Math.asin(angle));
		Coordinate right_45 = new Coordinate(this.position.getX() + length * Math.acos(angle + Math.PI/4), this.position.getY() + length * Math.asin(angle + Math.PI/4));
		
		list.add(left_45);
		list.add(straight);
		list.add(right_45);
		
		return list;
	}
	
	//setters
	public void setRadius(int r) {
		this.radius = r;
	}
	
	
}
