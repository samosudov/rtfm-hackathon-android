package work.samosudov.rtfm.manager;

import java.io.IOException;
import java.util.concurrent.Callable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;
import work.samosudov.rtfm.generated.OtherModels;

public class CallCheckProto implements Callable<Boolean> {

    @Override
    public Boolean call() throws Exception {
        Timber.d("started");

        Call<ResponseBody> call = ServerManager.protoApi().validList();
        try {
            Response<ResponseBody> response = call.execute();
            Timber.d("call=%s", response);
//            ResponseBody rb = response.body().bytes();
            OtherModels.ClientValidationList list = OtherModels.ClientValidationList.parseFrom(response.body().bytes());
            Timber.d("list size=%d", list.getClientsCount());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            Timber.e("call ");
        }

        Timber.d("lastFromDb = %d");
        return true;
    }


}

