package itg8.com.stockmanagement.common;

import android.app.ActivityManager;
import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by swapnilmeshram on 15/03/18.
 */
//@ReportsCrashes(formUri = "", mailTo = "steponesolutions.01@gmail.com", mode = ReportingInteractionMode.SILENT)
public class MyApplication extends Application {


    private static final String PREF_NAME = "StockManagementApp";
    private static final String DB_NAME = "StockManagementApp";

    private static MyApplication mInstance;

    public boolean isLoggingNeeded;
    //TODO version: change when new api changes arrives
    public static final int VERSION = 1;



    public static MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
//        ACRA.init(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        DebugOverlay.with(this).install();
        isLoggingNeeded =true;
        mInstance = this;
        mInstance.initPref();
        mInstance.initNetworkCall();
        mInstance.getCustomPicasso();
        timberLogging();


    }

    private void timberLogging() {
        Timber.plant(new StockManagementLogPlanTree());

    }


    private void initNetworkCall() {
        new NetworkCall.NetworkBuilder().build();
    }


    private void initPicassoCache() {
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }



    private void initPref() {
        new Prefs.Builder()
                .setContext(this)
                .setMode(MODE_PRIVATE)
                .setPrefsName(PREF_NAME)
                .setUseDefaultSharedPreference(false)
                .build();
    }




    public boolean isOnline() {
        boolean isConnectivity;
        if (AppConnectivityStatus.getInstance(this).isOnline())
            isConnectivity = true;
        else
            isConnectivity = false;


        return isConnectivity;
    }

    public void getCustomPicasso(){
        Picasso.Builder builder = new Picasso.Builder(this);
        //set 12% of available app memory for image cache
        builder.memoryCache(new LruCache(getBytesForMemCache(75)));
        //set request transformer
        Picasso.get().setLoggingEnabled(true);

        Picasso.RequestTransformer requestTransformer =  new Picasso.RequestTransformer() {
            @Override
            public com.squareup.picasso.Request transformRequest(com.squareup.picasso.Request request) {
                return request;
            }
        };

        builder.requestTransformer(requestTransformer);

        builder.build();
    }


    //returns the given percentage of available memory in bytes
    private int getBytesForMemCache(int percent){
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)
                getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);

        double availableMemory= mi.availMem;

        return (int)(percent*availableMemory/100);
    }



}
