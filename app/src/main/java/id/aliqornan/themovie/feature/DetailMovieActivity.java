package id.aliqornan.themovie.feature;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;
import id.aliqornan.themovie.BuildConfig;
import id.aliqornan.themovie.R;
import id.aliqornan.themovie.data.MovieSQLiteHelper;
import id.aliqornan.themovie.lib.DateFormatter;
import id.aliqornan.themovie.lib.Logger;
import id.aliqornan.themovie.model.Movie;
import id.aliqornan.themovie.model.AsyncLoader;

public class DetailMovieActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Movie> {

    static int LOAD_MOVIES_ID = 110;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        initView();
        displayHome();
        movieSQLiteHelper = new MovieSQLiteHelper(this);
        getSupportLoaderManager().initLoader(LOAD_MOVIES_ID, getIntent().getExtras(), this);
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

    @NonNull
    @Override
    public Loader<Movie> onCreateLoader(int id, @Nullable final Bundle args) {
        return new AsyncLoader<Movie>(this) {
            @Nullable
            @Override
            public Movie loadInBackground() {
                try {
                    movie = (Movie) args.getSerializable("movie");
                    movieSQLiteHelper.open();
                    if (movieSQLiteHelper.queryById(movie.getId(), null, null) != null) {
                        movieSQLiteHelper.beginTransaction();
                        isFavorited = movieSQLiteHelper.update(movie) > 0;
                        movieSQLiteHelper.endTransaction();
                    } else {
                        isFavorited = false;
                    }
                    movieSQLiteHelper.close();
                    return movie;
                } catch (Exception e) {
                    Logger.log(Log.ERROR, e.getMessage());
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Movie> loader, Movie data) {
        try {
            setMovieFavorited(isFavorited);
            actionBar.setTitle(data.getTitle());
            Picasso.with(this).load(BuildConfig.BASE_IMAGE + "w154" + movie.getPosterPath()).error(R.color.colorAccent).into(ivMoviePoster);
            Picasso.with(this).load(BuildConfig.BASE_IMAGE + "w500" + movie.getBackdropPath()).error(R.color.colorAccent).into(ivMovieBackdrop);
            setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
            getSupportActionBar().setTitle(movie.getTitle());
            tvMovieOverview.setText(movie.getOverview());
            tvMovieLanguage.setText(movie.getOriginalLanguage().toUpperCase());
            tvMovieRating.setText(String.format(getString(R.string.rating_value), movie.getVoteAverage(), movie.getVoteCount()));
            tvMovieReleaseDate.setText(DateFormatter.format("MMM dd, yyyy").parseToString(
                    DateFormatter.format("yyyy-MM-dd").parseToDate(movie.getReleaseDate())));
        } catch (Exception e) {
            Logger.log(Log.ERROR, e.getMessage());
        }
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Movie> loader) {

    }


}
