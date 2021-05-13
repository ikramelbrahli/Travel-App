package com.example.ps4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.ps4.Models.Review;
import com.example.ps4.firestore.HotelsFirestoreDbContract;
import com.example.ps4.firestore.ReservationFirestoreDB;
import com.example.ps4.firestore.ReviewFirestoreManager;
import com.example.ps4.firestore.VoyageFirestoreManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class RatingForm extends AppCompatActivity {
    private String document_id_reservation ;
    private String document_id_endroit ;
    private String nom_voyageur ;
    private String prenom_voyageur;

    private String rating ;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    private Review review = new Review();

    private EditText comment ;
    private RatingBar ratingbar ;
    private Button submit ;
    private ReviewFirestoreManager reviewFirestoreManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_form);
        Bundle bundle = getIntent().getExtras();
        document_id_endroit=bundle.getString("doc_id_endroit");
        document_id_reservation=bundle.getString("ReservationFirestoreDB.DOCUMENT_ID");
        review.setEndroitID(document_id_endroit);
        review.setReservationID(document_id_reservation);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        comment = findViewById(R.id.comment);
        review.setUserID(userID);
        ratingbar = findViewById(R.id.rating_bar);
      //  int rating_score =Integer.parseInt(String.valueOf(ratingbar.getRating()).trim());


       // int ratingbar_ = Integer.parseInt(String.valueOf(ratingbar.getRating()));
     //   review.setRating(rating_score);
        submit = findViewById(R.id.button9);
        submit.setOnClickListener(new RatingForm.SubmitOnClickListener());
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("User").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@androidx.annotation.Nullable DocumentSnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {
              nom_voyageur=  value.getString("nom");
                prenom_voyageur=value.getString("prenom");
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

                    }
                   /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();*/

                }
            };




    public class SubmitOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String comment_ = comment.getText().toString().trim();
            float score = ratingbar.getRating();
            review.setRating(score);
            review.setUser_nom(nom_voyageur);
            review.setUser_prenom(prenom_voyageur);
            review.setComment(comment_);
            reviewFirestoreManager = ReviewFirestoreManager.newInstance();
            Log.d("ik", "" + review.getComment() + review.getRating());
            reviewFirestoreManager.createDocument(review);
            startActivity(new Intent(getApplicationContext(), TripListActivity.class));
            finish();
        }
    }
}