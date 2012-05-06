package com.chalmers.speedtype.util;

import com.chalmers.speedtype.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackgroundSoundService extends Service {
	
    private static final String BackgroundSoundService = null;
    MediaPlayer player;
    public IBinder onBind(Intent arg0) {

        return null;
    }
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.menu_music);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);

    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return 1;
    }

    public void onStart(Intent intent, int startId) {
        player.start();
    }
    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {
    	
    }
    public void onPause() {
    	player.pause();
    }
    public void onDestroy() {
        player.stop();
        player.release();
    }

    public void onLowMemory() {

    }
}