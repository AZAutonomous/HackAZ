#include "Obstacle.h"

Obstacle::Obstacle(double xCoor, double yCoor, double radius, double height) {
	xCoordinate = xCoor;
	yCoordinate = yCoor;
	this->radius = radius;
	this->height = height;
	infinite = false;
}

Obstacle::Obstacle(double xCoor, double yCoor, double radius) {
	xCoordinate = xCoor;
	yCoordinate = yCoor;
	this->radius = radius;
	infinite = true;
}