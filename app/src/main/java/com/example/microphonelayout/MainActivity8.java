package com.example.microphonelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity8 extends AppCompatActivity {

    private Button Start;
    private Button Settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        Start = (Button) findViewById(R.id.Start);
        Settings = (Button) findViewById(R.id.Settings);

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Start.isPressed()){
                    openMainActivity9();
                }
            }
        });
        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Settings.isPressed()) {
                    openMainActivity6();
                }
            }
        });


    }




    public void openMainActivity9() {
        Intent intent = new Intent(this, MainActivity9.class);
        startActivity(intent);
    }



    public void openMainActivity6() {
        Intent intent = new Intent(this, MainActivity6.class);
        startActivity(intent);
    }
}