package id.aliqornan.themovie.feature;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import id.aliqornan.themovie.BuildConfig;
import id.aliqornan.themovie.R;
import id.aliqornan.themovie.model.Movie;
import id.aliqornan.themovie.util.DateFormatter;

public class DetailMovieActivity extends BaseActivity {

    @BindView(R.id.text_movie_overview)
    TextView tvMovieOverview;
    @BindView(R.id.text_movie_rating)
    TextView tvMovieRating;
    @BindView(R.id.text_movie_language)
    TextView tvMovieLanguage;
    @BindView(R.id.text_movie_release_date)
    TextView tvMovieReleaseDate;
    @BindView(R.id.image_movie_backdrop)
    ImageView ivMovieBackdrop;
    @BindView(R.id.image_movie_poster)
    ImageView ivMoviePoster;

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        initView();
        try {
            movie = (Movie) getIntent().getExtras().getSerializable("movie");
            Picasso.with(this).load(BuildConfig.BASE_IMAGE + "w154/" + movie.getPosterPath()).error(R.color.colorAccent).into(ivMoviePoster);
            Picasso.with(this).load(BuildConfig.BASE_IMAGE + "w500/" + movie.getBackdropPath()).error(R.color.colorAccent).into(ivMovieBackdrop);
            setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
            getSupportActionBar().setTitle(movie.getTitle());
            tvMovieOverview.setText(movie.getOverview());
            tvMovieLanguage.setText(movie.getOriginalLanguage().toUpperCase());
            tvMovieRating.setText(String.format(getString(R.string.rating_value), movie.getVoteAverage(), movie.getVoteCount()));
            tvMovieReleaseDate.setText(DateFormatter.format("MMM dd, yyyy").parseToString(
                    DateFormatter.format("yyyy-MM-dd").parseToDate(movie.getReleaseDate())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
