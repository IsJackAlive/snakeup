package com.snakeupgrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/*
TODO:
- Obsługa klasy gracza
- Pozycje przeszkód
- Pozycje punktów
*/
public class GameEvents {

    // [0] width X    [1] height Y
    private int[] surface = {0, 0};
    private int[] pointPos = {0, 0};
    private static final int POINT_SIZE = 40;
    private final List<Obstacle> obstacleList = new ArrayList<>();
    private Timer timer;

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

            Obstacle newObstacle = new Obstacle((POINT_SIZE * randomXpos) + POINT_SIZE,
                    (POINT_SIZE * randomYpos) + POINT_SIZE);
            obstacleList.add(newObstacle);

        } else {
            addObstacle();
        }
    }

    private void gamePlay(Player player) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                // first move -> start game
                player.moveSnake();
            }

        }, 1000 - player.getSpeed(), 1000 - player.getSpeed());
    }

    public void gameInit() {
        // clear snake points and set default score
        obstacleList.clear();

        Player player = new Player(surface);
        player.playerInit();

        // add random point on the screen
        addPoint();

        // add random obstacle on the screen
        addObstacle();

        gamePlay(player);
    }
}
