// https://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
package com.foodie.foodographer;
//import necessary libraries
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
/**
 * Download an external image by its URL and then fill it into an ImageView.
 * Usage: new DownloadImageTask('ImageView's viewID').execute('image URL')
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    
    //fetch the targeted ImageView from the user as a constructor parameter
    public DownloadImageTask(ImageView bmImage) {

        this.bmImage = bmImage;
    }
    
    //download the image(Async) from its URL
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }
    
    //if the download task finished successfully, set the image to the imageView
    protected void onPostExecute(Bitmap result) {

        bmImage.setImageBitmap(result);
    }
}
