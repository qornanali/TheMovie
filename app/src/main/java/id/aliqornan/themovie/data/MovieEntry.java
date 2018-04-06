package id.aliqornan.themovie.data;

import android.provider.BaseColumns;

/**
 * Created by qornanali on 06/04/18.
 */

public class MovieEntry implements BaseColumns {

    public static final String TBL_NAME = "favorite_movie";
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

    public static String createTable() {
        return "CREATE TABLE " + TBL_NAME + " ("
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

}

