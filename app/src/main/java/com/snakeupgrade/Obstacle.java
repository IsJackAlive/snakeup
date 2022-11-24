package com.snakeupgrade;

import android.graphics.Color;
import android.graphics.Paint;

public class Obstacle {
    private int positionX;
    private int positionY;
    private Paint obstacleColor;

    public Obstacle(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public Paint createObstacle() {
        if (obstacleColor == null) {
            obstacleColor = new Paint();
            obstacleColor.setColor(Color.RED);
            obstacleColor.setStyle(Paint.Style.FILL);
            obstacleColor.setAntiAlias(true);
        }
        return obstacleColor;
    }
}
