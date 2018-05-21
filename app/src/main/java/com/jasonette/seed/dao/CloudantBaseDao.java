package com.jasonette.seed.dao;

import android.content.Context;

import com.cloudant.sync.documentstore.DocumentStore;
import com.cloudant.sync.documentstore.DocumentStoreNotOpenedException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasonette.seed.Cloudant.DocumentStoreHelper;

public class CloudantBaseDao {

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    protected DocumentStore documentStore;

    public CloudantBaseDao(String dbName, Context context) throws DocumentStoreNotOpenedException {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.documentStore = DocumentStoreHelper.getDocumentStore(dbName, context);
    }
}