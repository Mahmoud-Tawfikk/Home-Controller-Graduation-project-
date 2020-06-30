package com.gmail.eng_mahmoud.homecontroler;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class Gas extends AppCompatActivity implements View.OnClickListener {
    Button disable, enable;

    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas);
        //receive the address of the bluetooth device
        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS);
        disable = (Button) findViewById(R.id.btnDisable);
        enable = (Button) findViewById(R.id.btnEnable);
        enable.setOnClickListener(this);
        disable.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == enable) {
            if (btSocket != null) {
                try {
                    btSocket.getOutputStream().write("9".toString().getBytes());
                } catch (IOException e) {
                    msg("Error");
                }
            }

            Toast.makeText(getApplicationContext(), "Gas Controller is Opened.", Toast.LENGTH_LONG).show();



        } else if (v == disable) {
            if (btSocket != null) {
                try {
                    btSocket.getOutputStream().write("z".toString().getBytes());
                } catch (IOException e) {
                    msg("Error");
                }
            }
            Toast.makeText(getApplicationContext(), "Gas Controller is Closed.", Toast.LENGTH_LONG).show();


        }
    }

    //inner class in Room1 Class *************************************************************
    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(Gas.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            } catch (IOException e) {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            } else {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    //end of inner class  **************************************************************************
    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }


}
