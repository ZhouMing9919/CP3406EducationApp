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
        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        currentDifficulty = (TextView) findViewById(R.id.currentDifficulty);
        swapDifficultyEasy = (Button) findViewById(R.id.swapDifficultyEasy);
        swapDifficultyHard = (Button) findViewById(R.id.swapDifficultyHard);

    }

    public void setHardDifficulty(View view){
        currentDifficulty.setText("Current Difficulty: Hard");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("clockTime", 10000).apply();
        editor.putString("currentGameDifficulty", "Hard");
    }

    public void setEasyDifficulty(View view){
        currentDifficulty.setText(R.string.default_difficulty);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("clockTime", 20000).apply();
        editor.putString("currentGameDifficulty", "Easy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_home) {
            Intent homeViewIntent = new Intent(this, MainActivity.class);
            startActivity(homeViewIntent);
            return true;
        }else if (id == R.id.action_highScore){
            Intent highScoreViewIntent = new Intent(this, HighScoreActivity.class);
            startActivity(highScoreViewIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
