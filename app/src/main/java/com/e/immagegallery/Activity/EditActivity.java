package com.e.immagegallery.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.e.immagegallery.R;
import com.theartofdev.edmodo.cropper.CropImage;

public class EditActivity extends AppCompatActivity {

    private Button btnRotate, btnCrop, btnBlur, btnFilter, btnLibrary;
    private Button btnPlus,btnMinus,btnDone;
    private ImageView imageView;
    private LinearLayout linearLayout;

    private Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        imageView = findViewById(R.id.imageViewEdit);
        btnRotate = findViewById(R.id.btnRotate);
        btnCrop   = findViewById(R.id.btnCrop);
        btnFilter = findViewById(R.id.btnFilter);
        btnBlur   = findViewById(R.id.btnBlur);
        btnLibrary = findViewById(R.id.btnLable);

        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnDone = findViewById(R.id.btnDone);
        linearLayout = findViewById(R.id.layoutRotate);

        imgUri = (Uri) getIntent().getParcelableExtra("imgUri");
        imageView.setImageURI(imgUri);

        btnRotate.setOnClickListener(view -> {
            linearLayout.setVisibility(View.VISIBLE);
        });

        btnCrop.setOnClickListener(view -> {
            CropImage.activity(imgUri).start(EditActivity.this);
        });

        btnPlus.setOnClickListener(view -> imageView.setRotation(imageView.getRotation()+10));
        btnMinus.setOnClickListener(view -> imageView.setRotation(imageView.getRotation()-10));
        btnDone.setOnClickListener(view -> linearLayout.setVisibility(View.GONE));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                imgUri = result.getUri();
                imageView.setImageURI(imgUri);
            }
        }
    }
}