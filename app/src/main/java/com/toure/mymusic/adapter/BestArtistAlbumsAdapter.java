package com.toure.mymusic.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.toure.mymusic.OnClickAlbumHandler;
import com.toure.mymusic.R;
import com.toure.mymusic.data.Album;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BestArtistAlbumsAdapter extends PagedListAdapter<Album, BestArtistAlbumsAdapter.ViewHolder> {

    static final String TAG = BestArtistAlbumsAdapter.class.getSimpleName();
    private Context mContext;
    private OnClickAlbumHandler mClickHandler;
    private String artistName;

    private static DiffUtil.ItemCallback<Album> DIFF_CALLBACK = new DiffUtil.ItemCallback<Album>() {
        @Override
        public boolean areItemsTheSame(@NonNull Album oldItem, @NonNull Album newItem) {
            return oldItem.getMbid().equals(newItem.getMbid()) && oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Album oldItem, @NonNull Album newItem) {
            return oldItem.equals(newItem);
        }
    };

    public BestArtistAlbumsAdapter(Context context, OnClickAlbumHandler handler) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        mClickHandler = handler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.best_album_item_layout;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = getItem(position);

        if (album != null) {
            holder.albumName.setText(album.getName());
            Glide
                    .with(mContext)
                    .load(album.getImageUrl())
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_artist_image)
                    .error(R.drawable.placeholder_artist_image)
                    .into(holder.albumImage);
        }

    }

    public void setArtist(String artistname) {
        artistName = artistname;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.album_image)
        ImageView albumImage;
        @BindView(R.id.album_name)
        TextView albumName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Log.d(TAG, artistName + " " + getItem(pos).getName());
            mClickHandler.onClick(artistName, getItem(pos).getName());
        }
    }
}
