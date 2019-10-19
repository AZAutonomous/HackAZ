import pygame
import numpy
import math
from Waypoint import Waypoint
from Obstacle import Obstacle
from Plane import Plane

colorDict = {"left": (125, 255, 0), "right": (125, 125, 125), "top": (255, 255, 50), "bottom": (200, 50, 200),
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
# output: if on collision course which side
# compares angle from center to corners of obstacle
# atan2's range is (-pi, pi]
def intersect(plane: Plane, obstacle: Obstacle):
    planeDimensions = plane.getBoundaries()
    planeVelocity = plane.getVelocityRT()
    obstacleDimensions = obstacle.getBoundaries()
    # rectangle sides are 0-3 clockwise with left == 0
    # gets the near miss angle by calculating angle from "closest corners"
    DistPlaneTLtoObjBR = [obstacleDimensions[2] - planeDimensions[0], obstacleDimensions[3] - planeDimensions[1]]
    thetaTLBR = math.atan2(DistPlaneTLtoObjBR[1], DistPlaneTLtoObjBR[0])
    DistPlaneTRtoObjBL = [obstacleDimensions[0] - planeDimensions[2], obstacleDimensions[3] - planeDimensions[1]]
    thetaTRBL = math.atan2(DistPlaneTRtoObjBL[1], DistPlaneTRtoObjBL[0])
    DistPlaneBRtoObjTL = [obstacleDimensions[0] - planeDimensions[2], obstacleDimensions[1] - planeDimensions[3]]
    thetaBRTL = math.atan2(DistPlaneBRtoObjTL[1], DistPlaneBRtoObjTL[0])
    DistPlaneBLtoObjTR = [obstacleDimensions[2] - planeDimensions[0], obstacleDimensions[1] - planeDimensions[3]]
    thetaBLTR = math.atan2(DistPlaneBLtoObjTR[1], DistPlaneBLtoObjTR[0])

    # compares velocity direction with near miss angles
    if -math.pi < thetaTRBL <= planeVelocity[1] <= thetaTLBR <= 0:
        return "bottom"
    elif math.pi / -2 <= thetaBRTL <= planeVelocity[1] <= thetaTRBL <= math.pi / 2:
        return "left"
    elif 0 <= thetaBLTR <= planeVelocity[1] <= thetaBRTL <= math.pi:
        return "top"
    # polar coordinates are discontinuous range shift into continuous by adding 2pi
    else:
        if thetaBLTR < 0:
            thetaBLTR += 2*math.pi
            if thetaTLBR < 0:
                thetaTLBR += 2*math.pi
            if planeVelocity[1] < 0:
                planeVelocity[1] += 2 * math.pi
        if math.pi/2 <= thetaTLBR <= planeVelocity[1] <= thetaBLTR <= 3*math.pi/2:
            return "right"
    return "none"


def main():
    pygame.init()

    pygame.display.set_caption("abstract algorithm")

    # create a surface on screen that has the size of 400 x 300
    screen = pygame.display.set_mode((1000, 500))

    # define a variable to control the main loop
    running = True
    pause = False
    # controlRect
    # start Position
    x = 970.0
    y = 470.0
    # plane size
    xSize = 60.0
    ySize = 60.0
    # framerate limiter
    clock = pygame.time.Clock()

    # initialize plane
    plane1 = Plane((0, 128, 255), pygame.Rect(x, y, xSize, ySize))
    speed = 6

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
        if not pause:
            # movement algorithm
            if len(listWaypoints) > 0:
                point: Waypoint = listWaypoints[0]
                if plane1.getRect().centerx + 2 * speed > point.xCoordinate > plane1.getRect().centerx - 2 * speed \
                        and plane1.getRect().centery + 2 * speed > point.yCoordinate > plane1.getRect().centery - 2 * speed:
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
            pygame.draw.rect(screen, element.getColor(), pygame.Rect(element.attributes()))

        if len(listWaypoints) > 0:
            pygame.draw.line(screen, (0, 255, 0), plane1.getRect().bottomleft,
                             numpy.add(listWaypoints[0].getCoordinate(), [5, 5]))
            pygame.draw.line(screen, (0, 255, 0), plane1.getRect().bottomright,
                             numpy.add(listWaypoints[0].getCoordinate(), [5, 5]))
            pygame.draw.line(screen, (0, 255, 0), plane1.getRect().topleft,
                             numpy.add(listWaypoints[0].getCoordinate(), [5, 5]))
            pygame.draw.line(screen, (0, 255, 0), plane1.getRect().topright,
                             numpy.add(listWaypoints[0].getCoordinate(), [5, 5]))
        # refresh screen
        pygame.display.flip()

        # set framerate
        clock.tick(60)


if __name__ == "__main__":
    # call the main function
    main()
