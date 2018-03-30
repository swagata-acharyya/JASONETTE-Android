package com.jasonette.seed.Action;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.jasonette.seed.Core.JasonViewActivity;
import com.jasonette.seed.Helper.JasonHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JasonLoadscreenAction {
    public void load(final JSONObject action, final JSONObject data, final JSONObject event, final Context context) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject options = action.getJSONObject("options");
                    String url = options.getString("url");
                    Iterator<?> keys = options.keys();
                    Map<String,String> params = new HashMap<>();
                    while(keys.hasNext()) {
                        String key = (String) keys.next();
                        if(!key.equals("url")) {
                            params.put(key, options.getString(key));
                        }
                    }

                    Intent intent = new Intent(context,JasonViewActivity.class);
                    intent.putExtra("url",url);
                    for(String param : params.keySet()) {
                        intent.putExtra(param,params.get(param));
                    }
                    context.startActivity(intent);

                } catch (Exception e){
                    Log.d("Warning", e.getStackTrace()[0].getMethodName() + " : " + e.toString());
                }
            }
        });
        try {
            JasonHelper.next("success", action, new JSONObject(), event, context);
        } catch (Exception e) {
            Log.d("Warning", e.getStackTrace()[0].getMethodName() + " : " + e.toString());
        }
    }
}
