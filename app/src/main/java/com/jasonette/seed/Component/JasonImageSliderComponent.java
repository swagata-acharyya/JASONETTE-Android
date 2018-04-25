package com.jasonette.seed.Component;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.jasonette.seed.Core.JasonViewActivity;
import com.jasonette.seed.Helper.JasonHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JasonImageSliderComponent {
    public static View build(View view, final JSONObject component, final JSONObject parent, final Context context) {
//        if(view == null) {
//            return new AutoCompleteTextView(context);
//        } else {
//            try {
//                String placeholder = component.optString("placeholder");
//                JSONArray data = component.getJSONArray("data");
////                final String[] tokens = data.split("\\|");
//
//                view = JasonComponent.build(view, component, parent, context);
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                        (context, android.R.layout.select_dialog_item, tokens);
//                JSONObject style = JasonHelper.style(component, context);
//                String type = component.getString("type");
//                ((AutoCompleteTextView)view).setThreshold(1);
//                ((AutoCompleteTextView)view).setAdapter(adapter);
//                if(placeholder!=null && !placeholder.isEmpty()){
//                    ((AutoCompleteTextView)view).setHint(placeholder);
//                }
//
//                ((AutoCompleteTextView)view).setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        try {
//                            ((JasonViewActivity) context).model.var.put(component.getString("name"), ((AppCompatTextView)view).getText());
//                        } catch (JSONException e) {
//                            Log.e("Warning", e.getStackTrace()[0].getMethodName() + " : " + e.toString());
//                        }
//                    }
//                });
//                ((AutoCompleteTextView)view).requestLayout();
//                return view;
//            } catch (Exception e){
//                Log.d("Warning", e.getStackTrace()[0].getMethodName() + " : " + e.toString());
//                return new View(context);
//            }
//        }
        return null;
    }
}
