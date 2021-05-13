package com.example.ps4.firestore;



import com.example.ps4.Models.Hotel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class HotelsFirestoreManager {


    private static final String COLLECTION_NAME = "Hotel";
    /* ContactsFirestoreManager object **/
    private static  HotelsFirestoreManager hotelsFirestoreManager;
    /* Firestore objects */
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference hotelsCollectionReference;

    public static HotelsFirestoreManager newInstance() {
        if (hotelsFirestoreManager == null) {
            hotelsFirestoreManager = new HotelsFirestoreManager();
        }
        return hotelsFirestoreManager;
    }

    private HotelsFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        hotelsCollectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }

    public void createDocument(Hotel hotel) {
        hotelsCollectionReference.add(hotel);
    }


    public void getAllHotels(OnCompleteListener<QuerySnapshot> onCompleteListener , String destination)
    {
        hotelsCollectionReference.whereEqualTo("nom_ville",destination).get().addOnCompleteListener(onCompleteListener);
    }

    public void updateHotel(Hotel hotel) {
        String documentId = hotel.getDocumentId();
        DocumentReference documentReference = hotelsCollectionReference.document(documentId);
        documentReference.set(hotel);
    }

    public void deleteHotel(String documentId) {
        DocumentReference documentReference = hotelsCollectionReference.document(documentId);
        documentReference.delete();
    }

  /*  public void sendHotelsBulk() {

        // Create a new Contact document map of values and add it to the collection
        createDocument(new Hotel("Wazo hotel", "marrakech", "https://cf.bstatic.com/xdata/images/hotel/square200/95371059.webp?k=48c509b5e50e13aa8ba79431ac9b63a33b6383c70a5876f6fd01f88204a41416&o="));

        // Create a new Contact document map of values and add it to the collection
        createDocument(new Hotel("Le Semiramis", "marrakech", "https://cf.bstatic.com/xdata/images/hotel/square200/24621060.webp?k=cd2bf93a8f58640fc81c085630ed012b2746303a41437c38c045d4c2a53729e7&o="));

        // Create a new Contact document map of values and add it to the collection
        createDocument(new Hotel("Palm Plaza", "marrakech", "https://cf.bstatic.com/xdata/images/hotel/square200/97217147.webp?k=06e578c52c3f1c9ca95c761104583b5c60c34c6a238b8296442609f6eec278ec&o="));

        // Create a new Contact document map of values and add it to the collection
        createDocument(new Hotel("Atlas Asni", "marrakech", "https://cf.bstatic.com/xdata/images/hotel/square200/7249450.webp?k=f07fc713a01f2638c0fe16d889e63e135e7ee50aeb78b32456bc40985756e72b&o="));
    }
*/

}
