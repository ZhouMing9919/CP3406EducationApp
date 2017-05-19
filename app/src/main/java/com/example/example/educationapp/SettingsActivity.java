package com.example.example.educationapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private TextView currentDifficulty;
    private Button swapDifficultyEasy;
    private Button swapDifficultyHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        currentDifficulty = (TextView) findViewById(R.id.currentDifficulty);
        swapDifficultyEasy = (Button) findViewById(R.id.swapDifficultyEasy);
        swapDifficultyHard = (Button) findViewById(R.id.swapDifficultyHard);
        currentDifficulty.setText("Current Difficulty: " + sharedPreferences.getString("currentGameDifficulty", "Easy"));

    }

    public void setHardDifficulty(View view) {
        currentDifficulty.setText("Current Difficulty: Hard");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("clockTime", 10000).apply();
        editor.putLong("clockReward", 1000).apply();
        editor.putLong("clockPenalty", 3000).apply();
        editor.putString("currentGameDifficulty", "Hard").apply();
    }

    public void setEasyDifficulty(View view) {
        currentDifficulty.setText(R.string.default_difficulty);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("clockTime", 20000).apply();
        editor.putLong("clockReward", 3000).apply();
        editor.putLong("clockPenalty", 2000).apply();
        editor.putString("currentGameDifficulty", "Easy").apply();
    }

}
