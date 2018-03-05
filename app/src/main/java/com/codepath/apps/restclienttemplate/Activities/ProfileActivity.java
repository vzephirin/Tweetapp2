package com.codepath.apps.restclienttemplate.Activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.Adapter.ProfilePageAdapter;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.service.RoundedCornersTransformation;
import com.codepath.apps.restclienttemplate.service.TwiterApp;
import com.codepath.apps.restclienttemplate.service.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.Locale;

import cz.msebera.android.httpclient.Header;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class ProfileActivity extends AppCompatActivity {
    ProfilePageAdapter profilePageAdapter;
    private ImageView im_profil;

    private ImageView im_profile_user;
    String itemTitle;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    TextView title;
    TextView following;
    TextView idfolowers;
    TextView idscren_name;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("TWEETS"));
        tabLayout.addTab(tabLayout.newTab().setText("PHOTOS"));
        tabLayout.addTab(tabLayout.newTab().setText("FAVORITES"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);

        profilePageAdapter = new ProfilePageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(profilePageAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        //   ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
        supportPostponeEnterTransition();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //    String itemTitle = getIntent().getStringExtra(EXTRA_TITLE);


        //    final ImageView image = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.title);
        idscren_name = (TextView) findViewById(R.id.idscren_name);


        idfolowers = (TextView) findViewById(R.id.idfolowers);


        following = (TextView) findViewById(R.id.following);


        im_profil=(ImageView) findViewById(R.id.imProfile);
        im_profile_user=(ImageView) findViewById(R.id.im_profile_user);
        TwitterClient client = TwiterApp.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", "user: " + response.toString());

                try {
                    title.setText(response.getString("name").toString());
                    idfolowers.setText(response.getString("friends_count").toString()+" Following");
                    following.setText(response.getString("followers_count").toString()+ " Followers");
                    idscren_name.setText(String.format(Locale.US, "@%s", response.getString("screen_name")));
                    Glide.with(getContext())
                            .load(response.getString("profile_background_image_url"))
                            //   .override(100, 100)
                            .bitmapTransform(new RoundedCornersTransformation(getContext(), 30, 10))

                            .into(im_profil);

                    Glide.with(getContext())
                            .load(response.getString("profile_image_url_https"))
                            //   .override(100, 100)
                            .bitmapTransform(new RoundedCornersTransformation(getContext(), 30, 10))

                            .into(im_profile_user);
                    im_profile_user.bringToFront();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
//       ("Fevrun");
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Fevrun");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));




    }
}
