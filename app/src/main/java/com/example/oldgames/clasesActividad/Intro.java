package com.example.oldgames.clasesActividad;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.oldgames.R;

import java.util.Timer;
import java.util.TimerTask;

public class Intro extends AppCompatActivity {
    private ImageView imv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);

        imv= (ImageView) findViewById(R.id.animImv);
        imv.setBackgroundResource(R.drawable.animacion);

        AnimationDrawable animation=(AnimationDrawable)imv.getBackground();
        animation.start();

        TimerTask task= new TimerTask() {
            @Override
            public void run() {
              Intent intent= new Intent(Intro.this,Login.class);
              startActivity(intent);
              finish();
            }
        };

        Timer timer= new Timer();
        timer.schedule(task,5800);
    }
}
