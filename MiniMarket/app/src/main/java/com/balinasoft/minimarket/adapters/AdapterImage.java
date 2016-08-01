package com.balinasoft.minimarket.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.balinasoft.minimarket.R;

import java.util.ArrayList;

/**
 * Created by Microsoft on 26.07.2016.
 */
public class AdapterImage extends RecyclerView.Adapter<AdapterImage.ImageViewHolder>{
    public ArrayList<Uri> getUris() {
        return uris;
    }

    ArrayList<Uri> uris;
    Context context;

    public AdapterImage(Context context, ArrayList<Uri> uris) {
        this.context = context;
        this.uris = uris;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(getView(R.layout.image,parent));
    }

    private View getView(@LayoutRes int id, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(id,parent,false);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        final Uri uri=uris.get(position);

        holder.imageView.setImageURI(uri);
        holder.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uris.remove(uri);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    public void addUri(Uri uri) {
        this.uris.add(uri);
        notifyDataSetChanged();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView,ivClose;
        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.image_ivImage);
            ivClose=(ImageView)itemView.findViewById(R.id.image_ivClose);
        }
    }
}
