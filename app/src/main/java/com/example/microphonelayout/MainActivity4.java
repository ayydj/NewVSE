package com.example.microphonelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity4 extends AppCompatActivity {
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private ImageButton micButton;
    private Button button_first;
    EditText speechText;
    SharedPreferences prefs;
    static final String myprefrence = "mypref";
    static final String safeword = "safe";

// public void save(View v){
 //    String st = speechText.getText().toString();
   //  SharedPreferences.Editor editor = prefs.edit();
  //   editor.putString(safeword,st);
   //  editor.commit();
 //}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);

        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
        micButton = (ImageButton) findViewById(R.id.micButton);
        button_first = (Button)  findViewById(R.id.button_first);
        speechText = (EditText) findViewById(R.id.edit_text);

        micButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(micButton.isPressed()) {
                    startVoiceInput();
                }
            }
        });

        button_first.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(button_first.isPressed()) {
                    prefs = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("safeword", speechText.getText().toString());
                    editor.apply();
                    openMainActivity5();
                }
            }
        });
    }

    public void openMainActivity5() {
        Intent intent = new Intent(this, MainActivity5.class);
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
                    speechText.setText(result.get(0).toString());
                }
                break;
            }

        }


    }
}
