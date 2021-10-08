package com.example.ps4.firestore;

import com.example.ps4.Models.Restaurant;

public class RestaurantFirestoreDB {
    // Root collection name
    public static final String COLLECTION_NAME = "Restaurant";

    // Document ID
    public static final String DOCUMENT_ID = "document_id";

    // Document field names
    public static final String FIELD_RESTAURANT_NAME = "nom_restaurant";
    public static final String FIELD_CITY_NAME = "nom_ville";
    public static final String FIELD_IMAGE_URL = "imageURI";
    public static final String FIELD_DESCRIPTION= "description";
    public static final String FIELD_ADRESSE= "adresse";

    private RestaurantFirestoreDB(){}
}
