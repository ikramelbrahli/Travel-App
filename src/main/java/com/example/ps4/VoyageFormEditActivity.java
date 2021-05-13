package com.example.ps4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import java.util.Calendar;

import static com.example.ps4.firestore.VilleFirestoreDB.DOCUMENT_ID;
import static com.example.ps4.firestore.VilleFirestoreDB.FIELD_CITY_NAME;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_BUDGET;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_DATE_DEBUT;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_DATE_FIN;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_NOM_VOYAGE;
import static com.example.ps4.firestore.VoyageFirestoreDB.FIELD_TYPE_VOYAGE;

public class VoyageFormEditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = null ;
    private String documentId;
    private String destination_city ;
    private String trip_title ;
    private String budget ;
    private String type_voyage ;
    private String date_fin ;
    private String date_debut ;
    private Voyage voyage = new Voyage() ;;
    private VoyageFirestoreManager voyageFirestoreManager;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    private EditText trip_title_view ;
    private EditText budget_view ;
    private Button okButtonn ;

    private TextView mDisplayDate;
    private TextView mDisplayDate2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voyage_form_edit);
        Bundle bundle = getIntent().getExtras();
        documentId = bundle.getString(DOCUMENT_ID);
        destination_city = bundle.getString(FIELD_CITY_NAME);
        trip_title =  bundle.getString(FIELD_NOM_VOYAGE);
        budget = bundle.getString(FIELD_BUDGET);
        type_voyage = bundle.getString(FIELD_TYPE_VOYAGE);
        date_fin = bundle.getString(FIELD_DATE_FIN);
        date_debut = bundle.getString(FIELD_DATE_DEBUT);
        voyage.setDocumentId(documentId);
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate2 = (TextView) findViewById(R.id.tvDate2);

        trip_title_view = findViewById(R.id.nom_voyage);
        Spinner spinner =findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_de_voyage , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        String trip_type = spinner.getSelectedItem().toString();
        voyage.setType_voyage(trip_type);
        okButtonn = findViewById(R.id.okButton);
        okButtonn.setOnClickListener(new VoyageFormEditActivity.OKButtonOnClickListener());


      /*  EditText date_debut ;
        date_debut = findViewById(R.id.tvDate);
        EditText date_fin ;*/
        budget_view = findViewById(R.id.budget);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        VoyageFormEditActivity.this,
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
                voyage.setDate_debut(date_debut);
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
                        VoyageFormEditActivity.this,
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
                voyage.setDate_fin(date_debut);
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



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class OKButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String nom_voyage1 = trip_title_view.getText().toString().trim();
            int budget1 =  Integer.parseInt(budget_view.getText().toString().trim());
            voyage.setNom_voyage(nom_voyage1);
            voyage.setBudget(budget1);
            Log.d(TAG, voyage.getNom_voyage() + " " + voyage.getBudget());
            voyageFirestoreManager = VoyageFirestoreManager.newInstance();
            voyageFirestoreManager.updateDocument(voyage);
            startActivity(new Intent(getApplicationContext(), TripListActivity.class));
            finish();

        }
    }


}