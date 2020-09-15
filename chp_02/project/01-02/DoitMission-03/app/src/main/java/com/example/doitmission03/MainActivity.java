package com.example.doitmission03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
        ImageView imageView;
        ImageView imageView2;

        int imageIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView5);
        imageView2 = findViewById(R.id.imageView6);
        //
        Button buttonLayout = findViewById((R.id.buttonLayout));
        buttonLayout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){ changeImage();}
        });
    }

    private void changeImage(){
        if(imageIndex == 0){
            imageView.setImageResource(R.drawable.p2);
            imageView2.setImageResource(R.drawable.p1);
            imageIndex = 1;
        }else {
            imageView.setImageResource(R.drawable.p1);
            imageView2.setImageResource(R.drawable.p2);
            imageIndex = 0;
        }
    }
}