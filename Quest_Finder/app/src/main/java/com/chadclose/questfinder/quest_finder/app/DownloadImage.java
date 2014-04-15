package com.chadclose.questfinder.quest_finder.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Chad on 4/15/2014.
 */
public class DownloadImage extends AsyncTask<ImageView, Void, Bitmap> {

    // reference to our imageview
    private ImageView mImage;

    public DownloadImage() {

    }

    protected Bitmap doInBackground(ImageView... images) {
        mImage = images[0];
        String url = (String)mImage.getTag();
        return loadImageFromNetwork(url);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        // Set the image view before the content is shown.
        mImage.setImageBitmap(result);


    }

    private Bitmap loadImageFromNetwork(String url) {
        Bitmap bm = null;
        try {
            URL urln = new URL(url);
            bm = BitmapFactory.decodeStream(urln.openConnection().getInputStream());
            //Scale the new bitmap
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleFactor = ((float)mImage.getHeight())/((float)height);
            int newWidth = Math.round(width * scaleFactor);
            int newHeight = Math.round(height* scaleFactor);
            if(newWidth > 0 && newHeight > 0)
                bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, false);
            else
                return null;

        } catch (IOException e) {
            Log.e("HUE", "Error downloading the image from server : " + e.getMessage().toString());
        }
        return bm;
    }
}
