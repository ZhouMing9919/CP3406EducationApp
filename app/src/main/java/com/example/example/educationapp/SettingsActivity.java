package com.example.example.educationapp;

import android.content.Intent;
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
    GameData _gameData;
    private TextView currentDifficulty;
    private Button swapDifficultyEasy;
    private Button swapDifficultyHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        _gameData = (GameData) getIntent().getExtras().getSerializable("_gameData");
        currentDifficulty = (TextView) findViewById(R.id.currentDifficulty);
        swapDifficultyEasy = (Button) findViewById(R.id.swapDifficultyEasy);
        swapDifficultyHard = (Button) findViewById(R.id.swapDifficultyHard);

    }

    public void setHardDifficulty(View view){
        currentDifficulty.setText("Current Difficulty: Hard");
        _gameData.clockTime = 10000;
    }

    public void setEasyDifficulty(View view){
        currentDifficulty.setText(R.string.default_difficulty);
        _gameData.clockTime = 20000;
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
            homeViewIntent.putExtra("_gameData", _gameData);
            startActivity(homeViewIntent);
            return true;
        }else if (id == R.id.action_highScore){
            Intent highScoreViewIntent = new Intent(this, HighScoreActivity.class);
            highScoreViewIntent.putExtra("_gameData", _gameData);
            startActivity(highScoreViewIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
