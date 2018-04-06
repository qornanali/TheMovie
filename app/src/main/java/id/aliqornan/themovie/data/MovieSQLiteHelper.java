package id.aliqornan.themovie.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import id.aliqornan.themovie.model.Movie;

/**
 * Created by qornanali on 06/04/18.
 */

public class MovieSQLiteHelper {

    public SQLiteDatabase database;
    private Context context;
    private DatabaseHelper dbHelper;

    public MovieSQLiteHelper(Context context) {
        this.context = context;
    }


    public MovieSQLiteHelper open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public List<Movie> query(String columns[], String selection, String limit) {
        List<Movie> movies = new ArrayList<Movie>();
        Cursor cursor = database.query(MovieEntry.TBL_NAME, columns, selection, null, null, null, MovieEntry._ID + " ASC", limit);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                movies.add(new Movie(cursor));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return movies;
    }

    public Movie queryById(int id, String columns[], String selection) {
        List<Movie> movies = query(columns, MovieEntry._ID + " = " + id + " " + (selection != null ? "AND " + selection : ""), "1");
        return movies.size() > 0 ? movies.get(0) : null;
    }

    public long insert(Movie movie) {
        return database.insert(MovieEntry.TBL_NAME, null, movie.toContentValues());
    }

    public int update(Movie movie) {
        return database.update(MovieEntry.TBL_NAME, movie.toContentValues(), MovieEntry._ID + "= '" + movie.getId() + "'", null);
    }

    public int delete(int id) {
        return database.delete(MovieEntry.TBL_NAME, MovieEntry._ID + " = '" + id + "'", null);
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

}