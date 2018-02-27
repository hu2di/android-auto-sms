package com.blogspot.huyhungdinh.autosms;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallReceiver extends BroadcastReceiver {

    private static final String TAG = "PhoneStatReceiver";
    private static boolean incomingFlag = false;
    private static String incoming_number = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
                incomingFlag = false;
                String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                Log.i(TAG, "call OUT:" + phoneNumber);
            } else {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
                try {
                    switch (tm.getCallState()) {
                        case TelephonyManager.CALL_STATE_RINGING:
                            incomingFlag = true;//标识当前是来电
                            incoming_number = intent.getStringExtra("incoming_number");
                            Log.i(TAG, "RINGING :" + incoming_number);
                            onRingGing(context, incoming_number);
                            break;
                        case TelephonyManager.CALL_STATE_OFFHOOK:
                            if (incomingFlag) {
                                Log.i(TAG, "incoming ACCEPT :" + incoming_number);
                            }
                            break;
                        case TelephonyManager.CALL_STATE_IDLE:
                            if (incomingFlag) {
                                Log.i(TAG, "incoming IDLE");
                            }
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onRingGing(Context context, String phoneNumber) {
        if (PhoneUtil.getOnePhone(context, phoneNumber) == 0) {
            PhoneUtil.addOnePhone(context, phoneNumber);
            PhoneUtil.addAllPhone(context, phoneNumber);

            sendSMS(phoneNumber);
        } else {
            PhoneUtil.addOnePhone(context, phoneNumber);
        }
    }

    private void sendSMS(String phoneNumber) {
        try {
            String message = "Hi.I'm a auto robot.Please contact my boss at Zalo, FB.. or he'll call you later on 25/02/2018.HPNY.";
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
