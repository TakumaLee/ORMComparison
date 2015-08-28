package com.takumalee.ormcomparison.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class AppConfig {

	private static String TAG = AppConfig.class.getSimpleName();

    protected static SharedPreferences sharedPrefs = null;
    protected static boolean isInDebugMode = false;
    protected static String Revision = null;
    protected static String appVersion = "";
    protected static String appName = "";
    protected static int appVersionCode = 0;
    
    public static String IP_URL = "http://ip-api.com/json";

    public AppConfig() {
    	
    }

    public static void initConfig(Context context, String app_name) {
        if (sharedPrefs == null) {
            sharedPrefs = context.getSharedPreferences("ConfigChocolabsApp", Context.MODE_PRIVATE);
        }
        try {
            PackageInfo infos = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            appVersion = infos.versionName;
            appVersionCode = infos.versionCode;
            ApplicationInfo appinfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), 0);
            if ((appinfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
                isInDebugMode = true;
            }

            appName = app_name;
        } catch (NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
    }
    
//    public static void setCountryCity(Context context) {
//		try {
//			JSONObject object = new JSONObject(new HttpGetOOMAsyncTask(context).execute(AppConfig.IP_URL).get(3000, TimeUnit.MILLISECONDS));
//			UserInfoManager.getUserInfo().setCountry(object.getString("country"));
//			UserInfoManager.getUserInfo().setCity(object.getString("city"));
//		} catch (Exception e) {
////			UserInfoManager.getUserInfo().setCity(Locale.getDefault().toString());
////			UserInfoManager.getUserInfo().setCountry(Locale.getDefault().getCountry());
//			e.printStackTrace();
//		}
//	}

    private static void checkInitConfig() {
        if (sharedPrefs == null) {
            throw new RuntimeException(
                    "Configuration have to be initialize with AppConfig.initConfig(Context context)");
        }
    }

    public static Menu createConfigMenu(Menu menu) {
        checkInitConfig();
        if (isInDebugMode) {
//            menu.add(1, INTEGRATION, 1, "integration");
//            menu.add(1, PREPROD, 2, "preprod");
//            menu.add(1, PROD, 3, "prod");
//            menu.add(1, INFOS, 4, "infos");
        }
        return menu;
    }

    public static void configMenuItemSelected(MenuItem item, Context context) {
//        switch (item.getItemId()) {
//        case INTEGRATION:
//            setIntegration();
//            break;
//        case PREPROD:
//            setPreProduction();
//            break;
//        case PROD:
//            setProduction();
//            break;
//        default:
//            showInfoToast(context);
//            break;
//        }
    }

    private static void setPreProduction() {
        Editor editPrefs = sharedPrefs.edit();
        editPrefs.commit();
        System.exit(0);
    }

    private static void setIntegration() {
        Editor editPrefs = sharedPrefs.edit();
        editPrefs.commit();
        System.exit(0);
    }

    private static void showInfoToast(Context context) {
        StringBuilder config = new StringBuilder(5);

        config.append("\nAPP VERSION : ");
        config.append(appVersion);
        config.append("\nSVN REVISION : ");
        config.append(Revision);
        Toast.makeText(context, "This is current configuration : \n\n" + config, Toast.LENGTH_LONG)
                .show();
    }

    public static boolean isInDebugMode() {
        return isInDebugMode;
    }
    
    public static int getAppVersionCode() {
    	return appVersionCode;
    }

    public static String getAppVersion() {
        return appVersion;
    }

    public static String getAppName() {
        return appName;
    }
    
}

