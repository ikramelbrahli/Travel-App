package com.example.ps4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ps4.Adapters.RecyclerViewAdapter;
import com.example.ps4.Models.Ville;
import com.example.ps4.firestore.VilleFirestoreManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class CitiesActivity extends AppCompatActivity {
    ListView listView;
    /************/
    private RecyclerView villeListRecyclerView;
    /***********/
    private static final String TAG = "FirestoreListActivity";
    ArrayList<String> cityList = new ArrayList<>();
    String mTitle[] = {"City1", "City2", "City3", "City4", "City5"};
    String mDescription[] = {"Hotel Description", "Hotel Description", "Hotel Description", "Hotel Description", "Hotel Description", "Hotel Description"};
    int images[] = {R.drawable.golden_view_restau, R.drawable.golden_view_restau, R.drawable.golden_view_restau, R.drawable.golden_view_restau, R.drawable.golden_view_restau, R.drawable.golden_view_restau};
    TextView nom, prenom;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    private ArrayAdapter<Ville> adapter;
    private static final String Ville = "Ville";

    /***********/
    private VilleFirestoreManager villeFirestoreManager;
    private FloatingActionButton sendBulkDataFloatingButton;

    private List<Ville> villeList;

    /************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cities);
        /***********/
        villeFirestoreManager = VilleFirestoreManager.newInstance();
        // Set up the contactListRecyclerView
        villeListRecyclerView = findViewById(R.id.recyclerview);
        villeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Set up the sendBulkDataFloatingButton
       // sendBulkDataFloatingButton = findViewById(R.id.sendBulkDataFloatingButton);
       // sendBulkDataFloatingButton.setOnClickListener(new SendContactsBulkFloatingButtonOnClickListener());

        /**************/
        listView = findViewById(R.id.listView);
        ArrayAdapter<Ville> adapter = new ArrayAdapter<Ville>(CitiesActivity.this, android.R.layout.simple_list_item_1, new ArrayList<Ville>());

        nom = findViewById(R.id.textView);
        prenom = findViewById(R.id.textView3);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("User").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@androidx.annotation.Nullable DocumentSnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {
                nom.setText(value.getString("nom"));
                prenom.setText(value.getString("prenom"));
            }
        });
        DocumentReference newdocumentReference = fstore.collection("Ville").document("imageURI");


      /*  ArrayList<Ville> villes = new ArrayList<>();
        CitiesActivity.MyAdapter adapter1 = new CitiesActivity.MyAdapter(this , villes ) ;
        listView.setAdapter(adapter);*/

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void voyage_form(View view) {
        startActivity(new Intent(getApplicationContext(), VoyageFormActivity.class));
        finish();
    }

    /*************/
    private class SendContactsBulkFloatingButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            villeFirestoreManager.sendVilleBulk();
            Toast.makeText(CitiesActivity.this, "Bulk contacts sent", Toast.LENGTH_LONG).show();
        }
    }

    /*************/

    @Override
    protected void onStart() {
        super.onStart();
        // Populate the ContactListMainActivity with the available data
        villeFirestoreManager.getAllVilles(new GetAllVillesOnCompleteListener());

    }

    private class GetAllVillesOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {

            if (task.isSuccessful()) {

                // Get the query snapshot from the task result
                QuerySnapshot querySnapshot = task.getResult();

                if (querySnapshot != null) {
                    villeList = querySnapshot.toObjects(Ville.class);
                    populateVilleRecyclerView(villeList);

                }

            } else {
                Log.w(TAG, "Error getting documents: ", task.getException());
            }

            if (villeList == null || villeList.size() == 0) {
//
            }
        }





        private void populateVilleRecyclerView(List<Ville> villeList) {
            // Set the contactListMainRecyclerViewAdapter in the contactListRecyclerView

            RecyclerViewAdapter villeListMainRecyclerViewAdapter = new RecyclerViewAdapter(villeList);
           villeListRecyclerView.setAdapter(villeListMainRecyclerViewAdapter);
        }
        /** Called when the user clicks on an item of the contact list */
        public class VilleListRecyclerViewOnItemClickListener implements View.OnClickListener {

            @Override
            public void onClick(View view) {

                int itemIndex = villeListRecyclerView.indexOfChild(view);
                Log.d("ContactListMainActivity", "" + itemIndex);

                Ville contact = villeList.get(itemIndex);

                Intent intent = new Intent();
                intent.setClass(CitiesActivity.this, SightsActivity.class);


                // TODO: 4.2 Updating a Contact

                startActivity(intent);
            }
        }


    }
}