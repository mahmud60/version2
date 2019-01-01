package com.example.kratos.securityproject;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class callNumberLog extends AppCompatActivity {

    String uname;
    String num;

    Intent myintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_number_log);

        myintent = getIntent();
        uname = myintent.getStringExtra("Name");

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

        while (cursor.moveToNext())
        {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{ id }, null);

            while (phoneCursor.moveToNext())
            {
                if(name.equals(uname))
                {
                    num = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    break;
                }
            }


        }

        Cursor mCursor = managedQuery(CallLog.Calls.CONTENT_URI,null,null, null, null);
        int number = mCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int date = mCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = mCursor.getColumnIndex(CallLog.Calls.DURATION);
        int type = mCursor.getColumnIndex(CallLog.Calls.TYPE);

        int name = mCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);

        StringBuilder sb = new StringBuilder();

        while (mCursor.moveToNext())
        {
            String pname = mCursor.getString(name);
            String phnumber = mCursor.getString(number);
            String callduration = mCursor.getString(duration);
            String callType = mCursor.getString(type);
            String callDate = mCursor.getString(date);

            Date d = new Date(Long.valueOf(callDate));

            String callTypeStr = "";

            switch (Integer.parseInt(callType))
            {
                case CallLog.Calls.OUTGOING_TYPE:
                    callTypeStr = "Outgoing";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    callTypeStr = "Incoming";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    callTypeStr = "Missed";
                    break;

            }

            if(phnumber.equals(num)) {
                sb.append("\nPhone number: " + phnumber);
                sb.append("\nCall duration: " + callduration + "s");
                sb.append("\nCall Type: " + callTypeStr);
                sb.append("\nCall Date: " + d);
                sb.append("\n");
                //sb.append("\n-------------------------------------------");
                //sb.append(System.getProperty("line.seperator"));
            }

        }

        TextView callDetails = (TextView) findViewById(R.id.numLog);
        callDetails.setText(sb.toString());

    }
}
