package com.example.ps4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ps4.Adapters.RecyclerViewAdapter;
import com.example.ps4.Adapters.RecyclerViewAdapterTrip;
import com.example.ps4.Models.Ville;
import com.example.ps4.Models.Voyage;
import com.example.ps4.firestore.VilleFirestoreDB;
import com.example.ps4.firestore.VilleFirestoreManager;
import com.example.ps4.firestore.VoyageFirestoreDB;
import com.example.ps4.firestore.VoyageFirestoreManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TripListActivity extends AppCompatActivity {
    ListView listView;
    String mTitle[] = {"trip1", "trip2", "trip3", "trip4", "trip5"};
    String mDescription[] = {"trip Description", "trip Description", "trip Description", "trip Description", "trip Description" , "trip Description"};
    int images[] = {R.drawable.golden_view_restau, R.drawable.golden_view_restau, R.drawable.golden_view_restau, R.drawable.golden_view_restau, R.drawable.golden_view_restau, R.drawable.golden_view_restau};

    private RecyclerView voyageListRecyclerView;
    private static final String TAG = "FirestoreListActivity";
    TextView nom, prenom;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    private VoyageFirestoreManager voyageFirestoreManager;
    private List<Voyage> voyageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        voyageFirestoreManager = VoyageFirestoreManager.newInstance();
        voyageListRecyclerView = findViewById(R.id.recyclerview);
        voyageListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listView = findViewById(R.id.listView);
        nom = findViewById(R.id.textView);
        prenom = findViewById(R.id.textView3);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();



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





    @Override
    protected void onStart() {
        super.onStart();
        // Populate the ContactListMainActivity with the available data
        voyageFirestoreManager.getAllVoyages(new TripListActivity.GetAllVoyagesOnCompleteListener() , userID);

    }

    public class GetAllVoyagesOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {

            if (task.isSuccessful()) {

                // Get the query snapshot from the task result
                QuerySnapshot querySnapshot = task.getResult();

                if (querySnapshot != null) {
                    voyageList = querySnapshot.toObjects(Voyage.class);
                    Log.d("HotelListMainActivity", "done2" + voyageList.size());
                    populateVilleRecyclerView(voyageList);

                }

            } else {
                Log.w(TAG, "Error getting documents: ", task.getException());
            }

            if (voyageList == null || voyageList.size() == 0) {
//
            }
        }

        private void populateVilleRecyclerView(List<Voyage> villeList) {
            // Set the contactListMainRecyclerViewAdapter in the contactListRecyclerView

            RecyclerViewAdapterTrip voyageListMainRecyclerViewAdapter = new RecyclerViewAdapterTrip(villeList , new TripListActivity.GetAllVoyagesOnCompleteListener.VoyageListRecyclerViewOnItemClickListener());
            voyageListRecyclerView.setAdapter(voyageListMainRecyclerViewAdapter);
        }
        /** Called when the user clicks on an item of the contact list */
        public class VoyageListRecyclerViewOnItemClickListener implements View.OnClickListener {


            @Override
            public void onClick(View view) {

                int itemIndex = voyageListRecyclerView.indexOfChild(view);
                Log.d("ContactListMainActivity", "" + itemIndex);
                Voyage contact = voyageList.get(itemIndex);
               Intent intent = new Intent();
               intent.setClass(TripListActivity.this, TripDetailsActivity.class);
              intent.putExtra(VoyageFirestoreDB.DOCUMENT_ID, contact.getDocumentId());
                intent.putExtra(VoyageFirestoreDB.FIELD_BUDGET, contact.getBudget());
                intent.putExtra(VoyageFirestoreDB.FIELD_TYPE_VOYAGE , contact.getType_voyage());
                intent.putExtra(VoyageFirestoreDB.FIELD_DATE_FIN, contact.getDate_fin());
                intent.putExtra(VoyageFirestoreDB.FIELD_DATE_DEBUT, contact.getDate_debut());
                intent.putExtra(VoyageFirestoreDB.FIELD_DESTINATION, contact.getDestination());
                intent.putExtra(VoyageFirestoreDB.FIELD_NOM_VOYAGE, contact.getNom_voyage());

                startActivity(intent);
            }
        }



    }
}