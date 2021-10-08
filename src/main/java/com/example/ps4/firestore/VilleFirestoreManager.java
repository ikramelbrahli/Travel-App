package com.example.ps4.firestore;

import com.example.ps4.Models.Ville;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;



public class VilleFirestoreManager {
    /* ContactsFirestoreManager object **/
    private static VilleFirestoreManager villefirestoremanager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference villeCollectionReference;
    public static final String COLLECTION_NAME = "Ville";

    public static VilleFirestoreManager newInstance() {
        if (villefirestoremanager == null) {
            villefirestoremanager = new VilleFirestoreManager();
        }
        return villefirestoremanager;
    }
    private VilleFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        villeCollectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }
    public void getAllVilles(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {

        villeCollectionReference.get().addOnCompleteListener(onCompleteListener);

    }
    public void createDocument(Ville contact) {
        villeCollectionReference.add(contact);
    }
    public void sendVilleBulk() {

        // Create a new Contact document map of values and add it to the collection
        createDocument(new Ville("London", "England" ));


    }




}
