package com.bjmasc.arcore.dashboard;

import android.content.Context;
import android.view.View;

import com.bjmasc.arcore.AppNavigator;

public class DashboardViewModel {

    private Context mContext;

    DashboardViewModel(Context context) {
        mContext = context;
    }

    public void helloArClick(View view) {
        AppNavigator.navigateToHelloArActivity(mContext);
    }

    public void augmentedImagesClick(View view) {
        AppNavigator.navigateToAugmentedImagesActivity(mContext);
    }
}
