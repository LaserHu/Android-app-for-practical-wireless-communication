package com.example.D2D;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Task0 extends AppCompatActivity  {
    Button btnStart, btnStop;
    EditText editFreq;
    AudioTrack audioTrack;
    TextView txt1;
    byte[] wave;
    int bufferSize, frequency;
    boolean runFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task0);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnStop.setEnabled(false);
        editFreq = findViewById(R.id.editFreq);

        txt1 = findViewById(R.id.txt1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                runFlag=true;
                while (runFlag){
                    if(audioTrack!=null&&audioTrack.getPlayState()==AudioTrack.PLAYSTATE_PLAYING){
                        audioTrack.write(wave, 0, wave.length);
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                audioTrack.stop();
                audioTrack.release();
                audioTrack=null;
            }
        }).start();

        btnStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                frequency = Integer.parseInt(editFreq.getText().toString());
                txt1.setText("The frequency is "+frequency+"Hz");
                if (audioTrack == null){
                    if (frequency > 0){
                        int waveLength = 44100/frequency;
                        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
                                AudioFormat.CHANNEL_OUT_MONO,
                                AudioFormat.ENCODING_PCM_8BIT,
                                waveLength,AudioTrack.MODE_STREAM);
                        wave=SinWave.generateSinWave(frequency,44100);
                        audioTrack.play();
                        btnStart.setEnabled(false);
                        btnStop.setEnabled(true);
                    }
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioTrack.stop();
                audioTrack.release();
                audioTrack=null;
                txt1.setText("Please input the frequency: ");
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
            }
        });
    }
}
