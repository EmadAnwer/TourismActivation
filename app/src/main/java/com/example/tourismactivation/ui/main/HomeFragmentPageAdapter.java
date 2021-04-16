package com.example.tourismactivation.ui.main;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.tourismactivation.HomeExperiencesFragment;
import com.example.tourismactivation.HomeGovernoratesFragment;
import com.example.tourismactivation.HomeRecommendedFragment;


public class HomeFragmentPageAdapter  extends FragmentPagerAdapter {
    Fragment homeGovernoratesFragment,homeRecommendedFragment,homeExperiencesFragment;

    private final Context mContext;

    public HomeFragmentPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position == 0)
        {
            if(homeGovernoratesFragment == null)
                homeGovernoratesFragment = new HomeGovernoratesFragment();

            return homeGovernoratesFragment;

        }else if(position == 1)
        {
            if(homeExperiencesFragment == null)
                homeExperiencesFragment = new HomeExperiencesFragment();

            return homeExperiencesFragment;
        }
        else
        {
            if(homeRecommendedFragment == null)
                homeRecommendedFragment = new HomeRecommendedFragment();

            return homeRecommendedFragment;

        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}
