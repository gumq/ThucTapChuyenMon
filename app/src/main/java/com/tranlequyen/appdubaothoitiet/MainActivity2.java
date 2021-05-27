package com.tranlequyen.appdubaothoitiet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity2 extends AppCompatActivity {

    private  OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main2 );

       layoutOnboardingIndicators = findViewById( R.id.layoutOnboardingIndicators );
        setupOnboardingItems ();

        buttonOnboardingAction = findViewById ( R.id.buttonOnboardingAction );
        final ViewPager2 onboardingViewPager = findViewById ( R.id.onboardingViewPager );
        onboardingViewPager.setAdapter ( onboardingAdapter );


        setupOnboardingIndicators ();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback ( new ViewPager2.OnPageChangeCallback () {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected ( position );
                setCurrentOnboardingIndicator ( position );
            }
        } );

        buttonOnboardingAction.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()){
                    onboardingViewPager.setCurrentItem ( onboardingViewPager.getCurrentItem () +1 );

            }else{
                    startActivity ( new Intent ( getApplicationContext (),HomeActivity.class ) );
                    finish ();
                }}
        } );
    }
    private void setupOnboardingItems(){
        List<OnboardingItem> onboardingItems = new ArrayList<> ();
        OnboardingItem itemweatherrealtime = new OnboardingItem ();
        itemweatherrealtime.setTitle ( "Check real-time weather!" );
        itemweatherrealtime.setDescription ( "Amet minim mollit non deserunt ullamco est sit aliqua ament sint");
        itemweatherrealtime.setImage ( R.drawable.onboarding );

        OnboardingItem itemgetential = new OnboardingItem ();
        itemgetential.setTitle ( "Get potential weather!" );
        itemgetential.setDescription ( "Amet minim mollit non deserunt ullamco est sit aliqua ament sint");
        itemgetential.setImage ( R.drawable.onboarding4);

        OnboardingItem itemgetweather = new OnboardingItem ();
        itemgetweather.setTitle ( "Get the weather and stay safe" );
        itemgetweather.setDescription ( "Amet minim mollit non deserunt ullamco est sit aliqua ament sint");
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