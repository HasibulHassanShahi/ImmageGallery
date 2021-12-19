package com.e.immagegallery.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.e.immagegallery.R;
import com.e.immagegallery.Services.ServiceClass;
import com.e.immagegallery.Services.ServiceFilter;
import com.squareup.picasso.Picasso;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;

import java.io.IOException;

import jp.wasabeef.picasso.transformations.gpu.SepiaFilterTransformation;

public class FilterActivity extends AppCompatActivity {

    private Button btnFilter1,btnFilter2,btnFilter3,btnSave;
    private Button btnPlus,btnMinus,btnDone, btnClose;
    private ImageView imageView;
    private Uri uri = null;
    private Bitmap bitmap,originBitmap;
    private LinearLayout linearLayout;
    private int contrast = 5;
    private int brightness = 0;
    private SeekBar seekBarContrast, seekBarBright;

    private int progressChangedValueBrightness = 0;
    private int progressChangedValueContrast = 1;

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);

        btnFilter1 = findViewById(R.id.btnFilter1);
        btnFilter2 = findViewById(R.id.btnFilter2);
        btnFilter3 = findViewById(R.id.btnFilter3);
        btnSave    = findViewById(R.id.btnSave);
        imageView  = findViewById(R.id.filterImageView);

        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnDone = findViewById(R.id.btnDone);
        btnClose = findViewById(R.id.btnClose);
        linearLayout = findViewById(R.id.layoutControl);
        seekBarContrast = findViewById(R.id.seekBarContrast);
        seekBarBright   = findViewById(R.id.seekBarBright);

        uri = getIntent().getParcelableExtra("img");
        imageView.setImageURI(uri);

        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            originBitmap = bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnFilter1.setOnClickListener(view -> {
            try{
                bitmap = ServiceFilter.toGrayscale(bitmap);
                imageView.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        btnFilter2.setOnClickListener(view -> linearLayout.setVisibility(View.VISIBLE));

//        btnPlus.setOnClickListener(view -> {
//            brightness = brightness + 20;
//            contrast = contrast + 2;
//
//            imageView.setImageBitmap(enhanceImage(bitmap,contrast,brightness));
//        });
//
//        btnMinus.setOnClickListener(view -> {
//            brightness = brightness - 20;
//            contrast = contrast - 1;
//
//            imageView.setImageBitmap(enhanceImage(bitmap,contrast,brightness));
//        });

        seekBarBright.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressChangedValueBrightness = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //bitmap = ServiceFilter.enhanceImage(bitmap,progressChangedValueContrast,progressChangedValueBrightness);
                imageView.setImageBitmap(ServiceFilter.enhanceImage(bitmap,progressChangedValueContrast,progressChangedValueBrightness));
            }
        });

        seekBarContrast.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressChangedValueContrast = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //bitmap = ServiceFilter.enhanceImage(bitmap,progressChangedValueContrast,progressChangedValueBrightness);
                imageView.setImageBitmap(ServiceFilter.enhanceImage(bitmap,progressChangedValueContrast,progressChangedValueBrightness));
            }
        });

        btnClose.setOnClickListener(view -> {
            bitmap = originBitmap;
            linearLayout.setVisibility(View.GONE);
        });

        btnDone.setOnClickListener(view -> {
            originBitmap = bitmap;
            linearLayout.setVisibility(View.GONE);
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceClass.imageSaveToPhoneGallery(imageView,"SaveImages",0);
            }
        });

    }
}