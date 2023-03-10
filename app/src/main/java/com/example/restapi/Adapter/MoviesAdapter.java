package com.example.restapi.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restapi.Interface.IOnClickItem;
import com.example.restapi.Model.Result;
import com.example.restapi.R;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    List<Result> results;
    Context context;
    IOnClickItem iOnClickItem;

    public MoviesAdapter(List<Result> results, Context context, IOnClickItem iOnClick) {
        this.results = results;
        this.context = context;
        iOnClickItem = iOnClick;
    }

    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {

        String image_url = holder.IMAGE_URL_BASE_PATH + results.get(position).getPoster_path();

        Log.e("image_url", "onBindViewHolder: " + image_url );

        Glide.with(context)
                        .load(image_url)
                        .placeholder(android.R.drawable.sym_def_app_icon)
                        .error(android.R.drawable.sym_def_app_icon)
                        .into(holder.movieImage);

        holder.movieTitle.setText(results.get(position).getTitle());
        holder.data.setText(results.get(position).getRelease_date());
        holder.movieDescription.setText(results.get(position).getOverview());
        holder.rating.setText(String.valueOf(results.get(position).getVote_average()));

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // declaration of widget
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;
        ImageView movieImage;
        CardView cardView;
        String IMAGE_URL_BASE_PATH = "https://image.tmdb.org/t/p/w342//";

        public ViewHolder(@NonNull View v) {
            super(v);

            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieImage = (ImageView) v.findViewById(R.id.movie_image);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.date);
            movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
            cardView = (CardView) v.findViewById(R.id.cardView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            iOnClickItem.onClickItem(getAbsoluteAdapterPosition());
        }
    }
}
