package com.example.permissionexe1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private EditText EDT_name;
    private MaterialButton BTN_login;
    private static BatteryManager myBatteryManager;

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
                if(getBatteryPercentage() > 50)
                    Log.d("ppt", getBatteryPercentage() + "");

            }
        });
    }

    private void initSystemServices(Context context) {
        myBatteryManager =  (BatteryManager) context.getSystemService(BATTERY_SERVICE);
    }

    private void findViews() {
        EDT_name = findViewById(R.id.EDT_name);
        BTN_login = findViewById(R.id.BTN_login);
    }

    public static int getBatteryPercentage() {
        return myBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
    }
}