package com.example.nayan.rssrecipe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Nayan on 6/9/2017.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 8;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "সকল";
            case 1:
                return "স্যুপের রেসিপি";
            case 2:
                return "মাংস";
            case 3:
                return "মিষ্টি রেসিপি";
            case 4:
                return "ভর্তা";
            case 5:
                return "আচার ও চাটনি";
            case 6:
                return "পিঠা";
            case 7:
                return "সালাদ";
            case 8:
                return "মাংস";
        }
        return null;
    }
}
