package com.example.chandnimittal.classassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        button = (Button)findViewById(R.id.intro_button);




    }

    public void OnCickingIntro(View v) {
        Intent i = new Intent(IntroActivity.this,LoginActivity.class);
        startActivity(i);
    }
}
