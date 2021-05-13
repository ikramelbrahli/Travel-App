package com.example.ps4.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ps4.CitiesActivity;
import com.example.ps4.Models.Ville;
import com.example.ps4.R;
import com.google.firebase.storage.StorageReference;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.picasso.Picasso;

import java.text.BreakIterator;
import java.util.Arrays;
import java.util.List;

import static com.squareup.picasso.Picasso.get;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {

    private static final String TAG = null ;
    private List villeList;
    private CitiesActivity.GetAllVillesOnCompleteListener.VilleListRecyclerViewOnItemClickListener villeListRecyclerViewOnItemClickListener;
    private Context context ;
    public RecyclerViewAdapter(List villeList, CitiesActivity.GetAllVillesOnCompleteListener.VilleListRecyclerViewOnItemClickListener villeListRecyclerViewOnItemClickListener) {
        this.villeList = villeList;
        this.villeListRecyclerViewOnItemClickListener = villeListRecyclerViewOnItemClickListener;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_city, parent, false);

        itemView.setOnClickListener((View.OnClickListener) villeListRecyclerViewOnItemClickListener);

        return new  ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ville ville = (Ville) villeList.get(position);
       // String i_am_tired = ville.toStringImage()

        holder.cityname.setText(ville.getNom_ville());
        holder.country.setText(ville.getNom_pays());

        Picasso.get()
                .load(ville.getImageURI())
                .into(holder.image_url , new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        // will load image
                    }

                    @Override
                    public void onError(Exception e) {

                    }



                });
        Log.d(TAG, ville.getImageURI());
    }


    @Override
    public int getItemCount() {
        return villeList.size();
    }

    /** ViewHolder pattern: Inner class needed to keep the references between widgets and data to improve the performance */
    static class ViewHolder extends RecyclerView.ViewHolder {


        ImageView image_url;
        TextView cityname;
        TextView country;


        ViewHolder(View itemView) {

            super(itemView);

            cityname = itemView.findViewById(R.id.textView1);

            country = itemView.findViewById(R.id.textView2);
            image_url= itemView.findViewById(R.id.image);






        }
    }
}
