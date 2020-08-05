package com.example.gps2;

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
    Button btn, btn2;
    TextView txt, txt2, txt3;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        txt = findViewById(R.id.textView);
        txt2 = findViewById(R.id.textView2);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (isGPSEnabled) {
                    txt.setText("GPS is active\n");
                }
                else {
                    txt.setText("GPS is not active\n");
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                GPSReader gpsReader = new GPSReader(getApplicationContext());
                Location gps = gpsReader.getLocationByGPS();
                Location network = gpsReader.getLocationByNetwork();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long time = gps.getTime();
                Date date = new Date(time);
                String textOfDate = sdf.format(date);
                txt2.setText("Date/Time: " + textOfDate + "\n" +
                        "Provider: " + gps.getProvider() + "\n" +
                        "Accuracy: " + gps.getAccuracy() + "\n" +
                        "Latitude: " + gps.getLatitude() + "\n" +
                        "Longitude: " + gps.getLongitude() + "\n" +
                        "Speed: " + gps.getSpeed() + "\n");
                txt3.setText("Date/Time: " + textOfDate + "\n" +
                        "Provider: " + network.getProvider() + "\n" +
                        "Accuracy: " + network.getAccuracy() + "\n" +
                        "Latitude: " + network.getLatitude() + "\n" +
                        "Longitude: " + network.getLongitude() + "\n" +
                        "Speed: " + network.getSpeed() + "\n");
            }
        });
    }
}
