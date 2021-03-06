#UsageStats Android Library to get app usage stats

[![](https://jitpack.io/v/rddewan/UsageStats.svg)](https://jitpack.io/#rddewan/UsageStats)

1. add maven { url 'https://jitpack.io' } to top level gradle build

```
allprojects {

    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }

    }
}
```

2. add dependencies  TAG = check for the current version
```
implementation 'com.github.rddewan:UsageStats:TAG'
```

3. set the package manager. These setter must be set before accessing any other method
```
UsageStatsHelper.setPackageManager(context.getPackageManager());
```

4. set permission
```
<uses-permission
        android:name="android.permission.PACKAGE_USAGE_STATS"
        tools:ignore="ProtectedPermissions" />
```

5. request runtime permission
```
private void checkUsageStatsPermission() {
        boolean isPermissionGranted = UsageStatsHelper.getAppUsageStatsPermission(getActivity());
        if (!isPermissionGranted) {
            askForUsageStatsPermission();
        } else {
            getUsageStats();
        }
    }

private void askForUsageStatsPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setTitle("Permission");
        builder.setMessage("Please Provide Usage Stats App Permission");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                startActivityForResult(intent, USAGE_STATS_PERMISSION);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                checkUsageStatsPermission();
            }
        });
        builder.show();
    }
```

6. get the sorted map
```
SortedMap<Long, UsageStats> mySortedMap = UsageStatsHelper.getForegroundApp(context);

//check for empty sorted map

if (!mySortedMap.isEmpty()) {
    ArrayList<AppUsageStatsProperty> appStatsLists = UsageStatsHelper.getAppUsageStatsList(mySortedMap);
    
    //sort array list
    Collections.sort(appStatsLists,AppUsageStatsProperty.totalUsageTimeComparator);
    
    AppUsageStatsProperty property = appStatsLists.get(0);
    Log.e(TAG, "MAX: " + property.getTotalTimeInForeground());

    for (int i = 0; i < appStatsLists.size(); i++){
        AppUsageEntity appUsageEntity = new AppUsageEntity();
        AppUsageStatsProperty appUsageStatsProperty = new AppUsageStatsProperty();
        appUsageStatsProperty = appStatsLists.get(i);

        appUsageEntity.setApp_name(appUsageStatsProperty.getAppName());
        appUsageEntity.setPackage_name(appUsageStatsProperty.getPackageName());
        appUsageEntity.setApp_icon(appUsageStatsProperty.getAppIcon());
        double totalTime = appUsageStatsProperty.getTotalTimeInForeground();
        double totalSec = totalTime / 1000;
        double totalMin = totalTime / 1000 / 60;
        double totalHr = totalTime / 1000 / 60 / 60;

        appUsageEntity.setTotalAppInForegroundSec(Double.parseDouble(decimalFormat.format(totalSec)));
        appUsageEntity.setTotalAppInForegroundMin(Double.parseDouble(decimalFormat.format(totalMin)));
        appUsageEntity.setTotalAppInForegroundHr(Double.parseDouble(decimalFormat.format(totalHr)));
        appList.add(appUsageEntity);
    }


}
```
