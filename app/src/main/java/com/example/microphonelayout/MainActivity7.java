package com.example.microphonelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Locale;

public class MainActivity7 extends AppCompatActivity {
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private Button button_first;
    private ImageButton micImage;
    private EditText edit_text2;
    private String st2 = edit_text2.getText().toString();
    private String st = getIntent().getStringExtra("value");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);


        button_first = (Button) findViewById(R.id.button_first);
        micImage = (ImageButton) findViewById(R.id.imageView);
        edit_text2 = (EditText) findViewById(R.id.edit_text2);

        micImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(micImage.isPressed()) {
                    startVoiceInput();
                }
            }
        });


        button_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity6();
            }
        });
    }

    public void openMainActivity6() {
        Intent intent = new Intent(this, MainActivity6.class);
        startActivity(intent);
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
                    edit_text2.setText(result.get(0).toString());
                }
                break;
            }

        }
        if (st == st2){

            Toast.makeText(getApplicationContext(),"That's your safe word!",Toast.LENGTH_SHORT).show();

        }


    }
}
