package com.example.ps4.firestore;

import android.util.Log;

import com.example.ps4.Models.Hotel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class RestaurantFirestoreManager {

    private static final String COLLECTION_NAME = "Restaurant";
    /* ContactsFirestoreManager object **/
    private static  RestaurantFirestoreManager restaurantFirestoreManager;
    /* Firestore objects */
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference restaurantCollectionReference;

    public static RestaurantFirestoreManager newInstance() {
        if (restaurantFirestoreManager == null) {
            restaurantFirestoreManager = new RestaurantFirestoreManager();
        }
        return restaurantFirestoreManager;
    }

    private RestaurantFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        restaurantCollectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }

    public void createDocument(Hotel hotel) {
        restaurantCollectionReference.add(hotel);
    }


    public void getAllRestaurants(OnCompleteListener<QuerySnapshot> onCompleteListener , String destination)
    {
        Log.d("sth" , "in get allrestau");
        restaurantCollectionReference.whereEqualTo("nom_ville",destination).get().addOnCompleteListener(onCompleteListener);
    }



}
