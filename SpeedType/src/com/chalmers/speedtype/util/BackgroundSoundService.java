package com.chalmers.speedtype.util;

import com.chalmers.speedtype.R;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class BackgroundSoundService extends Service {

	private final String MUSIC_VOLUME = "musicVolume";

	SharedPreferences preferences;
	MediaPlayer player;
	private final IBinder binder = new BackgroundSoundBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	public class BackgroundSoundBinder extends Binder {
		public BackgroundSoundService getService() {
			return BackgroundSoundService.this;
		}
	}

	public void onCreate() {
		super.onCreate();
		preferences = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
		player = MediaPlayer.create(this, R.raw.menu_music);
		player.setLooping(true);
		player.setVolume(preferences.getInt(MUSIC_VOLUME, 70),
				preferences.getInt(MUSIC_VOLUME, 70));
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		player.start();
		return 1;
	}

	public void onStart(Intent intent, int startId) {
		player.start();
	}

	public IBinder onUnBind(Intent arg0) {
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

	public void setVolume(int volume) {
		float fVolume = (float) volume / 100;
		player.setVolume(fVolume, fVolume);
	}
}