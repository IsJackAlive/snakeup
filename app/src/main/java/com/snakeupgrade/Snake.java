package com.snakeupgrade;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

public class Snake{
    private int score;
    private int tailLenght;
    private Paint snakeColor;
    // snake moving speed, max value = 1000
    private int snakeSpeed;
    List<Integer> positionX;
    List<Integer> positionY;

    public Snake(int score, int tailLenght, int snakeSpeed, List<Integer> positionX, List<Integer> positionY) {
        this.score = score;
        this.tailLenght = tailLenght;
        this.snakeSpeed = snakeSpeed;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTailLenght() {
        return tailLenght;
    }

    public void setTailLenght(int tailLenght) {
        this.tailLenght = tailLenght;
    }

    public Paint getSnakeColor() {
        return snakeColor;
    }

    public void setSnakeColor(Paint snakeColor) {
        this.snakeColor = snakeColor;
    }

    public int getSnakeSpeed() {
        return snakeSpeed;
    }

    public void setSnakeSpeed(int snakeSpeed) {
        this.snakeSpeed = snakeSpeed;
    }

    public List<Integer> getPositionX() {
        return positionX;
    }

    public void setPositionX(List<Integer> positionX) {
        this.positionX = positionX;
    }

    public List<Integer> getPositionY() {
        return positionY;
    }

    public void setPositionY(List<Integer> positionY) {
        this.positionY = positionY;
    }

    public Paint createSnake() {
        if (snakeColor == null) {
            snakeColor = new Paint();
            snakeColor.setColor(Color.BLUE);
            snakeColor.setStyle(Paint.Style.FILL);
            snakeColor.setAntiAlias(true);
        }
        return snakeColor;
    }
}
