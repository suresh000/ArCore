package com.bjmasc.sharedcode.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public final class AppUtils {

    private AppUtils() {
        throw new IllegalStateException(getClass().getName());
    }

    public static void showToast(Context context, String message, int length) {
        if (Toast.LENGTH_LONG == length) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        } else if (Toast.LENGTH_SHORT == length) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void hideKeyboardFragment(Context context, View view) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
