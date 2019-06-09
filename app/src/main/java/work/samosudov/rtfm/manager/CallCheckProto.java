package work.samosudov.rtfm.manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;
import work.samosudov.rtfm.generated.OtherModels;
import work.samosudov.rtfm.persistence.main.LocalUserDataSource;
import work.samosudov.rtfm.persistence.main.User;

public class CallCheckProto implements Callable<Map<Long, Boolean>> {

    @Override
    public Map<Long, Boolean> call() throws Exception {
        Timber.d("started");

        Map<Long, Boolean> validMap = new HashMap<>();

        Call<ResponseBody> call = ServerManager.protoApi().validList();
        try {
            Response<ResponseBody> response = call.execute();
            Timber.d("call=%s", response);
            OtherModels.ClientValidationList list = OtherModels.ClientValidationList.parseFrom(response.body().bytes());
            validMap = list.getClientsMap();

//            Map<Long, Boolean> validMap = new HashMap<>();
//            validMap.put(123456L, true);
//            validMap.put(54321L, false);

            Timber.d("list size=%d", list.getClientsCount());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            Timber.e("call e%s", ioe.getMessage());
        }

        Timber.d("lastFromDb = %d");
        return validMap;
    }


}

