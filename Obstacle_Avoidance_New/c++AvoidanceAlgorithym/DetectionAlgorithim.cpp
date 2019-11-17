#include "DetectionAlgotrythm.h"
#include "Plane.h"
#include "Waypoint.h"
#include "Obstacle.h"
#include <vector>
#include <list>
#include <math.h>

double magnitude(double list[], int size) {
	double sum = 0;
	for (int i = 0; i < size; i++) {
		sum += pow(list[i], 2);
	}
	return sum;
}

DetectionIntersect DetectionAlgorithim(Obstacle o, Plane p)
{
	DetectionIntersect intersect;
	vector<double> planeVel = p.getVelocityRT();
	vector<double> obstacleCenter = o.getCenter();
	vector<double> planeCenter = p.getCoordinates();
	double translation[2] = { obstacleCenter[0] - planeCenter[0], obstacleCenter[1] - planeCenter[1] };
	double polar[2] = { magnitude(translation,2), atan2(translation[1],translation[0]) };
	intersect.pole = polar[0] * cos(polar[1] - planeVel[1]);
	intersect.clearance = sqrt(pow(polar[0], 2) - pow(intersect.pole, 2));
	intersect.angle = planeVel[1];
	return intersect;
}

DetectionIntersect DetectionAlgorithim(Waypoint p1, Waypoint p2, Obstacle obstacle)
{
	DetectionIntersect intersect;
	vector<double> p1Center = p1.getCoordinate();
	vector<double> obstacleCenter = obstacle.getCenter();
	vector<double> p2Center = p2.getCoordinate();
	double relativePoint[2] = { p2Center[0] - p1Center[0], p2Center[1] - p2Center[1] };
	double relativeObs[2] = { obstacleCenter[0] - p1Center[0], obstacleCenter[1] - p1Center[1] };
	double polarPoint[2] = { magnitude(relativePoint,2), atan2(relativePoint[1],relativePoint[0]) };
	double polarObs[2] = { magnitude(relativeObs,2), atan2(relativeObs[1], relativeObs[0]) };
	intersect.angle = polarPoint[1];
	intersect.pole = polarObs[0] * cos(polarObs[1] - intersect.angle);
	intersect.clearance = sqrt(pow(polarObs[0], 2) - pow(intersect.pole, 2));
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


//TODO: account for cases 
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