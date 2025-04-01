package com.TechInfinityStudios.TapTapTap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class gameSelection extends AppCompatActivity {

    Button btt1, btt2, btt3, btt4;
    ImageButton infoButton, scoreButton;
    int time;
    int level;
    databaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);

        db = new databaseHelper(this);

        infoButton = findViewById(R.id.infoButton);
        infoButton.setOnClickListener(view -> showDialog());

        scoreButton = findViewById(R.id.scoreButton);
        scoreButton.setOnClickListener(view -> showHighScores());

        btt1 = findViewById(R.id.time5Sec);
        btt1.setOnClickListener(v -> startGame(5, 1));

        btt2 = findViewById(R.id.time10Sec);
        btt2.setOnClickListener(v -> startGame(10, 2));

        btt3 = findViewById(R.id.time30Sec);
        btt3.setOnClickListener(v -> startGame(30, 3));

        btt4 = findViewById(R.id.time60Sec);
        btt4.setOnClickListener(v -> startGame(60, 4));
    }

    private void startGame(int time, int level) {
        Intent intent = new Intent(gameSelection.this, gamePlay.class);
        intent.putExtra("choice", time);
        intent.putExtra("level", level);
        startActivity(intent);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("How to Play")
                .setMessage("1. Select the time you want to play for.\n" +
                        "2. Wait for the timer to start.\n" +
                        "3. Tap as many times as you can in the time given.\n" +
                        "4. The game ends when times up. \nYour score is the number of taps you made.")
                .setPositiveButton("Got it!", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showHighScores() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the custom layout for high scores
        View view = getLayoutInflater().inflate(R.layout.score_popup, null);

        // Set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.scoreRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize a list to hold the high scores
        List<Integer> highScores = new ArrayList<>();
        final ScoreAdapter adapter = new ScoreAdapter(highScores);
        recyclerView.setAdapter(adapter);

        // Set button listeners for each level
        setupLevelButtons(view, adapter);

        // Set the custom view to the dialog
        builder.setView(view);

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Update the high scores for the selected level
        updateHighScores(adapter, level);

        // Set window size to 90% width and 70% height of the screen
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.9),  // 90% of screen width
                    (int) (getResources().getDisplayMetrics().heightPixels * 0.7)  // 70% of screen height
            );
        }


        // Ensure the dialog scales well on different screen densities
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    private void setupLevelButtons(View view, final ScoreAdapter adapter) {
        // Set button listeners for each level
        view.findViewById(R.id.level1Button).setOnClickListener(v -> updateHighScores(adapter, 1));
        view.findViewById(R.id.level2Button).setOnClickListener(v -> updateHighScores(adapter, 2));
        view.findViewById(R.id.level3Button).setOnClickListener(v -> updateHighScores(adapter, 3));
        view.findViewById(R.id.level4Button).setOnClickListener(v -> updateHighScores(adapter, 4));
    }

    private void updateHighScores(ScoreAdapter adapter, int level) {
        // Get the high score for the selected level from the database
        List<Integer> highScores = new ArrayList<>();
        highScores.add(db.getHighScore(level));  // Assume this is how you get high scores for the levels

        // Update the RecyclerView with the new scores for the selected level
        adapter.updateScores(highScores);
    }


}
