package com.example.ps4.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps4.CitiesActivity;
import com.example.ps4.EndroitDetails;
import com.example.ps4.Models.Review;
import com.example.ps4.Models.Ville;
import com.example.ps4.R;

import java.text.BreakIterator;
import java.util.List;

import static com.squareup.picasso.Picasso.get;

public class RecyclerViewAdapterReview extends RecyclerView.Adapter<RecyclerViewAdapterReview.ViewHolder> {
    private static final String TAG = null ;
    private List villeList;
    private EndroitDetails.GetAllReviewsOnCompleteListener.ReviewListRecyclerViewOnItemClickListener reviewListRecyclerViewOnItemClickListener;
    private Context context ;
    public RecyclerViewAdapterReview(List villeList, EndroitDetails.GetAllReviewsOnCompleteListener.ReviewListRecyclerViewOnItemClickListener reviewListRecyclerViewOnItemClickListener) {
        this.villeList = villeList;
        this.reviewListRecyclerViewOnItemClickListener = reviewListRecyclerViewOnItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("", "in on create view holder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_rating, parent, false);

        itemView.setOnClickListener((View.OnClickListener) reviewListRecyclerViewOnItemClickListener);

        return new RecyclerViewAdapterReview.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterReview.ViewHolder holder, int position) {
        Log.d("", "bin review");
        Review review = (Review) villeList.get(position);
        // String i_am_tired = ville.toStringImage()
        String full_name = review.getUser_nom() + " " + review.getUser_prenom();
        holder.cityname.setText(full_name);
        holder.country.setText(review.getComment());
        holder.rating_bar.setRating(review.getRating());




    }

    @Override
    public int getItemCount() {
        return villeList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {



        ImageView image_url;
        TextView cityname;
        TextView country;
        RatingBar rating_bar ;


        ViewHolder(View itemView) {

            super(itemView);

            cityname = itemView.findViewById(R.id.textView1);
            rating_bar = itemView.findViewById(R.id.rating_bar);
            country = itemView.findViewById(R.id.textView2);
            image_url= itemView.findViewById(R.id.image);






        }
    }
}
