package com.codepath.apps.restclienttemplate.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.codepath.apps.restclienttemplate.fragments.UserPhotoFragment;
import com.codepath.apps.restclienttemplate.fragments.UserTimeLineFragment;



public class ProfilePageAdapter  extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    String screen_name;
    public ProfilePageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
              return  UserTimeLineFragment.newInstance(screen_name);// UserTweetFragment();
              //  return tab1;
            case 1:
                UserPhotoFragment tab2 = new UserPhotoFragment();
                return tab2;
            case 2:
                return  UserTimeLineFragment.newInstance(screen_name);


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}