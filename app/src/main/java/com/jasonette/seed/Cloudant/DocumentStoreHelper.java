package com.jasonette.seed.Cloudant;

import android.content.Context;
import android.util.Log;

import com.cloudant.sync.documentstore.DocumentNotFoundException;
import com.cloudant.sync.documentstore.DocumentRevision;
import com.cloudant.sync.documentstore.DocumentStore;
import com.cloudant.sync.documentstore.DocumentStoreException;
import com.cloudant.sync.documentstore.DocumentStoreNotOpenedException;

public class DocumentStoreHelper {

    public static String getDoc(String dbName, String id, Context context) throws DocumentStoreException, DocumentNotFoundException {
        DocumentStore documentStore = getDocumentStore(dbName, context);
        DocumentRevision documentRevision = documentStore.database().read(id);
        Log.d("CLOUDANT", "Doc: " + documentRevision.getBody().toString());
        return documentRevision.getBody().toString();
    }

    private static DocumentStore getDocumentStore(String dbName, Context context) throws DocumentStoreNotOpenedException {
        return DocumentStore.getInstance(context.getDir(dbName, Context.MODE_PRIVATE));
    }
}