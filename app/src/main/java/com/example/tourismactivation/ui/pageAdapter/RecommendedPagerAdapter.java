package com.example.tourismactivation.ui.pageAdapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tourismactivation.RecommendedOverviewFragment;
import com.example.tourismactivation.RecommendedPlacesFragment;

public class RecommendedPagerAdapter extends FragmentPagerAdapter {

    Fragment recommendedOverviewFragment,recommendedPlacesFragment;

    private final Context mContext;

    public RecommendedPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position == 0)
        {
            if(recommendedOverviewFragment == null)
                recommendedOverviewFragment = new RecommendedOverviewFragment();

            return recommendedOverviewFragment;

        }else
        {
            if(recommendedPlacesFragment == null)
                recommendedPlacesFragment = new RecommendedPlacesFragment();

            return recommendedPlacesFragment;
        }

    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}
