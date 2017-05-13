package com.example.example.educationapp;
//SONG LINKS
//button press: https://www.freesound.org/people/Bertrof/sounds/131660/
//correct sound: https://www.freesound.org/people/Bertrof/sounds/351564/
//incorrect sound: https://www.freesound.org/people/Bertrof/sounds/351563/

import android.content.Context;
import android.media.SoundPool;

public class SoundManager {
    private SoundPool pool;
    private Context context;

    public SoundManager(Context context) {
        this.context = context;
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(10);
        pool = builder.build();
    }

    public int addSound(int rescourceID) {
        return pool.load(context, rescourceID, 1);
    }

    public void play(int soundID) {
        pool.play(soundID, 1, 1, 1, 0, 1);
    }

    public void loop(int soundID) {
        pool.play(soundID, 1, 1, 1, 1, 1);
    }
}
