package com.codepath.apps.restclienttemplate.Adapter;

import android.view.View;

import com.codepath.apps.restclienttemplate.models.Tweet;

/**
 * Created by Administrator on 2/17/2018.
 */

public interface ItemClickListenerInterface {
    //public void setItemClick(View view, int position);
    public void onProfileClicked(Tweet tweet);
    public void onRetweetClicked(Tweet tweet, int position);
    public void onReplyClicked(Tweet tweet);
    public void onFavClicked(Tweet tweet, int position);
    public void onUserNameClicked(String userName);
}
