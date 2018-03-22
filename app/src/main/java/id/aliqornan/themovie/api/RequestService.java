package id.aliqornan.themovie.api;


import java.util.List;

import id.aliqornan.themovie.BuildConfig;
import id.aliqornan.themovie.model.Movie;
import id.aliqornan.themovie.model.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestService {

    @GET(BuildConfig.GET_SEARCH)
    Call<Response<List<Movie>>> getSearchService(@Query("query") String query, @Query("page") String page);

    @GET(BuildConfig.GET_NOW_PLAYING)
    Call<Response<List<Movie>>> getNowPlayingService();

    @GET(BuildConfig.GET_UPCOMING)
    Call<Response<List<Movie>>> getUpcomingService();
}
