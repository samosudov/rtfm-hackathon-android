package work.samosudov.rtfm.manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;
import work.samosudov.rtfm.generated.DbModels;
import work.samosudov.rtfm.generated.OtherModels;

public class CallPostTx implements Callable<Boolean> {

    private Long clientid;
    private final Long transportId = 7L;

    public CallPostTx(Long clientid) {
        this.clientid = clientid;
    }

    @Override
    public Boolean call() throws Exception {
        Timber.d("started");

        OtherModels.Payment transaction = OtherModels.Payment
                .newBuilder()
                .setClientID(clientid)
                .setTransactionID(System.currentTimeMillis())
                .setTransportID(transportId).build();

        Call<ResponseBody> call = ServerManager.protoApi().postTx(transaction);
        try {
            Response<ResponseBody> response = call.execute();
            Timber.d("call=%s", response);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            Timber.e("call e%s", ioe.getMessage());
        }

        return true;
    }

}

