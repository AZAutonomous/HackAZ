import pygame


class Plane:
    def __init__(self, color= (0, 128, 255), rect=pygame.Rect(0, 0, 1, 1), velocity=[0, 0]):
        self.color = color
        self.rect = rect
        self.velocity = velocity

    def setVelocity(self, vel):
        self.velocity[0] = vel[0]
        self.velocity[1] = vel[1]

    def advancePosition(self):
        self.rect.left += self.velocity[0]
        self.rect.top += self.velocity[0]

    def setColor(self, color):
        self.color = color

    def setRectangle(self, rect):
        self.rect = rect

    def getRect(self):
        return self.rect

    def getColor(self):
        return self.color

    def getVelocity(self):
        return self.velocity
