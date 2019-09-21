package com.bjmasc.sharedcode.utils;

import android.content.Context;
import android.graphics.Typeface;

import com.bjmasc.sharedcode.widgets.FontCache;

public class UIUtils {

    public static Typeface getFont(Context context, int mTextStyle) {
        switch (mTextStyle) {
            case 0:
                return FontCache.getRobotoRegularFont(context);
            case 1:
                return FontCache.getRobotoMediumFont(context);
            case 2:
                return FontCache.getRobotoBoldFont(context);
            default:
                return FontCache.getRobotoRegularFont(context);
        }
    }
}
