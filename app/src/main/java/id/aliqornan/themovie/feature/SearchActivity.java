package id.aliqornan.themovie.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import id.aliqornan.themovie.R;
import id.aliqornan.themovie.adapter.DefaultRVAdapter;
import id.aliqornan.themovie.adapter.GridMovieHolder;
import id.aliqornan.themovie.adapter.ItemClickListener;
import id.aliqornan.themovie.adapter.ListMovieHolder;
import id.aliqornan.themovie.api.RequestService;
import id.aliqornan.themovie.api.RetrofitClient;
import id.aliqornan.themovie.model.Movie;
import id.aliqornan.themovie.model.Response;
import id.aliqornan.themovie.util.Logger;
import retrofit2.Call;
import retrofit2.Callback;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.edit_movie_name)
    EditText etMovieName;
    @BindView(R.id.btn_search_movie)
    Button btnSearch;

    List<Movie> movies;

    DefaultRVAdapter<ListMovieHolder, Movie> moviesAdapter;

    RequestService requestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        actionBar.setTitle(getString(R.string.title_search));

        movies = new ArrayList<>();

        requestService = RetrofitClient.init(this).create(RequestService.class);
        moviesAdapter = new DefaultRVAdapter<ListMovieHolder, Movie>(movies, this) {
            @Override
            public ListMovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ListMovieHolder(inflate(R.layout.item_list_movie, parent, false));
            }
        };
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setNestedScrollingEnabled(false);
        rvMovies.setAdapter(moviesAdapter);

        etMovieName = (EditText) findViewById(R.id.edit_movie_name);
        btnSearch = (Button) findViewById(R.id.btn_search_movie);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieName = etMovieName.getText().toString();

                if (TextUtils.isEmpty(movieName)) return;

                Call<Response<List<Movie>>> callMovies = requestService.getSearchService(movieName, "1");
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
            }
        });
        moviesAdapter.setItemClickListener(new ItemClickListener<Movie>() {
            @Override
            public void onItemClick(int position, Movie data) {
                if (data != null) {
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("movie", data);
                        openActivity(DetailMovieActivity.class, bundle, false);
                    }
                }
            }
        });
    }

}