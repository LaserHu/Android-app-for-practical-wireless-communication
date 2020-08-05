package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button btnStatus, btnLocation, btnGPS;
    TextView txtStatus, txtLocation;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStatus = findViewById(R.id.btnStatus);
        btnLocation = findViewById(R.id.btnLocation);
        btnGPS = findViewById(R.id.btnGPS);
        txtStatus = findViewById(R.id.txtStatus);
        txtLocation = findViewById(R.id.txtLocation);

        btnStatus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (isGPSEnabled) {
                    txtStatus.setText("GPS is active\n");
                }
                else {
                    txtStatus.setText("GPS is not active\n");
                }
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                GPSReader gpsReader = new GPSReader(getApplicationContext());
                Location location = gpsReader.getLocation(1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long time = location.getTime();
                Date date = new Date(time);
                String textOfDate = sdf.format(date);
                if(location!=null){
                    txtLocation.setText("Date/Time: " + textOfDate + "\n" +
                            "Provider: " + location.getProvider() + "\n" +
                            "Accuracy: " + location.getAccuracy() + "\n" +
                            "Latitude: " + location.getLatitude() + "\n" +
                            "Longitude: " + location.getLongitude() + "\n" +
                            "Speed: " + location.getSpeed() + "\n");
                }
            }
        });

        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                GPSReader gpsReader = new GPSReader(getApplicationContext());
                Location location = gpsReader.getLocation(0);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long time = location.getTime();
                Date date = new Date(time);
                String textOfDate = sdf.format(date);
                if(location!=null){
                    txtLocation.setText("Date/Time: " + textOfDate + "\n" +
                            "Provider: " + location.getProvider() + "\n" +
                            "Accuracy: " + location.getAccuracy() + "\n" +
                            "Latitude: " + location.getLatitude() + "\n" +
                            "Longitude: " + location.getLongitude() + "\n" +
                            "Speed: " + location.getSpeed() + "\n");
                }
            }
        });
    }
}
