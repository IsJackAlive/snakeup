package com.snakeupgrade;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private int speed = 650;
    private int tail;
    private int color = Color.GREEN;
    private int snakeHeadX, snakeHeadY;

    private static final int POINT_SIZE = 40;
    private List<SnakePoints> points = new ArrayList<>();

    public Snake() {
    }

    public Snake(int snakeHeadX, int snakeHeadY) {
        this.snakeHeadX = snakeHeadX;
        this.snakeHeadY = snakeHeadY;
    }

    public int getSnakeHeadX() {
        return snakeHeadX;
    }

    public void setSnakeHeadX(int snakeHeadX) {
        this.snakeHeadX = snakeHeadX;
    }

    public int getSnakeHeadY() {
        return snakeHeadY;
    }

    public void setSnakeHeadY(int snakeHeadY) {
        this.snakeHeadY = snakeHeadY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getTail() {
        return tail;
    }

    public void setTail(int tail) {
        this.tail = tail;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<SnakePoints> getPoints() {
        return points;
    }

    public void setPoints(List<SnakePoints> points) {
        this.points = points;
    }

    public void addPoint(SnakePoints sp) {
        points.add(sp);
    }
}
