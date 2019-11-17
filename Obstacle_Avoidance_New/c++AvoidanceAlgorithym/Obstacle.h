#pragma once
#ifndef Obstacle_H
#define Obstacle_H
#include <vector>
using namespace std;

class Obstacle
{
private:
	double xCoordinate;
	double yCoordinate;
	double radius;
	double height;
	bool infinite; 
public:
	double getRadius(){
		return radius;}
	vector<double> getCenter() {
		double point[2] = {xCoordinate, yCoordinate};
		vector<double> out(point, point + 2);
		return out;
	};
	vector<double> getAttributes() {
		double list[4] = { xCoordinate, yCoordinate, radius, height };
		vector<double> out(list, list + sizeof(list) / sizeof(double));
		return out;
	};
	bool isInfiniteHeight()
	{
		return infinite;
	};

	Obstacle(double xCoor, double yCoor, double radius, double height);
	Obstacle(double xCoor, double yCoor, double radius);
};



#endif
