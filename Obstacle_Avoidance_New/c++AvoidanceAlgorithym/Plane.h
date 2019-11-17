#pragma once
#ifndef Plane_H
#define Plane_H
#include <math.h>
#include <vector>
using namespace std;

class Plane {
private:
	double xCoordinate;
	double yCoordinate;
	double zCoordinate = 0;
	double speed;
	double velocityTheta; //radians
	double velocityXDir;
	double velocityYDir;
	double width;
	double length;
public:
	void setCoordinate(double xCoord, double yCoord)
	{
		xCoordinate = xCoord;
		yCoordinate = yCoord;
	};
	void setVelocityRT(double speed, double theta) //theat in radians
	{
		this->speed = speed;
		velocityTheta = theta;
		velocityXDir = speed*cos(theta);
		velocityYDir = speed*sin(theta);
	};
	void setVelocityXY(double xVel, double yVel)
	{
		velocityXDir = xVel;
		velocityYDir = yVel;
		speed = sqrt(xVel*xVel + yVel*yVel);
		velocityTheta = atan2(yVel, xVel);
	}
	vector<double> advance();
	vector<double> getVelocityXY() {
		double arr[] = { velocityXDir, velocityYDir };
		vector<double> out(arr, arr + sizeof(arr)/sizeof(double));
		return out;
	}
	vector<double> getVelocityRT() {
		double arr[] = { speed, velocityTheta };
		vector<double> out(arr, arr + 2);
		return out;
	}
	vector<double> getCoordinates() {
		double arr[] = { xCoordinate,yCoordinate,zCoordinate };
		vector<double> out(arr, arr + 3);
		return out;
	}
	Plane();
	Plane(double length, double width);
	Plane(double xCoord, double yCoord, double length, double width);
};
#endif // !Plane

