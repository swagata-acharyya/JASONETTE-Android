package com.jasonette.seed.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;
import com.moengage.core.MoEngage;

import java.util.Map;

public class MoEngageUtil {

    public static void initAnalytics(Application application, String sdkName, String key) {
        Log.d("JASONCHECK","Init with key: " + key+" and sdk name = " +sdkName);
        if(sdkName.equalsIgnoreCase("moengage")) {
            MoEngage moEngage =
                    new MoEngage.Builder(application, key).build();
            MoEngage.initialise(moEngage);
            markAnalyticsInitialized(application);
        }
    }

    public static boolean isAnalyticsInitialized(Application context) {
        SharedPreferences sharedPref = context.getSharedPreferences("analytics-keys", Context.MODE_PRIVATE);
        return sharedPref.contains("initAnalytics");
    }

    private static void markAnalyticsInitialized(Application context) {
        SharedPreferences sharedPref = context.getSharedPreferences("analytics-keys", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("initAnalytics",true);
        editor.commit();
    }

    public static void addEvent(Application ctx, String event, Map<String, String> eventDetails) {
        Log.d("JASONCHECK","Adding event util");
        if(event.equalsIgnoreCase("login")) {
            loginUser(ctx,eventDetails.get("user_id"));
        }
        MoEHelper.getInstance(ctx).trackEvent(event,eventDetails);

        Log.d("JASONCHECK","After tracking event");
    }

    public static void addKey(Application context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences("analytics-keys", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("key",key);
        editor.commit();
    }

    public static void loginUser(Application context, String userId) {
        MoEHelper.getInstance(context).setUniqueId(userId);
    }

    public static void logoutUser(Application context) {
        MoEHelper.getInstance(context).logoutUser();
    }
}
