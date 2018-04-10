package id.aliqornan.themovie.util;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by qornanali on 09/04/18.
 */

public class StackWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(intent, this.getApplicationContext());
    }
}
