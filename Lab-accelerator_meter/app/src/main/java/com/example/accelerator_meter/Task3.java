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
        if (z > 9)
            txt3.setText("On the table");

        else if (y > 9)
            txt3.setText("Default");

        else if (x > 9)
            txt3.setText("Left");

        else if (x < -9)
            txt3.setText("Right");

        else if (y < -9)
            txt3.setText("Upside down");


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
