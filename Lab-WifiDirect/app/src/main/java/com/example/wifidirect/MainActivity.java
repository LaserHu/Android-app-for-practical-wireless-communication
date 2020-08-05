package com.example.wifidirect;

import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnOnOff, btnDiscover;
    ListView listView;
    TextView status;
    TextView check;

    WifiManager wifiManager;
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;

    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;
    List<WifiP2pDevice> peers = new ArrayList<>();
    String[] deviceNameArray;
    WifiP2pDevice[] deviceArray;

    static final int MESSAGE_READ = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkP2p();
        initialwork();
        exqListener();
    }
    private void checkP2p(){
        check = findViewById(R.id.txtCheck);
        mManager = (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE);
        if(mManager.WIFI_P2P_STATE_ENABLED!=2){
            check.setText("wifi direct is not available");
        }
        else
            check.setText("wifi direct is available");
    }
    // check wifi is enabled or not
    private void exqListener() {


        //discover the available nearby devices.
        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // A channel that connects the application to the Wifi p2p framework.
                // Most p2p operations require a Channel as an argument.
                mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        status.setText("Discovery started");
                    }

                    @Override
                    public void onFailure(int reason) {
                        status.setText("Discovery failed to start");
                    }
                });
            }
        });

        // set click method on item of list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final WifiP2pDevice device = deviceArray[position];
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;
                // Start a p2p connection to a device with the specified configuration.
                mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(),"Connected to" + device.deviceName,Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(int reason) {
                        Toast.makeText(getApplicationContext(),"Not Connected", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
    private void initialwork() {
        //btnOnOff = (Button) findViewById(R.id.btnOnOff);
        btnDiscover = (Button) findViewById((R.id.btnSearchPeer));
        listView = (ListView) findViewById(R.id.listView);
        status = (TextView) findViewById(R.id.txtStatus);

        wifiManager=(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        // register the application with WIFI frame work
        mChannel = mManager.initialize(this,getMainLooper(),null);

        mReceiver=new WiFiDirectBroadcastReceiver(mManager, mChannel,this);
        mIntentFilter=new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    //to be notified of the success or failure of WifiP2pManager method calls.
    WifiP2pManager.PeerListListener peerListListener=new WifiP2pManager.PeerListListener() {
        @Override
        // check the peer list
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            if(!peerList.getDeviceList().equals(peers)){
                peers.clear();
                peers.addAll(peerList.getDeviceList());
                deviceNameArray = new String[peerList.getDeviceList().size()];
                deviceArray= new WifiP2pDevice[peerList.getDeviceList().size()];
                int index = 0;

                for(WifiP2pDevice device:peerList.getDeviceList()){
                    deviceNameArray[index]="device name: "+device.deviceName + '\n' +"Device address: " + device.deviceAddress + '\n' +"status: "+device.status + '\n' +"primary type:"+device.primaryDeviceType; // MAC address add wps devacpb status
                    deviceArray[index]=device;
                    index++;
                }

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,deviceNameArray);
                listView.setAdapter(adapter);

            }
            if(peers.size()==0){
                Toast.makeText(getApplicationContext(),"No Devices Found",Toast.LENGTH_SHORT).show();
                return;
            }
        }
    };
    //Interface for callback invocation when connection info is available
    WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo info) {
            // The requested connection info is available
            final InetAddress groupOwnerAddress = info.groupOwnerAddress;

            // isGroupOwner Indicates if the current device is the group owner
            //groupFormed indicates if a p2p group has been successfully formed
            if(info.groupFormed && info.isGroupOwner){
                //status.setText("Host");
            }else if (info.groupFormed){
                //status.setText("Client");
            }
        }
    };
    @Override
    protected void onPostResume() {
        super.onPostResume();
        registerReceiver(mReceiver,mIntentFilter);
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }
}
