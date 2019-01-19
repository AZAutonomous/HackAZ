package Bodies;

import Geometry.Coordinate;
import Geometry.Vector;

public class Body {
	protected Coordinate position;
	protected Vector velocity;
	
	public Body(Coordinate position, Vector velocity) {
		this.position = position.copy();
		this.velocity = velocity.copy();
	}
	
	//getters
	public Coordinate getPosition() {
		return this.position;
	}
	public Vector getVelocity() {
		return this.velocity;
	}
	
	//setters
	public void setPosition(Coordinate p){
		this.position = p;
	}
	public void setVelocity(Vector v) {
		this.velocity = v;
	}
}
