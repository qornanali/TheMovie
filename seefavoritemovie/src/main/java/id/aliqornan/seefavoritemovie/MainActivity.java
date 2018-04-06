package id.aliqornan.seefavoritemovie;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    static int LOAD_MOVIES_ID = 110;
    @BindView(R.id.my_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    List<Movie> movies;
    DefaultRVAdapter<ListMovieHolder, Movie> moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        movies = new ArrayList<Movie>();
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        moviesAdapter = new DefaultRVAdapter<ListMovieHolder, Movie>(movies, this) {
            @Override
            public ListMovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ListMovieHolder(inflate(R.layout.item_list_movie, parent, false));
            }
        };
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setNestedScrollingEnabled(false);
        rvMovies.setAdapter(moviesAdapter);
        moviesAdapter.setItemClickListener(new ItemClickListener<Movie>() {
            @Override
            public void onItemClick(int position, Movie data) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie", data);
                openActivity(DetailMovieActivity.class, bundle, false);
            }
        });
        getSupportLoaderManager().initLoader(LOAD_MOVIES_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_MOVIES_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        onDataLoading(true);
        return new CursorLoader(this, MovieContract.CONTENT_URI, null,
                null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            onDataLoading(false);
            data.moveToFirst();
            if (data.getCount() > 0) {
                do {
                    movies.add(new Movie(data));
                    data.moveToNext();
                } while (!data.isAfterLast());
            }
            data.close();
            moviesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        movies.clear();
        onDataLoading(true);
    }

    public void onDataLoading(boolean yes) {
        rvMovies.setVisibility(yes ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(yes ? View.VISIBLE : View.GONE);
    }
}
