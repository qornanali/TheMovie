package id.aliqornan.seefavoritemovie;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by qornanali on 22/03/18.
 */

public abstract class DefaultRVAdapter<H extends BaseHolder<M>, M> extends RecyclerView.Adapter<H> {

    private List<M> datum;
    private Context context;
    private ItemClickListener<M> itemClickListener;


    public DefaultRVAdapter(List<M> datum, Context context) {
        this.datum = datum;
        this.context = context;
    }

    @Override
    public abstract H onCreateViewHolder(ViewGroup parent, int viewType);

    @NonNull
    protected View inflate(@LayoutRes int idResLayout, ViewGroup parent, boolean attachToRoot) {
        return LayoutInflater.from(context).inflate(idResLayout, parent, attachToRoot);
    }

    @Override
    public void onBindViewHolder(H holder, final int position) {
        holder.bind(context, position, datum.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(position, datum.get(position));
                }
            }
        });
    }

    public void setItemClickListener(ItemClickListener<M> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public int getItemCount() {
        return datum.size();
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }
}
