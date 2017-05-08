package com.example.example.educationapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    private TextView timeField;
    long clockTime = 10000;
    public CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        timeField = (TextView) findViewById(R.id.timeField);
        createTimer();
    }

    public void createTimer() {
        countDownTimer = new CountDownTimer(clockTime, 1000) {
            public void onTick(long millisUntilFinished) {
                timeField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timeField.setText("done!");
            }
        }.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_home) {
            Intent homeViewIntent = new Intent(this, MainActivity.class);
            startActivity(homeViewIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
