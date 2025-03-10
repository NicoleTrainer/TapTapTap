package com.TechInfinityStudios.TapTapTap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class gameSelection extends AppCompatActivity {

    Button btt1, btt2, btt3, btt4;
    int time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);

        ImageButton scoreButton = findViewById(R.id.scoreButton);


        btt1 = findViewById(R.id.time5Sec);

        btt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time=5;
                Intent intent = new Intent(gameSelection.this, gamePlay.class);
                intent.putExtra("choice",time);
                startActivity(intent);
            }
        });

        btt2 = findViewById(R.id.time10Sec);

        btt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time=10;
                Intent intent = new Intent(gameSelection.this, gamePlay.class);
                intent.putExtra("choice",time);
                startActivity(intent);
            }
        });

        btt3 = findViewById(R.id.time30Sec);

        btt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time=30;
                Intent intent = new Intent(gameSelection.this, gamePlay.class);
                intent.putExtra("choice",time);
                startActivity(intent);
            }
        });

        btt4 = findViewById(R.id.time60Sec);

        btt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time=60;
                Intent intent = new Intent(gameSelection.this, gamePlay.class);
                intent.putExtra("choice",time);
                startActivity(intent);
            }
        });




        }




}
