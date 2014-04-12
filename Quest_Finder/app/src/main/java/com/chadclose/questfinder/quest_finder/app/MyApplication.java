package com.chadclose.questfinder.quest_finder.app;

import android.app.Application;
import com.parse.Parse;

public class MyApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "nyV2iKoWp4ZvHfRj1nOeQElDRjZLcmXKviq0LQKB", "V59gduRqnzJZdT90F9rItnPY4kEoaNPScrQxWPc1");
    }
}