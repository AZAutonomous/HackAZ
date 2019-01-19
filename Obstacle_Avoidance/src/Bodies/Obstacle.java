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
		ArrayList<Line> dangers = new ArrayList<Line>(3);
		
		double angle = this.velocity.getDirection();
		double left = angle + Math.PI/4;
		double right = angle - Math.PI/4;
		
//		double newX = Math.cos(left) * length;
//		double newY = Math.sin(left) * length;
		Vector newV = this.velocity.shiftVectorByTheta(Math.PI/4);
		dangers.add(new Line(this.position, new Coordinate(newV.getX() + this.position.getX(), newV.getY() + this.position.getY())));
		
		System.out.println(newV.getX() + ", " + newV.getY());
//		newX = Math.cos(angle) * length + this.position.getX();
//		newY = Math.sin(angle) * length + this.position.getY();
		newV = this.velocity.shiftVectorByTheta(0);
		dangers.add(new Line(this.position, new Coordinate(newV.getX() + this.position.getX(), newV.getY() + this.position.getY())));
		
		System.out.println(newV.getX() + ", " + newV.getY());
//		newX = Math.cos(right) * length + this.position.getX();
//		newY = Math.sin(right) * length + this.position.getY();
		newV = this.velocity.shiftVectorByTheta(-1 * Math.PI/4);
		dangers.add(new Line(this.position, new Coordinate(newV.getX() + this.position.getX(), newV.getY() + this.position.getY())));
		
		System.out.println(newV.getX() + ", " + newV.getY());
		return dangers;
	}
	

	
}
