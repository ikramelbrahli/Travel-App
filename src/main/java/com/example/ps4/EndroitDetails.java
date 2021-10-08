package com.example.ps4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ps4.Adapters.RecyclerViewAdapterReview;
import com.example.ps4.Adapters.RecyclerViewAdapterTrip;
import com.example.ps4.Models.Hotel;
import com.example.ps4.Models.Reservation;
import com.example.ps4.Models.Restaurant;
import com.example.ps4.Models.Review;
import com.example.ps4.Models.Voyage;
import com.example.ps4.firestore.HotelsFirestoreDbContract;
import com.example.ps4.firestore.HotelsFirestoreManager;
import com.example.ps4.firestore.ReservationFirestoreDB;
import com.example.ps4.firestore.ReservationFirestoreManager;
import com.example.ps4.firestore.RestaurantFirestoreDB;
import com.example.ps4.firestore.ReviewFirestoreManager;
import com.example.ps4.firestore.UserFirestoreDB;
import com.example.ps4.firestore.VoyageFirestoreDB;
import com.example.ps4.firestore.VoyageFirestoreManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.ps4.firestore.HotelsFirestoreDbContract.FIELD_HOTEL_NAME;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_DESTINATION;

public class EndroitDetails extends AppCompatActivity {
    private static final String TAG = null;
    private String destination ;//city
    private String document_id_voyage;
    private String document_id_endroit ;
    private String hotel_name ;//endroit
    private String description ; //description
    private int price ; //prix
    private String imageurl ;
    private Button AddReservation ;
    private Button feedback ;
    private Voyage voyage = new Voyage();
    private Hotel hotel = new Hotel ();
    private String document_id_reservation ;
    private Reservation reservation = new Reservation();
    private RecyclerView reviewListRecyclerView;
    private ReviewFirestoreManager reviewFirestoreManager;
    private List<Review> reviewList;
    private FloatingActionButton floatingActionButton2 ;

    private FirebaseFirestore firebaseFirestore;
    private CollectionReference hotelCollectionReference;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    private String type_endroit ;
    private String prix ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endroit_details);
        Bundle bundle = getIntent().getExtras();
        reviewFirestoreManager = ReviewFirestoreManager.newInstance();
        reviewListRecyclerView = findViewById(R.id.recyclerview);
        reviewListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        document_id_reservation = bundle.getString("ReservationFirestoreDB.DOCUMENT_ID");

        reservation.setDocumentId(document_id_reservation);
        document_id_voyage = bundle.getString(VoyageFirestoreDB.DOCUMENT_ID);

        reservation.setVoyageID(document_id_voyage);
        /*********/
        destination = bundle.getString(FIELD_DESTINATION);
        reservation.setDestination(destination);
        /**********/
        type_endroit=bundle.getString("type_endroit");

document_id_endroit = bundle.getString("doc_id_endroit");

            reservation.setEndroitID(document_id_endroit);
            hotel_name = bundle.getString(FIELD_HOTEL_NAME);
        prix= bundle.getString(HotelsFirestoreDbContract.FIELD_PRIX);
        description=bundle.getString(HotelsFirestoreDbContract.FIELD_DESCRIPTION);
            imageurl = bundle.getString(ReservationFirestoreDB.FIELD_IMAGE_URL);
        Log.d("id_doc_endroit", "doc_id_endroit" +document_id_endroit);
      /*  if(prix.equals(null)){
            prix="600";
        }*/
        description = bundle.getString(RestaurantFirestoreDB.FIELD_DESCRIPTION);
       /* if(description.equals(null)){
            description="Angle Rue Taha Hussein et rue Abou chouaib addoukkali Résidence ";
        }*/
            AddReservation = findViewById(R.id.button3);
            AddReservation.setOnClickListener(new AddReservationButtonOnClickListener());
            floatingActionButton2 = findViewById(R.id.floatingActionButton2);
            floatingActionButton2.setOnClickListener(new FeedbackButtonOnClickListener());
            TextView destination_view;
            destination_view = findViewById(R.id.textView10);
            destination_view.setText(destination);
            TextView endroit_view;
            TextView price ;
            price = findViewById(R.id.textView12);
            price.setText(String.valueOf(prix));
            endroit_view = findViewById(R.id.textView9);
            endroit_view.setText(hotel_name);
            TextView description_view;
            description_view = findViewById(R.id.textView11);
            description_view.setText(description);
            ImageView image;
            image = findViewById(R.id.imageView6);
            Picasso.get()
                    .load(imageurl)
                    .into(image, new Callback() {
                        @Override
                        public void onSuccess() {
                            // will load image
                        }

                        @Override
                        public void onError(Exception e) {

                        }


                    });

        Log.d(TAG, "endroit details" + reservation.getEndroitID() + reservation.getVoyageID() + reservation.getDestination() + imageurl + hotel_name);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("User").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@androidx.annotation.Nullable DocumentSnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {

            }
        });


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemReselectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemReselectedListener navListener =
            new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null ;
                    switch(item.getItemId()){
                        case R.id.nav_trips :
                            startActivity(new Intent(getApplicationContext(), TripListActivity.class));
                           /* selectedFragment = new HomeFragment();
                            Log.d("HotelListMainActivity", "in frag nav");*/
                            break ;
                        case R.id.nav_logout :
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                            finish();
                            break;

                        case R.id.nav_cities :

                            startActivity(new Intent(getApplicationContext(), CitiesActivity.class));

                            finish();
                            break;
                        case R.id.nav_recommendations :

                            startActivity(new Intent(getApplicationContext(), RecommendationActivity.class));

                            finish();
                            break;

                    }
                   /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();*/

                }
            };

    @Override
    protected void onStart() {
        super.onStart();
        // Populate the ContactListMainActivity with the available data
        reviewFirestoreManager.getAllReviews(new EndroitDetails.GetAllReviewsOnCompleteListener() , document_id_endroit);

    }

    public class GetAllReviewsOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {

            if (task.isSuccessful()) {

                // Get the query snapshot from the task result
                QuerySnapshot querySnapshot = task.getResult();

                if (querySnapshot != null) {
                    reviewList = querySnapshot.toObjects(Review.class);
                    Log.d("endroit details", "review size" + reviewList.size());
                    if(reviewList.size()!=0) {
                        populateReviewRecyclerView(reviewList);
                    }
                    else{
                        Log.d("endroit details", "no reviews" + reviewList.size());
                    }

                }

            } else {
                Log.w(TAG, "Error getting documents: ", task.getException());
            }

            if (reviewList == null || reviewList.size() == 0) {
//
            }
        }

        private void populateReviewRecyclerView(List<Review> reviewList) {
            // Set the contactListMainRecyclerViewAdapter in the contactListRecyclerView
            Log.d("", "populate" + reviewList.size());
            RecyclerViewAdapterReview reviewListMainRecyclerViewAdapter = new RecyclerViewAdapterReview(reviewList, new EndroitDetails.GetAllReviewsOnCompleteListener.ReviewListRecyclerViewOnItemClickListener());
            reviewListRecyclerView.setAdapter(reviewListMainRecyclerViewAdapter);
        }

        /**
         * Called when the user clicks on an item of the contact list
         */
        public class ReviewListRecyclerViewOnItemClickListener implements View.OnClickListener {


            @Override
            public void onClick(View view) {

                int itemIndex = reviewListRecyclerView.indexOfChild(view);
                Log.d("ContactListMainActivity", "" + itemIndex);
                Review contact = reviewList.get(itemIndex);
                Intent intent = new Intent();
                intent.setClass(EndroitDetails.this, EndroitDetails.class);

                startActivity(intent);
            }
        }

    }

        private class AddReservationButtonOnClickListener implements View.OnClickListener {
        public void onClick(View view) {
            voyage.setDocumentId(document_id_voyage);
            voyage.setDestination(destination);

            Intent intent = new Intent();
            intent.setClass(EndroitDetails.this, ReservationFormActivity.class);
            Hotel hotel = new Hotel();
            hotel.setDocumentId(reservation.getEndroitID());
            intent.putExtra("doc_id_endroit",hotel.getDocumentId());
            Log.d("endroit details", "review size" +hotel.getDocumentId());
            intent.putExtra(HotelsFirestoreDbContract.FIELD_HOTEL_NAME, hotel_name);
            intent.putExtra(HotelsFirestoreDbContract.FIELD_PRIX, prix);
            intent.putExtra(HotelsFirestoreDbContract.FIELD_DESCRIPTION, description);
            intent.putExtra(VoyageFirestoreDB.DOCUMENT_ID, voyage.getDocumentId());

            intent.putExtra(VoyageFirestoreDB.FIELD_DESTINATION, voyage.getDestination());
            intent.putExtra(VoyageFirestoreDB.FIELD_NOM_VOYAGE, voyage.getNom_voyage());
            intent.putExtra(HotelsFirestoreDbContract.FIELD_IMAGE_URL, imageurl);

            startActivity(intent);
        }
    }

    private class FeedbackButtonOnClickListener implements View.OnClickListener {
        public void onClick(View view) {

            Intent intent = new Intent();
            intent.setClass(EndroitDetails.this, RatingForm.class);
            intent.putExtra("doc_id_endroit",document_id_endroit);//endroitid
            intent.putExtra(HotelsFirestoreDbContract.FIELD_HOTEL_NAME, hotel_name);
            intent.putExtra("ReservationFirestoreDB.DOCUMENT_ID", document_id_reservation);//reserv id
            Log.d("endroit details", "hotel name end details" + hotel_name + " "+document_id_endroit + " "+document_id_reservation );
            intent.putExtra(UserFirestoreDB.FIELD_USER_ID,userID);//userid
            startActivity(intent);
        }
    }
}