package com.jasonette.seed.Component;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.cloudant.sync.documentstore.DocumentRevision;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.DefaultSliderView;
import com.jasonette.seed.Cloudant.DocumentStoreHelper;
import com.jasonette.seed.utils.AppUtil;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JasonImageSliderComponent {
    public static View build(View view, final JSONObject component, final JSONObject parent, final Context context) {
        if (view == null) {
            return new SliderLayout(context);
        } else {
            List<String> imageUrls = new ArrayList<>();
            List<String> actionUrls = new ArrayList<>();

            try {
                JasonComponent.build(view, component, parent, context);

                List<DocumentRevision> revs = DocumentStoreHelper.getAllDocs(AppUtil.getConfigValue(AppUtil.KEY_DOCS_DB, context), context);
                for (DocumentRevision rev : revs) {
                    Map<String, Object> docBody = rev.getBody().asMap();
                    if (docBody.containsKey("doc_type") && ((String) docBody.get("doc_type")).equalsIgnoreCase("advertisement")) {
                        if (((String) docBody.get("is_active")).equalsIgnoreCase("true")) {
                            String startDate = (String) docBody.get("start_date");
                            String endDate = (String) docBody.get("end_date");
                            if (advertForToday(startDate, endDate)) {
                                Map<String, String> urls = (Map<String, String>) docBody.get("advert");
                                String imageUrl = urls.get("image_url");
                                String actionUrl = urls.get("action_url");
                                imageUrls.add(imageUrl);
                                actionUrls.add(actionUrl);
                            }
                        }
                    }
                }

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.centerCrop();
                Log.d("IMAGESLIDER","URL size " + imageUrls.size());
                ((SliderLayout)view).removeAllSliders();
                for (int i = 0; i < imageUrls.size(); i++) {
                    DefaultSliderView sliderView = new DefaultSliderView(context);
                    sliderView
                            .image(imageUrls.get(i))
                            .description(actionUrls.get(i))
                            .setRequestOption(requestOptions)
                            .setBackgroundColor(Color.WHITE)
                            .setProgressBarVisible(true);

                    //add your extra information
                    sliderView.bundle(new Bundle());
                    sliderView.getBundle().putString("extra", actionUrls.get(i));
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

    private static boolean advertForToday(String startDate, String endDate) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd");
        DateTime start = DateTime.parse(startDate, fmt);
        Log.d("DATECHECK", "StartDate " + start);
        DateTime end = DateTime.parse(endDate, fmt);
        Log.d("DATECHECK", "end Date " + end);
        DateTime today = new DateTime();
        Log.d("DATECHECK", "Current Date " + today);
        Log.d("DATECHECK", "Result " + (today.isAfter(start) && today.isBefore(end)));
        return today.isAfter(start) && today.isBefore(end);
    }
}
