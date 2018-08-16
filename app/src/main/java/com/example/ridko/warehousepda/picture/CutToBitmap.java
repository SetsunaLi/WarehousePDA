package com.example.ridko.warehousepda.picture;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by mumu on 2018/8/11.
 */

public class CutToBitmap {

    public static Bitmap decodeResourceBySampleRate(Resources res, int resId,
                                                    int imageViewWidth, int imageViewHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        int sampleRate = calSampleRate(options, imageViewWidth, imageViewHeight);
        options.inSampleSize = sampleRate;
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeResource(res, resId, options);
        return bm;
    }

    public static int calSampleRate(BitmapFactory.Options options,
                                    int imageViewWidth, int imageViewHeight) {
        int sampleRate = 1;

        if (imageViewWidth == 0 || imageViewHeight == 0) {
            return sampleRate;
        }
        int imageRawWidth = options.outWidth;
        int imageRawHeight = options.outHeight;
        if (imageRawWidth > imageViewWidth || imageRawHeight > imageViewHeight) {
            sampleRate = 2;
            int halfImageRawWidth = imageRawWidth / sampleRate;
            int halfImageRawHeight = imageRawHeight / sampleRate;
            while ((halfImageRawWidth / sampleRate) >= imageViewWidth
                    || (halfImageRawHeight / sampleRate) >= imageViewHeight) {
                sampleRate = sampleRate * 2;
            }
        }
        return sampleRate;

    }


}