package id.aliqornan.themovie.feature;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import id.aliqornan.themovie.R;
import id.aliqornan.themovie.model.Movie;

public class MyFavoritesActivity extends BaseActivity {

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.my_progress_bar)
    ProgressBar myProgressBar;
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites);
        initView();
        displayHome();
    }
}
