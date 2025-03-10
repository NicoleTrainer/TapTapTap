package com.TechInfinityStudios.TapTapTap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class endGame extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);

        int taps;
        int choice = 0;
        Intent intent = getIntent();
        taps = intent.getIntExtra("taps", 0);
        choice = intent.getIntExtra("choice", 0);

        TextView totalScore = findViewById(R.id.totalScore);
        TextView scoreMessage = findViewById(R.id.scoreMessage);
        TextView currentHighScore = findViewById(R.id.currentHighScore);
        ImageButton scoreButton = findViewById(R.id.scoreButton);
        totalScore.setText(String.valueOf(taps));

        int finalChoice = choice;
        int highestScore = scoreStorage.getHighestScore(this, choice);


        if (taps > highestScore) {
            scoreMessage.setText("New High Score!");
            scoreStorage.saveScore(this, choice, taps);
            currentHighScore.setText("");
        } else {
            scoreMessage.setText("Nice Try!");
            currentHighScore.setText("Current High Score: " + String.valueOf(highestScore));
        }



        Button redoButton = findViewById(R.id.redobutton);
        Button homeButton = findViewById(R.id.homebutton);

        redoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redoSelection(finalChoice);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeSelection();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onBackPressed();
    }

    private void homeSelection() {
        Intent intent = new Intent(endGame.this, gameSelection.class);
        startActivity(intent);
    }

    private void redoSelection(int c) {
        Intent intent = new Intent(endGame.this, gamePlay.class);
        intent.putExtra("choice", c);
        startActivity(intent);
        finish();
    }
}
