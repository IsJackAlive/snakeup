package com.snakeupgrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
TODO:
- Obsługa klasy gracza
- Pozycje przeszkód
- Pozycje punktów
*/
public class GameEvents {

    // [0] width X    [1] height Y
    private int[] surface = {0,0};
    private int[] pointPos = {0,0};
    private static final int POINT_SIZE = 40;
    private final List<Obstacle> obstacleList = new ArrayList<>();

    private void addPoint() {
        int surfaceWidth = surface[0] - POINT_SIZE * 2;
        int surfaceHeight = surface[1] - POINT_SIZE * 2;
        int randomXpos = new Random().nextInt(surfaceWidth / POINT_SIZE);
        int randomYpos = new Random().nextInt(surfaceHeight / POINT_SIZE);

        if ((randomXpos % 2) != 0) randomXpos = randomXpos + 1;
        if ((randomYpos % 2) != 0) randomYpos = randomYpos + 1;

        pointPos[0] = (POINT_SIZE * randomXpos) + POINT_SIZE;
        pointPos[1] = (POINT_SIZE * randomYpos) + POINT_SIZE;
        for (Obstacle obstacle : obstacleList) {
            if (obstacle.getPositionX() == pointPos[0] && obstacle.getPositionY() == pointPos[1]) {
                addPoint();
            }
        }
    }

    private void addObstacle() {
        int surfaceWidth = surface[0] - POINT_SIZE * 2;
        int surfaceHeight = surface[1] - POINT_SIZE * 2;
        int randomXpos = new Random().nextInt(surfaceWidth / POINT_SIZE);
        int randomYpos = new Random().nextInt(surfaceHeight / POINT_SIZE);

        if ((randomXpos % 2) != 0) randomXpos = randomXpos + 1;
        if ((randomYpos % 2) != 0) randomYpos = randomYpos + 1;

        if ((POINT_SIZE * randomXpos) + POINT_SIZE != pointPos[0] &&
                (POINT_SIZE * randomYpos) + POINT_SIZE != pointPos[1]) {
            Obstacle newObstacle = new Obstacle((POINT_SIZE * randomXpos) + POINT_SIZE, (POINT_SIZE * randomYpos) + POINT_SIZE);
            obstacleList.add(newObstacle);
        } else {
            addObstacle();
        }
    }

    private void gameInit () {
        // clear snake points and set default score
        obstacleList.clear();

        Player player = new Player(surface);

        int startPositionX = (POINT_SIZE) * player.getTail();

        for (int i = 0; i < player.getTail(); i++) {

            // add point
            SnakePoints snakePoints = new SnakePoints(startPositionX, POINT_SIZE);
//            snakePointsList.add(snakePoints);

            startPositionX = startPositionX - POINT_SIZE * 2;
        }

    }
}
