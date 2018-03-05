package com.codepath.apps.restclienttemplate.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.restclienttemplate.fragments.HomeTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.MentionsTimeFragment;

/**
 * Created by Administrator on 2/27/2018.
 */

public class TweetPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTile = new String []{"Home","Mentions"};
    private Context context;
    public TweetPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context =context;
    }
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new HomeTimelineFragment();
        }
        else if (position==1){return new MentionsTimeFragment();
        }
        else   return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTile[position];
    }
}
