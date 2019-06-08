package work.samosudov.rtfm;


import androidx.multidex.MultiDexApplication;

import timber.log.Timber;

/**
 * Created by samosudovd on 21/06/2018.
 */

public class RtfmApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }

}
