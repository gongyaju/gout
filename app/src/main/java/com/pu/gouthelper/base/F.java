package com.pu.gouthelper.base;


import android.app.Activity;
import android.util.Log;

/**
 * Created by requiem on 16/03/05.
 */
public class F {
    public static int PAGE_SIZE = 100;
    public static boolean isDebug = true;

    public static void I(String TAG, Object obj) {
        if (isDebug) {
            Log.i(TAG, "" + obj);
        }
    }

    public static void E(String TAG, Object obj) {
        if (isDebug) {
            Log.e(TAG, "" + obj);
        }
    }

    public static void D(String TAG, Object obj) {
        if (isDebug) {
            Log.d(TAG, "" + obj);
        }
    }

    public static void SCREEN_SIZE(Activity c) {
        int screenWidth = c.getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = c.getWindowManager().getDefaultDisplay().getHeight();
        F.E("SCREEN_SIZE", screenWidth + "*" + screenHeight);
    }


}
