package com.example.example.educationapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class GameOverDialog extends Dialog {
    private TextView gameOverTitle;
    private TextView gameOverMessage;
    private EditText gameOverNameInput;
    private Button gameOverInputButton;

    public GameOverDialog(Context context, myOnClickListener myclick) {
        super(context);
        this.myListener = myclick;
    }

    public myOnClickListener myListener;

    public interface myOnClickListener {
        void onButtonClick();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_game_over);
        gameOverTitle = (TextView) findViewById(R.id.gameOverTitle);
        gameOverMessage = (TextView) findViewById(R.id.gameOverMessage);
        gameOverNameInput = (EditText) findViewById(R.id.gameOverNameInput);
        gameOverInputButton = (Button) findViewById(R.id.gameOverInputButton);



    }

}
