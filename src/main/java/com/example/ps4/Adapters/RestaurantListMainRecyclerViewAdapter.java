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
import com.example.ps4.Models.Restaurant;
import com.example.ps4.R;
import com.example.ps4.RestaurantsActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantListMainRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantListMainRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = null ;
    private List restaurantList;
    private RestaurantsActivity.RestaurantListRecyclerViewOnItemClickListener restaurantListRecyclerViewOnItemClickListener;

    public RestaurantListMainRecyclerViewAdapter(List restaurantList, RestaurantsActivity.RestaurantListRecyclerViewOnItemClickListener restaurantListRecyclerViewOnItemClickListener) {
        this.restaurantList = restaurantList;
        this.restaurantListRecyclerViewOnItemClickListener = restaurantListRecyclerViewOnItemClickListener;
    }

    @NonNull
    @Override
    public RestaurantListMainRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_hotels, parent, false);
        itemView.setOnClickListener(restaurantListRecyclerViewOnItemClickListener);

        return new RestaurantListMainRecyclerViewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListMainRecyclerViewAdapter.ViewHolder holder, int position) {
        Restaurant hotel = (Restaurant) restaurantList.get(position);
        // String i_am_tired = ville.toStringImage()
        Log.d("HotelListMainActivity", "sth" );
        holder.restaurantname.setText(hotel.getNom_restaurant());
        holder.cityname.setText(hotel.getNom_ville());

        Picasso.get()
                .load(hotel.getImageURI())
                .into(holder.image_url , new Callback() {
                    @Override
                    public void onSuccess() {
                        // will load image
                    }

                    @Override
                    public void onError(Exception e) {

                    }



                });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image_url;
        TextView cityname;
        TextView restaurantname;


        ViewHolder(View itemView) {

            super(itemView);

            restaurantname = itemView.findViewById(R.id.textView1);
            cityname = itemView.findViewById(R.id.textView2);
            image_url= itemView.findViewById(R.id.image);
        }
    }
}
