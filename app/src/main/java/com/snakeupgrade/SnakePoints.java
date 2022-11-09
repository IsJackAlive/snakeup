package com.snakeupgrade;

public class SnakePoints {

    private int positionX, PositionY;

    public SnakePoints(int positionX, int PositionY) {
        this.positionX = positionX;
        this.PositionY = PositionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return PositionY;
    }

    public void setPositionY(int PositionY) {
        this.PositionY = PositionY;
    }
}
