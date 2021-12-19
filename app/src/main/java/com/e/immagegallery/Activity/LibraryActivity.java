package com.e.immagegallery.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.e.immagegallery.Adapter.ImageAdapter;
import com.e.immagegallery.Model.ImageModel;
import com.e.immagegallery.R;
import com.e.immagegallery.Services.ServiceClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener {

    private ArrayList<ImageModel> imageModels;
    private ImageModel imgModel;

    private RecyclerView imageRecyclerView;
    private LinearLayoutManager layoutManager;
    private ImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        imageRecyclerView = findViewById(R.id.imageRecyclerView);

        imageModels = ServiceClass.getImageList("/SaveImages");

        if(imageModels != null){
            layoutManager = new LinearLayoutManager(this);
            imageRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new ImageAdapter(this, imageModels, this);
            imageRecyclerView.setAdapter(mAdapter);
        }

    }

    @Override
    public void OnItemClick(int position) {
        Toast.makeText(this,imageModels.get(position).toString(),Toast.LENGTH_SHORT).show();
    }
}