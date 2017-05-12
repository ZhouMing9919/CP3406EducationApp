package com.example.example.educationapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements SensorEventListener {
    GameData _gameData;
    private TextView timeField;
    private TextView shakeNotice;
    private TextView elementName;
    private EditText answerInput;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    int randomIndex;
    long clockTime = 10000;
    public CountDownTimer countDownTimer;
    float roundX, roundY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        _gameData = new GameData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        timeField = (TextView) findViewById(R.id.timeField);
        shakeNotice = (TextView) findViewById(R.id.shakeNotice);
        elementName = (TextView) findViewById(R.id.elementName);
        answerInput = (EditText) findViewById(R.id.answerInput);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeNotice.setTextColor(Color.GRAY);
        createTimer();
        getElementName();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            roundX = Math.round(x);
            roundY = Math.round(y);

            if (answerInput.getText().toString().isEmpty()) {
                shakeNotice.setTextColor(Color.GRAY);
            }else {
                shakeNotice.setTextColor(Color.RED);
                if (roundY > 4){
                    checkAnswer();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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

    @Override
    protected void onStart() {
        super.onStart();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void getElementName() {
        randomIndex = 0;
        Random generator = new Random();
        randomIndex = generator.nextInt(7);
        elementName.setText(_gameData.elementNamesList.get(randomIndex));
        System.out.println(randomIndex);
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

    public void checkAnswer() {
        System.out.println(randomIndex);
        if (answerInput.getText().toString().trim().equals(_gameData.elementSymbolsList.get(randomIndex))) {
            Toast.makeText(GameActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            answerInput.getText().clear();
            ++_gameData.playerScore;
            getElementName();
        } else {
            Toast.makeText(GameActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
            answerInput.getText().clear();
            getElementName();
        }
    }

}
