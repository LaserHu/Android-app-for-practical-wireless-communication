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

public class Task2 extends AppCompatActivity implements SensorEventListener {
    TextView txt1, txt2;
    SensorManager sensorManager;
    Sensor sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        DecimalFormat df = new DecimalFormat("0.00");
        double x = event.values[0];
        double y = event.values[1];
        double z = event.values[2];
        txt1.setText("Acceleration Force including gravity" + "\n" +
                "x: " + df.format(x) + "\n" +
                "y: " + df.format(y) + "\n" +
                "z: " + df.format(z) + "\n");

        double alpha = (float) 0.8;
        double[] gravity = {9.8,9.8,9.8};
        double[] linear_acceleration = {0,0,0};

        gravity[0] = alpha * gravity[0] + (1-alpha) * x;
        gravity[1] = alpha * gravity[1] + (1-alpha) * y;
        gravity[2] = alpha * gravity[2] + (1-alpha) * z;

        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];
        txt2.setText("Acceleration Force without gravity" + "\n" +
                "x: " + df.format(linear_acceleration[0]) + "\n" +
                "y: " + df.format(linear_acceleration[1]) + "\n" +
                "z: " + df.format(linear_acceleration[2]) + "\n");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
