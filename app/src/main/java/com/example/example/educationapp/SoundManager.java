package com.example.example.educationapp;
//SONG LINKS
//button press: https://www.freesound.org/people/Bertrof/sounds/131660/
//correct sound: https://www.freesound.org/people/Bertrof/sounds/351564/
//incorrect sound: https://www.freesound.org/people/Bertrof/sounds/351563/

import android.content.Context;
import android.media.SoundPool;

class SoundManager {
    private SoundPool pool;
    private Context context;

    SoundManager(Context context) {
        this.context = context;
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(10);
        pool = builder.build();
    }

    int addSound(int rescourceID) {
        return pool.load(context, rescourceID, 1);
    }

    void play(int soundID) {
        pool.play(soundID, 1, 1, 1, 0, 1);
    }
}
