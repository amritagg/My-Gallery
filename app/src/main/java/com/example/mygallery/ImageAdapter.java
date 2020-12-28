package com.example.mygallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter{

    private final ArrayList<String> allImages;
    private final Context context;

    public ImageAdapter(Context context, ArrayList<String> allImages) {
        this.allImages = allImages;
        this.context = context;
    }

    @Override
    public int getCount() {
        return allImages.size();
    }

    @Override
    public Object getItem(int position) {
        return getItemId(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.card, null);
        }

        ImageView imageView = convertView.findViewById(R.id.image);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Uri contentUris = Uri.parse(allImages.get(position));

        Glide.with(context).load(contentUris).into(imageView);

        return convertView;
    }

}
