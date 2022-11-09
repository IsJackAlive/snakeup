package com.snakeupgrade;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import java.util.*;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    // list of snake points (snake length)
    private final List<SnakePoints> snakePointsList = new ArrayList<>();
    private SurfaceView surfaceView;
    private TextView scoreTV;

    // surface holder to draw snake on surface's canvas
    private SurfaceHolder surfaceHolder;

    // snake default moving position
    private String movingPosition="right";

    private int score = 0;

    // snake / point size
    private static final int pointSize = 30;

    // snake default tail
    private static final int defaultTail = 3;
    private static final int snakeColor = Color.GREEN;

    // snake moving speed, max value = 1000
    private static final int snakeSpeed = 500;

    // random points position
    private int positionX, positionY;

    // time to change snake position
    private Timer timer;

    // canvas to draw snake and show on surface view
    private Canvas canvas = null;

    // point color, snake tail
    private Paint pointColor = null;

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
        snakePointsList.clear();
        scoreTV.setText("0");

        // reset score
        score = 0;

        // set snake default moving position
        movingPosition = "bottom";

        int startPositionX = (pointSize) * defaultTail;

        for (int i=0; i < defaultTail; i++) {

            // add point
            SnakePoints snakePoints =  new SnakePoints(startPositionX, pointSize);
            snakePointsList.add(snakePoints);
            startPositionX = startPositionX - pointSize * 2;
        }

        // add random point on the screen
        addPoint();

        // first move -> start game
        moveSnake();
    }

    private void addPoint() {
        int surfaceWidth = surfaceView.getWidth() - pointSize * 2;
        int surfaceHeight = surfaceView.getHeight() - pointSize * 2;
        int randomXpos = new Random().nextInt(surfaceWidth / pointSize);
        int randomYpos = new Random().nextInt(surfaceHeight / pointSize);

        if ((randomXpos % 2) != 0) randomXpos = randomXpos + 1;
        if ((randomYpos % 2) != 0) randomYpos = randomYpos + 1;

        positionX = (pointSize * randomXpos) + pointSize;
        positionY = (pointSize * randomYpos) + pointSize;
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
                if (headPositionX == positionX && positionY == headPositionY) {
                    growSnake();
                    addPoint();
                }

                // check snake moving direction
                // other points follow snake's head
                switch (movingPosition) {
                    case "right":
                        snakePointsList.get(0).setPositionX(headPositionX + pointSize * 2);
                        snakePointsList.get(0). setPositionY(headPositionY);
                        break;

                    case "left":
                        snakePointsList.get(0).setPositionX(headPositionX - pointSize * 2);
                        snakePointsList.get(0). setPositionY(headPositionY);
                        break;

                    case "top":
                        snakePointsList.get(0).setPositionX(headPositionX);
                        snakePointsList.get(0). setPositionY(headPositionY - pointSize * 2);
                        break;

                    case "bottom":
                        snakePointsList.get(0).setPositionX(headPositionX);
                        snakePointsList.get(0). setPositionY(headPositionY + pointSize * 2);
                        break;
                }

                // check does snake touched edge or itself
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
                }
                else {
                    canvas = surfaceHolder.lockCanvas();

                    // clean canvas
                    canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);

                    canvas.drawCircle(
                            snakePointsList.get(0).getPositionX(),
                            snakePointsList.get(0).getPositionY(),
                            pointSize, createPointColor());

                    // draw random point
                    canvas.drawCircle(positionX, positionY, pointSize, createPointColor());

                    // other points following snake's head
                    for (int i = 1; i < snakePointsList.size(); i++) {
                        int getTempPositionX = snakePointsList.get(i).getPositionX();
                        int getTempPositionY = snakePointsList.get(i).getPositionY();

                        snakePointsList.get(i).setPositionX(headPositionX);
                        snakePointsList.get(i).setPositionY(headPositionY);
                        canvas.drawCircle(
                                snakePointsList.get(i).getPositionX(),
                                snakePointsList.get(i).getPositionY(),
                                pointSize, createPointColor());

                        headPositionX = getTempPositionX;
                        headPositionY = getTempPositionY;
                    }

                    // unlock canvas to draw on surfaceview
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }, 1000 - snakeSpeed, 1000 - snakeSpeed);
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
                snakePointsList.get(0).getPositionY() >= surfaceView.getHeight() ) {
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
        return gameOver;
    }

    private Paint createPointColor() {
        if (pointColor == null) {
            pointColor = new Paint();
            pointColor.setColor(snakeColor);
            pointColor.setStyle(Paint.Style.FILL);
            pointColor.setAntiAlias(true);
        }
        return pointColor;
    }
}
