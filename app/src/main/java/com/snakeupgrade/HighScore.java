package com.snakeupgrade;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HighScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score);

//        TextView scoreLabel = (TextView) findViewById(R.id.editTextNumber12);
//        TextView highScoreLabel = (TextView) findViewById(R.id.editTextNumber13);
//
//        int score = getIntent().getIntExtra("SCORE", 0);
//        scoreLabel.setText(score+"");
//
//        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
//        int highScore = settings.getInt("HIGH_SCORE", 0);

//        highScoreLabel.setText(highScore);
//
//        if (score > highScore){
//            highScoreLabel.setText(score);
//            /*Save*/
//            SharedPreferences.Editor editor = settings.edit();
//            editor.putInt("HIGH_SCORE", score);
//            editor.commit();
//        }else{
//            highScoreLabel.setText("High Score : "+highScore);
//        }

    }
}
