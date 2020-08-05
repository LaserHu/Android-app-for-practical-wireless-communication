package com.example.frequency;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    WifiManager manager;
    WifiInfo info;
    TextView check5G;
    TextView checkFrequency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        check5GSupport();
        checkFrequency();
    }

    @TargetApi(21)
    private void check5GSupport() {
        check5G = findViewById(R.id.textView1);
        //boolean isSupport5G = manager.is5GHzBandSupported();
        WifiManager wifiManager= (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        boolean is5GhzSupported = false;
        try{
            Class cls = Class.forName("android.net.wifi.WifiManager");
            Method method = cls.getMethod("isDualBandSupported");
            Object invoke = method.invoke(wifiManager);
            is5GhzSupported=(boolean)invoke;
        } catch (Exception e) {
            e.printStackTrace();
        }


        if(is5GhzSupported) {
            check5G.setText("This device supports 5G.");
        }
        else{
            check5G.setText("This device doesn't support 5G.");
        }
    }

    @TargetApi(21)
    private void checkFrequency() {
        checkFrequency = findViewById(R.id.textView2);
        info = manager.getConnectionInfo();
        int frequency = info.getFrequency();
        double frequencyGHz = frequency*0.001;
        int speed = info.getLinkSpeed();
        checkFrequency.setText(String.format("The frequency of current connection is %sGHz,\n\n " +
                "The speed of current connection is %sMbps.",frequencyGHz,speed));
    }
}
