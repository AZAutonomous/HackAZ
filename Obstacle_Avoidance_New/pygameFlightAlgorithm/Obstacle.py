class Obstacle:


    def __init__(self, xCoordinate, yCoordinate, xSize, ySize, color = (255,0,0)):
        self.xCoordinate = xCoordinate
        self.yCoordinate = yCoordinate
        self.xSize = xSize
        self.ySize = ySize
        self.color = color

    def attribtes(self):
        return self.xCoordinate, self.yCoordinate, self.xSize, self.ySize

    def getColor(self):
        return self.color

    def getBoundaries(self):
        return self.xCoordinate, self.yCoordinate, self.xCoordinate + self.xSize, self.yCoordinate + self.ySize

    def setColor(self, color):
        self.color = color;


