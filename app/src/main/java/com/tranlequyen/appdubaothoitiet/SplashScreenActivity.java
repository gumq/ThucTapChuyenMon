package com.tranlequyen.appdubaothoitiet;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class SplashScreenActivity extends AppCompatActivity {

    Animation zoom;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.ativity_splash_screen );

        zoom = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom);
        img = findViewById(R.id.image);
        img.startAnimation(zoom);

        Handler h = new Handler ();
        h.postDelayed(new Runnable () {
            @Override
            public void run() {
                    Intent i = new Intent (getApplicationContext(),BoardingActivity.class);
                startActivity(i);
                finish();
            }
        },2560);
    }
}