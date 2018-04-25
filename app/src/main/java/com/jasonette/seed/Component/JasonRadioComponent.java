package com.jasonette.seed.Component;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.jasonette.seed.Core.JasonViewActivity;
import com.jasonette.seed.Helper.JasonHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class JasonRadioComponent {
    public static View build(View view, final JSONObject component, final JSONObject parent, final Context context) {
        if(view == null) {
            return new RadioGroup(context);
        } else {
            try {
                String data = component.getString("data");
                final String[] radioData = data.split("\\|");
                view = JasonComponent.build(view, component, parent, context);
                RadioButton[] buttons = new RadioButton[radioData.length];
                RadioButton button;
                for(int i=0; i< radioData.length; i++) {
                    button = new RadioButton(context);
                    button.setText(radioData[i]);
                    ((RadioGroup)view).addView(button);
                }

//                RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams
//                        (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//                view.setLayoutParams(layoutparams);
//
//                JSONObject style = JasonHelper.style(component, context);
//                String type = component.getString("type");
//                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
//                        (context, android.R.layout.simple_spinner_item,
//                                spinnerData);
//                spinnerArrayAdapter.setDropDownViewResource(android.R.layout
//                        .simple_spinner_dropdown_item);
//                ((Spinner)view).setAdapter(spinnerArrayAdapter);
//
//                ((Spinner)view).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        try {
//                            ((JasonViewActivity) context).model.var.put(component.getString("name"), spinnerData[position]);
//                        } catch (JSONException e) {
//                            Log.e("Warning", e.getStackTrace()[0].getMethodName() + " : " + e.toString());
//                        }
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });

                ((RadioGroup)view).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        try {
                            ((JasonViewActivity) context).model.var.put(component.getString("name"), radioData[i]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Warning", e.getStackTrace()[0].getMethodName() + " : " + e.toString());
                        }
                    }
                });

                ((RadioGroup)view).requestLayout();
                return view;
            } catch (Exception e){
                e.printStackTrace();
                Log.e("Warning", e.getStackTrace()[0].getMethodName() + " : " + e.toString());
                return new View(context);
            }
        }
    }
}
