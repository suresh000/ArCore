package com.bjmasc.sharedcode.widgets;

import android.content.Context;
import android.graphics.Typeface;

public class FontCache {

    private static Typeface robotoRegular, robotoBold, robotoMedium;

    public static Typeface getRobotoRegularFont(Context context) {
        if (robotoRegular == null) {
            robotoRegular = Typeface.createFromAsset(context.getResources()
                    .getAssets(), "fonts/Roboto-Regular.ttf");
        }
        return robotoRegular;
    }

    public static Typeface getRobotoMediumFont(Context context) {
        if (robotoMedium == null) {
            robotoMedium = Typeface.createFromAsset(context.getResources()
                    .getAssets(), "fonts/Roboto-Medium.ttf");
        }
        return robotoMedium;
    }

    public static Typeface getRobotoBoldFont(Context context) {
        if (robotoBold == null) {
            robotoBold = Typeface.createFromAsset(context.getResources()
                    .getAssets(), "fonts/Roboto-Bold.ttf");
        }
        return robotoBold;
    }

}
