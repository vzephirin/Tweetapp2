package com.codepath.apps.restclienttemplate.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.restclienttemplate.service.TwiterApp;
import com.codepath.apps.restclienttemplate.service.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

/**
 * Created by SAUL on 3/3/2018.
 */

public class UserPhotoFragment extends PhotoFragment {
      ProgressDialog pd;
    public static final String TAG = "UserTimelineFragment";
    private TwitterClient client;
    private static final String SCREEN_NAME = "screen_name";

    private String screen_name;

    public static UserPhotoFragment  newInstance(String screenname) {
        UserPhotoFragment  fragment = new UserPhotoFragment ();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SCREEN_NAME,screenname);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwiterApp.getRestClient();
//        screen_name = getArguments().getString(SCREEN_NAME);
        populateUserTimeline();

    }


    private void populateUserTimeline() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.show();
           client.getUserTimeline(0, screen_name,new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    Log.i(TAG, response.toString());
                    addItems(response);
                    Log.d("DEBUG",response.toString());
                    pd.dismiss();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Log.i(TAG, errorResponse.toString());
                    pd.dismiss();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.i(TAG, responseString);
                    throwable.printStackTrace();
                    pd.dismiss();
                }

            });
        }



}
