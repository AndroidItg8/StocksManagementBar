package itg8.com.stockmanagement.common;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import timber.log.Timber;


public class StockManagementLogPlanTree extends Timber.Tree {
    private int logLevel=4;

    public StockManagementLogPlanTree() {
        logLevel= Log.INFO;
    }

    @Override
    protected void log(int priority, @Nullable String tag, @NotNull String message, @Nullable Throwable t) {
        if(priority>=logLevel){
            Timber.d(message);
        }


    }
}