package com.snakeupgrade;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;

import java.util.*;

/*
TODO:
- punkt pojawia się w przeszkodzie
- przeszkody tworza sciany
- zwiekszaj predkosc w trakcie gry
- przyciski, grafika weza
 */


public class MainActivity extends Menu implements SurfaceHolder.Callback {

    // list of snake points (snake length)
    private final List<SnakePoints> snakePointsList = new ArrayList<>();
    private SurfaceView surfaceView;
    private TextView scoreTV;

    // surface holder to draw snake on surface's canvas
    private SurfaceHolder surfaceHolder;

    // snake default moving position
    private String movingPosition = "bottom";

    private int score = 0;

    // snake / point size
    private static final int POINT_SIZE = 40;

    // snake default tail
    private static final int DEFAULT_TAIL = 3;
    private static final int SNAKE_COLOR = Color.GREEN;

    // snake moving speed, max value = 1000
    private static final int SNAKE_SPEED = 650;

    // random points position
    private int pointPositionX, pointPositionY;

    // time to change snake position
    private Timer timer;

    // canvas to draw snake and show on surface view
    private Canvas canvas = null;

    // point color, snake tail
    private Paint pointColor = null;

    private final List<Obstacle> obstacleList = new ArrayList<>();
    public int canvasBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getting surfaceView and score textView from xml file
        surfaceView = findViewById(R.id.surfaceView);
        scoreTV = findViewById(R.id.scoreTV);

        // getting Imagebuttons from xml file
        final AppCompatImageButton topBtn = findViewById(R.id.topBtn);
        final AppCompatImageButton bottomBtn = findViewById(R.id.bottomBtn);
        final AppCompatImageButton rightBtn = findViewById(R.id.rightBtn);
        final AppCompatImageButton leftBtn = findViewById(R.id.leftBtn);

        // adding callback to surfaceView
        surfaceView.getHolder().addCallback(this);

        topBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check if previous moving position is not bottom, snake can't spin 180
                if (!movingPosition.equals("bottom")) {
                    movingPosition = "top";
                }
            }
        });

        bottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!movingPosition.equals("top")) {
                    movingPosition = "bottom";
                }
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!movingPosition.equals("left")) {
                    movingPosition = "right";
                }
            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!movingPosition.equals("right")) {
                    movingPosition = "left";
                }
            }
        });
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        // get surfaceHolder if surface is created
        this.surfaceHolder = surfaceHolder;

        // data for snake
        init();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    private void init() {
        // clear snake points and set default score
        obstacleList.clear();
        snakePointsList.clear();
        scoreTV.setText("0");
        canvas = surfaceHolder.lockCanvas();
        changeBackground();
        canvas.drawColor(canvasBackground);
        surfaceHolder.unlockCanvasAndPost(canvas);

        // reset score
        score = 0;

        // set snake default moving position
        movingPosition = "bottom";

        int startPositionX = (POINT_SIZE) * DEFAULT_TAIL;

        for (int i = 0; i < DEFAULT_TAIL; i++) {

            // add point
            SnakePoints snakePoints = new SnakePoints(startPositionX, POINT_SIZE);
            snakePointsList.add(snakePoints);
            startPositionX = startPositionX - POINT_SIZE * 2;
        }

        // add random point on the screen
        addPoint();

        // add random obstacle on the screen
        addObstacle();

        // first move -> start game
        moveSnake();
    }

    private void changeBackground(){
        switch (new Random().nextInt(6)){
            case 0:
                canvasBackground = Color.BLUE;
                break;
            case 1:
                canvasBackground = Color.BLACK;
                break;
            case 2:
                canvasBackground = Color.WHITE;
                break;
            case 3:
                canvasBackground = Color.YELLOW;
                break;
            case 4:
                canvasBackground = Color.MAGENTA;
                break;
            case 5:
                canvasBackground = Color.CYAN;
                break;
        }
    }

    private void addPoint() {
        int surfaceWidth = surfaceView.getWidth() - POINT_SIZE * 2;
        int surfaceHeight = surfaceView.getHeight() - POINT_SIZE * 2;
        int randomXpos = new Random().nextInt(surfaceWidth / POINT_SIZE);
        int randomYpos = new Random().nextInt(surfaceHeight / POINT_SIZE);

        if ((randomXpos % 2) != 0) randomXpos = randomXpos + 1;
        if ((randomYpos % 2) != 0) randomYpos = randomYpos + 1;

        pointPositionX = (POINT_SIZE * randomXpos) + POINT_SIZE;
        pointPositionY = (POINT_SIZE * randomYpos) + POINT_SIZE;
        for (Obstacle obstacle : obstacleList) {
            if (obstacle.getPositionX() == pointPositionX && obstacle.getPositionY() == pointPositionY) {
                addPoint();
            }
        }
    }

    private void addObstacle() {
        int surfaceWidth = surfaceView.getWidth() - POINT_SIZE * 2;
        int surfaceHeight = surfaceView.getHeight() - POINT_SIZE * 2;
        int randomXpos = new Random().nextInt(surfaceWidth / POINT_SIZE);
        int randomYpos = new Random().nextInt(surfaceHeight / POINT_SIZE);

        if ((randomXpos % 2) != 0) randomXpos = randomXpos + 1;
        if ((randomYpos % 2) != 0) randomYpos = randomYpos + 1;

        if ((POINT_SIZE * randomXpos) + POINT_SIZE != pointPositionX &&
                (POINT_SIZE * randomYpos) + POINT_SIZE != pointPositionY) {
            Obstacle newObstacle = new Obstacle((POINT_SIZE * randomXpos) + POINT_SIZE, (POINT_SIZE * randomYpos) + POINT_SIZE);
            obstacleList.add(newObstacle);
        } else {
            addObstacle();
        }
    }

    private void moveSnake() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                // get head position
                int headPositionX = snakePointsList.get(0).getPositionX();
                int headPositionY = snakePointsList.get(0).getPositionY();

                // check if snake get a point
                if (headPositionX == pointPositionX && pointPositionY == headPositionY) {
                    growSnake();
                    addPoint();
                    addObstacle();
                }

                // check snake moving direction
                // other points follow snake's head
                switch (movingPosition) {
                    case "right":
                        snakePointsList.get(0).setPositionX(headPositionX + POINT_SIZE * 2);
                        snakePointsList.get(0).setPositionY(headPositionY);
                        break;

                    case "left":
                        snakePointsList.get(0).setPositionX(headPositionX - POINT_SIZE * 2);
                        snakePointsList.get(0).setPositionY(headPositionY);
                        break;

                    case "top":
                        snakePointsList.get(0).setPositionX(headPositionX);
                        snakePointsList.get(0).setPositionY(headPositionY - POINT_SIZE * 2);
                        break;

                    case "bottom":
                        snakePointsList.get(0).setPositionX(headPositionX);
                        snakePointsList.get(0).setPositionY(headPositionY + POINT_SIZE * 2);
                        break;
                }

                // check does snake touched edge, itself or obstacle
                if (checkGameOver(headPositionX, headPositionY)) {

                    // stop timer, moving snake
                    timer.purge();
                    timer.cancel();

                    // show game over alert
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Your Score = " + score);
                    builder.setTitle("Game Over");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // restart game
                            init();
                        }
                    });

                    // timer runs in background, show dialog on main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            builder.show();
                        }
                    });
                } else {
                    canvas = surfaceHolder.lockCanvas();

                    // clean canvas
                    canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
                    canvas.drawColor(canvasBackground);

                    canvas.drawCircle(
                            snakePointsList.get(0).getPositionX(),
                            snakePointsList.get(0).getPositionY(),
                            POINT_SIZE, createPointColor());

                    // draw random point
                    canvas.drawCircle(pointPositionX, pointPositionY, POINT_SIZE, createPointColor());

                    // draw random obstacle
                    for (Obstacle obstacle1 : obstacleList) {
                        canvas.drawCircle(obstacle1.getPositionX(), obstacle1.getPositionY(), POINT_SIZE, obstacle1.createObstacle());
                    }

                    // other points following snake's head
                    for (int i = 1; i < snakePointsList.size(); i++) {
                        int getTempPositionX = snakePointsList.get(i).getPositionX();
                        int getTempPositionY = snakePointsList.get(i).getPositionY();

                        snakePointsList.get(i).setPositionX(headPositionX);
                        snakePointsList.get(i).setPositionY(headPositionY);
                        canvas.drawCircle(
                                snakePointsList.get(i).getPositionX(),
                                snakePointsList.get(i).getPositionY(),
                                POINT_SIZE, createPointColor());

                        headPositionX = getTempPositionX;
                        headPositionY = getTempPositionY;
                    }

                    // unlock canvas to draw on surfaceview
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }, 1000 - SNAKE_SPEED, 1000 - SNAKE_SPEED);
    }

    private void growSnake() {

        // create new snake point
        SnakePoints snakePoints = new SnakePoints(0, 0);

        // increase tail length
        snakePointsList.add(snakePoints);

        // increase score
        score++;

        // show score
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreTV.setText(String.valueOf(score));
            }
        });
    }

    private boolean checkGameOver(int headPositionX, int headPositionY) {
        boolean gameOver = false;

        // check if snake touched edge
        if (snakePointsList.get(0).getPositionX() < 0 || snakePointsList.get(0).getPositionY() < 0 ||
                snakePointsList.get(0).getPositionX() >= surfaceView.getWidth() ||
                snakePointsList.get(0).getPositionY() >= surfaceView.getHeight()) {
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
        for (Obstacle obstacle : obstacleList) {
            if (snakePointsList.get(0).getPositionX() == obstacle.getPositionX() &&
                    snakePointsList.get(0).getPositionY() == obstacle.getPositionY()) {
                gameOver = true;
            }
        }

        return gameOver;
    }

    private Paint createPointColor() {
        if (pointColor == null) {
            pointColor = new Paint();
            pointColor.setColor(SNAKE_COLOR);
            pointColor.setStyle(Paint.Style.FILL);
            pointColor.setAntiAlias(true);
        }
        return pointColor;
    }
}
