#include "Waypoint.h"

Waypoint::Waypoint(double xCoor, double yCoor, double zCoor, double tol) {
	xCoordinate = xCoor;
	yCoordinate = yCoor;
	zCoordinate = zCoor;
	tolerance = tol;
	line = false;
}

Waypoint::Waypoint(double xCoor, double yCoor, double tol) {
	xCoordinate = xCoor;
	yCoordinate = yCoor;
	zCoordinate = xCoor * xCoor + yCoor * yCoor;
	tolerance = tol;
	line = true;
}

Waypoint::Waypoint(double xCoor, double yCoor) {
	xCoordinate = xCoor;
	yCoordinate = yCoor;
	zCoordinate = xCoor * xCoor + yCoor * yCoor;
	tolerance = 0;
	line = true;
}