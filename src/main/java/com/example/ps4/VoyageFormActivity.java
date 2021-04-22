package com.example.ps4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ps4.Models.Voyage;
import com.example.ps4.firestore.VoyageFirestoreManager;

public class VoyageFormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = null;
    private EditText destination;
    private EditText  nom_voyage;
    private EditText type_voyage;
    private EditText date_debut;
    private EditText date_fin;
    private EditText budget;
    private Button okButtonn;
    /* Constants to define the database operation type */
    public static final String OPERATION = "OPERATION";
    public static final String CREATING = "CREATING";
    public static final String EDITING = "EDITING";

    private String operationTypeString;

    /* Repository reference */
    private VoyageFirestoreManager voyageFirestoreManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voyage_form);

        //Spinner spinner =findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this , R.array.type_de_voyage , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // spinner.setAdapter(adapter);
     //   spinner.setOnItemSelectedListener(this);
        okButtonn = findViewById(R.id.okButton);

        nom_voyage = findViewById(R.id.nom_voyage);
      //  type_voyage = findViewById(R.id.type_voyage);
        //date_debut = findViewById(R.id.date_debut);
       // date_fin = findViewById(R.id.date_fin);
        budget = findViewById(R.id.budget);
        okButtonn.setOnClickListener(new OKButtonOnClickListener());





    }
    private class OKButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String destination1 = "Paris";
            String nom_voyage1 = nom_voyage.getText().toString().trim();
            //String type_voyage1 = "yay";
            //String date_debut1 = date_debut.getText().toString();
           // String date_fin1 = date_fin.getText().toString();
           int budget1 =  Integer.parseInt(budget.getText().toString().trim());

            Voyage contact = new Voyage();
            contact.setNom_voyage(nom_voyage1);
            contact.setBudget(budget1);
          //  contact.setDestination(destination1);

            Log.d(TAG, contact.getNom_voyage() + " " + contact.getBudget());
            voyageFirestoreManager = VoyageFirestoreManager.newInstance();
            voyageFirestoreManager.createDocument(contact);
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));

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