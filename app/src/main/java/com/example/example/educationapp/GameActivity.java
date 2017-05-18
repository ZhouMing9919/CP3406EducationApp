package com.example.example.educationapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements SensorEventListener {
    GameData _gameData;
    SoundManager _soundManager;
    GameOverDialog _gameOverDialog;
    SharedPreferences sharedPreferences;
    int playerScore = 0;
    long clockTime, clockReward, clockPenalty;
    private TextView timeField;
    private TextView playerScoreField;
    private TextView shakeNotice;
    private TextView elementName;
    private EditText answerInput;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    int randomIndex;
    public CountDownTimer countDownTimer;
    int buttonPressSound, correctSound, incorrectSound;
    float aceValue, aceLast, shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        _gameData = new GameData();
        _soundManager = new SoundManager(this);
        _gameOverDialog = new GameOverDialog(this);
        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        clockTime = sharedPreferences.getLong("clockTime", 20000);
        clockReward = sharedPreferences.getLong("clockReward", 3000);
        clockPenalty = sharedPreferences.getLong("clockPenalty", 2000);
        buttonPressSound = _soundManager.addSound(R.raw.button_pressed);
        correctSound = _soundManager.addSound(R.raw.correct_answer);
        incorrectSound = _soundManager.addSound(R.raw.incorrect_answer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        timeField = (TextView) findViewById(R.id.timeField);
        playerScoreField = (TextView) findViewById(R.id.playerScoreField);
        shakeNotice = (TextView) findViewById(R.id.shakeNotice);
        elementName = (TextView) findViewById(R.id.elementName);
        answerInput = (EditText) findViewById(R.id.answerInput);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeNotice.setTextColor(Color.GRAY);
        playerScoreField.setText("Score: " + playerScore);
        createTimer();
        getElementName();
        aceValue = SensorManager.GRAVITY_EARTH;
        aceLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        aceLast = aceValue;
        aceValue = (float) Math.sqrt((double) (x * x + y * y + z * z));
        float delta = aceValue - aceLast;
        shake = shake * 0.9f + delta;

        if (answerInput.getText().toString().isEmpty()) {
            shakeNotice.setTextColor(Color.GRAY);
        } else {
            shakeNotice.setTextColor(Color.RED);
            if (shake > 4) {
                checkAnswer();
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        clockTime = sharedPreferences.getLong("clockTime", 20000);
        playerScore = 0;
        playerScoreField.setText("Score: " + playerScore);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
        mSensorManager.unregisterListener(this);
        saveGameInfo();
    }

    protected void onResume() {
        super.onResume();
        playerScore = 0;
        playerScoreField.setText("Score: " + playerScore);
        countDownTimer.start();
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
                clockTime = millisUntilFinished;
            }

            public void onFinish() {
                saveGameInfo();
                _gameOverDialog.show();
                timeField.setText("done!");
            }


        }.start();

    }

    public void updateTimer() {
        countDownTimer.cancel();
        countDownTimer = new CountDownTimer(clockTime, 1000) {
            public void onTick(long millisUntilFinished) {
                timeField.setText("seconds remaining: " + millisUntilFinished / 1000);
                clockTime = millisUntilFinished;
            }

            public void onFinish() {
                saveGameInfo();
                _gameOverDialog.show();
                timeField.setText("done!");
            }
        }.start();
    }

    public void checkAnswer() {
        System.out.println(randomIndex);
        if (answerInput.getText().toString().trim().equals(_gameData.elementSymbolsList.get(randomIndex))) {
            _soundManager.play(correctSound);
            Toast.makeText(GameActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            answerInput.getText().clear();
            ++playerScore;
            playerScoreField.setText("Score: " + playerScore);
            clockTime = clockTime + clockReward;
            updateTimer();
            getElementName();
        } else {
            _soundManager.play(incorrectSound);
            Toast.makeText(GameActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
            Toast.makeText(GameActivity.this, "The correct answer was: " + _gameData.elementSymbolsList.get(randomIndex), Toast.LENGTH_SHORT).show();
            answerInput.getText().clear();
            clockTime = clockTime - clockPenalty;
            updateTimer();
            getElementName();
        }
    }

    public void saveGameInfo() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("playerScore", playerScore).apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
        mSensorManager.unregisterListener(this);
    }
}
