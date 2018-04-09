package id.aliqornan.themovie.data;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.aliqornan.themovie.BuildConfig;
import id.aliqornan.themovie.R;
import id.aliqornan.themovie.feature.MyFavoriteWidget;
import id.aliqornan.themovie.model.Movie;
import id.aliqornan.themovie.util.DateFormatter;

/**
 * Created by qornanali on 09/04/18.
 */

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Movie> movies = new ArrayList<>();
    private Context context;
    private int mAppWidgetId;
    private MovieSQLiteHelper movieSQLiteHelper;

    public StackRemoteViewsFactory(Intent intent, Context context) {
        this.context = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        movieSQLiteHelper = new MovieSQLiteHelper(context);
    }

    @Override
    public void onCreate() {
        movieSQLiteHelper.open();
        movies.addAll(movieSQLiteHelper.query(null, null, null));
        movieSQLiteHelper.close();
    }


    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public RemoteViews getViewAt(final int position) {
        final RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.stack_item_movie);
        rv.setTextViewText(R.id.text_movie_name, movies.get(position).getTitle());
        try {
            Bitmap bmpBackdrop = Picasso.with(context)
                    .load(BuildConfig.BASE_IMAGE + "w500" + movies.get(position).getBackdropPath())
                    .error(R.color.colorAccent).get();
            rv.setImageViewBitmap(R.id.image_movie_backdrop, bmpBackdrop);
            rv.setTextViewText(R.id.text_movie_release_date, DateFormatter.format("dd/MM/yyy").parseToString(
                    DateFormatter.format("yyyy-MM-dd").parseToDate(movies.get(position).getReleaseDate())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(MyFavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.image_movie_backdrop, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
