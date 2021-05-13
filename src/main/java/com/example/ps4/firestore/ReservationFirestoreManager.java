package com.example.ps4.firestore;

import android.util.Log;

import com.example.ps4.Models.Reservation;
import com.example.ps4.Models.Voyage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ReservationFirestoreManager {
    private static final String TAG = null;
    private static  ReservationFirestoreManager voyagefirestoremanager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference voyageCollectionReference;
    public static final String COLLECTION_NAME = "Reservation";


    public static ReservationFirestoreManager newInstance() {
        if (voyagefirestoremanager == null) {
            voyagefirestoremanager = new  ReservationFirestoreManager();
        }
        return voyagefirestoremanager;
    }
    private ReservationFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        voyageCollectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }
    public void getAllReservations(OnCompleteListener<QuerySnapshot> onCompleteListener , String voyageid)
    {
        Log.d(TAG,"test all reserv" + voyageid);
        voyageCollectionReference.whereEqualTo("voyageID", voyageid).get().addOnCompleteListener(onCompleteListener);
    }
    public void createDocument(Reservation voyage) {

        voyageCollectionReference.add(voyage);
    }

    public void deleteDocument(String documentId){
        DocumentReference documentReference = voyageCollectionReference.document(documentId);
        documentReference.delete();
    }
}
