package com.e.immagegallery.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.e.immagegallery.R;
import com.e.immagegallery.Services.ServiceClass;
import com.theartofdev.edmodo.cropper.CropImage;

public class EditActivity extends AppCompatActivity {

    private Button btnRotate, btnCrop, btnBlur, btnFilter, btnLibrary;
    private Button btnPlus,btnMinus,btnDone, btnSave, btnBack;
    private ImageView imageView;
    private LinearLayout linearLayout;

    private Uri imgUri;
    private int angle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

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

        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        imgUri = (Uri) getIntent().getParcelableExtra("imgUri");
        imageView.setImageURI(imgUri);

        btnRotate.setOnClickListener(view -> {
            linearLayout.setVisibility(View.VISIBLE);
        });

        btnCrop.setOnClickListener(view -> {
            CropImage.activity(imgUri).start(EditActivity.this);
        });

        btnPlus.setOnClickListener(view -> {
            angle = angle+10;
            imageView.setRotation(imageView.getRotation()+angle);
        });
        btnMinus.setOnClickListener(view -> {
            angle = angle-10;
            imageView.setRotation(imageView.getRotation()-angle);
        });
        btnDone.setOnClickListener(view -> linearLayout.setVisibility(View.GONE));

        btnSave.setOnClickListener(view -> {
            try{
                ServiceClass.imageSaveToPhoneGallery(imageView,"SaveImages",angle);
                Toast.makeText(this,"Image Save Successfully",Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(this,"Image Can't be Saved!!",Toast.LENGTH_SHORT).show();
            }
        });

        btnLibrary.setOnClickListener(view -> {
            Intent intent = new Intent(EditActivity.this, LibraryActivity.class);
            startActivity(intent);
        });

        btnFilter.setOnClickListener(view -> {
            Intent intent = new Intent(EditActivity.this, FilterActivity.class);
            intent.putExtra("img",imgUri);
            startActivity(intent);
        });
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