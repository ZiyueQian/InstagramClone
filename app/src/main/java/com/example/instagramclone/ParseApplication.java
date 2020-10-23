package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("bIQjQy6UnkoT4qMqjNtfFjhZdzfVmBd83TM1mRpV")
                .clientKey("7fu8lwM9QbNZkxys52c58d2QMp8ZQC8UjqN3rxgy")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
