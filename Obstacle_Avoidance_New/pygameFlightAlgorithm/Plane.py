import pygame
from math import *


class Plane:
    def __init__(self, color = (0, 128, 255), rect :pygame.Rect =pygame.Rect(0.0, 0.0, 1.0, 1.0), velocity = [0.0, 0.0]):
        self.color = color
        self.rect = rect
        self.velocityXY = velocity
        self.velocityRT = [sqrt(velocity[0] * velocity[0] + velocity[1] * velocity[1]), atan2(velocity[1], velocity[0])]

    def setVelocity(self, vel):
        self.velocityXY[0] = vel[0]
        self.velocityXY[1] = vel[1]
        self.velocityRT = [sqrt(vel[0] * vel[0] + vel[1] * vel[1]), atan2(vel[1], vel[0])]

    def advancePosition(self):
        self.rect.left += self.velocityXY[0]
        self.rect.top += self.velocityXY[1]

    def setColor(self, color):
        self.color = color

    def setRectangle(self, rect):
        self.rect = rect

    def getRect(self):
        return self.rect

    def getColor(self):
        return self.color

    def getVelocity(self):
        return self.velocityXY

    def getVelocityRT(self):
        return self.velocityRT

    def getBoundaries(self):
        return self.rect.left, self.rect.top, self.rect.right, self.rect.bottom
