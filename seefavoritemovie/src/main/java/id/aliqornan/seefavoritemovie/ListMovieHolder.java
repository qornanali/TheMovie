package id.aliqornan.seefavoritemovie;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * Created by qornanali on 22/03/18.
 */

public class ListMovieHolder extends BaseHolder<Movie> {

    @BindView(R.id.text_movie_name)
    TextView tvMovieName;
    @BindView(R.id.text_movie_overview)
    TextView tvMovieOverview;
    @BindView(R.id.text_movie_rating)
    TextView tvMovieRating;
    @BindView(R.id.image_movie_poster)
    ImageView ivMoviePoster;

    public ListMovieHolder(View itemView) {
        super(itemView);
    }

    public void bind(Context context, int position, Movie data) {
        tvMovieName.setText(data.getTitle());
        tvMovieOverview.setText(data.getOverview());
        Picasso.with(context).load(BuildConfig.BASE_IMAGE + "w154" + data.getPosterPath()).error(R.color.colorAccent).into(ivMoviePoster);
        tvMovieRating.setText(String.format(context.getString(R.string.rating_value), data.getVoteAverage(), data.getVoteCount()));
    }

}
