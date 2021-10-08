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
import android.widget.ListView;

import com.example.ps4.Adapters.RecyclerViewAdapterRecommendationsRestaurant;
import com.example.ps4.Models.Hotel;
import com.example.ps4.Models.Recommendation;
import com.example.ps4.Models.Restaurant;
import com.example.ps4.Models.Review;
import com.example.ps4.firestore.HotelsFirestoreManager;
import com.example.ps4.firestore.ReviewFirestoreManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.eazegraph.lib.charts.PieChart;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class RecommendationRestaurant extends AppCompatActivity {

    private static final String TAG = null;
    private ReviewFirestoreManager reviewFirestoreManager;
    private HotelsFirestoreManager hotelsFirestoreManager;
    private Button click;
    private PieChart chart;
    private int i1 = 15;
    private int i2 = 25;
    private int i3 = 35;
    private int i4 = 45;
    private float sum = 0;
    private float cpt_ = 0;
    private float moyenne = 0;
    ListView listView;
    private ArrayList<String> ids = new ArrayList<>();
    private ArrayList<String> newids = new ArrayList<>();
    private List<Review> hotelList;
    private List<Hotel> hotels;
    private ArrayList<Restaurant> sth = new ArrayList<Restaurant>();
    FirebaseFirestore fstore;
    private ArrayList<Recommendation> rec = new ArrayList<>();
    private ArrayList<Recommendation> recommendationList = new ArrayList<>();
    private RecyclerView reservationListRecyclerView;
    private List<String> Id;
    private Button restaurant;
    private Button hotel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_restaurant);
        reviewFirestoreManager = ReviewFirestoreManager.newInstance();
        hotelsFirestoreManager = HotelsFirestoreManager.newInstance();
        //   reservationListRecyclerView = findViewById(R.id.recyclerview);
//        reservationListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fstore = FirebaseFirestore.getInstance();
        listView = findViewById(R.id.listView);
        reservationListRecyclerView = findViewById(R.id.recyclerview2);
        reservationListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hotel = findViewById(R.id.hotel);
        restaurant = findViewById(R.id.restaurant);
        hotel.setOnClickListener(new RecommendationRestaurant.HotelOnClickListener());
        restaurant.setOnClickListener(new RecommendationRestaurant.RestaurantOnClickListener());

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
        reviewFirestoreManager.getPositiveReviews(new RecommendationRestaurant.GetAllPositiveReviewsOnCompleteListener());
    }

    public class GetAllPositiveReviewsOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            FirebaseFirestore ref = FirebaseFirestore.getInstance();
            if (task.isSuccessful()) {
                // Get the query snapshot from the task result
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    // Get the contact list from the query snapshot
                    hotelList = querySnapshot.toObjects(Review.class);
                    float sum = 0;
                    int cpt = 0;
                    for (int i = 0; i < hotelList.size(); i++) {
                        Review hotel = hotelList.get(i);
                        System.out.println(hotelList.size());
                        ids.add(hotel.getEndroitID());
                        ids = new ArrayList<String>(new LinkedHashSet<String>(ids));
                        for (int j = 0; j < ids.size(); j++) {
                            System.out.println("equals" + ids.get(j) + ids.size());
                        }
                    }

                    for (int m = 0; m < ids.size(); m++) {

                        for (int k = 0; k < hotelList.size(); k++) {

                            if (ids.get(m).equals(hotelList.get(k).getEndroitID())) {
                                sum = sum + hotelList.get(k).getPourcentage();
                                cpt_ = cpt_ + 1;


                            }

                        }
                        System.out.println("compteur" + cpt_);
                        Recommendation rec_ = new Recommendation();
                        rec_.setId(ids.get(m));
                        rec_.setMoyenne(sum / cpt_);
                        rec.add(rec_);

                    }
                    for (int p = 0; p < rec.size(); p++) {
                        System.out.println(rec.get(p).getMoyenne());
                        newids.add(rec.get(p).getId());
                    }
                    System.out.println("in");

                    ref.collection("Restaurant").whereIn(FieldPath.documentId(), newids).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    Restaurant htl = new Restaurant();
                                    htl.setNom_restaurant((String) document.get("nom_hotel"));
                                    htl.setDescription((String) document.get("description"));
                                    htl.setDocumentId(document.getId());
                                    sth.add(htl);
                                    System.out.println("sth" + sth.size());
                                    Recommendation recom = new Recommendation();
                                    recom.setId(document.getId());
                                    recom.setDescription((String) document.get("description"));
                                    recom.setImageURI((String) document.get("imageURI"));
                                    recom.setNom_hotel((String) document.get("nom_restaurant"));
                                    recom.setNom_ville((String) document.get("nom_ville"));
                                    for (int p = 0; p < rec.size(); p++) {
                                        if (rec.get(p).getId().equals(recom.getId())) {
                                            recom.setMoyenne(rec.get(p).getMoyenne());
                                        }
                                    }
                                    recommendationList.add(recom);
                                    for (int k = 0; k < recommendationList.size(); k++) {
                                        System.out.println("recommandation list RESTAURANT" + recommendationList.get(k).getId() + recommendationList.get(k).getMoyenne());
                                    }
                                    populateTopReviewsRecyclerView(recommendationList);
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

                }
            } else {
            }
        }


        private void populateTopReviewsRecyclerView(List<Recommendation> recommendationList) {

            RecyclerViewAdapterRecommendationsRestaurant reservationListMainRecyclerViewAdapter = new RecyclerViewAdapterRecommendationsRestaurant(recommendationList, new RecommendationRestaurant.GetAllPositiveReviewsOnCompleteListener.RecommendationsListRecyclerViewOnItemClickListener());
            reservationListRecyclerView.setAdapter(reservationListMainRecyclerViewAdapter);      // Set the contactListMainRecyclerViewAdapter in the contactListRecyclerView

        }

        public class RecommendationsListRecyclerViewOnItemClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {

            }
        }
    }
    public class HotelOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), RecommendationActivity.class));
            finish();
        }
    }

    public class RestaurantOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), RecommendationRestaurant.class));
            finish();
        }
    }
}