package id.aliqornan.seefavoritemovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by qornanali on 22/03/18.
 */

public class BaseHolder<M> extends RecyclerView.ViewHolder {

    public BaseHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Context context, int position, M data){

    }

}
