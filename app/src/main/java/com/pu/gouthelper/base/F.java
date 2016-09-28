package com.pu.gouthelper.base;


import android.app.Activity;
import android.util.Log;

import com.orhanobut.logger.Logger;

/**
 * Created by requiem on 16/03/05.
 */
public class F {
    public static int PAGE_SIZE = 30;


    public static void SCREEN_SIZE(Activity c) {
        int screenWidth = c.getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = c.getWindowManager().getDefaultDisplay().getHeight();
        Logger.e("SCREEN_SIZE", screenWidth + "*" + screenHeight);
    }


}
