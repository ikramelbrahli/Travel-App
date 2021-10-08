package com.example.ps4.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps4.Models.Reservation;
import com.example.ps4.Models.Voyage;
import com.example.ps4.R;
import com.example.ps4.ReservationList;
import com.example.ps4.TripDetailsActivity;
import com.example.ps4.TripListActivity;
import com.example.ps4.firestore.ReservationFirestoreManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RecyclerViewAdapterReservation extends RecyclerView.Adapter<RecyclerViewAdapterReservation.ViewHolder>  {
    private static final String TAG = null ;
    private List reservationList;
    private ReservationList.GetAllReservationOnCompleteListener.ReservationListRecyclerViewOnItemClickListener reservationListRecyclerViewOnItemClickListener;
    private Context context ;
    private ReservationFirestoreManager reservationFirestoreManager = ReservationFirestoreManager.newInstance();
    public RecyclerViewAdapterReservation(List voyageList, ReservationList.GetAllReservationOnCompleteListener.ReservationListRecyclerViewOnItemClickListener reservationListRecyclerViewOnItemClickListener) {
        this.reservationList = voyageList;
        this.reservationListRecyclerViewOnItemClickListener = reservationListRecyclerViewOnItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterReservation.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_reservation_row, parent, false);
        Log.d("HotelListMainActivity", "done2" );
        itemView.setOnClickListener((View.OnClickListener) reservationListRecyclerViewOnItemClickListener);
        return new RecyclerViewAdapterReservation.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterReservation.ViewHolder holder, int position) {
        Reservation voyage = (Reservation) reservationList.get(position);
        Log.d("HotelListMainActivity", "in bind" );
        holder.date_debut.setText(voyage.getDate_debut());
        holder.date_fin.setText(voyage.getDate_fin());
        holder.destination.setText(voyage.getDestination());
        holder.hotel_name.setText(voyage.getHotel_name());
        holder.fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // remove your item from data base

                reservationFirestoreManager.deleteDocument(voyage.getDocumentId());
                reservationList.remove(position);  // remove the item from list
                notifyItemRemoved(position); // notify the adapter about the removed item
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }
    /** ViewHolder pattern: Inner class needed to keep the references between widgets and data to improve the performance */
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView date_debut;
        TextView date_fin ;
        TextView destination ;
        TextView hotel_name ;
        private FloatingActionButton fab ;
     ViewHolder(View itemView) {
            super(itemView);
            date_debut = itemView.findViewById(R.id.textView1);
            date_fin = itemView.findViewById(R.id.textView2);
            destination = itemView.findViewById(R.id.destination);
            hotel_name = itemView.findViewById(R.id.reservationName);
            fab = itemView.findViewById(R.id.floatingActionButton3);



        }
    }
}
