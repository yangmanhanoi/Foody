package com.dtn.foody.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dtn.foody.Adapters.ApdaptersViewPagerHomepage;
import com.dtn.foody.R;

public class HomepageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    RadioButton radioPlaces, radioFood;
    ViewPager viewPager;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        radioFood = findViewById(R.id.radioFood);
        radioPlaces = findViewById(R.id.radioPlaces);
        radioGroup = findViewById(R.id.radioGroup);
        viewPager = findViewById(R.id.viewPager);
        ApdaptersViewPagerHomepage apdaptersViewPagerHomepage = new ApdaptersViewPagerHomepage(getSupportFragmentManager());
        viewPager.setAdapter(apdaptersViewPagerHomepage);
        viewPager.addOnPageChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position)
        {
            case 0:
                radioPlaces.setChecked(true);
                break;
            case 1:
                radioFood.setChecked(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i)
        {
            case R.id.radioPlaces:
                viewPager.setCurrentItem(0);
                break;
            case R.id.radioFood:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}