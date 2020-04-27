package com.example.microphonelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity6 extends AppCompatActivity {

    private Button Contact;
    private Button Word;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        Contact = (Button) findViewById(R.id.button);
        Word = (Button) findViewById(R.id.button2);

        Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Contact.isPressed()){
                    openMainActivity3();
                }
            }
        });
        Word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Word.isPressed()) {
                    openMainActivity4();
                }
            }
        });


    }




    public void openMainActivity3() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }



    public void openMainActivity4() {
        Intent intent = new Intent(this, MainActivity4.class);
        startActivity(intent);
    }
}