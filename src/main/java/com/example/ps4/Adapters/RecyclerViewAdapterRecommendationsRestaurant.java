package com.example.ps4.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps4.Models.Recommendation;
import com.example.ps4.R;
import com.example.ps4.RecommendationActivity;
import com.example.ps4.RecommendationRestaurant;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class RecyclerViewAdapterRecommendationsRestaurant extends RecyclerView.Adapter<RecyclerViewAdapterRecommendationsRestaurant.ViewHolder> {

        private static final String TAG = null ;
        private List recList;
        private RecommendationRestaurant.GetAllPositiveReviewsOnCompleteListener.RecommendationsListRecyclerViewOnItemClickListener reviewListRecyclerViewOnItemClickListener;
        private Context context ;

        public RecyclerViewAdapterRecommendationsRestaurant(List recList, RecommendationRestaurant.GetAllPositiveReviewsOnCompleteListener.RecommendationsListRecyclerViewOnItemClickListener reviewListRecyclerViewOnItemClickListener) {
            this.recList = recList;
            this.reviewListRecyclerViewOnItemClickListener = reviewListRecyclerViewOnItemClickListener;
        }


    @NonNull
    @Override
    public RecyclerViewAdapterRecommendationsRestaurant.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommandation_row, parent, false);

        itemView.setOnClickListener((View.OnClickListener) reviewListRecyclerViewOnItemClickListener);

        return new RecyclerViewAdapterRecommendationsRestaurant.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterRecommendationsRestaurant.ViewHolder holder, int position) {
        Recommendation review = (Recommendation) recList.get(position);
        // String i_am_tired = ville.toStringImage()
        holder.hotelname.setText(review.getNom_hotel());
        holder.cityname.setText(review.getNom_ville());
        holder.moyenne.setText(String.valueOf((new DecimalFormat("##.##").format(review.getMoyenne()))+ "% of this Restaurant's reviews are positive"));
        Picasso.get()
                .load(review.getImageURI())
                .into(holder.image_url , new com.squareup.picasso.Callback() {
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
        return recList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image_url;
        TextView cityname;
        TextView hotelname;
        TextView moyenne ;


        ViewHolder(View itemView) {

            super(itemView);

            hotelname = itemView.findViewById(R.id.textView1);
            cityname = itemView.findViewById(R.id.textView2);
            image_url= itemView.findViewById(R.id.image);
            moyenne=itemView.findViewById(R.id.textView15);

        }
    }
}
