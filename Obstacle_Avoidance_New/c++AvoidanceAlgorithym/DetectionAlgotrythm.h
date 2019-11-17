#pragma once
#ifndef DetectionAlgorithim_H
#define DetectionAlgorithim_H

#include "Plane.h"
#include "Obstacle.h"
#include "Waypoint.h"
#include <vector>
#include <list>
#include <math.h>
#define _USE_MATH_DEFINES

struct DetectionIntersect {
	double clearance;
	double pole;
	double angle;
	Obstacle* obstacle;
};

struct DetectionCircle {
	double angle;
	Obstacle* obstacle;
};

DetectionIntersect DetectionAlgorithim(Obstacle o, Plane p);
DetectionIntersect DetectionAlgorithim(Waypoint p1, Waypoint p2, Obstacle obstacle);
DetectionCircle DetectionAlgorithimChord(Waypoint point1, Waypoint point2, Obstacle obstacles);
vector<DetectionCircle> DetectionAlgorithimChord(Waypoint point1, Waypoint point2, vector<Obstacle> obstacles);




#endif // !DetectionAlgorithim
