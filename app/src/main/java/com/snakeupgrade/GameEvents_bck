package com.snakeupgrade;

import android.graphics.*;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/*
TODO:
- Obsługa klasy gracza
- Pozycje przeszkód
- Pozycje punktów
*/
public class GameEvents extends AppCompatActivity {

    // [0] width X    [1] height Y
    private int[] surface = {0, 0};
    private int[] pointPos = {0, 0};
    private static final int POINT_SIZE = 40;
    private List<Obstacle> obstacleList = new ArrayList<>();
    private Timer timer;

    //idk
    private SurfaceView surfaceView;
    private TextView scoreTV;

    // surface holder to draw snake on surface's canvas
    private SurfaceHolder surfaceHolder;

    // canvas to draw snake and show on surface view
    private Canvas canvas;

    public Bitmap original;

    private TextView debugTxtApp;

    public GameEvents(SurfaceView surfaceView, TextView scoreTV, SurfaceHolder surfaceHolder, Canvas canvas) {
        this.surfaceView = surfaceView;
        this.scoreTV = scoreTV;
        this.surfaceHolder = surfaceHolder;
        this.canvas = canvas;
    }
//    private Context context;

    //

    private void addPoint(Player player) {
        int surfaceWidth = surface[0] - POINT_SIZE * 2;
        int surfaceHeight = surface[1] - POINT_SIZE * 2;
        int randomXpos = new Random().nextInt(surfaceWidth / POINT_SIZE);
        int randomYpos = new Random().nextInt(surfaceHeight / POINT_SIZE);

        if ((randomXpos % 2) != 0) randomXpos = randomXpos + 1;
        if ((randomYpos % 2) != 0) randomYpos = randomYpos + 1;

        pointPos[0] = (POINT_SIZE * randomXpos) + POINT_SIZE;
        pointPos[1] = (POINT_SIZE * randomYpos) + POINT_SIZE;

        List<SnakePoints> pointsList = player.getPoints();

        // point under player's snake
        for (SnakePoints point : pointsList) {
            if (point.getPositionY() == pointPos[0] && point.getPositionY() == pointPos[1]) {
                addPoint(player);
            }
        }

        // point under obstacle
        for (Obstacle obstacle : obstacleList) {
            if (obstacle.getPositionX() == pointPos[0] && obstacle.getPositionY() == pointPos[1]) {
                addPoint(player);
            }
        }
    }

    private void addObstacle() {
        int surfaceWidth = surface[0] - POINT_SIZE * 2;
        int surfaceHeight = surface[1] - POINT_SIZE * 2;
        int randomXpos = new Random().nextInt(surfaceWidth / POINT_SIZE);
        int randomYpos = new Random().nextInt(surfaceHeight / POINT_SIZE);

        if ((randomXpos % 2) != 0) randomXpos = randomXpos + 1;
        if ((randomYpos % 2) != 0) randomYpos = randomYpos + 1;

        if ((POINT_SIZE * randomXpos) + POINT_SIZE != pointPos[0] &&
                (POINT_SIZE * randomYpos) + POINT_SIZE != pointPos[1]) {

            Obstacle newObstacle = new Obstacle((POINT_SIZE * randomXpos) + POINT_SIZE,
                    (POINT_SIZE * randomYpos) + POINT_SIZE);
            obstacleList.add(newObstacle);

        } else {
            addObstacle();
        }
    }

    private void gamePlay(Player player) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                // first move -> start game
                player.moveSnake();

                if (!player.isAlive()) {

                    // stop timer, moving snake
                    timer.purge();
                    timer.cancel();

                    // return high score here

                    // show game over alert

                    // timer runs in background, show dialog on main thread

                } else {
                    canvas = surfaceHolder.lockCanvas();

                    // clean canvas
                    canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
                    canvas.drawBitmap(original, 0, 0, null);

//                    Bitmap snakeHead = BitmapFactory.decodeResource(context.getResources(), R.drawable.head30);
//                    Bitmap snakeTail = BitmapFactory.decodeResource(context.getResources(), R.drawable.snake30);
//                    Bitmap redApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.applered30);
//                    Bitmap greenApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.applegreen30);

                    Bitmap snakeHead = BitmapFactory.decodeResource(getResources(), R.drawable.head30);
                    Bitmap snakeTail = BitmapFactory.decodeResource(getResources(), R.drawable.snake30);
                    Bitmap redApple = BitmapFactory.decodeResource(getResources(), R.drawable.applered30);
                    Bitmap greenApple = BitmapFactory.decodeResource(getResources(), R.drawable.applegreen30);

                    canvas.drawBitmap(snakeHead, player.getPoints().get(0).getPositionX() - POINT_SIZE,
                            player.getPoints().get(0).getPositionY() - POINT_SIZE, null);
                    // draw random point
                    canvas.drawBitmap(greenApple, pointPos[0] - POINT_SIZE,
                            pointPos[1] - POINT_SIZE, null);


                    // draw random obstacle
                    for (Obstacle obstacle1 : obstacleList) {
                        canvas.drawBitmap(redApple, obstacle1.getPositionX() - POINT_SIZE,
                                obstacle1.getPositionY() - POINT_SIZE, null);
                    }

                    // other points following snake's head
                    for (int i = 1; i < player.getPoints().size(); i++) {
                        int getTempPositionX = player.getPoints().get(i).getPositionX();
                        int getTempPositionY = player.getPoints().get(i).getPositionY();

                        player.getPoints().get(i).setPositionX(player.getSnakeHeadX());
                        player.getPoints().get(i).setPositionY(player.getSnakeHeadY());

                        canvas.drawBitmap(snakeTail, player.getPoints().get(i).getPositionX() - POINT_SIZE,
                                player.getPoints().get(i).getPositionY() - POINT_SIZE, null);

                        player.setSnakeHeadX(getTempPositionX);
                        player.setSnakeHeadY(getTempPositionY);
                    }

                    // unlock canvas to draw on surfaceview
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

        }, 1000 - player.getSpeed(), 1000 - player.getSpeed());
    }

    public void gameInit() {
        // clear snake points and set default score
        obstacleList.clear();

        Player player = new Player(surface);
        player.playerInit();

        // add random point on the screen
        addPoint(player);

        // add random obstacle on the screen
        addObstacle();

        gamePlay(player);
    }

}
