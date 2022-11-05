package com.snakeupgrade;

import android.graphics.Color;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    // snake moving speed, max value = 100
    private static final int snakeSpeed = 60;

    // random points position
    private int positionX, positionY;

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
                if (movingPosition.equals("bottom")) {
                    movingPosition = "top";
                }
            }
        });

        bottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (movingPosition.equals("top")) {
                    movingPosition = "bottom";
                }
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (movingPosition.equals("left")) {
                    movingPosition = "right";
                }
            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (movingPosition.equals("right")) {
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
        movingPosition = "right";

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

    }
}
