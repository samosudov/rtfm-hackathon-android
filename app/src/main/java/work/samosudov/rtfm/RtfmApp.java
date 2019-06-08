package work.samosudov.rtfm;


import androidx.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;

import timber.log.Timber;

/**
 * Created by samosudovd on 21/06/2018.
 */

public class RtfmApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this);

        Timber.plant(new Timber.DebugTree());
    }

}
