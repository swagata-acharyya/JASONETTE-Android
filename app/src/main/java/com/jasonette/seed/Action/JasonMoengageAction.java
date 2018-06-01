package com.jasonette.seed.Action;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jasonette.seed.Helper.JasonHelper;
import com.jasonette.seed.utils.MoEngageUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JasonMoengageAction {

    public void addEvent(final JSONObject action, final JSONObject data, final JSONObject event, final Context context) {
        try {
        Application app = ((Activity)context).getApplication();
        final JSONObject options = action.getJSONObject("options");
        final JSONObject dataObj = options.getJSONObject("data");
        Map<String,String> eventDetails = getMapFromJsonObject(dataObj);
            Log.d("JASONCHECK",eventDetails+"");
//        if(!MoEngageUtil.isAnalyticsInitialized(app)) {
            Log.d("JASONCHECK","Init");
            MoEngageUtil.initAnalytics(app,"moengage", getAnalyticsKey(app));
//        }
            Log.d("JASONCHECK","Adding event");

            MoEngageUtil.addEvent(app,eventDetails.get("eventKey"),eventDetails);
            Log.d("JASONCHECK","Added event");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JasonHelper.next("success", action, new JSONObject(), event, context);
    }

    private Map<String, String> getMapFromJsonObject(JSONObject data) {
        Map<String,String> eventData = new HashMap<>();
        Iterator i = data.keys();
        while(i.hasNext()) {
            try {
                String key = (String) i.next();
                String value = (String) data.get(key);
                eventData.put(key,value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return eventData;
    }

    private String getAnalyticsKey(Application context) {
        SharedPreferences sharedPref = context.getSharedPreferences("analytics-keys", Context.MODE_PRIVATE);
        return sharedPref.getString("key","");
    }
}