package com.example.microphonelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity5 extends AppCompatActivity {

    private Button button2;
    SharedPreferences prefs;
    TextView message;
    String msg;
    String name;
    String contactName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fifth);

        button2 = (Button) findViewById(R.id.button2);
        message = (TextView) findViewById(R.id.textView3);

        prefs = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        name = prefs.getString("name","");
        contactName = prefs.getString("contactName","");


        message.setText(contactName+", this is an emergency text from this approximate location. You are receiving this because you have been made an emergency contact for "+ name+".");

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("msg", message.getText().toString());
                editor.apply();
                openMainActivity8();
            }
        });
    }

    public void openMainActivity8() {
        Intent intent = new Intent(this, MainActivity8.class);
        startActivity(intent);
    }
}
