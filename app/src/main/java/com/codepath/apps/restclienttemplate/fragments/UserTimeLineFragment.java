package com.codepath.apps.restclienttemplate.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.codepath.apps.restclienttemplate.service.EndlessRecyclerViewScrollListener;
import com.codepath.apps.restclienttemplate.service.TwiterApp;
import com.codepath.apps.restclienttemplate.service.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2/27/2018.
 */

public class UserTimeLineFragment extends TweetListFragment {
    private ProgressDialog pd;
    private TwitterClient client;
    private EndlessRecyclerViewScrollListener scrollListener;

public static UserTimeLineFragment newInstance(String screenName){
  UserTimeLineFragment userTimeLineFragment= new UserTimeLineFragment();
  Bundle bundle =new Bundle();
  bundle.putString("screenName", screenName);
  userTimeLineFragment.setArguments(bundle);
  return userTimeLineFragment;
}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwiterApp.getRestClient();


        populateTimeline(1);
    }


    @Override
    public void loadNextDataFromApi(int offset) {
        populateTimeline(offset+1);

    }


    private void populateTimeline(int i) {
        //pd= new ProgressDialog(getContext());
        //pd.setMessage("Please wait.");
        //pd.setCancelable(false);
       // pd.show();
        String screenName = getArguments().getString("screenName");

        client.getUserTimeline(screenName,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                Log.d("Tweetersucces",response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

//                pd.dismiss();
                // try {

                addItems(response);


                Log.d("Debug", "test");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("Tweeter",errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                // super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("Tweeter",errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Tweeter",responseString);
                throwable.printStackTrace();
            }
        });
    }
}
