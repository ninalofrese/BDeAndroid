package com.example.agendatelefonica.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.agendatelefonica.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private ConstraintLayout telaSplash;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        telaSplash = findViewById(R.id.tela_splash);

        telaSplash.setOnClickListener(view -> jump());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                jump();
            }
        }, 1000);
    }

    private void jump() {
        timer.cancel();
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}
