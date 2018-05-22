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
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.DefaultSliderView;
import com.jasonette.seed.dao.AdvertisementDao;
import com.jasonette.seed.utils.AppUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JasonImageSliderComponent {

    public static String IMAGE_URLS = "image_urls";
    public static String ACTION_URLS = "action_urls";

    public static View build(View view, final JSONObject component, final JSONObject parent, final Context context) {
        if (view == null) {
            return new SliderLayout(context);
        } else {
            List<String> imageUrls = new ArrayList<>();
            List<String> actionUrls = new ArrayList<>();

            try {
                JasonComponent.build(view, component, parent, context);

                AdvertisementDao advertisementDao = new AdvertisementDao(AppUtil.getConfigValue(AppUtil.KEY_DOCS_DB, context), context);
                Map<String, List<String>> urlsMap = advertisementDao.getAdverts();

                imageUrls = urlsMap.get(IMAGE_URLS);
                actionUrls = urlsMap.get(ACTION_URLS);

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.centerCrop();
                Log.d("IMAGESLIDER", "URL size " + imageUrls.size());
                ((SliderLayout) view).removeAllSliders();
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
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
