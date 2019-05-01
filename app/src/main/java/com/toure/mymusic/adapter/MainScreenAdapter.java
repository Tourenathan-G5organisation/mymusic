package com.toure.mymusic.adapter;

import android.content.Context;
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

public class MainScreenAdapter extends PagedListAdapter<Album, MainScreenAdapter.ViewHolder> {

    private static final String TAC = MainScreenAdapter.class.getSimpleName();
    private Context mContext;
    private OnClickAlbumHandler mClickHandler;

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

    public MainScreenAdapter(Context mContext, OnClickAlbumHandler handler) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
        mClickHandler = handler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.album_item_layout;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Album album =  getItem(position);
        if (album  != null){
            holder.albumName.setText(album.getName());
            holder.artistName.setText(album.getArtistName());
            Glide
                    .with(mContext)
                    .load(album.getImageUrl())
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_artist_image)
                    .error(R.drawable.placeholder_artist_image)
                    .into(holder.albumImage);
        }

    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.album_image)
        ImageView albumImage;
        @BindView(R.id.album_name)
        TextView albumName;
        @BindView(R.id.artist_name)
        TextView artistName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mClickHandler.onClick(getItem(pos).getArtistName(), getItem(pos).getName());
        }
    }
}
