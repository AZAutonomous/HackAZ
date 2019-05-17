package Bodies;

import java.util.ArrayList;
import java.util.List;

import Geometry.Coordinate;
import Geometry.Line;
import Geometry.Vector;

public class Obstacle {
	
	//this represents the maximum number of previous thetas a single obstacle can hold
	private static int MaxThetaBufferSize = 4;
	
	private Coordinate position;
	private Vector velocity;
	//a "theta" is the offsetted angle between one obstacles velocity vector angle and the previous vector angle of that same obstacle
	//the theta in the 0 index represnets the most recent, the 1 possition is the next most recent and so on
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
	public List<Double> getThetas() {
		return this.thetas;
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
	public void setThetas(List<Double> t) {
		this.thetas = t;
	}
	
	public void addTheta(double newTheta) {
		if(this.thetas.size() >= MaxThetaBufferSize) {
			List<Double> temp = new ArrayList<Double>();
			temp.add(newTheta);
			for(int i = 0; i < MaxThetaBufferSize - 1; i++) {
				temp.add(thetas.get(i));
			}
			this.thetas = temp;
		}
		else {
			List<Double> temp = new ArrayList<Double>();
			temp.add(newTheta);
			for(Double t : this.thetas) {
				temp.add(t);
			}
			this.thetas = temp;
		}
	}
	
	public double getAverageTheta() {
		double sumThetas = 0.0;
		for (int i = 0; i < this.thetas.size(); i++) {
			sumThetas += thetas.get(i);
		}
		return sumThetas / this.thetas.size();
	}
	
	public String theatasToString() {
		String retval = "";
		for(Double t : this.thetas) {
			retval = retval + " , " + t;
		}
		return retval;
	}
	
	public List<Line> getDangerLines() {
		return this.getDangerLines(this.velocity.getMagnitude());
	}
	
	public List<Line> getDangerLines(double length) {
		ArrayList<Line> dangers = new ArrayList<Line>(3);
		
		//here we do a double shift by the average turning theta and then 45 0 and -45 degrees
		Vector newV = this.velocity.shiftVectorByTheta(this.getAverageTheta()).shiftVectorByTheta(Math.PI/4, length);
		dangers.add(new Line(this.position, new Coordinate(newV.getX() + this.position.getX(), newV.getY() + this.position.getY())));

		newV = this.velocity.shiftVectorByTheta(this.getAverageTheta()).shiftVectorByTheta(0, length);
		dangers.add(new Line(this.position, new Coordinate(newV.getX() + this.position.getX(), newV.getY() + this.position.getY())));
		
		newV = this.velocity.shiftVectorByTheta(this.getAverageTheta()).shiftVectorByTheta(-1 * Math.PI/4, length);
		dangers.add(new Line(this.position, new Coordinate(newV.getX() + this.position.getX(), newV.getY() + this.position.getY())));
		
		return dangers;
	}
	

	
}
