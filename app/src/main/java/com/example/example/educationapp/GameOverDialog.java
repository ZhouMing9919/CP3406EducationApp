package com.example.example.educationapp;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


class GameOverDialog extends Dialog {
    private SoundManager _soundManager;
    private SharedPreferences sharedPreferences;
    private ScoresDAOHelper scoresDAO;
    private Context context;
    private TextView gameOverMessage;
    private EditText gameOverNameInput;
    private Button gameOverInputButton;
    private Button cancelNameDialog;
    private int playerScore;
    private String currentGameDifficulty;
    private int buttonPressSound;

    GameOverDialog(Context context) {
        super(context);
        this.context = context;
        this.setCancelable(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_game_over);
        gameOverMessage = (TextView) findViewById(R.id.gameOverMessage);
        gameOverNameInput = (EditText) findViewById(R.id.gameOverNameInput);
        gameOverInputButton = (Button) findViewById(R.id.gameOverInputButton);
        cancelNameDialog = (Button) findViewById(R.id.cancelNameDialog);
        sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        playerScore = sharedPreferences.getInt("playerScore", 0);
        currentGameDifficulty = sharedPreferences.getString("currentGameDifficulty", "Easy");
        scoresDAO = new ScoresDAOHelper(context);
        _soundManager = new SoundManager(context);
        buttonPressSound = _soundManager.addSound(R.raw.button_pressed);
        setGameOverText();
        updateScore();

        gameOverInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gameOverNameInput.getText().toString().equals(" ") && !gameOverNameInput.getText().toString().isEmpty()) {
                    savePlayerInput();
                }
            }
        });

        cancelNameDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelPlayerInput();
            }
        });


    }

    private void setGameOverText() {
        //this method determines the type of text that will be displayed based on the players score
        if (playerScore == 0) {
            gameOverMessage.setText("Try harder next time");
        } else if (playerScore <= 5) {
            gameOverMessage.setText("Nice effort but you can do better");
        } else if (playerScore <= 10) {
            gameOverMessage.setText("Good job");
        } else if (playerScore > 10 && currentGameDifficulty.equals("Easy")) {
            gameOverMessage.setText("Very nice. How about trying hard mode now for a greater challenge?");
        } else if (playerScore > 10) {
            gameOverMessage.setText("Wow! you know your elements");
        }
    }

    private void savePlayerInput() {
        //this method saves the players name when the button is pressed and adds the data to the database
        _soundManager.play(buttonPressSound);
        SQLiteDatabase db = scoresDAO.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_playerName", gameOverNameInput.getText().toString());
        contentValues.put("_playerScore", playerScore);
        contentValues.put("_playerDifficulty", currentGameDifficulty);
        db.insert("_id", null, contentValues);
        updateScore();
        this.dismiss();
        ((GameActivity) context).finish();
    }

    private void cancelPlayerInput() {
        //this method cancels the player name input
        _soundManager.play(buttonPressSound);
        this.dismiss();
        ((GameActivity) context).finish();
    }

    private void updateScore() {
        //this method updates the database
        Cursor cursor = scoresDAO.getReadableDatabase().rawQuery("select * from _id", null);
        StringBuilder builder = new StringBuilder();
        builder.append("_id: ");
        while (cursor.moveToNext()) {
            builder.append(cursor.getString(1)).append(" ");
        }
        cursor.close();

    }

}
