package com.example.ps4.Adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ps4.HotelListMainActivity;
import com.example.ps4.Models.Hotel;
import com.example.ps4.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HotelListMainRecyclerViewAdapter extends RecyclerView.Adapter<HotelListMainRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = null ;
    private List hotelList;
    private String type_endroit = "hotel";
    private HotelListMainActivity.HotelListRecyclerViewOnItemClickListener hotelListRecyclerViewOnItemClickListener;

    public HotelListMainRecyclerViewAdapter(List hotelList, HotelListMainActivity.HotelListRecyclerViewOnItemClickListener hotelListRecyclerViewOnItemClickListener) {
        this.hotelList = hotelList;
        this.hotelListRecyclerViewOnItemClickListener = hotelListRecyclerViewOnItemClickListener;
        Log.d("HotelListMainActivity", "lol1" );
    }

    @NonNull
    @Override
    public HotelListMainRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("HotelListMainActivity", "lol2" );
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_hotels, parent, false);
        itemView.setOnClickListener(hotelListRecyclerViewOnItemClickListener);

        return new HotelListMainRecyclerViewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelListMainRecyclerViewAdapter.ViewHolder holder, int position) {
        Log.d("HotelListMainActivity", "lol3" );
        Hotel hotel = (Hotel) hotelList.get(position);
        // String i_am_tired = ville.toStringImage()
        Log.d("HotelListMainActivity", "sth" );
        holder.hotelname.setText(hotel.getNom_hotel());
        holder.cityname.setText(hotel.getNom_ville());

        Picasso.get()
                .load(hotel.getImageURI())
                .into(holder.image_url , new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        // will load image
                    }

                    @Override
                    public void onError(Exception e) {

                    }



                });
        //holder.image_url.setImageURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/projets4-d3262.appspot.com/o/travel.jpg?alt=media&token=51af0d8b-55af-48c8-961b-01ade515e347"));
        //Log.d(TAG, hotel.getImageURI() + " " + hotel.getNom_hotel());

    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    /** ViewHolder pattern: Inner class needed to keep the references between widgets and data to improve the performance */
    class ViewHolder extends RecyclerView.ViewHolder {

       ImageView image_url;
        TextView cityname;
        TextView hotelname;


        ViewHolder(View itemView) {

            super(itemView);

            hotelname = itemView.findViewById(R.id.textView1);
            cityname = itemView.findViewById(R.id.textView2);
           image_url= itemView.findViewById(R.id.image);
        }
    }
}

