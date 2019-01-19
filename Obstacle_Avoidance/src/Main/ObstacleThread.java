package Main;

import java.io.*;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import Bodies.Obstacle;

import javax.imageio.IIOException;

public class ObstacleThread implements Runnable{

	Obstacle obstacle;
	
	//constructor
	public ObstacleThread(Obstacle o) {
		this.obstacle = o;
	}
	
	@Override
	public void run() {
		
		
	}
	//returns 0 if success
	//else
	public int updateObstacleList(ArrayList<Obstacle> obstacleList) {

		BufferedReader br = null;
		FileReader fr = null;
		File file = new File("obstacle.txt");

		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
		}
		catch (FileNotFoundException f){
			System.out.println("no");
		}

		ArrayList<Obstacle> newlist = new ArrayList<Obstacle>();

		Obstacle temp = new Obstacle;
		try {
			String x = " ";
			while ((x = br.readLine()) != null) {

			}
		}
		catch (IOException e){
			System.out.println("noo");
		}



		obstacleList = newlist;
		
		return 0;
	}

}
