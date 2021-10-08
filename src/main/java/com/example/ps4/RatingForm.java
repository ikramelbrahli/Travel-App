package com.example.ps4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.ps4.Models.Result;
import com.example.ps4.Models.Review;
import com.example.ps4.Models.TextClassificationClient;
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

import java.util.List;

public class RatingForm extends AppCompatActivity {
    private static final String TAG = null ;
    private String document_id_reservation ;
    private String document_id_endroit ;
    private String nom_voyageur ;
    private String prenom_voyageur;
    private Handler handler;

    private String rating ;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    private Review review = new Review();

    private EditText comment ;
    private RatingBar ratingbar ;
    private Button submit ;
    private ReviewFirestoreManager reviewFirestoreManager;
    private TextClassificationClient client;
    private float pourcentage ;
    private String type;
    private Review rv = new Review();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_form);
        Bundle bundle = getIntent().getExtras();
        client = new TextClassificationClient(getApplicationContext());
        handler = new Handler();
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
        Log.v(TAG, "onStart");
        handler.post(
                () -> {
                    client.load();
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop");
        handler.post(
                () -> {
                    client.unload();
                });
    }

    public class SubmitOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            String comment_ = comment.getText().toString().trim();
            classify(comment_);
            /*float score = ratingbar.getRating();
            review.setRating(score);
            review.setUser_nom(nom_voyageur);
            review.setUser_prenom(prenom_voyageur);
            review.setComment(comment_);
            System.out.println("on click"+ rv.getType()+" "+rv.getPourcentage());
            reviewFirestoreManager = ReviewFirestoreManager.newInstance();
            Log.d("ik", "" + review.getComment() + review.getPourcentage());
            reviewFirestoreManager.createDocument(review);
            startActivity(new Intent(getApplicationContext(), TripListActivity.class));
            finish();*/
        }
    }
    /** Send input text to TextClassificationClient and get the classify messages. */
    private void classify(final String text) {

        handler.post(
                () -> {
                    // Run text classification with TF Lite.
                    List<Result> results = client.classify(text);
                    for (int i = 0; i < results.size(); i++) {
                        Result result = results.get(i);
                        Log.d("result_classify", result.getTitle() + " " + result.getId());

                        if(result.getConfidence()>0.5){
                            System.out.println(result.getTitle()+result.getConfidence()* 100.0f);
                            pourcentage=result.getConfidence()* 100.0f;
                            System.out.println(pourcentage);
                            type=result.getTitle();
                            rv.setPourcentage(result.getConfidence()* 100.0f);
                            rv.setType(result.getTitle());
                            System.out.println("THIS IS RV TEST"+ rv.getType() + "  " + rv.getPourcentage());
                            float score = ratingbar.getRating();
                            review.setRating(score);
                            review.setUser_nom(nom_voyageur);
                            review.setUser_prenom(prenom_voyageur);
                            review.setComment(text);
                            review.setType(rv.getType());
                            review.setPourcentage(rv.getPourcentage());
                            System.out.println("on click"+ rv.getType()+" "+rv.getPourcentage());
                            reviewFirestoreManager = ReviewFirestoreManager.newInstance();
                            Log.d("ik", "" + review.getComment() + review.getPourcentage());
                            reviewFirestoreManager.createDocument(review);
                            startActivity(new Intent(getApplicationContext(), TripListActivity.class));
                            finish();
                        }
                    }



                });



    }

    /** Show classification result on the screen. */
    private void showResult(final String inputText, final List<Result> results) {
        // Run on UI thread as we'll updating our app UI
        runOnUiThread(
                () -> {
                    String textToShow = "Input: " + inputText + "\nOutput:\n";
                    for (int i = 0; i < results.size(); i++) {
                        Result result = results.get(i);
                        Log.d("result", result.getTitle() + " " + result.getId());
                        textToShow += String.format("    %s: %s\n", result.getTitle(), result.getConfidence());
                        if(result.getConfidence()>0.5){
                            System.out.println(result.getTitle()+result.getConfidence()* 100.0f);
                            pourcentage=result.getConfidence()* 100.0f;
                            System.out.println(pourcentage);
                            type=result.getTitle();
                            review.setPourcentage(result.getConfidence()* 100.0f);
                            review.setType(result.getTitle());
                        }
                    }
                    textToShow += "---------\n";


                });
    }
}