package com.snakeupgrade;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Button next = findViewById(R.id.button);
        next.setOnClickListener(view -> {
            Intent myIntent = new Intent(view.getContext(), MainActivity.class);
            startActivity(myIntent);
        });

        Button next2 = findViewById(R.id.button2);
        next2.setOnClickListener(view -> {
            Intent myIntent = new Intent(view.getContext(), MainActivity.class);
            startActivity(myIntent);
        });

        Button next3 = findViewById(R.id.button3);
        next3.setOnClickListener(view -> {
            Intent myIntent = new Intent(view.getContext(), MainActivity.class);
            startActivity(myIntent);
        });

        Button next4 = findViewById(R.id.button4);
        next4.setOnClickListener(view -> {
            Intent myIntent = new Intent(view.getContext(), MainActivity.class);
            startActivity(myIntent);
        });
    }
}
