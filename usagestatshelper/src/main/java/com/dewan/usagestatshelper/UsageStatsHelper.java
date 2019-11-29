package com.dewan.usagestatshelper;


import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.format.DateUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class UsageStatsHelper {
    private static final String TAG = "UsageStatsHelper";
    private static PackageManager packageManager;

    public static boolean getAppUsageStatsPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> appList;
            assert usm != null;
            appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
            return appList.size() > 0;
        }
        return false;
    }


    public static SortedMap getForegroundAppDaily(Context context) {
        SortedMap<Long, UsageStats> mySortedMap = new TreeMap<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            assert usm != null;
            List<UsageStats> applist = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
            if (applist != null && applist.size() > 0) {

                for (UsageStats usageStats : applist) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                /*if (!mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                    Log.e(TAG, "getForegroundApp: " + String.valueOf(mySortedMap.get(mySortedMap.lastKey()).getLastTimeUsed()));
                }*/
            }
        } else {
            return mySortedMap;
        }

        return mySortedMap;

    }

    public static SortedMap getForegroundAppWeekly(Context context) {
        SortedMap<Long, UsageStats> mySortedMap = new TreeMap<>();
        long time = System.currentTimeMillis();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

            assert usm != null;
            List<UsageStats> applist = usm.queryUsageStats(UsageStatsManager.INTERVAL_WEEKLY, DateUtils.WEEK_IN_MILLIS, time);
            if (applist != null && applist.size() > 0) {

                for (UsageStats usageStats : applist) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                /*if (!mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                    Log.e(TAG, "getForegroundApp: " + String.valueOf(mySortedMap.get(mySortedMap.lastKey()).getLastTimeUsed()));
                }*/
            }
        } else {
            return mySortedMap;
        }

        return mySortedMap;

    }

    public static SortedMap getForegroundAppMonthly(Context context) {
        SortedMap<Long, UsageStats> mySortedMap = new TreeMap<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            assert usm != null;
            List<UsageStats> applist = usm.queryUsageStats(UsageStatsManager.INTERVAL_MONTHLY, DateUtils.MINUTE_IN_MILLIS, time);
            if (applist != null && applist.size() > 0) {

                for (UsageStats usageStats : applist) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                /*if (!mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                    Log.e(TAG, "getForegroundApp: " + String.valueOf(mySortedMap.get(mySortedMap.lastKey()).getLastTimeUsed()));
                }*/
            }
        } else {
            return mySortedMap;
        }

        return mySortedMap;

    }

    public static SortedMap getForegroundAppYearly(Context context) {
        SortedMap<Long, UsageStats> mySortedMap = new TreeMap<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            assert usm != null;
            List<UsageStats> applist = usm.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY, DateUtils.YEAR_IN_MILLIS, time);
            if (applist != null && applist.size() > 0) {

                for (UsageStats usageStats : applist) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                /*if (!mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                    Log.e(TAG, "getForegroundApp: " + String.valueOf(mySortedMap.get(mySortedMap.lastKey()).getLastTimeUsed()));
                }*/
            }
        } else {
            return mySortedMap;
        }

        return mySortedMap;

    }

    public static ArrayList<AppUsageStatsProperty> getAppUsageStatsList(SortedMap sortedMap) {
        ArrayList<AppUsageStatsProperty> list = new ArrayList<>();
        if (!sortedMap.isEmpty()) {
            Set set = sortedMap.entrySet();
            for (Object o : set) {
                try {
                    Map.Entry entry = (Map.Entry) o;
                    long key = (long) entry.getKey();
                    UsageStats value = (UsageStats) entry.getValue();
                    AppUsageStatsProperty appUsageStats = new AppUsageStatsProperty();
                    appUsageStats.setPackageName(value.getPackageName());
                    appUsageStats.setTotalTimeInForeground(value.getTotalTimeInForeground());
                    appUsageStats.setAppName(getAppName(value.getPackageName()));
                    appUsageStats.setAppIcon(getAppIconByName(value.getPackageName()));

                    list.add(appUsageStats);
                } catch (Exception er) {
                    Log.e(TAG, "getAppUsageStatsList:" + er.getLocalizedMessage());
                }

            }
        }
        return list;

    }

    private static String getAppName(String packageName) {
        String appName = "";
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName,PackageManager.GET_META_DATA);
            appName = (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException er) {
            Log.e(TAG, er.getMessage());
        }
        return appName;
    }

    private static Drawable getAppIconByName(String packageName) {
        Drawable appIcon = null;
        try {
            appIcon = packageManager.getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException er) {
            Log.e(TAG, er.getMessage());
        }
        return appIcon;
    }

    private static PackageManager getPackageManager() {
        return packageManager;
    }

    /*
    set the package manager before you access any static method
     */
    public static void setPackageManager(PackageManager packageManager) {
        UsageStatsHelper.packageManager = packageManager;
    }
}


