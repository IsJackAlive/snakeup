package com.snakeupgrade;

import android.graphics.Canvas;
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

public class Player extends Snake {

    // [0] width    [1] height
    private int[] surface;
    private static final int POINT_SIZE = 40;
    private List<SnakePoints> snakePointsList = new ArrayList<>();
    private String movingPosition = "bottom";
    private int score = 0;
    private int[][] pointPos = {{0}, {0}};
    private int[][] obstaclePos = {{0}, {0}};
    private boolean alive = true;

    //
    private Canvas canvas;

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
//

    public Player(int[] surface) {
        this.surface = surface;
        this.movingPosition = movingPosition;
    }

    public Player(int[] surface, String movingPosition) {
        this.surface = surface;
        this.movingPosition = movingPosition;
    }

    public int getScore() {
        return score;
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

    int tail = 3;
    int startX = (POINT_SIZE) * tail;
    Snake snake = new Snake(startX, POINT_SIZE);

    public void playerInit() {

        for (int i = 0; i < tail; i++) {

            SnakePoints snakePoints = new SnakePoints(startX, POINT_SIZE);
            snake.addPoint(snakePoints);
            startX = startX - POINT_SIZE * 2;
        }
    }

    public void moveSnake() {

        // get head position
        snake.setSnakeHeadX(snakePointsList.get(0).getPositionX());
        snake.setSnakeHeadY(snakePointsList.get(0).getPositionY());

        // check snake moving direction
        // other points follow snake's head
        switch (movingPosition) {
            case "right":
                snakePointsList.get(0).setPositionX(snake.getSnakeHeadX() + POINT_SIZE * 2);
                snakePointsList.get(0).setPositionY(snake.getSnakeHeadY());
                break;

            case "left":
                snakePointsList.get(0).setPositionX(snake.getSnakeHeadX() - POINT_SIZE * 2);
                snakePointsList.get(0).setPositionY(snake.getSnakeHeadY());
                break;

            case "top":
                snakePointsList.get(0).setPositionX(snake.getSnakeHeadX());
                snakePointsList.get(0).setPositionY(snake.getSnakeHeadY() - POINT_SIZE * 2);
                break;

            case "bottom":
                snakePointsList.get(0).setPositionX(snake.getSnakeHeadX());
                snakePointsList.get(0).setPositionY(snake.getSnakeHeadY() + POINT_SIZE * 2);
                break;
        }

        // check does snake touched edge, itself or obstacle
        if (checkGameOver(snake.getSnakeHeadX(), snake.getSnakeHeadY())) {

            // if multiplayer mode continue game and delete player
            alive = false;

        } else {

            // Mozliwe ze to powinno byc w GameEvenst
            // other points following snake's head
            for (int i = 1; i < snakePointsList.size(); i++) {
                int getTempPositionX = snakePointsList.get(i).getPositionX();
                int getTempPositionY = snakePointsList.get(i).getPositionY();

                snakePointsList.get(i).setPositionX(snake.getSnakeHeadX());
                snakePointsList.get(i).setPositionY(snake.getSnakeHeadX());
                
                snake.setSnakeHeadX(getTempPositionX);
                snake.setSnakeHeadY(getTempPositionY);
            }
        }
    }

    public void growSnake() {

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
