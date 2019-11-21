1. add maven { url 'https://jitpack.io' } to top level gradle build
<br/>
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }

    }
}
<br/>
2. add dependencies  TAG = check for the current version
implementation 'com.github.rddewan:UsageStats:TAG'

3. set the package manager
UsageStatsHelper.setPackageManager(context.getPackageManager());

<br/>
4. get the sorted map
<br/>
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
