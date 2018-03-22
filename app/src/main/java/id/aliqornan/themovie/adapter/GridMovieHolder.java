package id.aliqornan.themovie.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import id.aliqornan.themovie.BuildConfig;
import id.aliqornan.themovie.R;
import id.aliqornan.themovie.model.Movie;

/**
 * Created by qornanali on 22/03/18.
 */

public class GridMovieHolder extends BaseHolder<Movie> {

    @BindView(R.id.text_movie_name)
    TextView tvMovieName;
    @BindView(R.id.text_movie_rating)
    TextView tvMovieRating;
    @BindView(R.id.image_movie_poster)
    ImageView ivMoviePoster;

    public GridMovieHolder(View itemView) {
        super(itemView);
    }

    public void bind(Context context, int position, Movie data) {
        tvMovieName.setText(data.getTitle());
        Picasso.with(context).load(BuildConfig.BASE_IMAGE + "w154/" + data.getPosterPath()).error(R.color.colorAccent).into(ivMoviePoster);
        tvMovieRating.setText(String.format("%.2f", data.getVoteAverage()));
    }

}
