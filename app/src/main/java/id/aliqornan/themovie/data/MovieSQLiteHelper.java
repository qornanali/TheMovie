package id.aliqornan.themovie.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import id.aliqornan.themovie.model.Movie;

/**
 * Created by qornanali on 27/03/18.
 */

public class MovieSQLiteHelper {

    private Context context;
    private DbSQLiteHelper dbHelper;
    private SQLiteDatabase database;

    public MovieSQLiteHelper(Context context) {
        this.context = context;
    }

    public static String createTable() {
        return "CREATE TABLE " + MovieSQLiteHelper.MovieEntry.TBL_NAME + " ("
                + MovieEntry._ID + " INTEGER PRIMARY KEY,"
                + MovieEntry.COL_NAME_ADULT + " INTEGER(1), "
                + MovieEntry.COL_NAME_BACKDROP_PATH + " TEXT, "
                + MovieEntry.COL_NAME_ORIGINAL_TITLE + " TEXT, "
                + MovieEntry.COL_NAME_OVERVIEW + " TEXT, "
                + MovieEntry.COL_NAME_POPULARITY + " REAL, "
                + MovieEntry.COL_NAME_POSTER_PATH + " TEXT, "
                + MovieEntry.COL_NAME_RELEASE_DATE + " TEXT, "
                + MovieEntry.COL_NAME_TITLE + " TEXT, "
                + MovieEntry.COL_NAME_VOTE_COUNT + " INTEGER, "
                + MovieEntry.COL_NAME_VOTE_AVERAGE + " REAL, "
                + MovieEntry.COL_NAME_VIDEO + " INTEGER(1), "
                + MovieEntry.COL_NAME_ORIGINAL_LANGUAGE + " TEXT " + " )";
    }

    public static String dropTable() {
        return "DROP TABLE IF EXISTS " + MovieEntry.TBL_NAME;
    }

    public MovieSQLiteHelper open() throws SQLException {
        dbHelper = new DbSQLiteHelper(context);
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
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MovieEntry._ID)));
                movie.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COL_NAME_BACKDROP_PATH)));
                movie.setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COL_NAME_ORIGINAL_LANGUAGE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COL_NAME_OVERVIEW)));
                movie.setOriginalTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COL_NAME_ORIGINAL_TITLE)));
                movie.setPopularity(cursor.getFloat(cursor.getColumnIndexOrThrow(MovieEntry.COL_NAME_POPULARITY)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COL_NAME_POSTER_PATH)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COL_NAME_RELEASE_DATE)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COL_NAME_TITLE)));
                movie.setVoteAverage(cursor.getFloat(cursor.getColumnIndexOrThrow(MovieEntry.COL_NAME_VOTE_AVERAGE)));
                movie.setVoteCount(cursor.getInt(cursor.getColumnIndexOrThrow(MovieEntry.COL_NAME_VOTE_COUNT)));
                movie.setVideo(cursor.getInt(cursor.getColumnIndexOrThrow(MovieEntry.COL_NAME_VIDEO)) == 1 ? true : false);
                movie.setAdult(cursor.getInt(cursor.getColumnIndexOrThrow(MovieEntry.COL_NAME_ADULT)) == 1 ? true : false);

                movies.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return movies;
    }

    public int count(String selection) {
        String[] column = {MovieEntry._ID};
        Cursor cursor = database.query(MovieEntry.TBL_NAME, column, selection, null, null, null, MovieEntry._ID + " ASC", null);
        return cursor.getCount();
    }

    public long insert(Movie movie) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(MovieEntry._ID, movie.getId());
        initialValues.put(MovieEntry.COL_NAME_BACKDROP_PATH, movie.getBackdropPath());
        initialValues.put(MovieEntry.COL_NAME_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
        initialValues.put(MovieEntry.COL_NAME_OVERVIEW, movie.getOverview());
        initialValues.put(MovieEntry.COL_NAME_ORIGINAL_TITLE, movie.getOriginalTitle());
        initialValues.put(MovieEntry.COL_NAME_POPULARITY, movie.getPopularity());
        initialValues.put(MovieEntry.COL_NAME_POSTER_PATH, movie.getPosterPath());
        initialValues.put(MovieEntry.COL_NAME_RELEASE_DATE, movie.getReleaseDate());
        initialValues.put(MovieEntry.COL_NAME_TITLE, movie.getTitle());
        initialValues.put(MovieEntry.COL_NAME_VOTE_AVERAGE, movie.getVoteAverage());
        initialValues.put(MovieEntry.COL_NAME_VOTE_COUNT, movie.getVoteCount());
        initialValues.put(MovieEntry.COL_NAME_VIDEO, movie.getVideo() == true ? 1 : 0);
        initialValues.put(MovieEntry.COL_NAME_ADULT, movie.getAdult() == true ? 1 : 0);
        return database.insert(MovieEntry.TBL_NAME, null, initialValues);
    }

    public int update(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(MovieEntry._ID, movie.getId());
        args.put(MovieEntry.COL_NAME_BACKDROP_PATH, movie.getBackdropPath());
        args.put(MovieEntry.COL_NAME_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
        args.put(MovieEntry.COL_NAME_OVERVIEW, movie.getOverview());
        args.put(MovieEntry.COL_NAME_ORIGINAL_TITLE, movie.getOriginalTitle());
        args.put(MovieEntry.COL_NAME_POPULARITY, movie.getPopularity());
        args.put(MovieEntry.COL_NAME_POSTER_PATH, movie.getPosterPath());
        args.put(MovieEntry.COL_NAME_RELEASE_DATE, movie.getReleaseDate());
        args.put(MovieEntry.COL_NAME_TITLE, movie.getTitle());
        args.put(MovieEntry.COL_NAME_VOTE_AVERAGE, movie.getVoteAverage());
        args.put(MovieEntry.COL_NAME_VOTE_COUNT, movie.getVoteCount());
        args.put(MovieEntry.COL_NAME_VIDEO, movie.getVideo() == true ? 1 : 0);
        args.put(MovieEntry.COL_NAME_ADULT, movie.getAdult() == true ? 1 : 0);
        return database.update(MovieEntry.TBL_NAME, args, MovieEntry._ID + "= '" + movie.getId() + "'", null);
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

    public static class MovieEntry implements BaseColumns {

        public static final String TBL_NAME = "movie";
        public static final String COL_NAME_VOTE_COUNT = "vote_count";
        public static final String COL_NAME_VIDEO = "video";
        public static final String COL_NAME_VOTE_AVERAGE = "vote_average";
        public static final String COL_NAME_TITLE = "title";
        public static final String COL_NAME_POPULARITY = "popularity";
        public static final String COL_NAME_POSTER_PATH = "poster_path";
        public static final String COL_NAME_ORIGINAL_LANGUAGE = "original_language";
        public static final String COL_NAME_ORIGINAL_TITLE = "original_title";
        public static final String COL_NAME_BACKDROP_PATH = "backdrop_path";
        public static final String COL_NAME_ADULT = "adult";
        public static final String COL_NAME_OVERVIEW = "overview";
        public static final String COL_NAME_RELEASE_DATE = "release_date";

    }

}
