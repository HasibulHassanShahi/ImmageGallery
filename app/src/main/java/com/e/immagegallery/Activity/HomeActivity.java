package com.e.immagegallery.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.e.immagegallery.R;
import com.e.immagegallery.Services.ServiceClass;

import java.io.FileInputStream;

public class HomeActivity extends AppCompatActivity {

    private ImageView imageView;
    private Bitmap bitmap;
    private Button btnShare, btnEdit,btnLib;
    private Uri imageUri = null;

    private Dialog myDialog;
    private Bitmap img = null;
    private String filePathName = null;
    private Uri imgUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageView = findViewById(R.id.imageView);
        btnShare = findViewById(R.id.btnShare);
        btnEdit = findViewById(R.id.btnEdit);
        btnLib = findViewById(R.id.btnLib);

        myDialog = new Dialog(this);

        imageUri = null;
        imageUri = (Uri) getIntent().getParcelableExtra("img");

        if(imageUri != null){
            //recieveBitmap(filePathName);
            //imageView = null;
            imageView.setImageURI(imageUri);
        }
        else {
            uploadImage();
        }

        btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this,EditActivity.class);
            intent.putExtra("imgUri",imageUri);
            startActivity(intent);
        });

        btnLib.setOnClickListener(view -> uploadImage());
    }

    public void ShowPopup(View v) {
        Button btnWhatsApp,btnFacebook,btnCancel;
        myDialog.setContentView(R.layout.layout_custompopup);

        btnWhatsApp = (Button) myDialog.findViewById(R.id.btnWhatsapp);
        btnFacebook = (Button) myDialog.findViewById(R.id.btnFacebook);
        btnCancel   = (Button) myDialog.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(view -> myDialog.dismiss());

        btnWhatsApp.setOnClickListener(view -> ServiceClass.shareImageOnWhatsApp("com.whatsapp", imageUri, this));
        btnFacebook.setOnClickListener(view ->
                Toast.makeText(this,"Facebook is not implemented yet", Toast.LENGTH_SHORT).show());

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    // new sharing data function
    public void shareImage(View v){
        ServiceClass.shareImage(HomeActivity.this, imageUri);
    }

    private void uploadImage(){
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Sellect Photo"), 100);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 100 && resultCode == RESULT_OK){
            imageUri = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                imageView.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}