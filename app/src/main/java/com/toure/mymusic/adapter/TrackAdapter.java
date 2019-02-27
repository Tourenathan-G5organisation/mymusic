package com.toure.mymusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toure.mymusic.PlayTrackActivity;
import com.toure.mymusic.R;
import com.toure.mymusic.data.Track;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {

    static final String TAG = TrackAdapter.class.getSimpleName();
    private Context mcContext;
    private List<Track> mData;

    public TrackAdapter(Context mcContext) {
        this.mcContext = mcContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.track_item_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.trackTextView.setText(String.format(Locale.getDefault(), "%d. %s",
                position + 1, mData.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public void setData(List<Track> tracks) {
        mData = tracks;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.track_textview)
        TextView trackTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Intent intent = new Intent(mcContext, PlayTrackActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, mData.get(pos).getUrl());
            mcContext.startActivity(intent);
        }
    }
}
