package com.example.microphonelayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.telephony.SmsManager;
import android.net.Uri;
import android.provider.Telephony;
import android.view.KeyEvent;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.PendingIntent;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity9 extends AppCompatActivity {
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private Button micButton;
    private EditText speechText2;
    private Button Settings;
    SharedPreferences prefs;
    String word;
    String number;
    String msg;
    String msg2;
    String cPermission= Manifest.permission.SEND_SMS;
    private static final int Request_Code_permission=2;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    private String latData;
    private String lonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);

        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
        micButton = (Button) findViewById(R.id.micButton);
        speechText2 = (EditText) findViewById(R.id.edit_text);
        Settings = (Button) findViewById(R.id.settingsButton);
        String flPermission = Manifest.permission.ACCESS_FINE_LOCATION;

        prefs = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        word = prefs.getString("safeword","");
        number = prefs.getString("phoneNumber","");
        msg = prefs.getString("msg","");

        try{
            if (ActivityCompat.checkSelfPermission(this,flPermission)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{flPermission},Request_Code_permission);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        String clPermission = Manifest.permission.ACCESS_COARSE_LOCATION;
        try{
            if (ActivityCompat.checkSelfPermission(this,clPermission)!= PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this,new String[]{clPermission},Request_Code_permission);

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        String cPermission= Manifest.permission.SEND_SMS;
        try{
            if (ActivityCompat.checkSelfPermission(this,cPermission)!= PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this,new String[]{cPermission},Request_Code_permission);

            }

        }
        catch(Exception e){
            e.printStackTrace();

        }
        String sPermission= Manifest.permission.INTERNET;
        try{
            if (ActivityCompat.checkSelfPermission(this,sPermission)!= PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this,new String[]{sPermission},Request_Code_permission);

            }

        }
        catch(Exception e){
            e.printStackTrace();

        }


        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Settings.isPressed()) {
                    openMainActivity6();
                }
            }
        });

        micButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(micButton.isPressed()) {
                    startVoiceInput();
                }
            }
        });


        speechText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (word.equals(String.valueOf(s))) {

                   sendMessage();
            }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });




    }



    public void sendMessage(){

        try{
            getLastLocation();
            SmsManager smgr = SmsManager.getDefault();
            msg2 = msg+" Please check in on them. "+getString(R.string.web)+latData+","+lonData;
            ArrayList<String> parts = smgr.divideMessage (msg2);
            smgr.sendMultipartTextMessage(number,null,parts,null,null);
            Toast.makeText(MainActivity9.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(MainActivity9.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();

        }

    }





    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please say safe word.");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    speechText2.setText(result.get(0).toString());

                }

                break;

            }

        }


    }

    public void openMainActivity6() {
        Intent intent = new Intent(this, MainActivity6.class);
        startActivity(intent);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    latData = String.valueOf(location.getLatitude());
                                    lonData = String.valueOf(location.getLongitude());
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }

    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latData = String.valueOf(mLastLocation.getLatitude());
            lonData = String.valueOf(mLastLocation.getLongitude());
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }



}