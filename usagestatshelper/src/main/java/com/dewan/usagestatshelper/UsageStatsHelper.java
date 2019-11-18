package com.dewan.usagestatshelper;

import android.app.AlertDialog;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class UsageStatsHelper {

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


    public static SortedMap getForegroundApp(Context context) {
        SortedMap<Long, UsageStats> mySortedMap = new TreeMap<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            assert usm != null;
            List<UsageStats>  applist = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
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
}
