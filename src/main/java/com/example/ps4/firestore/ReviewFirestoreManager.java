package com.example.ps4.firestore;

import android.util.Log;

import com.example.ps4.Models.Hotel;
import com.example.ps4.Models.Reservation;
import com.example.ps4.Models.Review;
import com.example.ps4.RecommendationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ReviewFirestoreManager {
    private static final String TAG = null;
    private static  ReviewFirestoreManager voyagefirestoremanager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference voyageCollectionReference;
    public static final String COLLECTION_NAME = "Review";


    public static ReviewFirestoreManager newInstance() {
        if (voyagefirestoremanager == null) {
            voyagefirestoremanager = new  ReviewFirestoreManager();
        }
        return voyagefirestoremanager;
    }
    private ReviewFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        voyageCollectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }



    public void getAllReviews(OnCompleteListener<QuerySnapshot> onCompleteListener , String endroitid)
    {
        Log.d(TAG,"test all reserv" + endroitid);
        voyageCollectionReference.whereEqualTo("endroitID", endroitid).get().addOnCompleteListener(onCompleteListener);
    }
    public void getPositiveReviews(OnCompleteListener<QuerySnapshot> onCompleteListener ){

        Log.d(TAG,"test all in");
        voyageCollectionReference.orderBy("pourcentage", Query.Direction.DESCENDING).get().addOnCompleteListener(onCompleteListener);

    }
    public void createDocument(Review voyage) {

        voyageCollectionReference.add(voyage);
    }
}
