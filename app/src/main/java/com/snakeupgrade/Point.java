package com.snakeupgrade;

import android.graphics.Color;
import android.graphics.Paint;

public class Point {
    private int positionX;
    private int positionY;
    private Paint pointColor;

    public Point(int positionX, int positionY) {
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

    public Paint getPointColor() {
        return pointColor;
    }

    public void setPointColor(Paint pointColor) {
        this.pointColor = pointColor;
    }

    public Paint createPoint() {
        if (pointColor == null) {
            pointColor = new Paint();
            pointColor.setColor(Color.GREEN);
            pointColor.setStyle(Paint.Style.FILL);
            pointColor.setAntiAlias(true);
        }
        return pointColor;
    }
}
