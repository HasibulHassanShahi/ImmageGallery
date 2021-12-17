package com.e.immagegallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.e.immagegallery.Activity.HomeActivity;

public class MainActivity extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}