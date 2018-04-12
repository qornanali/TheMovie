package id.aliqornan.themovie.feature;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import id.aliqornan.themovie.R;
import id.aliqornan.themovie.adapter.DefaultRVAdapter;
import id.aliqornan.themovie.adapter.GridMovieHolder;
import id.aliqornan.themovie.adapter.ItemClickListener;
import id.aliqornan.themovie.adapter.SpinnerAdapter;
import id.aliqornan.themovie.data.MovieSQLiteHelper;
import id.aliqornan.themovie.data.RetrofitClient;
import id.aliqornan.themovie.data.ServiceInterface;
import id.aliqornan.themovie.lib.Logger;
import id.aliqornan.themovie.model.Movie;
import id.aliqornan.themovie.model.Response;
import id.aliqornan.themovie.model.AsyncLoader;
import id.aliqornan.themovie.util.ReminderManager;
import retrofit2.Call;

public class MainActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    static int LOAD_MOVIES_ID = 110;
    int mPosition = 0;
    @BindView(R.id.my_spinner)
    Spinner spinner;
    @BindView(R.id.recycler_view_movies)
    RecyclerView rvMovies;
    @BindView(R.id.my_progress_bar)
    ProgressBar myProgressBar;
    List<Movie> movies;
    boolean initLoader = false;
    DefaultRVAdapter<GridMovieHolder, Movie> moviesAdapter;
    ServiceInterface serviceInterface;
    MovieSQLiteHelper movieSQLiteHelper;
    ReminderManager reminderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        actionBar.setDisplayShowTitleEnabled(false);
        spinner.setAdapter(new SpinnerAdapter(
                toolbar.getContext(),
                new String[]{
                        getString(R.string.now_playing),
                        getString(R.string.upcoming),
                        getString(R.string.my_favorites)
                }));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                if (!initLoader) {
                    initLoader = true;
                    getSupportLoaderManager().initLoader(LOAD_MOVIES_ID, null, MainActivity.this);
                } else {
                    getSupportLoaderManager().restartLoader(LOAD_MOVIES_ID, null, MainActivity.this);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        movies = new ArrayList<>();
        moviesAdapter = new DefaultRVAdapter<GridMovieHolder, Movie>(movies, this) {
            @Override
            public GridMovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new GridMovieHolder(inflate(R.layout.item_grid_movie, parent, false));
            }
        };
        rvMovies.setLayoutManager(new GridLayoutManager(this, 2));
        rvMovies.setNestedScrollingEnabled(false);
        rvMovies.setAdapter(moviesAdapter);
        moviesAdapter.setItemClickListener(new ItemClickListener<Movie>() {
            @Override
            public void onItemClick(int position, Movie data) {
                if (data != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("movie", data);
                    openActivity(DetailMovieActivity.class, bundle, false);
                }
            }
        });
        reminderManager = new ReminderManager(this);
        movieSQLiteHelper = new MovieSQLiteHelper(this);
        serviceInterface = RetrofitClient.init(this).create(ServiceInterface.class);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_set_notification);
        menuItem.setTitle(reminderManager.isTurnOn() ? getString(R.string.turn_off_notif) : getString(R.string.turn_on_notif));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_language:
                Toast.makeText(this, getString(R.string.action_change_language), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                return true;
            case R.id.action_search:
                openActivity(SearchActivity.class, null, false);
                return true;
            case R.id.action_set_notification:
                if(reminderManager.isTurnOn()){
                    reminderManager.turnOff(ReminderManager.DAILY);
                    reminderManager.turnOff(ReminderManager.RELEASE_TODAY);
                }else{
                    reminderManager.turnOn(7, 0, 0, ReminderManager.DAILY, null);
                    reminderManager.turnOn(8, 0, 0, ReminderManager.RELEASE_TODAY, null);
                }
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
                item.setTitle(reminderManager.isTurnOn() ? getString(R.string.turn_off_notif) : getString(R.string.turn_on_notif));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @NonNull
    @Override
    public Loader<List<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        onDataLoading(true);
        return new AsyncLoader<List<Movie>>(this) {

            @Nullable
            @Override
            protected List<Movie> onLoadInBackground() {
                return loadData();
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> data) {
        if (data != null) {
            movies.clear();
            movies.addAll(data);
            moviesAdapter.notifyDataSetChanged();
            onDataLoading(false);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {

    }

    public void onDataLoading(boolean yes) {
        rvMovies.setVisibility(yes ? View.GONE : View.VISIBLE);
        myProgressBar.setVisibility(yes ? View.VISIBLE : View.GONE);
    }

    private List<Movie> loadData() {
        List<Movie> movieResult;
        if (mPosition == 2) {
            movieSQLiteHelper.open();
            movieResult = movieSQLiteHelper.query(null, null, null);
            movieSQLiteHelper.close();
        } else {
            Call<Response<List<Movie>>> callMovies = (mPosition == 0) ?
                    serviceInterface.getUpcomingService() : serviceInterface.getNowPlayingService();
            try {
                movieResult = callMovies.execute().body().getResults();
            } catch (IOException e) {
                Logger.log(Log.ERROR, e.getMessage());
                return null;
            }
        }
        return movieResult;
    }
}
