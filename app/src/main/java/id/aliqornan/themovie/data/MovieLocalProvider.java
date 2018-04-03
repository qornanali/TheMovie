package id.aliqornan.themovie.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by qornanali on 03/04/18.
 */

public class MovieLocalProvider extends ContentProvider {

    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String AUTHORITY = "id.aliqornan.themovie";
    private static final String BASE_PATH = "movie";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    private static final int GET_MOVIES = 1;
    private static final int GET_MOVIES_BY_id = 2;

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, GET_MOVIES);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", GET_MOVIES_BY_id);
    }

    private SQLiteDatabase sqLiteDatabase;

    @Override
    public boolean onCreate() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;

        if (uriMatcher.match(uri) == GET_MOVIES) {
            cursor = sqLiteDatabase.query(MovieSQLiteHelper.MovieEntry.TBL_NAME, null, null, null, null, null, MovieSQLiteHelper.MovieEntry._ID + " DESC");
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long id = sqLiteDatabase.insert(MovieSQLiteHelper.MovieEntry.TBL_NAME, null, contentValues);

        if (id > 0) {
            Uri mUri = ContentUris.withAppendedId(CONTENT_URI, id);
            return mUri;
        }
        throw new SQLException("Insertion Failed for URI :" + uri);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int delCount = 0;
        switch (uriMatcher.match(uri)) {
            case GET_MOVIES:
                delCount = sqLiteDatabase.delete(MovieSQLiteHelper.MovieEntry.TBL_NAME, s, strings);
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        return delCount;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updCount = 0;
        switch (uriMatcher.match(uri)) {
            case GET_MOVIES:
                updCount = sqLiteDatabase.update(MovieSQLiteHelper.MovieEntry.TBL_NAME, contentValues, s, strings);
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        return updCount;
    }

    public Cursor queryByIdProvider(String id) {
        return sqLiteDatabase.query(MovieSQLiteHelper.MovieEntry.TBL_NAME, null
                , MovieSQLiteHelper.MovieEntry._ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return sqLiteDatabase.query(MovieSQLiteHelper.MovieEntry.TBL_NAME
                , null
                , null
                , null
                , null
                , null
                , MovieSQLiteHelper.MovieEntry._ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return sqLiteDatabase.insert(MovieSQLiteHelper.MovieEntry.TBL_NAME, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return sqLiteDatabase.update(MovieSQLiteHelper.MovieEntry.TBL_NAME, values, MovieSQLiteHelper.MovieEntry._ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return sqLiteDatabase.delete(MovieSQLiteHelper.MovieEntry.TBL_NAME, MovieSQLiteHelper.MovieEntry._ID + " = ?", new String[]{id});
    }
}
