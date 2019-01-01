package com.example.kratos.securityproject;

import android.database.Cursor;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class UserCallLog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log);

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

            sb.append("\nName: " + pname);
            sb.append("\nPhone number: " + phnumber);
            sb.append("\nCall duration: " + callduration);
            sb.append("\nCall Type: " + callTypeStr);
            sb.append("\nCall Date: " + d);
            sb.append("\n");
            //sb.append("\n-------------------------------------------");
            //sb.append(System.getProperty("line.seperator"));

        }

        TextView callDetails = (TextView) findViewById(R.id.callLog);
        callDetails.setText(sb.toString());
    }
}
