package com.example.gps2;

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
    public void onLocationChanged(Location location){

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

    public Location getLocationByGPS(){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context,"There is no permission to access location",Toast.LENGTH_SHORT).show();
            return null;
        }
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,this);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return location;
    }

    public Location getLocationByNetwork(){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context,"There is no permission to access location",Toast.LENGTH_SHORT).show();
            return null;
        }
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,1,this);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        return location;
    }

}
