package com.gmail.eng_mahmoud.homecontroler;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;

import com.polyak.iconswitch.IconSwitch;

import java.io.IOException;
import java.util.UUID;
public class Room1 extends Activity  {

    Switch btnPulp,btnFan,btnPulp2,btnFan2;


    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room1);

        //receive the address of the bluetooth device
        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS);

        // auto open  and close
        btnPulp=(Switch) findViewById(R.id.switchBulb);
        btnFan=(Switch) findViewById(R.id.switchFan);
        btnPulp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("3".toString().getBytes());
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Light Is Turned On.", Toast.LENGTH_LONG).show();

                }
                else {
                    if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("4".toString().getBytes());
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Light Is Turned OFF.", Toast.LENGTH_LONG).show();


                }


            }
        });
        btnFan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("7".toString().getBytes());
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Fan Is Turned On.", Toast.LENGTH_LONG).show();

                }
                else {
                    if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("8".toString().getBytes());
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Fan Is Turned OFF.", Toast.LENGTH_LONG).show();

                }


            }
        });

        // manual open and close
        btnPulp2=(Switch) findViewById(R.id.switchBulb2);
        btnFan2=(Switch) findViewById(R.id.switchFan2);
        btnPulp2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("a".toString().getBytes());
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }
                }
                else {
                    if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("b".toString().getBytes());
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }

                }


            }
        });
        btnFan2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             if(isChecked){
                 if (btSocket!=null)
                 {
                     try
                     {
                         btSocket.getOutputStream().write("c".toString().getBytes());
                     }
                     catch (IOException e)
                     {
                         msg("Error");
                     }
                 }

             }
             else {
                 if (btSocket!=null)
                 {
                     try
                     {
                         btSocket.getOutputStream().write("d".toString().getBytes());
                     }
                     catch (IOException e)
                     {
                         msg("Error");
                     }
                 }

             }

            }
        });





    }




    //inner class in Room1 Class *************************************************************
    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(Room1.this, "Connecting...", "Please wait!!!");  //show a progress dialog
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
        private void msg(String s)
        {
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        }



}

