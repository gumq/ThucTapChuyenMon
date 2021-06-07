package com.tranlequyen.appdubaothoitiet;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.tranlequyen.appdubaothoitiet.ui.acticity.Home;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenLOGO extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen( SplashScreenLOGO.this)
                .withFullScreen()
                .withTargetActivity( Home.class)
                .withSplashTimeOut(40)
                .withBackgroundColor( Color.parseColor("#FFFFFF"))
                .withLogo(R.drawable.giotnuoc);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
