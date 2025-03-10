package com.TechInfinityStudios.TapTapTap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class endGame extends AppCompatActivity {
    TextView totalScore, scoreMessage, currentHighScore;
    ImageButton Infobutton;
    Button redoButton, homeButton;
    int taps, choice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);

        Intent intent = getIntent();
        taps = intent.getIntExtra("taps", 0);
        choice = intent.getIntExtra("choice", 0);

         totalScore = findViewById(R.id.totalScore);
         scoreMessage = findViewById(R.id.scoreMessage);
         currentHighScore = findViewById(R.id.currentHighScore);
         totalScore.setText(String.valueOf(taps));
         Infobutton = findViewById(R.id.infoButton);
         redoButton = findViewById(R.id.redobutton);
         homeButton = findViewById(R.id.homebutton);

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



        Infobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

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
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("How to Play")
                .setMessage("1. Select the time you want to play for.\n" +
                        "2. Wait for the timer to start.\n" +
                        "3. Tap as many times as you can in the time given.\n" +
                        "4. The game ends when times up. \nYour score is the number of taps you made.")
                .setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
