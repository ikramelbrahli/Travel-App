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
import android.widget.TextView;

import com.example.ps4.Adapters.RecyclerViewAdapterReservation;
import com.example.ps4.Adapters.RecyclerViewAdapterTrip;
import com.example.ps4.Models.Hotel;
import com.example.ps4.Models.Reservation;
import com.example.ps4.Models.Voyage;
import com.example.ps4.firestore.HotelsFirestoreDbContract;
import com.example.ps4.firestore.HotelsFirestoreManager;
import com.example.ps4.firestore.ReservationFirestoreDB;
import com.example.ps4.firestore.ReservationFirestoreManager;
import com.example.ps4.firestore.VoyageFirestoreDB;
import com.example.ps4.firestore.VoyageFirestoreManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_BUDGET;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_DATE_DEBUT;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_DATE_FIN;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_DESTINATION;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_NOM_VOYAGE;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_TYPE_VOYAGE;

public class ReservationList extends AppCompatActivity {
    private static final String TAG = null ;
    private String document_id ;
    private String document_id_hotel ;
    private String budget ;
    private String date_debut ;
    private String date_fin ;
    private String destination ;
    private String trip_title ;
    private String type_voyage ;
    private Button AddReservation ;
    private Button test ;
    private String prix ;
    private String description;

    private String imageurl ;
    private HotelsFirestoreManager hotelsFirestoreManager ;
    private FloatingActionButton floatingActionButton3 ;

    private Voyage voyage = new Voyage();
    private List<Reservation> reservationList;

    private RecyclerView ListRecyclerView;
    private String document_id_endroit ;
    private String hotel_name ;

    TextView nom, prenom;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;

    private ReservationFirestoreManager reservationFirestoreManager;

    private RecyclerView reservationListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);
        reservationFirestoreManager = ReservationFirestoreManager.newInstance();
        reservationListRecyclerView = findViewById(R.id.recyclerview);
        reservationListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Bundle bundle = getIntent().getExtras();
        floatingActionButton3 = findViewById(R.id.floatingActionButton3);
        document_id = bundle.getString(VoyageFirestoreDB.DOCUMENT_ID);
        /*********/
        destination = bundle.getString(FIELD_DESTINATION);
        imageurl = bundle.getString("image");
        document_id_endroit=bundle.getString("doc_id_endroit");

        Log.d("endroit details", "imagename reservation" + imageurl);
        /**********/

        AddReservation = findViewById(R.id.button6);
        AddReservation.setOnClickListener(new ReservationList.AddReservationButtonOnClickListener());

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
        // Populate the ContactListMainActivity with the available data
        reservationFirestoreManager.getAllReservations(new ReservationList.GetAllReservationOnCompleteListener() , document_id  );

    }
    public class GetAllReservationOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {

            if (task.isSuccessful()) {

                // Get the query snapshot from the task result
                QuerySnapshot querySnapshot = task.getResult();

                if (querySnapshot != null) {
                    reservationList = querySnapshot.toObjects(Reservation.class);

                    Log.d("HotelListMainActivity", "list size" );
                    populateVilleRecyclerView(reservationList);

                }

            } else {
                Log.w(TAG, "Error getting documents: ", task.getException());
            }

            if (reservationList == null || reservationList.size() == 0) {
//
            }
        }

        private void populateVilleRecyclerView(List<Reservation> reservationList) {
            // Set the contactListMainRecyclerViewAdapter in the contactListRecyclerView

            RecyclerViewAdapterReservation reservationListMainRecyclerViewAdapter = new RecyclerViewAdapterReservation(reservationList , new ReservationList.GetAllReservationOnCompleteListener.ReservationListRecyclerViewOnItemClickListener());
            reservationListRecyclerView.setAdapter(reservationListMainRecyclerViewAdapter);
        }



            /** Called when the user clicks on an item of the contact list */
        public class ReservationListRecyclerViewOnItemClickListener implements View.OnClickListener  {


            @Override
            public void onClick(View view) {

                int itemIndex = reservationListRecyclerView.indexOfChild(view);
                Log.d("ContactListMainActivity", "" + itemIndex);
                Reservation contact = reservationList.get(itemIndex);

                Intent intent = new Intent();
                intent.setClass(ReservationList.this, EndroitDetails.class);
                intent.putExtra("ReservationFirestoreDB.DOCUMENT_ID", contact.getDocumentId());
                Hotel hotel = new Hotel();
                hotel.setNom_hotel(contact.getHotel_name());
               // hotel.setPrix();
                //hotel.setDescription();
                intent.putExtra(ReservationFirestoreDB.FIELD_DATE_FIN, contact.getDate_fin());
                intent.putExtra(ReservationFirestoreDB.FIELD_DATE_DEBUT, contact.getDate_debut());
                intent.putExtra(ReservationFirestoreDB.FIELD_DESTINATION, contact.getDestination());
              //  intent.putExtra(VoyageFirestoreDB.FIELD_NOM_VOYAGE, contact.getNom_voyage());
                intent.putExtra(ReservationFirestoreDB.DOCUMENT_ID, contact.getVoyageID());
                intent.putExtra(FIELD_DESTINATION, contact.getDestination());
                intent.putExtra("doc_id_endroit", contact.getEndroitID());
                intent.putExtra(ReservationFirestoreDB.FIELD_IMAGE_URL, contact.getImageURI());
                intent.putExtra(HotelsFirestoreDbContract.FIELD_HOTEL_NAME, contact.getHotel_name());

                Log.d("endroit details", "hotel name reserv list on click" + contact.getImageURI());
                Log.d("endroit details", "CONTACT" + imageurl + contact.getDocumentId() + contact.getDate_fin() + contact.getImageURI()+ contact.getEndroitID() );
               startActivity(intent);
            }


        }



}


    private class AddReservationButtonOnClickListener implements View.OnClickListener {
        public void onClick(View view) {
            voyage.setDocumentId(document_id);
            voyage.setDestination(destination);
            Intent intent = new Intent();
            intent.setClass(ReservationList.this, MenuActivity.class);
            intent.putExtra(VoyageFirestoreDB.DOCUMENT_ID, voyage.getDocumentId());
            intent.putExtra(VoyageFirestoreDB.FIELD_BUDGET, voyage.getBudget());
            intent.putExtra(VoyageFirestoreDB.FIELD_TYPE_VOYAGE , voyage.getType_voyage());
            intent.putExtra(VoyageFirestoreDB.FIELD_DATE_FIN, voyage.getDate_fin());
            intent.putExtra(VoyageFirestoreDB.FIELD_DATE_DEBUT, voyage.getDate_debut());
            intent.putExtra(VoyageFirestoreDB.FIELD_DESTINATION, voyage.getDestination());
            intent.putExtra(VoyageFirestoreDB.FIELD_NOM_VOYAGE, voyage.getNom_voyage());
            Log.d(TAG, "reserv list" + voyage.getDestination() + voyage.getDocumentId());
            startActivity(intent);
        }
    }
    }