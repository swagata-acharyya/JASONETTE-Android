package com.jasonette.seed.Cloudant;

import android.content.Context;

import com.cloudant.sync.documentstore.DocumentNotFoundException;
import com.cloudant.sync.documentstore.DocumentRevision;
import com.cloudant.sync.documentstore.DocumentStore;
import com.cloudant.sync.documentstore.DocumentStoreException;
import com.cloudant.sync.documentstore.DocumentStoreNotOpenedException;

import java.util.List;

public class DocumentStoreHelper {

    /**
     * Retrieves document from database
     *
     * @param dbName  name of DB
     * @param id      ID of the document
     * @param context {@link Context} object
     * @return document as {@link String}
     * @throws DocumentStoreException
     * @throws DocumentNotFoundException
     */
    public static String getDoc(String dbName, String id, Context context) throws DocumentStoreException, DocumentNotFoundException {
        DocumentStore documentStore = getDocumentStore(dbName, context);
        DocumentRevision documentRevision = documentStore.database().read(id);
        return documentRevision.getBody().toString();
    }

    /**
     * Retrieves all documents from database
     *
     * @param dbName  name of database
     * @param context {@link Context} object
     * @return {@link List<DocumentRevision>}s
     * @throws DocumentStoreException
     */
    public static List<DocumentRevision> getAllDocs(String dbName, Context context) throws DocumentStoreException {
        DocumentStore db = getDocumentStore(dbName, context);
        int nDocs = db.database().getDocumentCount();
        return db.database().read(0, nDocs, true);
    }

    private static DocumentStore getDocumentStore(String dbName, Context context) throws DocumentStoreNotOpenedException {
        return DocumentStore.getInstance(context.getDir(dbName, Context.MODE_PRIVATE));
    }
}