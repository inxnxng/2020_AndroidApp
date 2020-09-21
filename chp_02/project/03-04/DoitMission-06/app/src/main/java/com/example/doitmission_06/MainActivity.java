package com.example.doitmission_06;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;


public class MainActivity extends AppCompatActivity {
    EditText textView;
    SeekBar seek;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.editTextNumber);
        seek = findViewById(R.id.seekBar);
        progress = findViewById(R.id.progressBar);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView.setText(String.valueOf(i));
                progress.setProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}