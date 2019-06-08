package work.samosudov.rtfm.manager;

import java.util.concurrent.Callable;

import timber.log.Timber;

public class CallCheckProto implements Callable<Boolean> {

    @Override
    public Boolean call() throws Exception {
        Timber.d("started");

//        ServerManager.protoApi()

        Timber.d("lastFromDb = %d");
        return true;
    }


}

