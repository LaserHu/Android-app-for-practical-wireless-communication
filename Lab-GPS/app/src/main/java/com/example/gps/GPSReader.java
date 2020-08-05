package com.example.gps;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GPSReader extends Service implements LocationListener {
    Context context;
    Location location;
    public GPSReader(Context c){
        context = c;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = location.getTime();
        Date date = new Date(time);
        String textOfDate = sdf.format(date);
        Toast.makeText(context, "Date/Time: " + textOfDate + "\n" +
                "Provider: " + location.getProvider() + "\n" +
                "Accuracy: " + location.getAccuracy() + "\n" +
                "Latitude: " + location.getLatitude() + "\n" +
                "Longitude: " + location.getLongitude() + "\n" +
                "Speed: " + location.getSpeed() + "\n", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider){

    }

    @Override
    public void onProviderEnabled(String provider){

    }

    public Location getLocation(int i){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context,"There is no permission to access location",Toast.LENGTH_SHORT).show();
            return null;
        }
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


        if (i == 1){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,1,this);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            return location;
        }
        else if(i == 0){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,this);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return location;
        }
        else{
            Toast.makeText(context,"Please enable Network or GPS to get location",Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
