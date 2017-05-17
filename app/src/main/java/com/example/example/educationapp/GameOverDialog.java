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

import java.util.ArrayList;


public class GameOverDialog extends Dialog {
    SharedPreferences sharedPreferences;
    private ScoresDAOHelper scoresDAO;
    private Context context;
    private TextView gameOverTitle;
    private TextView gameOverMessage;
    private EditText gameOverNameInput;
    private Button gameOverInputButton;
    int playerScore;
    String currentGameDifficulty;

    public GameOverDialog(Context context) {
        super(context);
        this.context = context;
        this.setCancelable(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_game_over);
        gameOverTitle = (TextView) findViewById(R.id.gameOverTitle);
        gameOverMessage = (TextView) findViewById(R.id.gameOverMessage);
        gameOverNameInput = (EditText) findViewById(R.id.gameOverNameInput);
        gameOverInputButton = (Button) findViewById(R.id.gameOverInputButton);
        sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        playerScore = sharedPreferences.getInt("playerScore", 0);
        currentGameDifficulty = sharedPreferences.getString("currentGameDifficulty", "Easy");
        scoresDAO = new ScoresDAOHelper(context);
        setGameOverText();
        updateScore();
        gameOverInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePlayerInput();
            }
        });
    }

    private void setGameOverText() {
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
        SQLiteDatabase db = scoresDAO.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_playerName", gameOverNameInput.getText().toString());
        contentValues.put("_playerScore", playerScore);
        contentValues.put("_playerDifficulty", currentGameDifficulty);
        db.insert("_id", null, contentValues);
        updateScore();
        this.dismiss();
    }

    private void updateScore() {
        Cursor cursor = scoresDAO.getReadableDatabase().rawQuery("select * from _id", null);
        StringBuilder builder = new StringBuilder();
        builder.append("_id: ");
        while (cursor.moveToNext()) {
            //System.out.println("Player Name: " + cursor.getString(1) + " Player Score: " + cursor.getString(2) + " Difficulty Completed: " + cursor.getString(3));
            builder.append(cursor.getString(1)).append(" ");
        }
        cursor.close();

    }

}
