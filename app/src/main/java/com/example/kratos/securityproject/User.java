package com.example.kratos.securityproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class User extends AppCompatActivity {

    TelephonyManager tm;


    Intent intent;
    Intent intent2;

    EditText uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        tm = (TelephonyManager)
                getSystemService(Context.TELEPHONY_SERVICE);

        uname = (EditText) findViewById(R.id.name);

        intent = new Intent(this,UserCallLog.class);
        intent2 = new Intent(this,callNumberLog.class);
    }


    public void GetSimSerial(View view)
    {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String simID = tm.getSimSerialNumber();
        if (simID != null)
            Toast.makeText(this, "SIM card ID: " + simID,
                    Toast.LENGTH_LONG).show();
    }

    public void GetNumber(View view)
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //String telNumber  = tm.getLine1Number();
        String telNumber  = tm.getSubscriberId();
        /*if (telNumber != null)
            //tv1.setText(telNumber);
            //Toast.makeText(this, "Phone number: " + telNumber,
            //Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "failed" ,
                    Toast.LENGTH_LONG).show();*/

    }

    public void CallLog(View view)
    {
        startActivity(intent);
    }

    public void callNumberLog(View view)
    {
        String name = uname.getText().toString();
        intent2.putExtra("Name", name);
        startActivity(intent2);
    }
}
