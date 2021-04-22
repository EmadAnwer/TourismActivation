package com.example.tourismactivation.ui.pageAdapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.tourismactivation.placeFragments.GalleryFragment;
import com.example.tourismactivation.placeFragments.OverviewFragment;
import com.example.tourismactivation.placeFragments.ReviewsFragment;

public class PlacePagerAdapter extends FragmentPagerAdapter {

    Fragment galleryFragment,overviewFragment,reviewsFragment;

    private final Context mContext;

    public PlacePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position == 0)
        {
            if(overviewFragment == null)
                overviewFragment = new OverviewFragment();

            return overviewFragment;

        }else if(position == 1)
        {
            if(reviewsFragment == null)
                reviewsFragment = new ReviewsFragment();

            return reviewsFragment;
        }
        else
        {
            if(galleryFragment == null)
                galleryFragment = new GalleryFragment();

            return galleryFragment;

        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

}
