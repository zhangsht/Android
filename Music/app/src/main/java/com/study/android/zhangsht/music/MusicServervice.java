package com.study.android.zhangsht.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

/**
 * Created by zhangsht on 2016/11/2.
 */

public class MusicServervice extends Service{
    public final IBinder binder = new MyBinder();
    public static MediaPlayer mp = new MediaPlayer();
    @Override
    public void onCreate() {
        super.onCreate();
    }
//    public MusicServervice() {
//        try {
//            mp.setDataSource("/data/K.Will-Melt.mp3");
//            mp.prepare();
//            mp.setLooping(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class MyBinder extends Binder {
        MusicServervice getService() {
            return MusicServervice.this;
        }
    }

    public void play() {
        try {
            mp.setDataSource("/data/K.Will-Melt.mp3");
            mp.prepare();
            mp.setLooping(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!mp.isPlaying()) {
            mp.start();
        }
    }

    public void pause() {
        if (mp != null && mp.isPlaying()) {
            mp.pause();
        }
    }

    public void stop() {
        if (mp != null) {
            mp.stop();
            try {
                mp.prepare();
                mp.seekTo(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
