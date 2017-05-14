package com.example.example.educationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    GameData _gameData;
    SoundManager _soundManager;
    int buttonPressSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prepareGameData();
        _soundManager = new SoundManager(this);
        buttonPressSound = _soundManager.addSound(R.raw.button_pressed);
    }

    public void startGame(View view){
        //method launches the game when the start button is pressed
        _soundManager.play(buttonPressSound);
        Intent gameViewIntent = new Intent(this, GameActivity.class);
        gameViewIntent.putExtra("_gameData", _gameData);
        startActivity(gameViewIntent);

    }

    public void quitGame(View view){
        //method closes app and saves data when the quit button is pressed
        _soundManager.play(buttonPressSound);
        finishAffinity();
        System.exit(0);

    }

    public void prepareGameData(){
        if (_gameData.settingsChanged){
            try {
                _gameData = (GameData) getIntent().getExtras().getSerializable("_itemsList");
            } catch (Exception ignored) {
            }
        }else {
            _gameData = new GameData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            _soundManager.play(buttonPressSound);
            Intent settingViewIntent = new Intent(this, SettingsActivity.class);
            settingViewIntent.putExtra("_gameData", _gameData);
            startActivity(settingViewIntent);
            return true;
        }else if (id == R.id.action_highScore){
            _soundManager.play(buttonPressSound);
            Intent highScoreViewIntent = new Intent(this, HighScoreActivity.class);
            highScoreViewIntent.putExtra("_gameData", _gameData);
            startActivity(highScoreViewIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
