package com.example.mrhridoy.musicplayerapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    SeekBar progess;
    Handler handler;
    Runnable runnable;

    public void play(View view){
        super.onResume();
        mediaPlayer.start();


    }
    public void pause(View view){
        super.onPause();
        mediaPlayer.pause();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler=new Handler();

        mediaPlayer = MediaPlayer.create(this,R.raw.tera);
        audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int volumeMax=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int cotrolvol=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);


        SeekBar volcontrol=(SeekBar) findViewById(R.id.songvol);
        volcontrol.setMax(volumeMax);
        volcontrol.setProgress(cotrolvol);


        volcontrol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        ;
        progess=(SeekBar) findViewById(R.id.songProgress);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progess.setMax(mediaPlayer.getDuration());


            }
        });

       /* new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                progess.setProgress(mediaPlayer.getCurrentPosition());


            }
        },0,100);*/

        progess.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){

                    mediaPlayer.seekTo(progress);
                    playCycle();


                }

                }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "Song Turning:",Toast.LENGTH_SHORT).show();


            }
        });


    }
    public void playCycle(){
        progess.setProgress(mediaPlayer.getCurrentPosition());
        if (mediaPlayer.isPlaying()){

            runnable = new Runnable() {
                @Override
                public void run() {
                    playCycle();
                }
            };
            handler.postDelayed(runnable,100);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        handler.removeCallbacks(runnable);


    }
}
