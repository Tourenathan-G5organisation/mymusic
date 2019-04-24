package com.toure.mymusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.toure.mymusic.OnClickSearchItemListerner;
import com.toure.mymusic.R;
import com.toure.mymusic.data.Artist;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtistSearchAdapter extends PagedListAdapter<Artist, ArtistSearchAdapter.ViewHolder> {

    private static final String TAC = ArtistSearchAdapter.class.getSimpleName();
    private Context mContext;
    private OnClickSearchItemListerner mClickHandler;

    private static DiffUtil.ItemCallback<Artist> DIFF_CALLBACK = new DiffUtil.ItemCallback<Artist>() {
        @Override
        public boolean areItemsTheSame(@NonNull Artist oldItem, @NonNull Artist newItem) {

            return oldItem.getMbid().equals(newItem.getMbid());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Artist oldItem, @NonNull Artist newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.artist_item_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ArtistSearchAdapter.ViewHolder(view);
    }

    public ArtistSearchAdapter(Context mContext, OnClickSearchItemListerner handler) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
        mClickHandler = handler;
    }

   /* @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public void setData(List<Artist> artist) {
        mData = artist;
        notifyDataSetChanged();
    }*/

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Artist artist = getItem(position);
        if (artist != null){
            holder.artistName.setText(artist.getName());
            Glide
                    .with(mContext)
                    .load(artist.getImageUrl())
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_artist_image)
                    .error(R.drawable.placeholder_artist_image)
                    .into(holder.albumImage);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.artist_image)
        ImageView albumImage;
        @BindView(R.id.artist_name)
        TextView artistName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mClickHandler.onClick(getItem(pos).getName());
        }
    }
}
