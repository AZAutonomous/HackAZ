import pygame
from Waypoint import Waypoint
from Obstacle import Obstacle

def main():

    pygame.init()

    pygame.display.set_caption("minimal program")

    # create a surface on screen that has the size of 240 x 180
    screen = pygame.display.set_mode((400, 300))
    #pygame.Surface.fill(screen,(0,0,255))


    # define a variable to control the main loop
    running = True
    isblue = True
    x = 30
    y = 30
    xSize = 60
    ySize = 60
    clock = pygame.time.Clock()

    #controlRect
    listWaypoints = []
    listObstacle = []
    # main loop
    while running:
        # event handling, gets all event from the event queue
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
            if event.type == pygame.KEYDOWN and event.key == pygame.K_SPACE:
                isblue = not isblue

        if pygame.mouse.get_pressed()[0]:
            pos = pygame.mouse.get_pos()
            newPoint: Waypoint = Waypoint(pos[0],pos[1])
            if not (newPoint in listWaypoints):
                listWaypoints.append(newPoint)

        if pygame.mouse.get_pressed()[2]:
            pos = pygame.mouse.get_pos()
            newPoint: Obstacle = Obstacle(pos[0],pos[1],xSize/2, ySize/2)
            if not (newPoint in listObstacle):
                listObstacle.append(newPoint)

        if len(listWaypoints) > 0:
            point: Waypoint = listWaypoints[0]
            if x + xSize + 1.5 > point.xCoordinate > x - 1.5 and y + ySize + 1.5 > point.yCoordinate > y - 1.5:
                del listWaypoints[0]

            if point.xCoordinate - x >= 1.5: x +=3
            elif point.xCoordinate - x <= -1.5: x -= 3

            if point.yCoordinate - y >= 1.5: y += 3
            elif point.yCoordinate - y <= -1.5: y -= 3

        screen.fill((0,0,0))

        if(isblue == True):
            color = (0, 128, 255)
        else:
            color = (255, 128, 0)
        pygame.draw.rect(screen, color, pygame.Rect(x, y, xSize, ySize))

        for element in listWaypoints:
            pygame.draw.rect(screen, (128,128,128), pygame.Rect(element.xCoordinate, element.yCoordinate, 10,10))

        for element in listObstacle:
            pygame.draw.rect(screen, (255, 0, 0), pygame.Rect(element.attribtes()))

        pygame.display.flip()

        clock.tick(60)


if __name__ == "__main__":

    # call the main function
    main()
