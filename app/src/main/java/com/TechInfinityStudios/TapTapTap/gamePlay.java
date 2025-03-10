package com.TechInfinityStudios.TapTapTap;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class gamePlay extends AppCompatActivity {

    int choice = 0;
    CountDownTimer countDownTimer, gameStartTimer;
    private View main;
    int taps = 0;
    boolean isDone = false;
    boolean gameStarted = false;
    TextView countdown, timer, tapCount, startingtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

         countdown = findViewById(R.id.countdown);
         timer = findViewById(R.id.timeRemaining);
         tapCount = findViewById(R.id.tapcount);
         main = findViewById(R.id.actgame);
         startingtext = findViewById(R.id.startingText);

        Intent intent = getIntent();
        choice = intent.getIntExtra("choice", 0);

        // Start pre-game countdown first
        startPreGameCountdown(countdown, timer);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isDone && gameStarted) { // Only count taps after countdown ends
                    taps++;
                    tapCount.setText("Taps: " + taps);
                }
            }
        });
    }

    private void startPreGameCountdown(TextView countdown, TextView timer) {
        gameStartTimer = new CountDownTimer(4000, 1000) { // 3..2..1..Go!
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) (millisUntilFinished / 1000);
                if (secondsLeft > 0) {
                    countdown.setText(String.valueOf(secondsLeft));
                } else {
                    countdown.setText("Go!");
                }
            }

            @Override
            public void onFinish() {
                countdown.setVisibility(View.GONE);
                startingtext.setVisibility(View.GONE);
                timer.setVisibility(View.VISIBLE);
                tapCount.setVisibility(View.VISIBLE);
                gameStarted = true;
                startGameTimer(choice, timer);
            }
        };
        gameStartTimer.start();
    }

    private void startGameTimer(int choice, TextView timer) {
        long time = choice * 1000L;

        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = (long) Math.ceil(millisUntilFinished / 1000.0);
                timer.setText(String.valueOf(seconds));
            }

            @Override
            public void onFinish() {
                main.setClickable(false);
                isDone = true;
                startNewActivity();
            }
        };

        countDownTimer.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onBackPressed();
    }

    private void startNewActivity() {
        Intent intent = new Intent(gamePlay.this, endGame.class);
        intent.putExtra("taps", taps);
        intent.putExtra("choice", choice);
        startActivity(intent);
    }
}
