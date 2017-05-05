package com.example.owner.encryptiontextmessages;
/*
* David Gorden
* Final Project Android Development 2
* Text message encryption app
*
* This app allows the user to enter in a phone number then encrypt the message helping to eliminate man in the middle attacks
*
* NOTE!!!!
* To whomever tests my app, if you are using an emulator use the 4 digit number next to the emulator name, that is the emulators number and will allow you to
* message your own emulator EXAMPLE: Nexus_5_API_25: XXXX,
* to test the levelOne encryption it will require a phone on debugging mode
 */
import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    //instances objects needed******************************************************************************************************************************************************OBJECTS**
    ArrayList<String> smsMessagesList = new ArrayList<>();
    ListView messages;
    ArrayAdapter arrayAdapter;
    EditText input;
    EditText phoneNumber;
    SmsManager smsManager = SmsManager.getDefault();
    //END OF OBJECTS*************************************************************************************************************************************************************************
    private static MainActivity inst;
    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;
    @Override
    //ONCREATE*********************************************************************************************************************************************************************ONCREATE**
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //locally initializes all needed objects***********************************************************
        messages = (ListView) findViewById(R.id.messages);                                             //**
        input = (EditText) findViewById(R.id.input);                                                   //**
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);                                       //**
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, smsMessagesList); //**
        messages.setAdapter(arrayAdapter);                                                             //**
        //*************************************************************************************************

        //permission check
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            getPermissionToReadSMS();//re-asks if not enabled
        }else{
            //creates handler to hand 3/10th of a second delay
            Handler handler = new Handler();
            handler.postDelayed(runnable, 1000);//delays refreshSmsInbox as a workaround for broadcast receiver class
        }
    }
    //END************************************************************************************************************************************************************************************
    //Method that runs when the user hits send**********************************************************************************************************************************SEND*METHOD**
    public void onSendClick(View view){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            getPermissionToReadSMS();//double check abillity to send messages again
        }
        else{
            TextEncryption encrypt = new TextEncryption();//instances TextEncryption class

            int version = 0;//default version
            if(phoneNumber.getText().toString().trim().equals("")){//validates the phone number box
                Toast.makeText(this, "Enter In a number", Toast.LENGTH_LONG).show();
            }else{
                if (phoneNumber.getText().length() > 5) {
                    version = 1;//if device is not an emulator then the version is changed to a more difficult encryption
                }
                //converts selected phone number, then encrypts message
                smsManager.sendTextMessage(phoneNumber.getText().toString(), null, encrypt.encrypt(input.getText().toString(), version), null, null);
                Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show();//confirmation message
            }
        }
    }
    //***************************************************************************************************************************************************************************************
    public void refreshSms(){
        TextEncryption decrypt = new TextEncryption();//creates a new encryption object
        ContentResolver contentResolver = getContentResolver(); //first piece to reading the dbu
        Cursor smsCursor = contentResolver.query(Uri.parse("content://sms"), null, null, null, null);//places cursor on sms database to allow pulling of data
        int indexBody = smsCursor.getColumnIndex("body");//gets body of message
        int indexAddress = smsCursor.getColumnIndex("address");//gets senders address
        if (indexBody < 0 || !smsCursor.moveToFirst()) return;//if sms database is empty it cancels out the method
        arrayAdapter.clear();//clears ListView to prevent duplication of object
        do{
            String message = "SMS From: " + smsCursor.getString(indexAddress) + "\n" + decrypt.decrypt(smsCursor.getString(indexBody)) + "\n";//message displayed in ListView
            arrayAdapter.add(message);//Adds total message to arrayAdapter(ListView)
        } while (smsCursor.moveToNext());//post, it moves to the next message
    }
    //makes sure app has permission to read users sms messages******************************************************************************************************************PERMISSIONS**
    public void getPermissionToReadSMS(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)){
                Toast.makeText(this, "Please allow permission!", Toast.LENGTH_SHORT).show();//error message if permission not given
            }
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, READ_SMS_PERMISSIONS_REQUEST);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults){
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();//message once permission has been granted in the ui
                refreshSms();//after permission is granted it refreshes the inbox to grab all the data
            }
            else{
                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();//error message if permission is not grated
            }
    }
    //permissions check is needed since in android 6.0 they changed permissions to the user must confirm them in app this
    //prevents a crash and allows the program to ask for permission again
    //END OF PERMISSIONS*********************************************************************************************************************************************************************
    //runnable that executes the refresh inbox method******************************************************************************************************************************RUNNABLE**
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshSms();//action called in runnable
        }
    };
}
