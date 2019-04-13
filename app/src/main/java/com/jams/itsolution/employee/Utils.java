/*
 * Copyright 2014 A.C.R. Development
 */
package com.jams.itsolution.employee;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public  class Utils {

    public static int dpToPx(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) (dp * metrics.density + 0.5f);
    }

    @Nullable
    public static String getFileSize(@Nullable String uri) {
        if (uri == null || uri.isEmpty()) return "";
        String size=null;
        URL url;
        try {
            url = new URL(uri);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            size = String.valueOf(urlConnection.getContentLength());

        }catch (Exception e){
            e.printStackTrace();
        }

        return size;
    }


    @Nullable
    public static String getFileSizetoHttpURLConnection(@Nullable String uri) {
        if (uri == null || uri.isEmpty()) return "";
        String size=null;
        HttpURLConnection connection = null;
        try {

            URL url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            size = String.valueOf(connection.getContentLength());

        }catch (Exception e){
            e.printStackTrace();
        }

        return size;
    }





}
