package com.example.energy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Task2 extends AppCompatActivity {
    TextView txt11, txt22, txt33, txt44;
    float initialPercentage;
    int duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task2);
        txt11 = findViewById(R.id.txt11);
        txt22 = findViewById(R.id.txt22);
        txt33 = findViewById(R.id.txt33);
        txt44 = findViewById(R.id.txt44);
        duration = 10;
        txt_11();
        txt_22();

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable(){
                    public void run(){
                        txt_33();
                    }
                });
            }
        };
        timer.schedule(doTask,duration*1000*60);
    }

    private void txt_11(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isWifiEnabled = networkInfo.isConnected();
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isWifiEnabled && !isGPSEnabled){
            txt11.setText("Using Wi-Fi for " + duration + " minutes:");
        }
//        else if(isGPSEnabled && !isWifiEnabled){
        else if(isGPSEnabled){
            txt11.setText("Using GPS for " + duration + " minutes:");
        }
        else if(!isGPSEnabled && !isWifiEnabled){
            txt11.setText("Normal usage for " + duration + " minutes:");
        }
        else{
            txt11.setText("Other usage situation");
        }
    }

    private void txt_22(){
        this.registerReceiver(this.batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            float initialValue = level / (float)scale;
            if (initialPercentage==0){
                initialPercentage = initialValue * 100;
                txt22.setText("Initial level of battery: "+ initialPercentage+"%\n");
            }
        }
    };

    private void txt_33(){
        this.registerReceiver(this.batteryInfoReceiver2, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private BroadcastReceiver batteryInfoReceiver2 = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            int level2 = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale2 = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            float finalPercentage = (level2 / (float)scale2) * 100;
            txt33.setText("Final Level: " + finalPercentage + "%\n");
            float difference = initialPercentage - finalPercentage;
            txt44.setText("Consumed battery: " + difference+"%\n");
        }
    };
}
