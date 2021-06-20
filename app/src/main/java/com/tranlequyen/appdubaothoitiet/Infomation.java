package com.tranlequyen.appdubaothoitiet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Infomation extends AppCompatActivity {
ImageView imvBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_infomation );
        imvBack = findViewById ( R.id.imageBack );
        imvBack.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                onBackPressed ();
            }
        } );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isDestroyed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isDestroyed();
    }
}