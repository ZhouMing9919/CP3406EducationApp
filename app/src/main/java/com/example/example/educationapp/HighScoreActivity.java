package com.example.example.educationapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class HighScoreActivity extends AppCompatActivity {
    private ScoresDAOHelper scoresDAO;
    private ArrayAdapter<String> arrayAdapter;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scoresDAO = new ScoresDAOHelper(this);
        createArrayAdapter();
        updateScore();
        shareScore();
    }

    private void shareScore() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = arrayAdapter.getItem(position);
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });

    }


    public void createArrayAdapter() {
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.players_data);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
    }

    private void updateScore() {
        Cursor cursor = scoresDAO.getReadableDatabase().rawQuery("select * from _id", null);
        StringBuilder builder = new StringBuilder();
        builder.append("_id: ");
        while (cursor.moveToNext()) {
            //System.out.println("Player Name: " + cursor.getString(1)+ " Player Score: " + cursor.getString(2) + " Difficulty Completed: " + cursor.getString(3));
            builder.append(cursor.getString(1)).append(" ");
            arrayAdapter.add(cursor.getString(1) + " got a score of: " + cursor.getString(2) + " while playing on : " + cursor.getString(3) + " mode");
        }
        cursor.close();

    }
}
