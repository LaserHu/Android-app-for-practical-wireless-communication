package com.example.accelerator_meter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.StrictMath.abs;

public class Task3 extends AppCompatActivity implements SensorEventListener {
    TextView txt3;
    SensorManager sensorManager;
    Sensor sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        txt3 = findViewById(R.id.txt3);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent event){
        double x = event.values[0];
        double y = event.values[1];
        double z = event.values[2];
        if (abs(x-0) <= 1 && abs(y-0) <= 1 && abs(z-9.8) <= 1) {
            txt3.setText("On the table");
        }
        else if (abs(x-0) <= 1 && abs(y-9.8) <= 1 && abs(z-0) <= 1) {
            txt3.setText("Default");
        }
        else if (abs(x-9.8) <= 1 && abs(y-0) <= 1 && abs(z-0) <= 1) {
            txt3.setText("Left");
        }
        else if (abs(x+9.8) <= 1 && abs(y-0) <= 1 && abs(z-0) <= 1) {
            txt3.setText("Right");
        }
        else if (abs(x-0) <= 1 && abs(y+9.8) <= 1 && abs(z-0) <= 1) {
            txt3.setText("Upside down");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
