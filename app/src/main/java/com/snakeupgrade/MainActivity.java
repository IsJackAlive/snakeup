package com.snakeupgrade;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/*
TODO:
- punkt pojawia się w przeszkodzie - Już nie powinny, ale chyba się zdarza
- przeszkody tworza sciany
- zwiekszaj predkosc w trakcie gry
- bluetooth x_X
- multiplayer na 1 ekranie
 */


public class MainActivity extends Menu implements SurfaceHolder.Callback {

    public static final String GAME_PREFS = "prefs";
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

    private final List<Obstacle> obstacleList = new ArrayList<>();

    public Bitmap original;

    private SharedPreferences sharedPreferences;

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
        scoreTV.setText("Main init");
//        canvas = surfaceHolder.lockCanvas();
//        changeBackground();
//        canvas.drawBitmap(original, 0, 0, null);
//        surfaceHolder.unlockCanvasAndPost(canvas);

//        GameEvents gameEvents = new GameEvents(surfaceView, scoreTV, surfaceHolder, canvas);
//        gameEvents.gameInit();

    }

    private void changeBackground() {
        switch (new Random().nextInt(6)) {
            case 0:
                original = BitmapFactory.decodeResource(getResources(), R.drawable.sand_small);
                break;
            case 1:
                original = BitmapFactory.decodeResource(getResources(), R.drawable.rain_small);
                break;
            case 2:
                original = BitmapFactory.decodeResource(getResources(), R.drawable.moon_small);
                break;
            case 3:
                original = BitmapFactory.decodeResource(getResources(), R.drawable.ground_small);
                break;
            case 4:
                original = BitmapFactory.decodeResource(getResources(), R.drawable.grass_small);
                break;
            case 5:
                original = BitmapFactory.decodeResource(getResources(), R.drawable.desert_small);
                break;
        }
    }
}
