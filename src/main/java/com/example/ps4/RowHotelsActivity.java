package com.example.ps4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ps4.Models.Voyage;
import com.example.ps4.firestore.VoyageFirestoreDB;

public class RowHotelsActivity extends AppCompatActivity {

private Button add_to_trip ;
private Voyage voyage = new Voyage() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row_hotels);
//        add_to_trip = findViewById(R.id.button2);
   //     add_to_trip.setOnClickListener(new RowHotelsActivity.AddToTripButtonOnClickListener());
    }
    private class AddToTripButtonOnClickListener implements View.OnClickListener {
        @Override

        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(RowHotelsActivity.this, ReservationFormActivity.class);
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
}