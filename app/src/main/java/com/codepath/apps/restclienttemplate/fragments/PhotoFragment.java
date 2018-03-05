package com.codepath.apps.restclienttemplate.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.Adapter.PhotoAdapter;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;


import static android.content.ContentValues.TAG;

/**
 * Created by SAUL on 3/3/2018.
 */

public class PhotoFragment extends Fragment {

    PhotoAdapter adapter_photo;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_photo,container,false);
        rvTweet=(RecyclerView)view.findViewById(R.id.rcTweet);
        tweets=new ArrayList<>();
        adapter_photo=new PhotoAdapter(getActivity(),tweets);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        //rvTweet.setLayoutManager(layoutManager);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvTweet.setLayoutManager(gridLayoutManager);
        adapter_photo.notifyDataSetChanged();
        rvTweet.setAdapter(adapter_photo);
        Log.i(TAG, "onCreateView TweetListFragment = ");
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

   public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }
    public void addItems  (JSONArray response){
        tweets.clear();
       //  adapter.clear();
        tweets.addAll(Tweet.fromJson(response));
        adapter_photo.notifyItemRangeInserted(0, tweets.size() - 1);
    }

}
