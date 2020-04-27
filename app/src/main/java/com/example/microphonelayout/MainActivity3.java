package com.example.microphonelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {

    private Button button_second;
    private Button Submit;
    SharedPreferences prefs;
    private EditText Name;
    private EditText phoneNumber;
    private EditText contactName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_third);

        button_second = (Button) findViewById(R.id.button_second);
        Submit = (Button) findViewById(R.id.Submit);
        Name = (EditText) findViewById(R.id.contact_name);
        phoneNumber = (EditText) findViewById(R.id.Phone_Number);
        contactName = (EditText) findViewById(R.id.contact_name2);

        button_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity3();
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Submit.isPressed()) {
                    prefs = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("name", Name.getText().toString());
                    editor.putString("phoneNumber", phoneNumber.getText().toString());
                    editor.putString("contactName", contactName.getText().toString());
                    editor.apply();
                    Toast.makeText(MainActivity3.this,"Sumbit",Toast.LENGTH_SHORT).show();
            }
            }
        });

    }



    public void openMainActivity3() {
        Intent intent = new Intent(this, MainActivity4.class);
        startActivity(intent);
    }
}
