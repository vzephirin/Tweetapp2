package com.codepath.apps.restclienttemplate.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.Activities.TimelineActivity;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.databinding.ItemTweetBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.service.PatternEditableBuilder;
import com.codepath.apps.restclienttemplate.service.RoundedCornersTransformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2/24/2018.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    private List<Tweet> mTweets;
    private Context mContext;
    private ItemClickListenerInterface  clickListener;

    public TweetAdapter(@NonNull Context context, List<Tweet> tweets,  ItemClickListenerInterface  clickListener) {
        this.mTweets=tweets;
        this.mContext=context;
        this.clickListener=clickListener;
        Log.d("adapter","testOK");
    }

    @Override
    public TweetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);


        // Return a new holder instance
        TweetAdapter.ViewHolder viewHolder = new TweetAdapter.ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TweetAdapter.ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);

        // Populate data into the template view using the data object
        holder.tvbody.setText(tweet.getBody());
        holder.tvuUsername.setText(tweet.getUser().getName());
        holder.tvcreateAt.setText(tweet.getCreated_at());
        Glide.with(mContext)
                .load(Uri.parse(tweet.getUser().getProfile_imageURL())).
                bitmapTransform(new RoundedCornersTransformation(mContext,15, 2))
                .into(holder.image);


        holder.screen_name.setText("@"+tweet.getUser().getScreen_name());
       // body.setText(tweet.getBody());
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\@(\\w+)"),mContext.getResources().getColor(R.color.twitter_logo_blue),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                if(clickListener!=null){
                                    clickListener.onUserNameClicked(text);
                                }
                            }
                        }).into(holder.tvbody);
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\#(\\w+)"), mContext.getResources().getColor(R.color.twitter_logo_blue),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                Toast.makeText(mContext, "Clicked Hashtag: " + text,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).into(holder.tvbody);

        setListeners(tweet,holder,position);
        setVideoView(tweet,holder);
        setFavoriteColor(tweet,holder);
        setRetweetColor(tweet,holder);
        holder.reply_count.setText(""+tweet.getReply_count());
        holder.retweet_count.setText(""+tweet.getRetweet_count());
        holder.fav_count.setText(""+tweet.getFavorite_count());


    }
    private String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

 /*   public void setClickListener() {
        this.clickListener = clickListener;
    }
*/


    public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView image;
            public TextView tvuUsername;
            public TextView tvbody;
            public TextView tvcreateAt;
            public TextView screen_name;
            public TextView reply_count;
            public TextView retweet_count;
            public TextView fav_count;
            public ImageView reply_tweet;
            public ImageView retweet;
            public ImageView fav_tweet;
            public ColorStateList oldColors;
            public android.widget.VideoView video_tweet;
            private ItemTweetBinding binding;


            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);


                image = (ImageView)itemView.findViewById(R.id.ivProfieImageUrl);
                tvuUsername = (TextView)itemView.findViewById(R.id.tvUserName);
                tvbody=(TextView)itemView.findViewById(R.id.tvBody);
                tvcreateAt=(TextView)itemView.findViewById(R.id.tvcreated_at);
                screen_name = (TextView)itemView.findViewById(R.id.screen_name);
                video_tweet = (VideoView) itemView.findViewById(R.id.video_tweet);;


                reply_count = (TextView)itemView.findViewById(R.id.reply_count_text);
                retweet_count =(TextView)itemView.findViewById(R.id.retweet_count_text);
                fav_count = (TextView)itemView.findViewById(R.id.gradle_count_text);

                reply_tweet =(ImageView) itemView.findViewById(R.id.reply_item_tweet);
                retweet = (ImageView) itemView.findViewById(R.id.retweet_item_tweet);
                fav_tweet =(ImageView)itemView.findViewById(R.id.gradle_item_tweet);

                oldColors =  reply_count.getTextColors();
                // tvAuthor = (TextView)itemView.findViewById(R.id.tvAuthor);
               /*itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if (clickListener!=null)
                           clickListener.setItemClick(v,getAdapterPosition());


                   }
               });*/
            }


        }






    private void setFavoriteColor(Tweet tweet, TweetAdapter.ViewHolder viewHolder){
        if(tweet.isFavorited()){
            //fav_tweet.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_IN);
            viewHolder.fav_tweet.setImageResource(R.drawable.ic_grade_gold_24dp);
            viewHolder.fav_count.setTextColor(Color.MAGENTA);
        }
        else{
            viewHolder.fav_tweet.setImageResource(R.drawable.ic_grade_black_24dp);
            viewHolder.fav_count.setTextColor( viewHolder.oldColors);
        }
    }

    private void setRetweetColor(Tweet tweet,TweetAdapter.ViewHolder viewHolder){
        if(tweet.isRetweeted()){
            viewHolder.retweet.setColorFilter(mContext.getResources().getColor(R.color.twitter_retweet), PorterDuff.Mode.SRC_IN);
            viewHolder.retweet_count.setTextColor(mContext.getResources().getColor(R.color.twitter_retweet));
        }
        else{
            viewHolder.retweet.clearColorFilter();
            viewHolder.retweet_count.setTextColor( viewHolder.oldColors);
        }
    }

    private void setVideoView(Tweet tweet,TweetAdapter.ViewHolder viewHolder){
        if(tweet.getEntities()!=null&& tweet.getEntities().getMedia().getMedia_type().equals("video")){
            viewHolder.video_tweet.setVideoPath(tweet.getEntities().getMedia().getVideo_url());
            MediaController mediaController = new MediaController(mContext);
            mediaController.setAnchorView( viewHolder.video_tweet);
            mediaController.setVisibility(View.GONE);
            viewHolder.video_tweet.setMediaController(mediaController);
            viewHolder.video_tweet.setVisibility(View.VISIBLE);
            viewHolder.video_tweet.requestFocus();

            viewHolder.video_tweet.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                // Close the progress bar and play the video
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

        }
        else {
            viewHolder.video_tweet.setVisibility(View.GONE);
        }
    }


    private void setListeners(final Tweet tweet, TweetAdapter.ViewHolder viewHolder, final int position){
        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener!=null)
                    clickListener.onProfileClicked(tweet);
            }
        });

        viewHolder.retweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener!=null)
                    clickListener.onRetweetClicked(tweet,position);
            }
        });

        viewHolder.reply_tweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener!=null)
                    clickListener.onReplyClicked(tweet);
            }

        });


        viewHolder.fav_tweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener!=null)
                    clickListener.onFavClicked(tweet,position);
            }

        });
    }







//public void setClickListener(ItemClickListenerInterface   clickListener) {
          //  this.clickListener = clickListener;
      //  }

       // private ItemClickListenerInterface  clickListener;





        // Usually involves inflating a layout from XML and returning the holder



        // Easy access to the context object in the recyclerview
        private Context getContext() {
            return mContext;
        }


}
