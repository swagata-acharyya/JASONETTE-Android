package com.jasonette.seed.Cloudant;

import android.content.Context;

import com.cloudant.sync.documentstore.DocumentNotFoundException;
import com.cloudant.sync.documentstore.DocumentRevision;
import com.cloudant.sync.documentstore.DocumentStore;
import com.cloudant.sync.documentstore.DocumentStoreException;
import com.cloudant.sync.documentstore.DocumentStoreNotOpenedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentStoreHelper {

    private static Map<String, DocumentStore> documentStoreMap = new HashMap<>();

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

    /**
     * Gives cached instance of {@link DocumentStore} if exists, otherwise returns a new instance
     *
     * @param dbName  name of the DB
     * @param context {@link Context} object
     * @return {@link DocumentStore} instance for the given DB
     * @throws DocumentStoreNotOpenedException
     */
    public static DocumentStore getDocumentStore(String dbName, Context context) throws DocumentStoreNotOpenedException {
        DocumentStore documentStore;
        if (documentStoreMap.containsKey(dbName)) {
            documentStore = documentStoreMap.get(dbName);
        } else {
            documentStore = DocumentStore.getInstance(context.getDir(dbName, Context.MODE_PRIVATE));
            documentStoreMap.put(dbName, documentStore);
        }
        return documentStore;
    }
}