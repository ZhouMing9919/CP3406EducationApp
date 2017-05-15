package com.example.example.educationapp;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class GameOverDialog extends Dialog {
    SharedPreferences sharedPreferences;
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
        setGameOverText();
        gameOverInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePlayerInput();
            }
        });
    }

    private void setGameOverText(){
        if (playerScore == 0){
            gameOverMessage.setText("Try harder next time");
        } else if (playerScore <= 5){
            gameOverMessage.setText("Nice effort but you can do better");
        }else if (playerScore <= 10){
            gameOverMessage.setText("Good job");
        }else if (playerScore > 10 && currentGameDifficulty.equals("Easy")){
            gameOverMessage.setText("Very nice. How about trying hard mode now for a greater challenge?");
        }else if (playerScore > 10){
            gameOverMessage.setText("Wow! you know your elements");
        }
    }

    private void savePlayerInput(){
        System.out.println(gameOverNameInput.getText());
        this.dismiss();
    }

}
