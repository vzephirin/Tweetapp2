package com.codepath.apps.restclienttemplate.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.fragments.TweetListFragment;
import com.codepath.apps.restclienttemplate.Adapter.TweetPagerAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import android.support.v7.widget.Toolbar;

/**
 * Created by Administrator on 2/27/2018.
 */

public class TimelineActivity extends AppCompatActivity implements TweetListFragment.TweeSelectListener{
    TweetListFragment tweetListFragment;
    private DrawerLayout mDrawerLayout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_timeline);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
           // getActionBar().setTitle("");

            final ActionBar ab = getSupportActionBar();
            ab.setHomeAsUpIndicator(R.mipmap.ic_twitter_logo_round);
            ab.setDisplayHomeAsUpEnabled(true);

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            NavigationView navigationView = (NavigationView) findViewById(R.id.nvView);
            if (navigationView != null) {
                setupDrawerContent(navigationView);
            }

            //tweetListFragment = (TweetListFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewPager.setAdapter(new TweetPagerAdapter(getSupportFragmentManager(),this));
            TabLayout tabLayout =(TabLayout) findViewById(R.id.sliding_tabs);
            tabLayout.setupWithViewPager(viewPager);




            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.ft_tweet);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });


        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     getMenuInflater().inflate(R.menu.menu_timeline,menu);
     return true;

    }

    public void onClickProfilView(MenuItem item) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void onTweetSelect(Tweet tweet) {
        Toast.makeText(this,tweet.getBody(),Toast.LENGTH_LONG).show();
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
    }











