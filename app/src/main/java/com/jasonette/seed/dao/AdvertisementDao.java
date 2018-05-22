package com.jasonette.seed.dao;

import android.content.Context;
import android.util.Log;

import com.cloudant.sync.documentstore.DocumentRevision;
import com.cloudant.sync.documentstore.DocumentStoreNotOpenedException;
import com.cloudant.sync.query.FieldSort;
import com.cloudant.sync.query.Query;
import com.cloudant.sync.query.QueryException;
import com.cloudant.sync.query.QueryResult;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jasonette.seed.Component.JasonImageSliderComponent.ACTION_URLS;
import static com.jasonette.seed.Component.JasonImageSliderComponent.IMAGE_URLS;

public class AdvertisementDao extends CloudantBaseDao {

    public AdvertisementDao(String dbName, Context context) throws DocumentStoreNotOpenedException {
        super(dbName, context);
    }

    /**
     * @return docs having "doc_type" as advertisement and are active
     * @throws QueryException
     */
    public Map<String, List<String>> getAdverts() throws QueryException {
        Map<String, List<String>> keyValuesMap = new HashMap<>();
        Query query = documentStore.query();
        query.createJsonIndex(Arrays.asList(new FieldSort("doc_type")), "basic");
        Map<String, Object> indexMap = new HashMap<>();
        indexMap.put("doc_type", "advertisement");
        QueryResult queryResult = query.find(indexMap);

        List<String> imageUrls = new ArrayList<>();
        List<String> actionUrls = new ArrayList<>();

        List<String> defaultImageUrls = new ArrayList<>();
        List<String> defaultActionUrls = new ArrayList<>();

        if (null != queryResult && queryResult.iterator().hasNext()) {
            for (DocumentRevision rev : queryResult) {
                Map<String, Object> docBody = rev.getBody().asMap();
                if (((String) docBody.get("is_active")).equalsIgnoreCase("true")) {

                    if (advertForToday(docBody)) {
                        Map<String, String> urls = (Map<String, String>) docBody.get("advert");
                        String imageUrl = urls.get("image_url");
                        String actionUrl = urls.get("action_url");
                        if (docBody.get("default") == null) {
                            imageUrls.add(imageUrl);
                            actionUrls.add(actionUrl);
                        } else {
                            defaultImageUrls.add(imageUrl);
                            defaultActionUrls.add(actionUrl);
                        }
                    }
                }
            }

            if(!imageUrls.isEmpty()) {
                keyValuesMap.put(IMAGE_URLS, imageUrls);
                keyValuesMap.put(ACTION_URLS, actionUrls);
            } else {
                keyValuesMap.put(IMAGE_URLS, defaultImageUrls);
                keyValuesMap.put(ACTION_URLS, defaultActionUrls);
            }
        }
        return keyValuesMap;
    }

    private static boolean advertForToday(Map<String, Object> docBody) {
        Log.d("DOCBODY","Docbody is " + docBody);
        if(docBody.containsKey("default")) {
            return true;
        } else {
            String startDate = (String) docBody.get("start_date");
            String endDate = (String) docBody.get("end_date");
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
}