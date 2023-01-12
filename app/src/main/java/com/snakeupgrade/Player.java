package com.snakeupgrade;

import android.graphics.Color;

import java.util.*;

/*
TODO:
- Obsługa klasy snake dla gracza:
    - sterowanie
    - punkty
    - predkosc
    - smierc 1 gracza nie konczy gry 2giemu
*/

public class Player {

    // [0] width    [1] height
    private int[] surface;
    private static final int POINT_SIZE = 40;
    private List<SnakePoints> snakePointsList = new ArrayList<>();
    private String movingPosition = "bottom";
    private int score = 0;
    private int SNAKE_COLOR;
    private int SNAKE_SPEED = 650;
    private int DEFAULT_TAIL = 3;
    private int[] snakeHead = {0, 0};
    private int[][] pointPos = { {0}, {0} };
    private int[][] obstaclePos = { {0}, {0} };
    private boolean alive = true;

    public Player(int[] surface) {
        this.surface = surface;
        this.movingPosition = movingPosition;
        SNAKE_COLOR = Color.GREEN;
    }

    public Player(int[] surface, String movingPosition, int SNAKE_COLOR) {
        this.surface = surface;
        this.movingPosition = movingPosition;
        this.SNAKE_COLOR = SNAKE_COLOR;
    }

    public int getTail() {
        return DEFAULT_TAIL;
    }

    public int getScore() {
        return score;
    }

    public int[] getSnakeHead() {
        return snakeHead;
    }

    public void setPointPos(int[][] pointPos) {
        this.pointPos = pointPos;
    }

    public void setObstaclePos(int[][] obstaclePos) {
        this.obstaclePos = obstaclePos;
    }

    public boolean isAlive() {
        return alive;
    }

    private void moveSnake() {

                snakeHead[0] = snakePointsList.get(0).getPositionX();
                snakeHead[1] = snakePointsList.get(0).getPositionY();

                // check snake moving direction
                // other points follow snake's head
                switch (movingPosition) {
                    case "right":
                        snakePointsList.get(0).setPositionX(snakeHead[0] + POINT_SIZE * 2);
                        snakePointsList.get(0).setPositionY(snakeHead[1]);
                        break;

                    case "left":
                        snakePointsList.get(0).setPositionX(snakeHead[0] - POINT_SIZE * 2);
                        snakePointsList.get(0).setPositionY(snakeHead[1]);
                        break;

                    case "top":
                        snakePointsList.get(0).setPositionX(snakeHead[0]);
                        snakePointsList.get(0).setPositionY(snakeHead[1] - POINT_SIZE * 2);
                        break;

                    case "bottom":
                        snakePointsList.get(0).setPositionX(snakeHead[0]);
                        snakePointsList.get(0).setPositionY(snakeHead[1] + POINT_SIZE * 2);
                        break;
                }

                // check does snake touched edge, itself or obstacle
                if (checkGameOver(snakeHead[0], snakeHead[1])) {

                    // if multiplayer mode continue game and delete player
                    alive = false;
                } else {

                }
    }

    private void growSnake() {

        // create new snake point
        SnakePoints snakePoints = new SnakePoints(0, 0);

        // increase tail length
        snakePointsList.add(snakePoints);

        // increase score
        score++;

        // show score

    }

    private boolean checkGameOver(int headPositionX, int headPositionY) {
        boolean gameOver = false;

        // check if snake touched edge
        if (snakePointsList.get(0).getPositionX() < 0 || snakePointsList.get(0).getPositionY() < 0 ||
                snakePointsList.get(0).getPositionX() >= surface[0] ||
                snakePointsList.get(0).getPositionY() >= surface[1]) {
            gameOver = true;
        }
        // check if snake touched tail
        else {
            for (int i = 0; i < snakePointsList.size(); i++) {
                if (headPositionX == snakePointsList.get(i).getPositionX() &&
                        headPositionY == snakePointsList.get(i).getPositionY()) {
                    gameOver = true;
                    break;
                }
            }
        }
        // check if snake touched obstacle
        for (int i = 0; i < obstaclePos.length; i++) {
            if (snakePointsList.get(0).getPositionX() == obstaclePos[i][0] &&
                    snakePointsList.get(0).getPositionY() == obstaclePos[0][i]) {
                gameOver = true;
            }
        }

        return gameOver;
    }
}
