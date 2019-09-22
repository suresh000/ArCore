package com.bjmasc.arcore;

import android.content.Context;
import android.content.Intent;

import com.bjmasc.arcore.augmented.AugmentedImagesActivity;
import com.bjmasc.arcore.cloud.CloudAnchorsActivity;
import com.bjmasc.arcore.hello.HelloArActivity;

public final class AppNavigator {

    private AppNavigator() {}

    public static void navigateToHelloArActivity(Context context) {
        Intent intent = new Intent(context, HelloArActivity.class);
        context.startActivity(intent);
    }

    public static void navigateToAugmentedImagesActivity(Context context) {
        Intent intent = new Intent(context, AugmentedImagesActivity.class);
        context.startActivity(intent);
    }

    public static void navigateToCloudAnchorsActivity(Context context) {
        Intent intent = new Intent(context, CloudAnchorsActivity.class);
        context.startActivity(intent);
    }
}
