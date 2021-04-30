package com.example.tourismactivation.ui.pageAdapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tourismactivation.HomeFragment;
import com.example.tourismactivation.QrFragment;
import com.example.tourismactivation.SearchFragment;
import org.jetbrains.annotations.NotNull;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    Fragment homeFragment,searchFragment,qrFragment;
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position == 0)
        {
            if(homeFragment == null)
                homeFragment = new HomeFragment();

            return homeFragment;

        }else if(position == 1)
        {
            if(searchFragment == null)
                searchFragment = new SearchFragment();

            return searchFragment;
        }
        else
        {
            if(qrFragment == null)
                qrFragment = new QrFragment();

            return qrFragment;

        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}