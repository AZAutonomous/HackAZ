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
	
	public List<Line> getDangerLines(int length) {
		ArrayList<Line> dangers = new ArrayList<Line>(3);
		
		double angle = this.velocity.getDirection();
		double left = angle + Math.PI/4;
		double right = angle - Math.PI/4;
		
		double newX = Math.cos(left) * length + this.position.getX();
		double newY = Math.sin(left) * length + this.position.getY();
		dangers.add(new Line(this.position, new Coordinate(newX, newY)));
		
		newX = Math.cos(angle) * length + this.position.getX();
		newY = Math.sin(angle) * length + this.position.getY();
		dangers.add(new Line(this.position, new Coordinate(newX, newY)));
		
		newX = Math.cos(right) * length + this.position.getX();
		newY = Math.sin(right) * length + this.position.getY();
		dangers.add(new Line(this.position, new Coordinate(newX, newY)));
		
		return dangers;
	}

	public static void obstaclesToFile() throws IOException{
        int x = 1;
        int lat, lon, radius;
        FileWriter writer = null;
        File file = new File("obstacle.txt");

        lat = 1;
        lon = 1;
        radius = 1;

        file.createNewFile();

        writer = new FileWriter(file);

        writer.write("{\n\t\"Obstacles\": [\n");
        for (int i = 0; i < x; i++) {
            writer.write("\t\t{\n");
            writer.write(String.format("\t\t\t\"lat\": %d\n", lat));
            writer.write(String.format("\t\t\t\"lon\": %d\n", lon));
            writer.write(String.format("\t\t\t\"radius\": %d\n", radius));
            writer.write("\t\t}");

            if (x > 1 && i < x) {
                writer.write(",\n");
            }
        }
        writer.write("\n\t]\n}");

        writer.close();
    }
	
	
}
