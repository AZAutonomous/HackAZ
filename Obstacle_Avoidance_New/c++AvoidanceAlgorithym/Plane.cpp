#include "Plane.h"

Plane::Plane() {
	double xCoordinate = 0;
	double yCoordinate = 0;
	double speed = 0;
	double velocityTheta = 0;
	double velocityXDir = 0;
	double velocityYDir = 0;
	double width = 0;
	double length = 0;
}

Plane::Plane(double length, double width) {
	double xCoordinate = 0;
	double yCoordinate = 0;
	this->length = length;
	this->width = width;
	double speed = 0;
	double velocityTheta = 0;
	double velocityXDir = 0;
	double velocityYDir = 0;
}

Plane::Plane(double xCoord, double yCoord, double length, double width){
	xCoordinate = xCoord;
	yCoordinate = yCoord;
	this->length = length;
	this->width = width;
	double speed = 0;
	double velocityTheta = 0;
	double velocityXDir = 0;
	double velocityYDir = 0;
}

vector<double> Plane::advance() {
	xCoordinate += velocityXDir;
	yCoordinate += velocityYDir;
	double list[2] = {xCoordinate, yCoordinate};
	vector<double> out(list,list+2);
	return out;
}