package com.example.ps4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.ps4.Models.Reservation;
import com.example.ps4.firestore.HotelsFirestoreDbContract;
import com.example.ps4.firestore.ReservationFirestoreDB;
import com.example.ps4.firestore.ReservationFirestoreManager;
import com.example.ps4.firestore.RestaurantFirestoreDB;
import com.example.ps4.firestore.VoyageFirestoreDB;
import com.example.ps4.firestore.VoyageFirestoreManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import static com.example.ps4.firestore.HotelsFirestoreDbContract.FIELD_DESCRIPTION;
import static com.example.ps4.firestore.HotelsFirestoreDbContract.FIELD_HOTEL_NAME;
import static com.example.ps4.firestore.HotelsFirestoreDbContract.FIELD_PRIX;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_DESTINATION;

public class ReservationFormActivity extends AppCompatActivity {
    private static final String TAG = null ;
    private String destination ;
    private String document_id_voyage;
    private String document_id_endroit ;
    private String hotel_name ;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    private ReservationFirestoreManager reservationFirestoreManager;
    private Button okButtonn;

    private Reservation reservation = new Reservation() ;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    private TextView mDisplayDate;
    private TextView mDisplayDate2;
    private String imageurl;
    private String prix ;
    private String description ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_form);
        Bundle bundle = getIntent().getExtras();
        document_id_voyage = bundle.getString(VoyageFirestoreDB.DOCUMENT_ID);
        /*********/
        destination = bundle.getString(FIELD_DESTINATION);
        /**********/
        document_id_endroit=bundle.getString("doc_id_endroit");
          prix=bundle.getString(HotelsFirestoreDbContract.FIELD_PRIX);
          String type_endroit = bundle.getString("type_endroit");
          hotel_name = bundle.getString(FIELD_HOTEL_NAME);

        description = bundle.getString(FIELD_DESCRIPTION);
        reservation.setDestination(destination);
        reservation.setHotel_name(hotel_name);
        reservation.setVoyageID(document_id_voyage);
        reservation.setEndroitID(document_id_endroit);
        Log.d("endroit details", "hotel name end details" + reservation.getDocumentId() + reservation.getEndroitID());

        imageurl = bundle.getString(HotelsFirestoreDbContract.FIELD_IMAGE_URL);

        Log.d(TAG,  "reserv form hotal name " + reservation.getHotel_name() + description);
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate2 = (TextView) findViewById(R.id.tvDate2);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        okButtonn = findViewById(R.id.okButton);
        okButtonn.setOnClickListener(new OKButtonOnClickListener());
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReservationFormActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date_debut = day + "/" + month + "/" + year;
                reservation.setDate_debut(date_debut);
            /*    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                try {
                    Date d = dateFormat.parse(date);
                    Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year + "/" + d);
                    contact.setDate_debut(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
*/
                mDisplayDate.setText(date_debut);
            }
        };


        mDisplayDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReservationFormActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener2,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date_debut = day + "/" + month + "/" + year;
                reservation.setDate_fin(date_debut);
            /*    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                try {
                    Date d = dateFormat.parse(date);
                    Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year + "/" + d);
                    contact.setDate_debut(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
*/
                mDisplayDate2.setText(date_debut);
            }
        };

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




    private class OKButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            reservationFirestoreManager = ReservationFirestoreManager.newInstance();
            reservation.setImageURI(imageurl);

            reservationFirestoreManager.createDocument(reservation);
            Intent intent = new Intent();
            intent.setClass(ReservationFormActivity.this, ReservationList.class);
            intent.putExtra(VoyageFirestoreDB.DOCUMENT_ID, reservation.getVoyageID());
            intent.putExtra(FIELD_DESTINATION, reservation.getDestination());
            intent.putExtra("doc_id_endroit", reservation.getEndroitID());
            intent.putExtra(HotelsFirestoreDbContract.FIELD_PRIX, prix);
            intent.putExtra(HotelsFirestoreDbContract.FIELD_DESCRIPTION, description);
            intent.putExtra("image", reservation.getImageURI());
            intent.putExtra(FIELD_HOTEL_NAME, reservation.getHotel_name());
            Log.d(TAG, "hotel list this line is" + reservation.getEndroitID() + reservation.getVoyageID() +  reservation.getDestination() + imageurl);
            startActivity(intent);
            finish();
        }
    }
}