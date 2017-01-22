package com.shareads.user.myapplication;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.shareads.user.myapplication.preferences.BookPreferences;
import com.facebook.FacebookSdk;

/**
 * Created by ericbasendra on 16/09/16.
 */
public class AppController extends Application{

    private static AppController mInstance;
    private AppPrefs appPrefs;
    BookPreferences preferences;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        appPrefs = AppPrefs.getComplexPreferences(getBaseContext(), "mobikyte", MODE_PRIVATE);
        preferences = new BookPreferences(this);
        FacebookSdk.sdkInitialize(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public boolean isOnline(Context context) {
        boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }

    public AppPrefs getMobiKytePrefs() {
        if(appPrefs != null) {
            return appPrefs;
        }
        return null;
    }

    public BookPreferences getBookPrefs() {
        if(preferences != null) {
            return preferences;
        }
        return null;
    }


}
