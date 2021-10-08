package com.example.ps4.firestore;

import android.util.Log;

import com.example.ps4.Models.Ville;
import com.example.ps4.Models.Voyage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class VoyageFirestoreManager {
    private static final String TAG = null;
    private static VoyageFirestoreManager voyagefirestoremanager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference voyageCollectionReference;
    public static final String COLLECTION_NAME = "Voyage";


    public static VoyageFirestoreManager newInstance() {
        if (voyagefirestoremanager == null) {
            voyagefirestoremanager = new VoyageFirestoreManager();
        }
        return voyagefirestoremanager;
    }
    private VoyageFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        voyageCollectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }
    public void getAllVoyages(OnCompleteListener<QuerySnapshot> onCompleteListener , String userid)
    {
        Log.d(TAG,  "test");
        voyageCollectionReference.whereEqualTo("user_id",userid).get().addOnCompleteListener(onCompleteListener);

    }
    public void createDocument(Voyage voyage) {

        voyageCollectionReference.add(voyage);
    }
    public void deleteContact(String documentId) {
        DocumentReference documentReference = voyageCollectionReference.document(documentId);
        documentReference.delete();
    }
    public void updateDocument(Voyage contact) {
        String documentId = contact.getDocumentId();
        DocumentReference documentReference = voyageCollectionReference.document(documentId);
        documentReference.set(contact);
    }


}
