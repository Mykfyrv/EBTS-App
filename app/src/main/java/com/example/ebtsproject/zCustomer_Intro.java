package com.example.ebtsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

public class zCustomer_Intro extends AppCompatActivity {

    private Adapter_Intro onBoardingAdapter;
    private LinearLayout layoutBoardingIndicators;
    private MaterialButton buttonBoardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zcustomer_intro);

        layoutBoardingIndicators = findViewById(R.id.layoutBoardingIndicators);
        buttonBoardingAction = findViewById(R.id.buttonBoardingAction);

        setupOnBoardingItems();

        ViewPager2 onBoardingViewPager = findViewById(R.id.boardingViewPager);
        onBoardingViewPager.setAdapter(onBoardingAdapter);

        setupOnBoardingIndicators();
        setCurrentOnBoardingIndicator(0);

        onBoardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnBoardingIndicator(position);
            }
        });

        buttonBoardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onBoardingViewPager.getCurrentItem() + 1 < onBoardingAdapter.getItemCount()) {
                    onBoardingViewPager.setCurrentItem(onBoardingViewPager.getCurrentItem() + 1);
                }else {
                    startActivity(new Intent(getApplicationContext(), Customer_Register.class));
                    finish();
                }
            }
        });
    }

    private void setupOnBoardingItems(){
        List<Model_Intro> modelIntros = new ArrayList<>();

        Model_Intro itemChooseHouse = new Model_Intro();
        itemChooseHouse.setImage(R.drawable.ebts_choose);

        Model_Intro itemEasyBooking = new Model_Intro();
        itemEasyBooking.setImage(R.drawable.ebts_book);

        Model_Intro itemEnjoyStaying = new Model_Intro();
        itemEnjoyStaying.setImage(R.drawable.ebts_enjoy);

        modelIntros.add(itemChooseHouse);
        modelIntros.add(itemEasyBooking);
        modelIntros.add(itemEnjoyStaying);

        onBoardingAdapter = new Adapter_Intro(modelIntros);
    }

    private void setupOnBoardingIndicators() {
        ImageView[] indicators = new ImageView[onBoardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutBoardingIndicators.addView(indicators[i]);
        }
    }

    private void setCurrentOnBoardingIndicator(int index) {
        int childCount = layoutBoardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView)layoutBoardingIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_active)
                );
            }else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)
                );
            }
        }
        if(index == onBoardingAdapter.getItemCount()-1) {
            buttonBoardingAction.setText("START");
        }else {
            buttonBoardingAction.setText("NEXT");
        }

    }

}