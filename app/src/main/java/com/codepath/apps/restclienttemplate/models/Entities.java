package com.codepath.apps.restclienttemplate.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.codepath.apps.restclienttemplate.Database.MyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 3/2/2018.
 */

    @Table(database = MyDatabase.class)
    @org.parceler.Parcel(analyze={com.codepath.apps.restclienttemplate.models.Entities.class})
    public class Entities extends BaseModel implements Parcelable {

        public Long getEntities_id() {
            return entities_id;
        }

        public void setEntities_id(Long entities_id) {
            this.entities_id = entities_id;
        }

        @Column
        @PrimaryKey
        Long entities_id;


        @Column
        @ForeignKey(saveForeignKeyModel = true)
        Media media;

        public Media getMedia() {
            return media;
        }

        public void setMedia(Media media) {
            this.media = media;
        }

        public Entities() {
        }

        protected Entities(Parcel in) {
            this.entities_id = in.readLong();
            this.media = in.readParcelable(Media.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(entities_id);
            dest.writeParcelable(media,flags);

        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<com.codepath.apps.restclienttemplate.models.Entities> CREATOR = new Creator<com.codepath.apps.restclienttemplate.models.Entities>() {
            @Override
            public com.codepath.apps.restclienttemplate.models.Entities createFromParcel(Parcel in) {
                return new com.codepath.apps.restclienttemplate.models.Entities(in);
            }

            @Override
            public com.codepath.apps.restclienttemplate.models.Entities[] newArray(int size) {
                return new com.codepath.apps.restclienttemplate.models.Entities[size];
            }
        };

        public static com.codepath.apps.restclienttemplate.models.Entities fromJSON(JSONObject object, long tweet_id) throws JSONException {
            com.codepath.apps.restclienttemplate.models.Entities entity = new com.codepath.apps.restclienttemplate.models.Entities();
            entity.entities_id = tweet_id;
            entity.media = Media.fromJSON(object.getJSONArray("media"));
            return entity;
        }
    }

