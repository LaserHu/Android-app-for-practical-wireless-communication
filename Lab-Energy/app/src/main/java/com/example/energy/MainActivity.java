package com.example.energy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2;
    TextView txt1, txt2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task1();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Task2.class);
                startActivity(intent);
            }
        });
    }

    private void task1() {
        this.registerReceiver(this.batteryInfoReceiver,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // getIntExtra() Retrieve extended data from the intent
            // EXTRA_STATUS indicating integer containing the current status constant.
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = (chargePlug == BatteryManager.BATTERY_PLUGGED_USB);
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

            // containing the current battery level
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            // integer containing the maximum battery level.
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            // convert level to percentage
            float batteryPct = level / (float)scale;
            txt1.setText("Current level of battery is: " + batteryPct + "%\n");
            if (usbCharge && isCharging){
                txt2.setText("Mobile is charging via USB\n");
            }
            else if (acCharge && isCharging){
                txt2.setText("Mobile is charging via AC\n");
            }
            else{
                txt2.setText("");
            }
        }
    };
}
