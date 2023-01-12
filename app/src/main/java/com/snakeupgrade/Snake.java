package com.snakeupgrade;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private int speed = 650;
    private int tail = 3;
    private int color = Color.GREEN;
    private int[] snakeHead = {0, 0};
    private static final int POINT_SIZE = 40;
    private List<SnakePoints> points = new ArrayList<>();

    public Snake() {}

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

    public int[] getSnakeHead() {
        return snakeHead;
    }

    public void setSnakeHead(int[] snakeHead) {
        this.snakeHead = snakeHead;
    }

    public List<SnakePoints> getPoints() {
        return points;
    }

    public void setPoints(List<SnakePoints> points) {
        this.points = points;
    }
}
