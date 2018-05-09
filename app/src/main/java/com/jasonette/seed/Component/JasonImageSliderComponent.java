package com.jasonette.seed.Component;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.DefaultSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.jasonette.seed.Core.JasonViewActivity;
import com.jasonette.seed.Helper.JasonHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JasonImageSliderComponent {
    public static View build(View view, final JSONObject component, final JSONObject parent, final Context context) {
        if (view == null) {
            return new SliderLayout(context);
        } else {
            ArrayList<String> listUrl = new ArrayList<>();
            ArrayList<String> listName = new ArrayList<>();

            try {
                JasonComponent.build(view, component, parent, context);
                String data = component.getString("data").substring(2);
                Log.d("IMAGESLIDER","Received data as " + data);
                String[] ads = data.split("~~");
                Log.d("IMAGESLIDER","ads are " + ads.length);
                listUrl.clear();
                listName.clear();
                if(ads.length!=0) {
                    for (String ad : ads) {
                        Log.d("IMAGESLIDER","ad is " + ad);
                        String url = ad.split(":::")[0];
                        String action = ad.split(":::")[1];
                        listUrl.add(url);
                        listName.add(action);
                    }
                } else {
                    String defaultAd = component.getString("default_ad");
                }

                RequestOptions requestOptions = new RequestOptions();
                requestOptions
                        .centerCrop();
                Log.d("IMAGESLIDER","URL size " + listUrl.size());
                ((SliderLayout)view).removeAllSliders();
                for (int i = 0; i < listUrl.size(); i++) {
                    DefaultSliderView sliderView = new DefaultSliderView(context);
                    sliderView
                            .image(listUrl.get(i))
                            .description(listName.get(i))
                            .setRequestOption(requestOptions)
                            .setBackgroundColor(Color.WHITE)
                            .setProgressBarVisible(true);

                    //add your extra information
                    sliderView.bundle(new Bundle());
                    sliderView.getBundle().putString("extra", listName.get(i));
                    ((SliderLayout) view).addSlider(sliderView);
                    sliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView baseSliderView) {
                            Toast.makeText(context,"Clicked on " + baseSliderView.getDescription(),Toast.LENGTH_LONG).show();
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(baseSliderView.getDescription()));
                            context.startActivity(browserIntent);
                        }
                    });
                }

                ((SliderLayout) view).setPresetTransformer(SliderLayout.Transformer.Accordion);

                ((SliderLayout) view).setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                ((SliderLayout) view).setCustomAnimation(new DescriptionAnimation());
                ((SliderLayout) view).setDuration(4000);
                view.requestLayout();
                return view;
            }catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

    }
}
