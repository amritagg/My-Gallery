package com.example.mygallery;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class ViewPageImageAdapter extends RecyclerView.Adapter<ViewPageImageAdapter.ViewHolder> {

    private final Context mContext;
    private final ArrayList<String> imageUris;

    public ViewPageImageAdapter(Context mContext, ArrayList<String> imageUris) {
        this.mContext = mContext;
        this.imageUris = imageUris;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.show_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri uri = Uri.parse(imageUris.get(position));
        Glide.with(mContext).load(uri).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageUris.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }

}
