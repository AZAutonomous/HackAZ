#include "DetectionAlgotrythm.h"
#include "Plane.h"
#include "Waypoint.h"
#include "Obstacle.h"
#include <vector>
#include <list>
#include <math.h>

//Inputs: euclidiane coordinates, number of coordinates
double magnitude(double list[], int size) {
	double sum = 0;
	for (int i = 0; i < size; i++) {
		sum += pow(list[i], 2);
	}
	return sum;
}

/*
	Input: Plane, Obstacle
	Output: struct with angle to "positive x-axis" of the plane velocity
		minimum distance from line defined by Plane velocity to object
		projection of line defined by Plane to Obstacle onto Plane Velocity line
		pointer to Obstacle
*/
DetectionIntersect DetectionAlgorithim(Plane p, Obstacle o)
{
	DetectionIntersect intersect;
	vector<double> planeVel = p.getVelocityRT();
	vector<double> obstacleCenter = o.getCenter();
	vector<double> planeCenter = p.getCoordinates();
	//translates plane to orgin and Obstacle relative to Plane
	double translation[2] = { obstacleCenter[0] - planeCenter[0], obstacleCenter[1] - planeCenter[1] };
	//convert Obstacle to polar
	double polar[2] = { magnitude(translation,2), atan2(translation[1],translation[0]) };
	//dot projection of Obstacle line onto Plane Velocity
	intersect.pole = polar[0] * cos(polar[1] - planeVel[1]);
	//calculates orthogonal distance from plane velocity
	intersect.clearance = sqrt(pow(polar[0], 2) - pow(intersect.pole, 2));
	//store plane velocity angle
	intersect.angle = planeVel[1];

	return intersect;
}


/*
	Input: start Waypoint, end Waypoint, and 1 Obstacle
	Output: struct with angle to "positive x-axis" of the line connecting two waypoints
		minimum distance from line connecting two Waypoint to object
		projection of line from start Waypoint to Obstacle onto two Waypoint line
		pointer to Obstacle
	Method: translates and transforms the objects then
		calculates the projection then uses pythagorean
		to calculate clearance.
*/
DetectionIntersect DetectionAlgorithim(Waypoint p1, Waypoint p2, Obstacle obstacle)
{
	DetectionIntersect intersect;
	vector<double> p1Center = p1.getCoordinate();
	vector<double> obstacleCenter = obstacle.getCenter();
	vector<double> p2Center = p2.getCoordinate();

	//translate p1 to origion and apply asme translation to p2 and obstacle
	double relativePoint[2] = { p2Center[0] - p1Center[0], p2Center[1] - p2Center[1] };
	double relativeObs[2] = { obstacleCenter[0] - p1Center[0], obstacleCenter[1] - p1Center[1] };
	//transform p2 and obs into polar coordinates
	double polarPoint[2] = { magnitude(relativePoint,2), atan2(relativePoint[1],relativePoint[0]) };
	double polarObs[2] = { magnitude(relativeObs,2), atan2(relativeObs[1], relativeObs[0]) };

	//store angle from p1 to p2
	intersect.angle = polarPoint[1];
	//calculate dot projection of p1Obs onto p1p2 
	intersect.pole = polarObs[0] * cos(polarObs[1] - intersect.angle);
	//calculate orthognal distance to obs from p1p2 line
	intersect.clearance = sqrt(pow(polarObs[0], 2) - pow(intersect.pole, 2));
	//point to obstacle 
	intersect.obstacle = &obstacle;
	
	return intersect;
}

vector<DetectionIntersect> DetectionAlgorithim(Waypoint point1, Waypoint point2, vector<Obstacle> obstacles)
{
	vector<DetectionIntersect> list;
	for (vector<Obstacle>::iterator obs = obstacles.begin(); obs != obstacles.end(); obs++)
	{
		list.push_back(DetectionAlgorithim(point1, point2, *obs));
	}
	return list;
}


//TODO: disregard
DetectionCircle DetectionAlgorithimChord(Waypoint point1, Waypoint point2, Obstacle obstacles)
{
	DetectionCircle circle;
	DetectionIntersect intersect = DetectionAlgorithim(point1, point2, obstacles);
	double angle = M_PI_2 + intersect.angle;
	circle.angle = angle + acos(intersect.pole / intersect.obstacle->getRadius());
	circle.obstacle = intersect.obstacle;
	return circle;
}

vector<DetectionCircle> DetectionAlgorithimChord(Waypoint point1, Waypoint point2, vector<Obstacle> obstacles)
{
	vector<DetectionCircle> list;


	return list;
}