package com.example.owner.encryptiontextmessages;
/*
*Class that manages incoming text messages and displays them in a toast message upon receiving
* then reloads the UI to show on message stream
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
public class SmsBroadcastReceiver extends BroadcastReceiver{
    @Override
    //activates when phone receives a new message
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        if(bundle != null){
            final Object[] pduObjects = (Object[]) bundle.get("pdus");
            for(int i = 0; i < pduObjects.length; ++i){
                TextEncryption decrypt = new TextEncryption();//instances TextEncryption Object
                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pduObjects[i]);//grabs current message for display
                String number = currentMessage.getDisplayOriginatingAddress();//places phone number into object
                String message = currentMessage.getDisplayMessageBody();//places message content into object
                Toast.makeText(context, "SENDER NUMBER: " + number + "; MESSAGE: " + decrypt.decrypt(message), Toast.LENGTH_LONG).show();//displays decrypted message in a toast message
                //reloads activity to refresh list***************************
                Intent reload = new Intent(context, MainActivity.class); //**
                context.startActivity(reload);                           //**
                //***********************************************************

            }
        }
    }

}