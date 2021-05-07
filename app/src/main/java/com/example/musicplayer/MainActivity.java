package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer m;
    AudioManager audioManager;
    TextView timePresent,totalTime;

    public void playMe(View v){
        m.start();
    }

    public void pauseMe(View v){
        m.pause();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m = MediaPlayer.create(this,R.raw.bolo_har_har);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        SeekBar volume = findViewById(R.id.seekBar);
        SeekBar timeline = findViewById(R.id.timeline);

        int maxVolume =  audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volume.setMax(maxVolume);
        volume.setProgress(currentVolume);

        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        //setting timer on volume change by volume button


        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                int changedVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                volume.setProgress(changedVolume);
            }
        },0,1000L);

        timeline.setMax(m.getDuration());
        timePresent =findViewById(R.id.timePresent);
        totalTime = findViewById(R.id.totalTime);
        int mili = m.getDuration();
        int seconds = mili/1000;
        float minutes = (float) seconds/60;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        String min = decimalFormat.format(minutes);
        String arr[] = min.split("\\.");

        totalTime.setText(arr[0] + " : " + arr[1]);

        timeline.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    m.seekTo(progress);
                }
                int interSeconds = progress/1000;

                if(interSeconds > 60 && interSeconds <= 120){
                    int b =1;
                    for(int a=61;a<=120;a++){

                        if(interSeconds == a){
                            interSeconds = b;
                        }
                        b++;
                    }

                }

                if(interSeconds > 120 && interSeconds <= 180){
                    int b =1;
                    for(int a=120;a<=180;a++){

                        if(interSeconds == a){
                            interSeconds = b;
                        }
                        b++;
                    }

                }

                if(interSeconds > 180 && interSeconds <= 240){
                    int b =1;
                    for(int a=180;a<=240;a++){

                        if(interSeconds == a){
                            interSeconds = b;
                        }
                        b++;
                    }

                }
                timePresent.setText(((int)(Math.floor((progress*0.001)/60)))+" : " + interSeconds);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        //timer for timeline

        Timer timelineUpdater = new Timer();
        timelineUpdater.schedule(new TimerTask() {
            @Override
            public void run() {
                timeline.setProgress(m.getCurrentPosition());

            }
        }, 0, 1000l);




    }
}