package com.example.D2D;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static android.media.AudioTrack.getMinBufferSize;

public class Task1 extends AppCompatActivity {
    AudioRecord audioRecord;
    TextView txtTask1;
    int bufferSize,frequency,readLength,maxFrequency;
    boolean runFlag;
    byte[] data;
    double max,magnitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task1);
        txtTask1 = findViewById(R.id.txtTask1);
        bufferSize = 44100;
//        bufferSize = getMinBufferSize(44100,
//                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_8BIT);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 44100,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_8BIT, bufferSize);
        if(audioRecord != null)
            audioRecord.startRecording();
        new Thread(new Runnable() {
            @Override
            public void run(){
                runFlag = true;
                data = new byte[bufferSize];
                while(runFlag){
                    readLength = audioRecord.read(data, 0, bufferSize);
                    max = frequency = 0;
                    for(int i=0;i<maxFrequency;i++){
                        magnitude = Goertzel.detection(readLength,i,44100,data);
                        if(max<magnitude){
                            max=magnitude;
                            frequency=i;
                        }
                    }
                }
                audioRecord.stop();
                audioRecord.release();
                audioRecord=null;
            }
        }).start();
        txtTask1.setText(frequency+"Hz");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        runFlag=false;
    }
}
