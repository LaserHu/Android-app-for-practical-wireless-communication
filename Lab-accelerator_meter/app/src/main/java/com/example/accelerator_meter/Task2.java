package com.example.accelerator_meter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;
import static android.hardware.Sensor.TYPE_LINEAR_ACCELERATION;

public class Task2 extends AppCompatActivity implements SensorEventListener {
    TextView txt1, txt2;
    SensorManager sensorManager, sensorManager2;
    Sensor sensor, sensor2;
    String string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager2 = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(TYPE_ACCELEROMETER);
        sensor2 = sensorManager2.getDefaultSensor(TYPE_LINEAR_ACCELERATION);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mSensor = sensorEvent.sensor;
        DecimalFormat df = new DecimalFormat("0.00");
        if (mSensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            string = "Acceleration force including gravity: " + "\n";
            string += "X: " + df.format(x) + "\n";
            string += "Y: " + df.format(y) + "\n";
            string += "Z: " + df.format(z) + "\n";
            txt1.setText(string);
        }
        if (mSensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            double alpha = 0.8;
            float[] gravity = new float[3];
            gravity[0] = (float) (alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0]);
            gravity[1] = (float) (alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1]);
            gravity[2] = (float) (alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2]);
            string = "Acceleration force including gravity: " + "\n";
            string += "X: " + df.format(sensorEvent.values[0] - gravity[0]) + "\n";
            string += "Y: " + df.format(sensorEvent.values[1] - gravity[1]) + "\n";
            string += "Z: " + df.format(sensorEvent.values[2] - gravity[2]) + "\n";
            txt2.setText(string);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this,sensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensor2, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
