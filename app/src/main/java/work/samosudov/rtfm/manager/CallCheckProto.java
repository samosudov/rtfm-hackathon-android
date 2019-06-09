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
import work.samosudov.rtfm.UserDataSource;
import work.samosudov.rtfm.generated.OtherModels;
import work.samosudov.rtfm.persistence.AppDatabase;
import work.samosudov.rtfm.persistence.main.User;

public class CallCheckProto implements Callable<Boolean> {

    private UserDataSource userDataSource;

    public CallCheckProto(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    @Override
    public Boolean call() throws Exception {
        Timber.d("started");

//        Call<ResponseBody> call = ServerManager.protoApi().validList();
//        try {
//            Response<ResponseBody> response = call.execute();
//            Timber.d("call=%s", response);
//            OtherModels.ClientValidationList list = OtherModels.ClientValidationList.parseFrom(response.body().bytes());
//            Map<Long, Boolean> validMap = list.getClientsMap();
            Map<Long, Boolean> validMap = new HashMap<>();
            validMap.put(123456L, true);
            validMap.put(54321L, false);
            for (Map.Entry<Long, Boolean> entry : validMap.entrySet()) {
                if (entry.getValue()) {
                    Observable.just(userDataSource.insertOrUpdateUser(new User(entry.getKey())))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe((v) -> {
                                Timber.d("Observable.just completed");
                            });
                }
//                    userDataSource.insertOrUpdateUser(new User(entry.getKey()));
            }

//            Timber.d("list size=%d", list.getClientsCount());
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//            Timber.e("call e%s", ioe.getMessage());
//        }

        Timber.d("lastFromDb = %d");
        return true;
    }


}

