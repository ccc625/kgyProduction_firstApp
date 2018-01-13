package com.example.kgy_product.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by ccc62 on 2018-01-02.
 */

public class BitmapUtil
{
    public static String getStringToBitamp(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] imageBytes = baos.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
    }

    public static Bitmap getBitmapToString(String strImage)
    {
        byte[] imageBytes = Base64.decode(strImage, Base64.NO_WRAP);

        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }
}
