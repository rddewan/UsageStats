package com.dewan.usagestatshelper;

import android.graphics.drawable.Drawable;

import java.util.Comparator;

public class AppUsageStatsProperty {
    private String appName;
    private String packageName;
    private long totalTimeInForeground;
    private Drawable appIcon;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getTotalTimeInForeground() {
        return totalTimeInForeground;
    }

    public void setTotalTimeInForeground(long totalTimeInForeground) {
        this.totalTimeInForeground = totalTimeInForeground;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public static Comparator<AppUsageStatsProperty> totalUsageTimeComparator = new Comparator<AppUsageStatsProperty>() {
        @Override
        public int compare(AppUsageStatsProperty t0, AppUsageStatsProperty t1) {
            return Double.compare(t1.getTotalTimeInForeground(),t0.getTotalTimeInForeground());
        }
    };
}
