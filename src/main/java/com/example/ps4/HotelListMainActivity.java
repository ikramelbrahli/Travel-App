package com.example.ps4;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps4.Adapters.HotelListMainRecyclerViewAdapter;
import com.example.ps4.Models.Hotel;
import com.example.ps4.Models.Restaurant;
import com.example.ps4.Models.Voyage;
import com.example.ps4.firestore.HotelsFirestoreDbContract;
import com.example.ps4.firestore.HotelsFirestoreManager;
import com.example.ps4.firestore.RestaurantFirestoreManager;
import com.example.ps4.firestore.VoyageFirestoreDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static com.example.ps4.firestore.HotelsFirestoreDbContract.FIELD_HOTEL_NAME;
import static com.example.ps4.firestore.VoyageFirestoreDB.DOCUMENT_ID;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_DESTINATION;

public class HotelListMainActivity extends AppCompatActivity {
    private static String TAG = HotelListMainActivity.class.toString();


    private String destination ;
    /* Widgets */
    private Toolbar toolbar;
    private RecyclerView hotelListRecyclerView;
    private Button add_to_trip ;
    private Voyage voyage = new Voyage() ;
    private String activity_type ;

    private FloatingActionButton sendBulkDataFloatingButton;
    private FloatingActionButton addFloatingButton;
    private TextView emptyTextView;
    private String doc_id_voyage ;
    /* Content objects */
    private List<Hotel> hotelList;
    private List<Restaurant> restaurantList ;

    /* Repository reference */
    private HotelsFirestoreManager hotelsFirestoreManager;
    private RestaurantFirestoreManager restaurantsFirestoreManager;
    private String type_endroit = "hotel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_list_main_activity);
        Bundle bundle = getIntent().getExtras();

        /*********/
        destination = bundle.getString(FIELD_DESTINATION);
        voyage.setDestination(destination);

        doc_id_voyage = bundle.getString(DOCUMENT_ID);
        voyage.setDocumentId(doc_id_voyage);
        voyage.setDestination(destination);
        activity_type = bundle.getString("type_activity");
        hotelsFirestoreManager = HotelsFirestoreManager.newInstance();
        hotelListRecyclerView = findViewById(R.id.hotelListRecyclerView);
        hotelListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
    hotelsFirestoreManager.getAllHotels(new HotelListMainActivity.GetAllHotelsOnCompleteListener(), destination);


    }

    private class GetAllHotelsOnCompleteListener implements OnCompleteListener<QuerySnapshot> {


        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {

            if (task.isSuccessful()) {

                // Get the query snapshot from the task result
                QuerySnapshot querySnapshot = task.getResult();

                if (querySnapshot != null) {

                    // Get the contact list from the query snapshot
                    hotelList = querySnapshot.toObjects(Hotel.class);

                    Log.d("HotelListMainActivity", "done" + hotelList.size());
                    populateHotelRecyclerView(hotelList);
                    Log.d("HotelListMainActivity", "done" );
                }

            } else {
                Log.w(TAG, "Error getting documents: ", task.getException());
            }


        }


    }




    /** Sets the contact List in the Adapter to populate the RecyclerView */
    private void populateHotelRecyclerView(List<Hotel> contactList) {

        // TODO: 3.4 Populating the Main Screen


        // Set the contactListMainRecyclerViewAdapter in the contactListRecyclerView
        HotelListMainRecyclerViewAdapter hotelListMainRecyclerViewAdapter = new HotelListMainRecyclerViewAdapter(hotelList, new com.example.ps4.HotelListMainActivity.HotelListRecyclerViewOnItemClickListener());
        hotelListRecyclerView.setAdapter(hotelListMainRecyclerViewAdapter);
        Log.d("HotelListMainActivity", "done2" );
    }

    /** Called when the user clicks on an item of the contact list */
    public class HotelListRecyclerViewOnItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            int itemIndex = hotelListRecyclerView.indexOfChild(view);
            Log.d("HotelListMainActivity", "" + itemIndex);
            Hotel hotel = hotelList.get(itemIndex);
            Intent intent = new Intent();
            intent.setClass(HotelListMainActivity.this, EndroitDetails.class); /******* This should take you to details of an endroit , check getTripdetails pass id_endroit , details endroit should have endroit details , reviews of users, add a reservation button that takes u to form , for rating , user clicks on a reservation from the list , add a ui abt reservation details should pass idendroit , idreserv , iduser ,( idvoyage just in case , ) , add button there dyall add a review ,  takes u to form  */

            intent.putExtra("doc_id_endroit", hotel.getDocumentId());
            intent.putExtra(FIELD_HOTEL_NAME,  hotel.getNom_hotel());
            intent.putExtra(HotelsFirestoreDbContract.FIELD_PRIX, hotel.getPrix());
            Log.d("HotelListMainActivity", FIELD_HOTEL_NAME + hotel.getNom_hotel());
            intent.putExtra(HotelsFirestoreDbContract.FIELD_IMAGE_URL, hotel.getImageURI());
            intent.putExtra(HotelsFirestoreDbContract.FIELD_DESCRIPTION, hotel.getDescription());
            intent.putExtra(HotelsFirestoreDbContract.FIELD_PRIX, String.valueOf(hotel.getPrix()));
            intent.putExtra(VoyageFirestoreDB.DOCUMENT_ID, voyage.getDocumentId());
            intent.putExtra(VoyageFirestoreDB.FIELD_BUDGET, voyage.getBudget());
            intent.putExtra(VoyageFirestoreDB.FIELD_TYPE_VOYAGE , voyage.getType_voyage());
            intent.putExtra(VoyageFirestoreDB.FIELD_DATE_FIN, voyage.getDate_fin());
            intent.putExtra(VoyageFirestoreDB.FIELD_DATE_DEBUT, voyage.getDate_debut());
            intent.putExtra(VoyageFirestoreDB.FIELD_DESTINATION, voyage.getDestination());
            intent.putExtra(VoyageFirestoreDB.FIELD_NOM_VOYAGE, voyage.getNom_voyage());
            intent.putExtra("type_endroit" , type_endroit);

            Log.d(TAG, "hotel list" + hotel.getDocumentId() );
            startActivity(intent);

        }
    }

    /** Called when the user clicks the Send Contacts button */
   /* private class SendHotelsBulkFloatingButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // TODO: 3.2 Sending Bulk Data to Firestore

            hotelsFirestoreManager.sendHotelsBulk();

            Toast.makeText(com.example.ps4.HotelListMainActivity.this, "Contacts bulk sent", Toast.LENGTH_LONG).show();
        }
    }
*/
    /** Called when the user clicks the Add button */
    private class AddFloatingButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            Intent intent = new Intent();

            intent.setClass(com.example.ps4.HotelListMainActivity.this, MenuActivity.class);


            startActivity(intent);
        }
    }
}

