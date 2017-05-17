package com.example.example.educationapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity {
    private ScoresDAOHelper scoresDAO;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> scoresArrayList;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scoresDAO = new ScoresDAOHelper(this);
        scoresArrayList = new ArrayList<String>();
        updateScore();
        populateListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_high_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_home) {
            Intent homeViewIntent = new Intent(this, MainActivity.class);
            startActivity(homeViewIntent);
            return true;
        } else if (id == R.id.action_settings) {
            Intent settingsViewIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsViewIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void populateListView() {
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.players_data, scoresArrayList);
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
            scoresArrayList.add("Player Name: " + cursor.getString(1)+ " Player Score: " + cursor.getString(2) + " Difficulty Completed: " + cursor.getString(3));
        }
        cursor.close();

    }

}
