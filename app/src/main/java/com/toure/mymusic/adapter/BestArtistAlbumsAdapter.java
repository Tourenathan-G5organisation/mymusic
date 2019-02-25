package com.toure.mymusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.toure.mymusic.R;
import com.toure.mymusic.data.Album;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BestArtistAlbumsAdapter extends RecyclerView.Adapter<BestArtistAlbumsAdapter.ViewHolder> {

    static final String TAG = BestArtistAlbumsAdapter.class.getSimpleName();
    private Context mContext;
    private List<Album> mData;

    public BestArtistAlbumsAdapter(Context context) {
        this.mContext = context;
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
        holder.albumName.setText(mData.get(position).getName());
        Glide
                .with(mContext)
                .load(mData.get(position).getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.placeholder_artist_image)
                .error(R.drawable.placeholder_artist_image)
                .into(holder.albumImage);
    }

    @Override
    public int getItemCount() {
        return (mData != null) ? mData.size() : 0;
    }

    public void setData(List<Album> albums) {
        mData = albums;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.album_image)
        ImageView albumImage;
        @BindView(R.id.album_name)
        TextView albumName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            //TODO: Implement the click event which displays the details view
        }
    }
}
