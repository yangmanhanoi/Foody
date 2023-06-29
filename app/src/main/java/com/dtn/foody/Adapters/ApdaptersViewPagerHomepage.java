package com.dtn.foody.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dtn.foody.View.Fragments.FragmentFood;
import com.dtn.foody.View.Fragments.FragmentPlace;

public class ApdaptersViewPagerHomepage extends FragmentStatePagerAdapter {

    FragmentFood fragmentFood;
    FragmentPlace fragmentPlace;
    public ApdaptersViewPagerHomepage(@NonNull FragmentManager fm) {
        super(fm);
        fragmentFood = new FragmentFood();
        fragmentPlace = new FragmentPlace();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fragmentPlace;
            case 1:
                return fragmentFood;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
