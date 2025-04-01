package com.TechInfinityStudios.TapTapTap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class endGame extends AppCompatActivity {
    TextView totalScore, scoreMessage, currentHighScore;
    ImageButton infoButton, scoreButton;
    Button redoButton, homeButton;
    int taps, choice;
    int level = 0;
    databaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);

        Intent intent = getIntent();
        taps = intent.getIntExtra("taps", 0);
        choice = intent.getIntExtra("choice", 0);
        level = intent.getIntExtra("level", 0);

        totalScore = findViewById(R.id.totalScore);
        scoreMessage = findViewById(R.id.scoreMessage);
        currentHighScore = findViewById(R.id.currentHighScore);
        totalScore.setText(String.valueOf(taps));

        infoButton = findViewById(R.id.infoButton);
        redoButton = findViewById(R.id.redobutton);
        homeButton = findViewById(R.id.homebutton);
        scoreButton = findViewById(R.id.scoreButton);
        db = new databaseHelper(this);

        int highestScore = db.getHighScore(level);

        if (taps > highestScore) {
            scoreMessage.setText("New High Score!");
            currentHighScore.setText("");
        } else {
            scoreMessage.setText("Nice Try!");
            currentHighScore.setText("Current High Score: " + String.valueOf(highestScore));
        }
        db.insertScore(level, taps);

        infoButton.setOnClickListener(view -> showDialog());

        redoButton.setOnClickListener(view -> redoSelection(choice));

        homeButton.setOnClickListener(view -> homeSelection());

        scoreButton.setOnClickListener(view -> showHighScores());
    }

    private void homeSelection() {
        Intent intent = new Intent(endGame.this, gameSelection.class);
        startActivity(intent);
    }

    private void redoSelection(int choice) {
        Intent intent = new Intent(endGame.this, gamePlay.class);
        intent.putExtra("choice", choice);
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

        // Adjust dialog size dynamically based on screen size
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.85),  // 85% of screen width
                    (int) (getResources().getDisplayMetrics().heightPixels * 0.6)   // 60% of screen height
            );
        }

        // Update high scores based on the current level
        updateHighScores(adapter, level);
    }

    private void setupLevelButtons(View view, final ScoreAdapter adapter) {
        view.findViewById(R.id.level1Button).setOnClickListener(v -> updateHighScores(adapter, 1));
        view.findViewById(R.id.level2Button).setOnClickListener(v -> updateHighScores(adapter, 2));
        view.findViewById(R.id.level3Button).setOnClickListener(v -> updateHighScores(adapter, 3));
        view.findViewById(R.id.level4Button).setOnClickListener(v -> updateHighScores(adapter, 4));
    }

    private void updateHighScores(ScoreAdapter adapter, int level) {
        // Get the high score for the selected level
        List<Integer> highScores = new ArrayList<>();
        highScores.add(db.getHighScore(level));  // Assume this is how you get high scores for the levels

        // Update the RecyclerView with the new scores for the selected level
        adapter.updateScores(highScores);
    }
}
