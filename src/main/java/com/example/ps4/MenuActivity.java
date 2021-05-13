package com.example.ps4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.ps4.Models.Voyage;
import com.example.ps4.firestore.VoyageFirestoreDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.ps4.firestore.VoyageFirestoreDB.DOCUMENT_ID;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_DESTINATION;

public class MenuActivity extends AppCompatActivity {
    private static final String TAG = null;
    private String destination ;
    private String document_id_voyage ;
    private Voyage voyage = new Voyage();
    private String type_activity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Bundle bundle = getIntent().getExtras();

        /*********/
        destination = bundle.getString(FIELD_DESTINATION);
        voyage.setDestination(destination);
        /**********/
        document_id_voyage = bundle.getString(DOCUMENT_ID);
        voyage.setDestination(destination);
        voyage.setDocumentId(document_id_voyage);

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



    public void hotels(View view) {
        Intent intent = new Intent();
        type_activity = "hotels";
        intent.setClass(MenuActivity.this, HotelListMainActivity.class);
        intent.putExtra(VoyageFirestoreDB.FIELD_DESTINATION, voyage.getDestination());
        intent.putExtra(DOCUMENT_ID, voyage.getDocumentId());
        intent.putExtra("type_activity",type_activity);
        Log.d(TAG, "menu activ" + voyage.getDestination() );
        startActivity(intent);
    }
    public void restaurants(View view) {
        type_activity = "restaurants";
        Intent intent = new Intent();
        intent.setClass(MenuActivity.this, RestaurantsActivity.class);
        intent.putExtra(VoyageFirestoreDB.FIELD_DESTINATION, voyage.getDestination());
        intent.putExtra("type_activity",type_activity);
        intent.putExtra(DOCUMENT_ID, voyage.getDocumentId());
        Log.d(TAG, "menu activ" + voyage.getDestination());
        startActivity(intent);
    }


}