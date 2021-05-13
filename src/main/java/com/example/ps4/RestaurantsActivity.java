package com.example.ps4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps4.Adapters.HotelListMainRecyclerViewAdapter;
import com.example.ps4.Adapters.RestaurantListMainRecyclerViewAdapter;
import com.example.ps4.Models.Hotel;
import com.example.ps4.Models.Restaurant;
import com.example.ps4.Models.Voyage;
import com.example.ps4.firestore.HotelsFirestoreDbContract;
import com.example.ps4.firestore.RestaurantFirestoreDB;
import com.example.ps4.firestore.RestaurantFirestoreManager;
import com.example.ps4.firestore.VoyageFirestoreDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.example.ps4.firestore.HotelsFirestoreDbContract.FIELD_HOTEL_NAME;
import static com.example.ps4.firestore.RestaurantFirestoreDB.FIELD_RESTAURANT_NAME;
import static com.example.ps4.firestore.VoyageFirestoreDB.DOCUMENT_ID;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_DESTINATION;

public class RestaurantsActivity extends AppCompatActivity {
    private static final String TAG = null ;
    ListView listView;
    private List<Restaurant> restaurantList ;
    private RestaurantFirestoreManager restaurantsFirestoreManager = RestaurantFirestoreManager.newInstance();
    private String destination ;
    private Voyage voyage = new Voyage();
    private String doc_id_voyage ;
    private RecyclerView restaurantsListRecyclerView;
    private String type_endroit = "restaurant";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        Bundle bundle = getIntent().getExtras();
        destination = bundle.getString(VoyageFirestoreDB.FIELD_DESTINATION);
        Log.d("um", "um" + destination);
       // voyage.setDestination("Casablanca");
        doc_id_voyage = bundle.getString(DOCUMENT_ID);
        voyage.setDocumentId(doc_id_voyage);
        voyage.setDestination(destination);
         restaurantsListRecyclerView = findViewById(R.id.restaurantsListRecyclerView);
        restaurantsListRecyclerView.setLayoutManager(new LinearLayoutManager(this));



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




    @Override
    protected void onStart() {
        super.onStart();

        // Populate the HotelListMainActivity with the available data
        // TODO: 3.3 Reading Contacts

        // Populate the ContactListMainActivity with the available data
        restaurantsFirestoreManager.getAllRestaurants(new RestaurantsActivity.GetAllRestaurantsOnCompleteListener(), "Casablanca");
        Log.d("um", "um" + destination);


    }

    public class GetAllRestaurantsOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {

            if (task.isSuccessful()) {

                // Get the query snapshot from the task result
                QuerySnapshot querySnapshot = task.getResult();

                if (querySnapshot != null) {

                    // Get the contact list from the query snapshot
                    restaurantList = querySnapshot.toObjects(Restaurant.class);

                    Log.d("HotelListMainActivity", "done" + restaurantList.size());
                    populateRestaurantRecyclerView(restaurantList);
                    Log.d("HotelListMainActivity", "done" );
                }

            } else {
                Log.w(TAG, "Error getting documents: ", task.getException());
            }


        }

    }


    private void populateRestaurantRecyclerView(List<Restaurant> contactList) {

        // TODO: 3.4 Populating the Main Screen


        // Set the contactListMainRecyclerViewAdapter in the contactListRecyclerView
        RestaurantListMainRecyclerViewAdapter restaurantListMainRecyclerViewAdapter = new RestaurantListMainRecyclerViewAdapter(restaurantList, new RestaurantListRecyclerViewOnItemClickListener());
        restaurantsListRecyclerView.setAdapter(restaurantListMainRecyclerViewAdapter);
        Log.d("HotelListMainActivity", "done2" );
    }

    public class RestaurantListRecyclerViewOnItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            int itemIndex = restaurantsListRecyclerView.indexOfChild(view);
            Log.d("HotelListMainActivity", "" + itemIndex);
            Restaurant hotel = restaurantList.get(itemIndex);
            Intent intent = new Intent();
            intent.setClass(RestaurantsActivity.this, RestaurantDetails.class); /******* This should take you to details of an endroit , check getTripdetails pass id_endroit , details endroit should have endroit details , reviews of users, add a reservation button that takes u to form , for rating , user clicks on a reservation from the list , add a ui abt reservation details should pass idendroit , idreserv , iduser ,( idvoyage just in case , ) , add button there dyall add a review ,  takes u to form  */
            intent.putExtra("doc_id_endroit", hotel.getDocumentId());
            intent.putExtra(FIELD_RESTAURANT_NAME,  hotel.getNom_restaurant());
         //  intent.putExtra(RestaurantFirestoreDB.FIELD_PRIX, hotel.getPrix());
            Log.d("HotelListMainActivity", FIELD_RESTAURANT_NAME+ hotel.getNom_restaurant());
            intent.putExtra(RestaurantFirestoreDB.FIELD_IMAGE_URL, hotel.getImageURI());
            intent.putExtra(RestaurantFirestoreDB.FIELD_DESCRIPTION, hotel.getDescription());
           // intent.putExtra(RestaurantFirestoreDB.FIELD_PRIX, String.valueOf(hotel.getPrix()));
            intent.putExtra(VoyageFirestoreDB.DOCUMENT_ID, voyage.getDocumentId());
            intent.putExtra(VoyageFirestoreDB.FIELD_BUDGET, voyage.getBudget());
            intent.putExtra(VoyageFirestoreDB.FIELD_TYPE_VOYAGE , voyage.getType_voyage());
            intent.putExtra(VoyageFirestoreDB.FIELD_DATE_FIN, voyage.getDate_fin());
            intent.putExtra(VoyageFirestoreDB.FIELD_DATE_DEBUT, voyage.getDate_debut());
            intent.putExtra(VoyageFirestoreDB.FIELD_DESTINATION, voyage.getDestination());
            Log.d("HotelListMainActivity", "trip_destination" + voyage.getDestination());
            intent.putExtra(VoyageFirestoreDB.FIELD_NOM_VOYAGE, voyage.getNom_voyage());
            intent.putExtra("type_endroit" , type_endroit);
            startActivity(intent);



        }
    }

}