package com.example.permissionexe1;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    //UI Variables
    private EditText EDT_name;
    private MaterialButton BTN_login;

    //System Services
    private static BatteryManager myBatteryManager;
    private ConnectivityManager connManager;
    private BluetoothAdapter bluetoothadapter;
    private AudioManager audioManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initSystemServices(MainActivity.this);
        initButtons();
    }

    private void initButtons() {
        BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EDT_name.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Please fill in your name",Toast.LENGTH_SHORT).show();
                }else if(checkConditions(MainActivity.this))
                 Toast.makeText(MainActivity.this,"login successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSystemServices(Context context) {
        //Battery Service
        myBatteryManager =  (BatteryManager) context.getSystemService(BATTERY_SERVICE);
        //Connectivity Service
        connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //Bluetooth Adapter
        bluetoothadapter = BluetoothAdapter.getDefaultAdapter();
        //Audio Service  - Vibrate
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    }

    private void findViews() {
        EDT_name = findViewById(R.id.EDT_name);
        BTN_login = findViewById(R.id.BTN_login);
    }





    private boolean checkConditions(Context context){
        //Condition 1 - Battery percentages are less than 50
        if(getBatteryPercentage() > 50) {
            Toast.makeText(MainActivity.this, "Battery percentages need to be less than 50", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Condition 2 - Wifi connect
        if(!isWifiConnect()){
            Toast.makeText(MainActivity.this, "Wifi is not connected", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Condition 3 - Bluetooth is Enable
        if(!isBluetoothEnable()){
            Toast.makeText(MainActivity.this, "Bluetooth is disabled", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Condition 4 - Airplane Mode is On
        if(!isAirplaneModeOn(context)){
            Toast.makeText(MainActivity.this, "Airplane Mode is not On", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Condition 5 - isVibrateOn()
        if(!isVibrateOn()){
            Toast.makeText(MainActivity.this, "Vibrate is not On", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }




    public static int getBatteryPercentage() {
        return myBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
    }


    public boolean isWifiConnect(){
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }

    public boolean isBluetoothEnable(){
        if(bluetoothadapter.isEnabled())
            return true;
        else
            return false;

    }

    private boolean isVibrateOn(){
        if(audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE){
            return true;
        }
        else{
            return false;
        }
    }


    private static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }


}