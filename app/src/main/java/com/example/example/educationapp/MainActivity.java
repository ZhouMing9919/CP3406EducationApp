package com.example.example.educationapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    void startGame(View view){
        //method launches the game when the start button is pressed
        Intent gameViewIntent = new Intent(this, GameActivity.class);
        startActivity(gameViewIntent);

    }

    void quitGame(View view){
        //method closes app and saves data when the quit button is pressed
        finishAffinity();
        System.exit(0);

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
            Intent settingViewIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingViewIntent);
            return true;
        }else if (id == R.id.action_highScore){
            Intent highScoreViewIntent = new Intent(this, HighScoreActivity.class);
            startActivity(highScoreViewIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
