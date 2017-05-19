package com.example.example.educationapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class HighScoreActivity extends AppCompatActivity {
    SoundManager _soundManager;
    int buttonPressSound;
    private ScoresDAOHelper scoresDAO;
    private ArrayAdapter<String> arrayAdapter;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        scoresDAO = new ScoresDAOHelper(this);
        _soundManager = new SoundManager(this);
        buttonPressSound = _soundManager.addSound(R.raw.button_pressed);
        createArrayAdapter();
        updateScore();
        shareScore();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void shareScore() {
        //this method is used to share a players score when the item in the list view is pressed
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _soundManager.play(buttonPressSound);
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = arrayAdapter.getItem(position) + " in Awesome Elements";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });

    }


    public void createArrayAdapter() {
        //this method is used to create the array adapter
        arrayAdapter = new ArrayAdapter<>(this, R.layout.players_data);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
    }

    private void updateScore() {
        //this method adds the database items to the arrayAdapter
        Cursor cursor = scoresDAO.getReadableDatabase().rawQuery("select * from _id", null);
        StringBuilder builder = new StringBuilder();
        builder.append("_id: ");
        while (cursor.moveToNext()) {
            //System.out.println("Player Name: " + cursor.getString(1)+ " Player Score: " + cursor.getString(2) + " Difficulty Completed: " + cursor.getString(3));
            builder.append(cursor.getString(1)).append(" ");
            try {
                arrayAdapter.add(cursor.getString(1) + " got a score of: " + cursor.getString(2) + " while playing on : " + cursor.getString(3) + " mode");
            } catch (Exception ignored) {
            }
        }
        cursor.close();

    }

    public void clearScores(View view) {
        //this method delets all the scores in the databse and updates the arrayAdapter
        _soundManager.play(buttonPressSound);
        SQLiteDatabase db = scoresDAO.getWritableDatabase();
        db.delete("_id", null, null);
        arrayAdapter.notifyDataSetChanged();
        updateScore();
        finish();
    }

}
