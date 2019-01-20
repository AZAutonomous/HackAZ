package Bodies;

import java.util.ArrayList;
import java.util.List;

import Geometry.Coordinate;
import Geometry.Line;
import Geometry.Vector;

public class Plane {

	private Coordinate position;
	private double speed;
	private List<Obstacle> obstacles;
	
	public Plane(Coordinate p, double s) {
		this.position = p;
		this.speed = s;
		this.obstacles = new ArrayList<Obstacle>();
	}
	
	public Plane() {
		this.position = null;
		this.speed = 0;
	}
	
	//getters
	public Coordinate getPosition() {
		return this.position;
	}
	public double getSpeed() {
		return this.speed;
	}
	
	//setters
	public void setPosition(Coordinate p) {
		this.position = p;
	}
	public void setSpeed(double s) {
		this.speed = s;
	}

	public List<Waypoint> updatePath(List<Waypoint> currentPath) {
		Coordinate bisection = Line.findMidpoint(this.position, currentPath.get(0).getCoordinate());
		double lengthBisection = Line.distanceBetween(this.position, bisection);
		Vector towardsBisection = new Vector(this.position, bisection);
		List<Coordinate> candidateAnchors = new ArrayList<Coordinate>(5);
		candidateAnchors.add(bisection.add(towardsBisection.shiftVectorByTheta(Math.PI/2, lengthBisection)));
		candidateAnchors.add(bisection.add(towardsBisection.shiftVectorByTheta(Math.PI/2, lengthBisection / 2)));
		candidateAnchors.add(bisection);
		candidateAnchors.add(bisection.add(towardsBisection.shiftVectorByTheta(-Math.PI/2, lengthBisection / 2)));
		candidateAnchors.add(bisection.add(towardsBisection.shiftVectorByTheta(-Math.PI/2, lengthBisection)));
		
		Coordinate bestAnchor = findBestAnchor(candidateAnchors, currentPath.get(0).getCoordinate());
	}
	
	private Coordinate findBestAnchor(List<Coordinate> candidates, Coordinate nextWaypoint) {
		double minRisk = 1000;
		Coordinate bestAnchor = null;
		for (Coordinate c : candidates) {
			double risk = calculateRiskAlongPath(c, nextWaypoint);
			if (risk < minRisk) {
				minRisk = risk;
				bestAnchor = c;
			}
		}
		
		return bestAnchor;
	}
	
	private double calculateRiskAlongPath(Coordinate c, Coordinate destination) {
		double risk = 0;
		double totalLength = Line.distanceBetween(this.position, c) + Line.distanceBetween(c, destination);
		Coordinate currentPosition = this.position.copy();
		
		for (int i = 1; i < totalLength / this.speed; i++) {
			if (i < (totalLength / this.speed) / 2) {
				currentPosition = currentPosition.add(new Vector(this.position, c).shiftVectorByTheta(0, this.speed));
			} else {
				currentPosition = currentPosition.add(new Vector(c, destination).shiftVectorByTheta(0, this.speed));
			}
			for (Obstacle o : obstacles) { // for each obstacle in the list
				if (Line.distanceBetween(o.getCoordinate(), currentPosition) < o.getDirection().getMagnitude() * i) {
					if (Math.abs(o.getDirection().angleBetween(new Vector(o.getCoordinate(), currentPosition))) < Math.PI/4) {
						risk += (4*Math.pow(o.getRadius(), 2)) / ((2 * i - 1) * Math.pow(o.getDirection().getMagnitude(), 2));
					}
				}
			}
		}
		return risk;
	}
}
