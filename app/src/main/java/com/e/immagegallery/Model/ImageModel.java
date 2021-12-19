package com.e.immagegallery.Model;

import android.graphics.Bitmap;

public class ImageModel {

    Bitmap img;

    public ImageModel(Bitmap img) {
        this.img = img;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
