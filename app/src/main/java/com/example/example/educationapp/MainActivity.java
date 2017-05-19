package com.example.example.educationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity implements View.OnSystemUiVisibilityChangeListener {
    View decorView;
    private ActionBar actionBar;
    private GestureDetectorCompat gestureDetector;
    SoundManager _soundManager;
    int buttonPressSound;

    private static final int FULLSCREEN =
            View.SYSTEM_UI_FLAG_IMMERSIVE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

    private static final int NO_CONTROLS =
            View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        actionBar = getSupportActionBar();
        gestureDetector = new GestureDetectorCompat(this, new GestureHandler());
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(FULLSCREEN);
        decorView.setOnSystemUiVisibilityChangeListener(this);
        _soundManager = new SoundManager(this);
        buttonPressSound = _soundManager.addSound(R.raw.button_pressed);
    }

    public void startGame(View view) {
        //method launches the game when the start button is pressed
        _soundManager.play(buttonPressSound);
        Intent gameViewIntent = new Intent(this, GameActivity.class);
        startActivity(gameViewIntent);

    }

    public void quitGame(View view) {
        //method closes app and saves data when the quit button is pressed
        _soundManager.play(buttonPressSound);
        finishAffinity();
        System.exit(0);

    }

    public void howToPlay(View view) {
        _soundManager.play(buttonPressSound);
        Intent htpViewIntent = new Intent(this, HtpActivity.class);
        startActivity(htpViewIntent);

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
            startActivity(settingViewIntent);
            return true;
        } else if (id == R.id.action_highScore) {
            _soundManager.play(buttonPressSound);
            Intent highScoreViewIntent = new Intent(this, HighScoreActivity.class);
            startActivity(highScoreViewIntent);
            return true;
        } else if (id == R.id.action_ViewTable) {
            _soundManager.play(buttonPressSound);
            Intent ViewTableViewIntent = new Intent(this, ViewTableActivity.class);
            startActivity(ViewTableViewIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

 
    private class GestureHandler extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent e) {
            toggleControls();
        }
    }

    private void toggleControls() {
        int flags = decorView.getSystemUiVisibility();
        flags ^= NO_CONTROLS;
        decorView.setSystemUiVisibility(flags);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onSystemUiVisibilityChange(int visibility) {
        if (actionBar == null) return;
        switch (visibility) {
            case NO_CONTROLS:
                actionBar.hide();
                break;
            default:
                actionBar.show();
        }
    }
}
