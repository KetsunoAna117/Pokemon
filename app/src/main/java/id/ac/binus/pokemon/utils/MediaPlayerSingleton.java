package id.ac.binus.pokemon.utils;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerSingleton {
    private static MediaPlayerSingleton instance;
    private MediaPlayer music;
    private int currentMusicResourceId;

    private MediaPlayerSingleton(){
        music = new MediaPlayer();
    }

    public static MediaPlayerSingleton getInstance() {
        if (instance == null) {
            synchronized (MediaPlayerSingleton.class){
                if(instance == null){
                    instance = new MediaPlayerSingleton();
                }
            }
        }
        return instance;
    }
    public void initializeMediaPlayer(Context context, int resourceId) {
        if (music == null) {
            music = MediaPlayer.create(context, resourceId);
            music.setLooping(true); // Set looping if you want continuous playback
            currentMusicResourceId = resourceId;
        }
    }

    public void startMediaPlayer() {
        if (music != null && !music.isPlaying()) {
            music.start();
        }
    }

    public void stopMediaPlayer() {
        if (music != null && music.isPlaying()) {
            music.pause();
            music.seekTo(0);
        }
    }

    public void releaseMediaPlayer() {
        if (music != null) {
            music.release();
            music = null;
        }
    }

    public void changeMediaPlayerSource(Context context, int newMusicId) {
        stopMediaPlayer(); // Stop current playback
        releaseMediaPlayer(); // Release current resources

        initializeMediaPlayer(context, newMusicId); // Initialize with new source
        startMediaPlayer(); // Start playing the new source
        currentMusicResourceId = newMusicId;
    }

    public void setMediaPlayerVolume(float volume) {
        if (music != null) {
            music.setVolume(volume, volume);
        }
    }

    // function to get music playing now
    public int getCurrentMusic() {
        if (music != null) {
            return this.currentMusicResourceId;
        }
        return -1;

    }
}
