package com.jasonette.seed.dao;

import android.content.Context;

import com.cloudant.sync.documentstore.DocumentStore;
import com.cloudant.sync.documentstore.DocumentStoreNotOpenedException;
import com.jasonette.seed.Cloudant.DocumentStoreHelper;

public class CloudantBaseDao {

    protected DocumentStore documentStore;

    public CloudantBaseDao(String dbName, Context context) throws DocumentStoreNotOpenedException {
        this.documentStore = DocumentStoreHelper.getDocumentStore(dbName, context);
    }
}