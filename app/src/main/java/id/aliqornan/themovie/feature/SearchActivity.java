package id.aliqornan.themovie.feature;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import id.aliqornan.themovie.R;
import id.aliqornan.themovie.adapter.DefaultRVAdapter;
import id.aliqornan.themovie.adapter.ItemClickListener;
import id.aliqornan.themovie.adapter.ListMovieHolder;
import id.aliqornan.themovie.data.RetrofitClient;
import id.aliqornan.themovie.data.ServiceInterface;
import id.aliqornan.themovie.model.Movie;
import id.aliqornan.themovie.util.AsyncLoader;

public class SearchActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    static int LOAD_MOVIES_ID = 110;
    static String PAGE = "page";
    static String MOVIE_NAME = "movie_name";
    @BindView(R.id.recycler_view_movies)
    RecyclerView rvMovies;
    @BindView(R.id.edit_movie_name)
    EditText etMovieName;
    @BindView(R.id.btn_search_movie)
    Button btnSearch;
    @BindView(R.id.my_progress_bar)
    ProgressBar myProgressBar;
    List<Movie> movies;
    DefaultRVAdapter<ListMovieHolder, Movie> moviesAdapter;

    ServiceInterface serviceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        displayHome();
        actionBar.setTitle(getString(R.string.title_search));

        movies = new ArrayList<>();

        serviceInterface = RetrofitClient.init(this).create(ServiceInterface.class);
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
                Bundle args = new Bundle();
                args.putString(PAGE, "1");
                args.putString(MOVIE_NAME, movieName);
                getSupportLoaderManager().initLoader(LOAD_MOVIES_ID, args, SearchActivity.this);
            }
        });
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
    }

    @NonNull
    @Override
    public Loader<List<Movie>> onCreateLoader(int id, @Nullable final Bundle args) {
        onDataLoading(true);
        return new AsyncLoader<List<Movie>>(this) {

            @Nullable
            @Override
            protected List<Movie> onLoadInBackground() {
                return loadData(args);
            }
        };
    }

    private List<Movie> loadData(Bundle args) {
        try {
            return serviceInterface.getSearchService(
                    args.getString(MOVIE_NAME),
                    args.getString(PAGE))
                    .execute().body().getResults();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> data) {
        if (data != null) {
            movies.clear();
            movies.addAll(data);
            moviesAdapter.notifyDataSetChanged();
        }
        onDataLoading(false);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {

    }

    public void onDataLoading(boolean yes) {
        rvMovies.setVisibility(yes ? View.GONE : View.VISIBLE);
        myProgressBar.setVisibility(yes ? View.VISIBLE : View.GONE);
    }
}