package com.e.immagegallery.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.immagegallery.Model.ImageModel;
import com.e.immagegallery.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private LayoutInflater layoutInflater;
    private List<ImageModel> imageModelList;
    private OnItemClickListener itemClickListener;

    public ImageAdapter(Context context, List<ImageModel> imageModelList, OnItemClickListener itemClickListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.imageModelList = imageModelList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.items_layout, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view, itemClickListener);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ImageViewHolder holder, int position) {
        final ImageModel imgModel = imageModelList.get(position);
        holder.setImageView(imgModel.getImg());
    }

    @Override
    public int getItemCount() {
        return imageModelList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private OnItemClickListener mItemClickListener;

        public ImageViewHolder(@NonNull View itemView, OnItemClickListener itemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemImageView);

            this.mItemClickListener = itemClickListener;

            itemView.setOnClickListener(this);
        }

        public void setImageView(Bitmap bitmap) {
            this.imageView.setImageBitmap(bitmap);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.OnItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener{
        public void OnItemClick(int position);
    }
}