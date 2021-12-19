package com.e.immagegallery.Services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.e.immagegallery.Model.ImageModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServiceClass {

    public static String getFilePath(Context context, Bitmap bitmapImg){

        String filename = "bitmap.png";

        try{
            //Write file
            FileOutputStream stream = context.openFileOutput(filename, context.MODE_PRIVATE);
            bitmapImg.compress(Bitmap.CompressFormat.PNG, 100, stream);

            //Cleanup
            stream.close();
            bitmapImg.recycle();

        }catch (Exception e){
            e.printStackTrace();
        }

        return filename;
    }

    public static ArrayList<ImageModel> getImageList(String directory){
        ArrayList<ImageModel> imgList = new ArrayList<>();
        ImageModel imgModel = null;
        String[] fileNames = null;

        File path = new File(Environment.getExternalStorageDirectory(),directory);
        if(path.exists())
        {
            fileNames = path.list();

            for(int i = 0; i < fileNames .length; i++)
            {
                Bitmap mBitmap = BitmapFactory.decodeFile(path.getPath()+"/"+ fileNames[i]);
                //imgModel = new ImageModel(mBitmap);
                imgList.add(new ImageModel(mBitmap));
            }
        }

        return imgList;
    }

    public static void imageSaveToPhoneGallery(ImageView imageView, String pathName, int rotationAngle) {

        BitmapDrawable draw = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = draw.getBitmap();

        Matrix rotateMatrix = new Matrix();
        rotateMatrix.postRotate(rotationAngle);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), rotateMatrix, false);

        FileOutputStream outStream = null;
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() +"/"+ pathName);
        dir.mkdirs();
        String fileName = String.format("%d.jpg", System.currentTimeMillis());
        File outFile = new File(dir, fileName);
        try {
            outStream = new FileOutputStream(outFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        try {
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static void shareImageOnWhatsApp(String mediaName, Uri imageUri, Context context){
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        //whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage(mediaName);
        //whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        whatsappIntent.setType("image/jpeg");
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            context.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

}
