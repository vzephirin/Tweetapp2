package com.codepath.apps.restclienttemplate.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.Adapter.ItemClickListenerInterface;
import com.codepath.apps.restclienttemplate.Adapter.TweetAdapter;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.service.EndlessRecyclerViewScrollListener;
import com.codepath.apps.restclienttemplate.service.TwiterApp;
import com.codepath.apps.restclienttemplate.service.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2/27/2018.
 */

public class TweetListFragment extends Fragment implements ItemClickListenerInterface {
    public interface TweeSelectListener{
        public void onTweetSelect(Tweet tweet);
    }
    private  ProgressDialog pd;
    private TwitterClient client;
    private TweetAdapter tweetAdapter;
    private RecyclerView rvTweets;
    private ArrayList<Tweet> tweets;
    private EndlessRecyclerViewScrollListener scrollListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.framgment_tweets_list,container,false);
        setupViews(view);

        //populateTimeline(1);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }


    private void setupViews(View view){
        //client = TwiterApp.getRestClient();

        rvTweets = (RecyclerView) view.findViewById(R.id.rvTweet);
        tweets =new ArrayList<>();
        tweetAdapter = new TweetAdapter(getContext(),tweets,this);
        //tweetAdapter.setClickListener();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvTweets.setLayoutManager(linearLayoutManager);
        rvTweets.setAdapter(tweetAdapter);

        //rvTweets.setLayoutManager(gridLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list

                if (totalItemsCount>10)loadNextDataFromApi(page);


            }
        };

        rvTweets.addOnScrollListener(scrollListener);



    }

    public void loadNextDataFromApi(int offset) {
        Toast.makeText(getContext(), "Searching for test " , Toast.LENGTH_LONG).show();
        //populateTimeline(offset+1);
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        //onArticleSearchSroll(queryt, offset);
    }


    public void addItems (JSONArray response){

        final ArrayList<Tweet> listTweet = Tweet.fromJson(response);


        for (Tweet tweet : listTweet) {
            tweets.add(tweet);
            tweetAdapter.notifyItemInserted(tweets.size()-1);; // add book through the adapter
        }

    }
/*
    @Override
    public void setItemClick(View view, int position) {

        Tweet tweet  = tweets.get(position);
        ((TweeSelectListener)getActivity()).onTweetSelect(tweet);
        //int item = position;

       // Toast.makeText(getContext(),"test click",Toast.LENGTH_LONG);
    }*/

    @Override
    public void onProfileClicked(Tweet tweet) {
      //  Log.i(TAG,"User profile clicked "+tweet.getUser().getScreen_name());
       // ProfileLoadListener listener = (ProfileLoadListener) getActivity();
       // listener.onProfileLoad(tweet.getUser());
        Toast.makeText(getContext(),"Profile test click",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onReplyClicked(Tweet tweet) {
        /*Log.i(TAG,"Reply  clicked "+tweet.getUser().getScreen_name());
        ProfileLoadListener listener = (ProfileLoadListener) getActivity();
        listener.onReply(tweet);
        */
        Toast.makeText(getContext(),"reply test click",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRetweetClicked(Tweet tweet, int position) {
        /*Log.e(TAG,"This method should never be called.If called, something is Wrong ! ");
        Log.e(TAG,"Child fragment's OnRetweet Click to be called");
       */
        Toast.makeText(getContext(),"retweet test click",Toast.LENGTH_LONG).show();
    }



    @Override
    public void onFavClicked(Tweet tweet, int position) {
       /* Log.e(TAG,"This method should never be called.If called, something is Wrong ! ");
        Log.e(TAG,"Child fragment's onFavClicked to be called");
        */
        Toast.makeText(getContext(),"on fav clicked test click",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUserNameClicked(String userName) {
       /* Log.e(TAG,"This method should never be called.If called, something is Wrong ! ");
        Log.e(TAG,"Child fragment's onUserNameClicked to be called");
        */
        Toast.makeText(getContext(),"username test click",Toast.LENGTH_LONG).show();
    }

}
