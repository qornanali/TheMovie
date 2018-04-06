package id.aliqornan.themovie.feature;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;
import id.aliqornan.themovie.BuildConfig;
import id.aliqornan.themovie.R;
import id.aliqornan.themovie.data.MovieSQLiteHelper;
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
    @BindView(R.id.image_do_favorite)
    ImageView ivDoFavorite;

    Movie movie;
    MovieSQLiteHelper movieSQLiteHelper;
    boolean isFavorited = false;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        initView();
        displayHome();
        movieSQLiteHelper = new MovieSQLiteHelper(this);
        try {
            movie = (Movie) getIntent().getExtras().getSerializable("movie");
            Picasso.with(this).load(BuildConfig.BASE_IMAGE + "w154" + movie.getPosterPath()).error(R.color.colorAccent).into(ivMoviePoster);
            Picasso.with(this).load(BuildConfig.BASE_IMAGE + "w500" + movie.getBackdropPath()).error(R.color.colorAccent).into(ivMovieBackdrop);
            setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
            getSupportActionBar().setTitle(movie.getTitle());
            tvMovieOverview.setText(movie.getOverview());
            tvMovieLanguage.setText(movie.getOriginalLanguage().toUpperCase());
            tvMovieRating.setText(String.format(getString(R.string.rating_value), movie.getVoteAverage(), movie.getVoteCount()));
            tvMovieReleaseDate.setText(DateFormatter.format("MMM dd, yyyy").parseToString(
                    DateFormatter.format("yyyy-MM-dd").parseToDate(movie.getReleaseDate())));

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("");
            progressDialog.setCancelable(false);
            progressDialog.show();
            new GetFavoriteDatabyIdAsnyc(this).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.image_do_favorite)
    public void onImageDoFavoriteClicked() {
        if (movie != null) {
            movieSQLiteHelper.open();
            if (isFavorited) {
                isFavorited = movieSQLiteHelper.delete(movie.getId()) > 0;
                setMovieFavorited(false);
            } else {
                isFavorited = movieSQLiteHelper.insert(movie) > 0;
                setMovieFavorited(true);
            }
            movieSQLiteHelper.close();
        }
    }

    public void setMovieFavorited(boolean condition) {
        if (condition) {
            ivDoFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite));
        } else {
            ivDoFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border));
        }
    }

    private class GetFavoriteDatabyIdAsnyc extends AsyncTask<String, Void, Movie> {

        Context context;

        public GetFavoriteDatabyIdAsnyc(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Movie doInBackground(String... strings) {
            movieSQLiteHelper.open();
            Movie movieResult = movieSQLiteHelper.queryById(movie.getId(), null, null);
            movieSQLiteHelper.close();
            return movieResult;
        }

        @Override
        protected void onPostExecute(Movie movieResult) {
            super.onPostExecute(movie);
            if (movieResult != null) {
                movieSQLiteHelper.open();
                isFavorited = movieSQLiteHelper.update(movie) > 0;
                setMovieFavorited(true);
                movieSQLiteHelper.close();
            } else {
                isFavorited = false;
                setMovieFavorited(false);
            }
            progressDialog.dismiss();
        }
    }

}
