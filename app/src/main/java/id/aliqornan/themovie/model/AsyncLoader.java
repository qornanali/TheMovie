package id.aliqornan.themovie.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import id.aliqornan.themovie.lib.Logger;

/**
 * Created by qornanali on 10/04/18.
 */

public class AsyncLoader<M> extends AsyncTaskLoader<M> {

    private M data;
    private boolean hasResult = false;

    public AsyncLoader(@NonNull Context context) {
        super(context);
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        } else if (hasResult) {
            deliverResult(data);
        }
    }

    @Override
    public void deliverResult(@Nullable M data) {
        this.data = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult) {
            onReleaseResource(data);
            data = null;
            hasResult = false;
        }
    }

    protected void onReleaseResource(M data) {

    }

    @Nullable
    @Override
    public M loadInBackground() {
        return null;
    }
}
