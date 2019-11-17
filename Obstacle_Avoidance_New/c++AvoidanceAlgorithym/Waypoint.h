#pragma once
#ifndef Waypoint_H
#define Waypoint_H

#include <vector>
using namespace std;

class Waypoint {
private:
	double xCoordinate;
	double yCoordinate;
	double zCoordinate;
	double tolerance;
	bool line;
public:
	vector<double> getCoordinate() {
		double list[3] = { xCoordinate, yCoordinate, zCoordinate };
		vector<double> out(list, list + sizeof(list) / sizeof(double));
		return out;
	};
	bool isLine()
	{
		return line;
	};

	Waypoint(double xCoor, double yCoor, double zCoor, double tol);
	Waypoint(double xCoor, double yCoor, double tol);
	Waypoint(double xCoor, double yCoor);
};

#endif // !Waypoint_H
