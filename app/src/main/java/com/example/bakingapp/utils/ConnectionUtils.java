package com.example.bakingapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;


public class ConnectionUtils {
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
