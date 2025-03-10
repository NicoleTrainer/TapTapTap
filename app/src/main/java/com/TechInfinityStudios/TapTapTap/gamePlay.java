package com.TechInfinityStudios.TapTapTap;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class gamePlay extends AppCompatActivity {

    int choice = 0;
    CountDownTimer countDownTimer;
    private View main;
    int taps=0;
    boolean isDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        TextView timer = findViewById(R.id.timeRemaining);
        TextView tapCount = findViewById(R.id.tapcount);
        main = findViewById(R.id.actgame);


        Intent intent = getIntent();
        choice = intent.getIntExtra("choice", 0);
        timer(choice, timer);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isDone) {
                    taps++;
                    tapCount.setText("Taps: " + taps);
                }
            }
        });


    }

    public  void timer(int choice, TextView timer){

        long time = choice * 1000L;



        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long l) {
                long seconds = (long) Math.ceil(l / 1000.0); // Ensures 1 extra second if any ms remain
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
        Intent intent = new Intent(gamePlay.this, endGame.class );
        intent.putExtra("taps", taps);
        intent.putExtra("choice", choice);
        startActivity(intent);
    }


    }

