package id.aliqornan.seefavoritemovie;

import android.content.UriMatcher;
import android.net.Uri;

/**
 * Created by qornanali on 06/04/18.
 */

public class MovieContract {

    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String AUTHORITY = "id.aliqornan.themovie";
    private static final String BASE_PATH = MovieEntry.TBL_NAME;
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(BASE_PATH).build();
    private static final int GET_MOVIES = 1;
    private static final int GET_MOVIES_BY_id = 2;

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, GET_MOVIES);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", GET_MOVIES_BY_id);
    }
}
