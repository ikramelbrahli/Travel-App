package com.example.ps4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ps4.Models.Voyage;
import com.example.ps4.firestore.VoyageFirestoreManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.ps4.firestore.VilleFirestoreDB.DOCUMENT_ID;
import static com.example.ps4.firestore.VilleFirestoreDB.FIELD_CITY_NAME;

public class VoyageFormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = null;
    private EditText destination;
    private EditText  nom_voyage;
    private EditText type_voyage;
    private EditText date_debut;
    private EditText date_fin;
    private EditText budget;
    private Button okButtonn;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Date date ;
    private VoyageFirestoreManager voyageFirestoreManager;

    private TextView mDisplayDate;
    private TextView mDisplayDate2;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    private Voyage contact = new Voyage() ;


    /* Constants to define the database operation type */
    public static final String OPERATION = "OPERATION";
    public static final String CREATING = "CREATING";
    public static final String EDITING = "EDITING";

    private String operationTypeString;

    /* Repository reference */
    private String documentId;
    private String destination_city ;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


       // dateButton.setText(getTodaysDate());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voyage_form);
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate2 = (TextView) findViewById(R.id.tvDate2);
        Spinner spinner =findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this , R.array.type_de_voyage , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        contact.setUser_id(userID);
        Bundle bundle = getIntent().getExtras();
        documentId = bundle.getString(DOCUMENT_ID);
        destination_city = bundle.getString(FIELD_CITY_NAME);
        TextView destination_view ;
        destination_view = findViewById(R.id.topText);
        destination_view.setText("Plan your trip to "+destination_city);
        contact.setDestination(destination_city);
        Log.d(TAG, documentId  + " " + destination_city);

        okButtonn = findViewById(R.id.okButton);
        nom_voyage = findViewById(R.id.nom_voyage);
        budget = findViewById(R.id.budget);
        okButtonn.setOnClickListener(new OKButtonOnClickListener());
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        String trip_type = spinner.getSelectedItem().toString();
        contact.setType_voyage(trip_type);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        VoyageFormActivity.this,
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
                contact.setDate_debut(date_debut);
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
                        VoyageFormActivity.this,
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
                contact.setDate_fin(date_debut);
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
                        case R.id.nav_recommendations :

                            startActivity(new Intent(getApplicationContext(), RecommendationActivity.class));

                            finish();
                            break;

                    }
                   /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();*/

                }
            };





   private class OKButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String nom_voyage1 = nom_voyage.getText().toString().trim();
            int budget1 =  Integer.parseInt(budget.getText().toString().trim());
            contact.setNom_voyage(nom_voyage1);
            contact.setBudget(budget1);
            Log.d(TAG, contact.getNom_voyage() + " " + contact.getBudget());
            voyageFirestoreManager = VoyageFirestoreManager.newInstance();
            voyageFirestoreManager.createDocument(contact);
            startActivity(new Intent(getApplicationContext(), TripListActivity.class));
            finish();

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}