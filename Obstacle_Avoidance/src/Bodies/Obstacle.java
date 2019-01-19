package Bodies;

import java.util.ArrayList;
import java.util.List;

import Geometry.Coordinate;
import Geometry.Line;
import Geometry.Vector;

public class Obstacle {
	private Coordinate position;
	private Vector velocity;
	private List<Double> thetas;
	private double radius;
	
	public Obstacle(Coordinate position, Vector velocity) {
		this.position = position;
		this.velocity = velocity;
		this.thetas = new ArrayList<Double>();
	}
	
	public Obstacle() {
		this(new Coordinate(), new Vector());
	}
	
	
	//getters
	public Coordinate getCoordinate() {
		return this.position;
	}
	public Vector getDirection() {
		return this.velocity;
	}
	public double getRadius() {
		return this.radius;
	}
	
	//setters
	public void setCoordinate(Coordinate c){
		this.position = c;
	}
	public void setDirection(Vector v) {
		this.velocity = v;
	}
	public void setRadius(double r) {
		this.radius = r;
	}
	
	public void addTheta(double newTheta) {
		this.thetas.add(newTheta);
	}
	
	public double getAverageTheta() {
		double sumThetas = 0.0;
		for (int i = 0; i < this.thetas.size(); i++) {
			sumThetas += thetas.get(i);
		}
		return sumThetas / this.thetas.size();
	}
	
	public List<Line> getDangerLines() {
		return this.getDangerLines(this.velocity.getMagnitude());
	}
	
	public List<Line> getDangerLines(double length) {
		ArrayList<Line> dangers = new ArrayList<Line>(3);
		
		Vector newV = this.velocity.shiftVectorByTheta(Math.PI/4, length);
		dangers.add(new Line(this.position, new Coordinate(newV.getX() + this.position.getX(), newV.getY() + this.position.getY())));

		newV = this.velocity.shiftVectorByTheta(0, length);
		dangers.add(new Line(this.position, new Coordinate(newV.getX() + this.position.getX(), newV.getY() + this.position.getY())));
		
		newV = this.velocity.shiftVectorByTheta(-1 * Math.PI/4, length);
		dangers.add(new Line(this.position, new Coordinate(newV.getX() + this.position.getX(), newV.getY() + this.position.getY())));
		
		return dangers;
	}
	

	
}
