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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

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
    private TextView gameOutput;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    int randomIndex;
    public CountDownTimer countDownTimer;
    int buttonPressSound, correctSound, incorrectSound;
    float acceleratorValue, acceleratorLast, shakeAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        timeField = (TextView) findViewById(R.id.timeField);
        playerScoreField = (TextView) findViewById(R.id.playerScoreField);
        shakeNotice = (TextView) findViewById(R.id.shakeNotice);
        elementName = (TextView) findViewById(R.id.elementName);
        answerInput = (EditText) findViewById(R.id.answerInput);
        gameOutput = (TextView) findViewById(R.id.gameOutput);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeNotice.setTextColor(Color.GRAY);
        playerScoreField.setText("Score: " + playerScore);
        createTimer();
        getElementName();
        acceleratorValue = SensorManager.GRAVITY_EARTH;
        acceleratorLast = SensorManager.GRAVITY_EARTH;
        shakeAmount = 0.00f;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        acceleratorLast = acceleratorValue;
        acceleratorValue = (float) Math.sqrt((double) (x * x + y * y + z * z));
        float delta = acceleratorValue - acceleratorLast;
        shakeAmount = shakeAmount * 0.9f + delta;

        if (answerInput.getText().toString().isEmpty()) {
            shakeNotice.setTextColor(Color.GRAY);
        } else {
            shakeNotice.setTextColor(Color.RED);
            if (shakeAmount > 4) {
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

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
        mSensorManager.unregisterListener(this);
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
        //this is the method that selects the ellement pairs for use in the game
        randomIndex = 0;
        Random generator = new Random();
        randomIndex = generator.nextInt(118);
        elementName.setText("Your element: " + _gameData.elementNamesList.get(randomIndex));
        System.out.println(randomIndex);
    }

    public void createTimer() {
        //this method is for creating and starting the timer
        countDownTimer = new CountDownTimer(clockTime, 1000) {
            public void onTick(long millisUntilFinished) {
                timeField.setText("Seconds Remaining: " + millisUntilFinished / 1000);
                if (millisUntilFinished < 5000) {
                    timeField.setTextColor(Color.RED);
                }
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
        //this method is used for updating and restarting the timer when time is added to it
        countDownTimer.cancel();
        countDownTimer = new CountDownTimer(clockTime, 1000) {
            public void onTick(long millisUntilFinished) {
                timeField.setText("Seconds Remaining: " + millisUntilFinished / 1000);
                if (millisUntilFinished < 5000) {
                    timeField.setTextColor(Color.RED);
                }
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
        //this method is used to check the players answer to the question
        System.out.println(randomIndex);
        if (answerInput.getText().toString().trim().equals(_gameData.elementSymbolsList.get(randomIndex))) {
            _soundManager.play(correctSound);
            gameOutput.setText("Correct!");
            gameOutput.setTextColor(Color.GREEN);
            answerInput.getText().clear();
            ++playerScore;
            playerScoreField.setText("Score: " + playerScore);
            clockTime = clockTime + clockReward;
            updateTimer();
            getElementName();
        } else {
            _soundManager.play(incorrectSound);
            gameOutput.setText("Incorrect \n The correct answer was: " + _gameData.elementSymbolsList.get(randomIndex));
            gameOutput.setTextColor(Color.RED);
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
}
