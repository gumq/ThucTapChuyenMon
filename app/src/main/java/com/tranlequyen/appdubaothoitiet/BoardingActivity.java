package com.tranlequyen.appdubaothoitiet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;


public class BoardingActivity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_boarding);

        linksview();
        setupOnboardingItems ();
        setupOnboardingIndicators ();
        setCurrentOnboardingIndicator(0);

        final ViewPager2 onboardingViewPager = findViewById ( R.id.onboardingViewPager );
        onboardingViewPager.setAdapter ( onboardingAdapter );


        onboardingViewPager.registerOnPageChangeCallback ( new ViewPager2.OnPageChangeCallback () {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected ( position );
                setCurrentOnboardingIndicator ( position );
            }
        } );

        if(restorePredata ())  {
            startActivity ( new Intent ( getApplicationContext (), Home.class ) );
            finish ();
        }
        buttonOnboardingAction.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()){
                    onboardingViewPager.setCurrentItem ( onboardingViewPager.getCurrentItem () +1 );

                }
                else
                {
                    startActivity ( new Intent ( getApplicationContext (), Home.class ) );
                    savePreData();
                    finish ();
                }

            }
        } );



        //make full screen
//        requestWindowFeature ( Window.FEATURE_NO_TITLE  );
//        getWindow ().setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );

    }


    private boolean restorePredata () {
        SharedPreferences preferences = getApplicationContext ().getSharedPreferences ( "myprefs",MODE_PRIVATE );
        Boolean isOnboarndingBefore = preferences.getBoolean ( "isOnboarding",false );
        return isOnboarndingBefore;
    }
    private void savePreData() {
        SharedPreferences preferences = getApplicationContext ().getSharedPreferences ( "myprefs",MODE_PRIVATE );
        SharedPreferences.Editor editor = preferences.edit ();
        editor.putBoolean ( "isOnboarding",true ) ;
        editor.apply ();

    }

    private void linksview(){
        layoutOnboardingIndicators = findViewById( R.id.layoutOnboardingIndicators );
        buttonOnboardingAction = findViewById ( R.id.buttonOnboardingAction );
    }
    private void setupOnboardingItems(){
        List<OnboardingItem> onboardingItems = new ArrayList<> ();
        OnboardingItem itemweatherrealtime = new OnboardingItem ();
        itemweatherrealtime.setTitle ( getString( R.string.onbording1) );
        itemweatherrealtime.setDescription ( getString( R.string.onboarding1sub));
        itemweatherrealtime.setImage ( R.drawable.onboarding );

        OnboardingItem itemgetential = new OnboardingItem ();
        itemgetential.setTitle ( getString( R.string.onboarding2) );
        itemgetential.setDescription ( getString( R.string.onboarding2sub));
        itemgetential.setImage ( R.drawable.onboarding4);

        OnboardingItem itemgetweather = new OnboardingItem ();
        itemgetweather.setTitle ( getString( R.string.onboarding3) );
        itemgetweather.setDescription ( getString( R.string.onboarding3sub));
        itemgetweather.setImage ( R.drawable.onboarding3 );

        onboardingItems.add(itemweatherrealtime);
        onboardingItems.add(itemgetential);
        onboardingItems.add(itemgetweather);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }

    private void setupOnboardingIndicators() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount ()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams ( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        layoutParams.setMargins ( 8, 0, 8, 0 );
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView ( getApplicationContext () );
            indicators[i].setImageDrawable ( ContextCompat.getDrawable ( getApplicationContext (), R.drawable.onboarding_indicator_inactive ) );

            indicators[i].setLayoutParams ( layoutParams );
            layoutOnboardingIndicators.addView ( indicators[i] );
        }
    }
    private void setCurrentOnboardingIndicator(int index){
        int childCount = layoutOnboardingIndicators.getChildCount ();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt ( i );
            if (i == index) {
                imageView.setImageDrawable ( ContextCompat.getDrawable ( getApplicationContext (), R.drawable.onboarding_indicator_active ) );

            } else {
                imageView.setImageDrawable ( ContextCompat.getDrawable ( getApplicationContext (), R.drawable.onboarding_indicator_inactive ) );

            }

        }
        if(index == onboardingAdapter.getItemCount ()-1){
            buttonOnboardingAction.setText("Start");

        }else{
            buttonOnboardingAction.setText ( "Next" );
        }
    }

}