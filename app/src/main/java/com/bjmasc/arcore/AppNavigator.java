package com.bjmasc.arcore;

import android.content.Context;
import android.content.Intent;

import com.bjmasc.arcore.augmented.AugmentedImagesActivity;
import com.bjmasc.arcore.cloud.CloudAnchorsActivity;
import com.bjmasc.arcore.hello.HelloArActivity;
import com.bjmasc.arcore.load.LoadShapesDuringRuntimeActivity;
import com.bjmasc.arcore.play.PlayVideoActivity;
import com.bjmasc.arcore.runtime.ShapesDuringRuntimeActivity;

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

    public static void navigateToShapesDuringRuntimeActivity(Context context) {
        Intent intent = new Intent(context, ShapesDuringRuntimeActivity.class);
        context.startActivity(intent);
    }

    public static void navigateToLoadShapesDuringRuntimeActivity(Context context) {
        Intent intent = new Intent(context, LoadShapesDuringRuntimeActivity.class);
        context.startActivity(intent);
    }

    public static void navigateToPlayVideoActivity(Context context) {
        Intent intent = new Intent(context, PlayVideoActivity.class);
        context.startActivity(intent);
    }
}
