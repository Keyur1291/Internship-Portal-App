package com.android.internshipportal;

import android.app.Application;
import com.google.android.material.color.DynamicColors;

public class dynamicColors extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
