package id.aliqornan.themovie.feature;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.aliqornan.themovie.R;
import id.aliqornan.themovie.adapter.DefaultRVAdapter;
import id.aliqornan.themovie.adapter.GridMovieHolder;
import id.aliqornan.themovie.adapter.ItemClickListener;
import id.aliqornan.themovie.adapter.SpinnerAdapter;
import id.aliqornan.themovie.api.RequestService;
import id.aliqornan.themovie.api.RetrofitClient;
import id.aliqornan.themovie.model.Movie;
import id.aliqornan.themovie.model.Response;
import id.aliqornan.themovie.util.Logger;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends BaseActivity {

    @BindView(R.id.my_spinner)
    Spinner spinner;

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
                        getString(R.string.upcoming)
                }));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.my_container, MainFragment.newInstance(position + 1))
                        .commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public static class MainFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        @BindView(R.id.rv_movies)
        RecyclerView rvMovies;

        List<Movie> movies;

        DefaultRVAdapter<GridMovieHolder, Movie> moviesAdapter;

        RequestService requestService;

        public MainFragment() {
        }

        public static MainFragment newInstance(int sectionNumber) {
            MainFragment fragment = new MainFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ButterKnife.bind(this, rootView);
            movies = new ArrayList<>();
            requestService = RetrofitClient.init(getActivity()).create(RequestService.class);
            moviesAdapter = new DefaultRVAdapter<GridMovieHolder, Movie>(movies, getActivity()) {
                @Override
                public GridMovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    return new GridMovieHolder(inflate(R.layout.item_grid_movie, parent, false));
                }
            };
            rvMovies.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rvMovies.setNestedScrollingEnabled(false);
            rvMovies.setAdapter(moviesAdapter);

            moviesAdapter.setItemClickListener(new ItemClickListener<Movie>() {
                @Override
                public void onItemClick(int position, Movie data) {
                    if (data != null) {
                        {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("movie", data);
                            ((BaseActivity) getActivity()).openActivity(DetailMovieActivity.class, bundle, false);
                        }
                    }
                }
            });

            Call<Response<List<Movie>>> callMovies = (getArguments().getInt(ARG_SECTION_NUMBER) == 1) ?
                    requestService.getUpcomingService() : requestService.getNowPlayingService();
            callMovies.enqueue(new Callback<Response<List<Movie>>>() {
                @Override
                public void onResponse(Call<Response<List<Movie>>> call, retrofit2.Response<Response<List<Movie>>> response) {
                    if (response.body() != null) {
                        movies.addAll(response.body().getResults());
                        moviesAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<Response<List<Movie>>> call, Throwable t) {
                    Logger.log(Log.ERROR, t.getMessage());
                }
            });

            return rootView;
        }
    }

}
