package com.toure.mymusic.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Utility {

    private static String API_KEY = "34e6a2e60763140ea2aa9c9b88765cc2";

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Return the api key use to query last fm api
     *
     * @return API_KEY
     */
    public static String getApiKey() {
        return API_KEY;
    }

}
