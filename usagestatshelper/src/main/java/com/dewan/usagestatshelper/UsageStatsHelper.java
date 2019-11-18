package com.dewan.usagestatshelper;


import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class UsageStatsHelper {
    private static final String TAG = "UsageStatsHelper";

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

    public static ArrayList<AppUsageStats> getAppUsageStatsList(SortedMap sortedMap) {
        ArrayList<AppUsageStats> list = new ArrayList<>();
        if (!sortedMap.isEmpty()) {
            Set set = sortedMap.entrySet();
            for (Object o : set) {
                try {
                    Map.Entry entry = (Map.Entry) o;
                    long key = (long) entry.getKey();
                    UsageStats value = (UsageStats) entry.getValue();
                    AppUsageStats appUsageStats = new AppUsageStats();
                    appUsageStats.setPackageName(value.getPackageName());
                    appUsageStats.setTotalTimeInForeground(value.getTotalTimeInForeground());
                    list.add(appUsageStats);
                } catch (Exception er) {
                    Log.e(TAG, "getAppUsageStatsList:" + er.getLocalizedMessage());
                }

            }
        }
        return list;

    }
}
