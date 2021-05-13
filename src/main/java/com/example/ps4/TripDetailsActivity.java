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
import android.widget.TextView;

import com.example.ps4.Models.Voyage;
import com.example.ps4.firestore.VoyageFirestoreDB;
import com.example.ps4.firestore.VoyageFirestoreManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import static com.example.ps4.firestore.VilleFirestoreDB.DOCUMENT_ID;
import static com.example.ps4.firestore.VilleFirestoreDB.FIELD_CITY_NAME;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_BUDGET;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_DATE_DEBUT;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_DATE_FIN;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_DESTINATION;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_NOM_VOYAGE;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_TYPE_VOYAGE;

public class TripDetailsActivity extends AppCompatActivity {
    private static final String TAG = null;
    private VoyageFirestoreManager voyageFirestoreManager;
    private String document_id ;
    private String budget ;
    private String date_debut ;
    private String date_fin ;
    private String destination ;
    private String trip_title ;
    private String type_voyage ;
   // private Button deleteButton ;
    private Button updateButton ;
    private Button ListReservation ;
    private FloatingActionButton delete_button ;
    private Voyage voyage = new Voyage();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        Bundle bundle = getIntent().getExtras();
        document_id = bundle.getString(VoyageFirestoreDB.DOCUMENT_ID);
       destination = bundle.getString(FIELD_DESTINATION);
        date_debut = bundle.getString(FIELD_DATE_DEBUT);
        date_fin = bundle.getString(FIELD_DATE_FIN);
        budget = String.valueOf(bundle.getInt(FIELD_BUDGET));
        trip_title = bundle.getString(FIELD_NOM_VOYAGE) ;
        type_voyage = bundle.getString(FIELD_TYPE_VOYAGE);

        TextView destination_view ;
        TextView date_view;
        TextView budget_view ;
        TextView trip_title_view ;
        TextView trip_type ;
        trip_type = findViewById(R.id.textView8);
        budget_view = findViewById(R.id.textView6);
        date_view = findViewById(R.id.textView14);
        trip_title_view = findViewById(R.id.textView5);
        destination_view = findViewById(R.id.textView7);
        destination_view.setText(destination);
        budget_view.setText(budget + "MAD");
        trip_type.setText(type_voyage);
        date_view.setText(" From "+date_debut+" To "+date_fin);
        trip_title_view.setText(trip_title + " , " + type_voyage);
        voyageFirestoreManager = VoyageFirestoreManager.newInstance();
        delete_button = findViewById(R.id.floatingActionButton);
        delete_button.setOnClickListener(new DeleteButtonOnClickListener());

        ListReservation = findViewById(R.id.button6);
        ListReservation.setOnClickListener(new ListReservationButtonOnClickListener());
      voyage.setDestination(destination);
        voyage.setDocumentId(document_id);
        voyage.setDate_debut(date_debut);
        voyage.setDate_fin(date_fin);
        voyage.setBudget(Integer.parseInt(budget));
        voyage.setNom_voyage(trip_title);
        voyage.setType_voyage(type_voyage);


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



    private class UpdateButtonOnClickListener implements View.OnClickListener {
        @Override

        public void onClick(View view) {

            Intent intent = new Intent();
            intent.setClass(TripDetailsActivity.this, VoyageFormEditActivity.class);
            intent.putExtra(VoyageFirestoreDB.DOCUMENT_ID, voyage.getDocumentId());
            intent.putExtra(VoyageFirestoreDB.FIELD_BUDGET, voyage.getBudget());
            intent.putExtra(VoyageFirestoreDB.FIELD_TYPE_VOYAGE , voyage.getType_voyage());
            intent.putExtra(VoyageFirestoreDB.FIELD_DATE_FIN, voyage.getDate_fin());
            intent.putExtra(VoyageFirestoreDB.FIELD_DATE_DEBUT, voyage.getDate_debut());
            intent.putExtra(VoyageFirestoreDB.FIELD_DESTINATION, voyage.getDestination());
            intent.putExtra(VoyageFirestoreDB.FIELD_NOM_VOYAGE, voyage.getNom_voyage());


            startActivity(intent);
        }
    }

    private class DeleteButtonOnClickListener implements View.OnClickListener {
        @Override

        public void onClick(View view) {
            voyageFirestoreManager.deleteContact(document_id);
            finish();
        }
    }

    private class ListReservationButtonOnClickListener implements View.OnClickListener {
        @Override

        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(TripDetailsActivity.this, ReservationList.class);
            intent.putExtra(VoyageFirestoreDB.DOCUMENT_ID, voyage.getDocumentId());
            intent.putExtra(VoyageFirestoreDB.FIELD_BUDGET, voyage.getBudget());
            intent.putExtra(VoyageFirestoreDB.FIELD_TYPE_VOYAGE , voyage.getType_voyage());
            intent.putExtra(VoyageFirestoreDB.FIELD_DATE_FIN, voyage.getDate_fin());
            intent.putExtra(VoyageFirestoreDB.FIELD_DATE_DEBUT, voyage.getDate_debut());
            intent.putExtra(VoyageFirestoreDB.FIELD_DESTINATION, voyage.getDestination());
            intent.putExtra(VoyageFirestoreDB.FIELD_NOM_VOYAGE, voyage.getNom_voyage());
            Log.d(TAG, "details" + voyage.getDocumentId() );
            startActivity(intent);
        }
    }
}