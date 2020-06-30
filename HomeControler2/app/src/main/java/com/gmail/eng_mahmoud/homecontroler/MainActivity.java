package com.gmail.eng_mahmoud.homecontroler;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

  ImageButton btnBedRoom,btnLiving,btnDoor,btnGas;
  Button  btnPair;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBedRoom = (ImageButton)findViewById(R.id.btnRoom1);
        btnLiving  = (ImageButton)findViewById(R.id.btnRoom2);
        btnDoor=(ImageButton)findViewById(R.id.imageButton) ;
        btnGas=(ImageButton)findViewById(R.id.homeGas) ;
        btnPair=(Button)findViewById(R.id.btnBluetooth);
        btnBedRoom.setOnClickListener(this);
        btnLiving.setOnClickListener(this);
        btnPair.setOnClickListener(this);
        btnDoor.setOnClickListener(this);
        btnGas.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==btnBedRoom){
            Intent in=new Intent(MainActivity.this,Room1.class);
            startActivity(in);
        }
        else if (v==btnLiving){
            Intent in =new Intent(MainActivity.this,Room2.class);
            startActivity(in);
        }
        else if (v==btnPair){
            Intent in =new Intent(MainActivity.this,DeviceList.class);
            startActivity(in);
        }
        else  if(v==btnDoor){
            Intent in=new Intent(MainActivity.this,Door.class);
            startActivity(in);
        }
        else if(v==btnGas){
            Intent in=new Intent(MainActivity.this ,Gas.class);
            startActivity(in);
        }
    }





}
