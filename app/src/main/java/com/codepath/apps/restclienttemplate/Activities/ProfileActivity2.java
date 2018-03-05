package com.codepath.apps.restclienttemplate.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.fragments.UserTimeLineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.apps.restclienttemplate.service.TwiterApp;
import com.codepath.apps.restclienttemplate.service.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity2 extends AppCompatActivity {
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);
        String sceenName = getIntent().getStringExtra("screenName");
        UserTimeLineFragment userTimeLineFragment = UserTimeLineFragment.newInstance(sceenName);
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flContainer, userTimeLineFragment);
        ft.commit();
        client = TwiterApp.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    User user = User.fromJSON(response);
                    getSupportActionBar().setTitle(user.getScreen_name());
                    populateUser(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("debug", errorResponse.toString());
            }
        });
    }

    private void populateUser(User user){
        TextView tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(user.getName());
        TextView tvTagLine = (TextView) findViewById(R.id.tvTagLine);
        tvTagLine.setText(user.getDesc());
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        tvFollowers.setText(user.getFollowers() + " Followers");
        TextView tvFollowing = (TextView) findViewById(R.id.tvtvFollowing);
        tvFollowing.setText(user.getFollowing() +" Following");

        ImageView ivProfileUser = (ImageView) findViewById(R.id.ivProfieImageUser);
        Glide.with(this).load(user.getProfile_imageURL()).into(ivProfileUser);
    }
    }

