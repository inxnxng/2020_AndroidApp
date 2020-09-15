package com.example.doitmission04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText InputMessage;
    TextView TotalCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputMessage = findViewById(R.id.inputhere);
        TotalCount = findViewById(R.id.textinput_counter);

        //보냄
        Button SendButton = findViewById(R.id.buttonsend);
        SendButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String message = InputMessage.getText().toString();
                Toast.makeText(getApplicationContext(), "전송하는 메세지 : " + message, Toast.LENGTH_LONG).show();
            }
        });

        //끝냄
        Button CloseButton = findViewById(R.id.buttonclose);
        CloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"종료합니다", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        //https://lktprogrammer.tistory.com/185
        //https://jungwoon.github.io/android/2016/09/22/TextWatcher-Guide

        InputMessage.addTextChangedListener(textWatcher);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            int changeValue = Integer.parseInt(InputMessage.getText().toString())*100;
            TotalCount.setText(changeValue+" / 80 byte");
        }
    };
}