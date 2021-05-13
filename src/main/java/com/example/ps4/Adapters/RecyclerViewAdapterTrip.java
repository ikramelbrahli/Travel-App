package com.example.ps4.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps4.CitiesActivity;
import com.example.ps4.Models.Ville;
import com.example.ps4.Models.Voyage;
import com.example.ps4.R;
import com.example.ps4.TripListActivity;

import java.util.List;

import static com.squareup.picasso.Picasso.get;

public class RecyclerViewAdapterTrip extends RecyclerView.Adapter<RecyclerViewAdapterTrip.ViewHolder> {
    private static final String TAG = null ;
    private List voyageList;
    private TripListActivity.GetAllVoyagesOnCompleteListener.VoyageListRecyclerViewOnItemClickListener voyageListRecyclerViewOnItemClickListener;
    private Context context ;
    public RecyclerViewAdapterTrip(List voyageList, TripListActivity.GetAllVoyagesOnCompleteListener.VoyageListRecyclerViewOnItemClickListener voyageListRecyclerViewOnItemClickListener) {
        this.voyageList = voyageList;
        this.voyageListRecyclerViewOnItemClickListener = voyageListRecyclerViewOnItemClickListener;
    }


    @NonNull
    @Override
    public RecyclerViewAdapterTrip.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_trips, parent, false);
        Log.d("HotelListMainActivity", "done2" );
        itemView.setOnClickListener((View.OnClickListener) voyageListRecyclerViewOnItemClickListener);

        return new RecyclerViewAdapterTrip.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterTrip.ViewHolder holder, int position) {
        Voyage voyage = (Voyage) voyageList.get(position);
        // String i_am_tired = ville.toStringImage()
        Log.d("HotelListMainActivity", "done2" );
        holder.cityname.setText(voyage.getNom_voyage());
        holder.destination.setText(voyage.getDestination());
    }

    @Override
    public int getItemCount() {
        return voyageList.size();
    }

    /** ViewHolder pattern: Inner class needed to keep the references between widgets and data to improve the performance */
    class ViewHolder extends RecyclerView.ViewHolder {


        TextView cityname;
        TextView destination ;



        ViewHolder(View itemView) {

            super(itemView);

            cityname = itemView.findViewById(R.id.textView1);
            destination = itemView.findViewById(R.id.textView2);

        }
    }
}
