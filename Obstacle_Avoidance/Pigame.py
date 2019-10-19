import pygame
import numpy
import math
from Waypoint import Waypoint
from Obstacle import Obstacle
from Plane import Plane

colorDict = {"left": (125, 255, 0), "right": (125, 125, 125), "top": (200, 50, 50), "bottom": (200, 50, 200),
             "none": (255, 0, 0)}


def createUnitVector(first, second):
    vector = numpy.subtract(first, second)
    magnitude = math.sqrt(vector[0] * vector[0] + vector[1] * vector[1])
    if math.fabs(magnitude) < .1:
        return [0, 0]
    vector[0] = vector[0] / magnitude
    vector[1] = vector[1] / magnitude
    return vector


# input: plane, obstacle
# output: if on collision course
# compares angle from center to corners of obstacle
# TODO: expand to other quadrants & account for size
# atan2's range is (-pi, pi]
def intersect(plane: Plane, obstacle: Obstacle):
    planeCenter = plane.getRect().center
    planeVelocity = plane.getVelocityRT()
    obstacleDimensions = obstacle.getBoundaries()
    DistTL = [obstacleDimensions[0] - planeCenter[0], obstacleDimensions[1] - planeCenter[1]]
    thetaTL = math.atan2(DistTL[1], DistTL[0])
    DistBL = [obstacleDimensions[0] - planeCenter[0], obstacleDimensions[3] - planeCenter[1]]
    thetaBL = math.atan2(DistBL[1], DistBL[0])
    print(thetaBL, planeVelocity[1], thetaTL)

    if math.pi / -2 < thetaTL < planeVelocity[1] < thetaBL < math.pi / 2:
        return "left"
    return "none"


def main():
    pygame.init()

    pygame.display.set_caption("abstract algorithm")

    # create a surface on screen that has the size of 400 x 300
    screen = pygame.display.set_mode((400, 300))

    # define a variable to control the main loop
    running = True
    pause = False
    # controlRect
    # start Position
    x = 30.0
    y = 30.0
    # plane size
    xSize = 60.0
    ySize = 60.0
    # framerate limiter
    clock = pygame.time.Clock()

    # initialize plane
    plane1 = Plane((0, 128, 255), pygame.Rect(x, y, xSize, ySize))
    speed = 3

    # initialize queue's
    listWaypoints = []
    listObstacle = []
    # main loop
    while running:
        # event handling, gets all event from the event queue
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
            if pygame.key.get_pressed()[pygame.K_SPACE] == 1:
                pause = not pause
                pygame.time.wait(250)

        if not pause:
            # adds waypoints
            if pygame.mouse.get_pressed()[0]:
                pos = pygame.mouse.get_pos()
                newPoint: Waypoint = Waypoint(float(pos[0]), float(pos[1]))
                if not (newPoint in listWaypoints):
                    listWaypoints.append(newPoint)

            # adds obstacle
            if pygame.mouse.get_pressed()[2]:
                pos = pygame.mouse.get_pos()
                newPoint: Obstacle = Obstacle(pos[0], pos[1], xSize / 2, ySize / 2)
                if not (newPoint in listObstacle):
                    listObstacle.append(newPoint)

            # detection algorithm
            for obstacle in listObstacle:
                out = intersect(plane1, obstacle)
                obstacle.setColor(colorDict[out])

            # movement algorithm
            if len(listWaypoints) > 0:
                point: Waypoint = listWaypoints[0]
                if plane1.getRect().x + xSize + 1.5 > point.xCoordinate > plane1.getRect().x - 1.5 \
                        and plane1.getRect().y + ySize + 1.5 > point.yCoordinate > plane1.getRect().y - 1.5:
                    del listWaypoints[0]
                else:
                    plane1.setVelocity(speed * createUnitVector(point.getCoordinate(), plane1.getRect().center))
                    plane1.advancePosition()

            screen.fill((0, 0, 0))

            pygame.draw.rect(screen, plane1.getColor(), plane1.getRect())

            for element in listWaypoints:
                pygame.draw.rect(screen, (128, 128, 128),
                                 pygame.Rect(element.xCoordinate, element.yCoordinate, 10, 10))

            for element in listObstacle:
                pygame.draw.rect(screen, element.getColor(), pygame.Rect(element.attribtes()))

            if len(listWaypoints) > 0:
                pygame.draw.line(screen, (0, 255, 0), plane1.getRect().center,
                                 numpy.add(listWaypoints[0].getCoordinate(), [5, 5]))

        # refresh screen
        pygame.display.flip()

        # set framerate
        clock.tick(60)


if __name__ == "__main__":
    # call the main function
    main()
