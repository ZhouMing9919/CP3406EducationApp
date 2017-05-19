package com.example.example.educationapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    SoundManager _soundManager;
    int buttonPressSound;
    SharedPreferences sharedPreferences;
    private TextView currentDifficulty;

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
        currentDifficulty.setText("Current Difficulty: " + sharedPreferences.getString("currentGameDifficulty", "Easy"));
        _soundManager = new SoundManager(this);
        buttonPressSound = _soundManager.addSound(R.raw.button_pressed);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void setHardDifficulty(View view) {
        //this method sets the difficulty shared prefrences to hard
        _soundManager.play(buttonPressSound);
        currentDifficulty.setText("Current Difficulty: Hard");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("clockTime", 10000).apply();
        editor.putLong("clockReward", 1000).apply();
        editor.putLong("clockPenalty", 3000).apply();
        editor.putString("currentGameDifficulty", "Hard").apply();
    }

    public void setEasyDifficulty(View view) {
        //this method sets the difficulty shared prefrences to easy
        _soundManager.play(buttonPressSound);
        currentDifficulty.setText(R.string.default_difficulty);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("clockTime", 20000).apply();
        editor.putLong("clockReward", 3000).apply();
        editor.putLong("clockPenalty", 2000).apply();
        editor.putString("currentGameDifficulty", "Easy").apply();
    }

}
