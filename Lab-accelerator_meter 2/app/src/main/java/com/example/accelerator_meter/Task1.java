package com.example.accelerator_meter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Task1 extends AppCompatActivity {

    SensorManager sensorManager;
    List<Sensor> sensorList;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        listView = findViewById(R.id.listView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        for (Sensor itemSensor: sensorList) {
            list.add("Name: " + itemSensor.getName() + "\n" +
                    "Vendor: " + itemSensor.getVendor() + "\n" +
                    "Version: " + itemSensor.getVersion() + "\n"+
                    "Maximum Range: " + itemSensor.getMaximumRange() + "\n"+
                    "Minimum Delay: " + itemSensor.getMinDelay() + "\n"
            );
        }
        adapter.notifyDataSetChanged();
    }
}

