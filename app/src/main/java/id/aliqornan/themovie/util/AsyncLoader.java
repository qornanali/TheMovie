package id.aliqornan.themovie.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by qornanali on 10/04/18.
 */

public class AsyncLoader<M> extends AsyncTaskLoader<M> {

    public AsyncLoader(@NonNull Context context) {
        super(context);
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        }
    }

    @Nullable
    @Override
    public M loadInBackground() {
        return null;
    }
}
