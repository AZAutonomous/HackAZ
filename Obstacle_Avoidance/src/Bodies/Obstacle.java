package Bodies;

import java.util.ArrayList;
import java.util.List;

import Geometry.Coordinate;
import Geometry.Vector;

public class Obstacle {
	private Coordinate coordinate;
	private Vector direction;
	private List<Double> thetas;
	
	public Obstacle() {
		this.coordinate = new Coordinate();
		this.direction = new Vector();
		this.thetas = new ArrayList<Double>();
	}
	
	
	//getters
	public Coordinate getCoordinate() {
		return this.coordinate;
	}
	public Vector getDirection() {
		return this.direction;
	}
	
	//setters
	public void setCoordinate(Coordinate c){
		this.coordinate = c;
	}
	public void setDirection(Vector v) {
		this.direction = v;
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
	
	
	
	
}
