/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package work.samosudov.rtfm.ui.main;


import androidx.lifecycle.ViewModel;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import work.samosudov.rtfm.UserDataSource;
import work.samosudov.rtfm.manager.CallCheckProto;
import work.samosudov.rtfm.persistence.AppDatabase;
import work.samosudov.rtfm.persistence.main.User;

/**
 * View Model for the {@link UserActivity}
 */
public class UserViewModel extends ViewModel {

    private final UserDataSource mDataSource;

    private User mUser;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public UserViewModel(UserDataSource dataSource) {
        mDataSource = dataSource;
    }

    /**
     * Get the user name of the user.
     *
     * @return a {@link Flowable} that will emit every time the user name has been updated.
     */
    public Flowable<String> getUserName() {
        return mDataSource.getUser()
                // for every emission of the user, get the user name
                .map(user -> {
                    mUser = user;
                    return user.getClientId().toString();
                });
    }

    public Completable insert(final Long userName) {
        return mDataSource.insertOrUpdateUser(new User(userName));
    }

    public Flowable<Integer> checkTransaction(final Long userName) {
        return mDataSource.checkTransactions(userName)
                .map(user -> {
                    Timber.d("checkTransaction %b", user);
                    return user;
                });
    }

    public void checkProto() {
        mDisposable.add(Observable
                .fromCallable(new CallCheckProto(mDataSource))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        (b) -> Timber.d("checkProto true"),
                        e-> Timber.e("err =%s", e.getMessage())
                ));
//        mDisposable.add(ServerManager
//                .protoApi()
//                .estimatedObs()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        (resp) -> Timber.d("checkProto resp=%s", resp.toString()),
//                        (err) -> Timber.d("checkProto err=%s", err.getMessage())
//                )
//        );


//        ServerManager.protoApi().validList().enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//                Timber.d("validList %s", response);
//                if (!response.isSuccessful()) return;
////                OtherModels.ClientValidationList validList;
//                try {
//                    byte[] bytes = response.body().bytes();
////                    validList = OtherModels.ClientValidationList.parseFrom(bytes);
////                    Timber.d("getClientsCount=%d", validList.getClientsCount());
//                    Timber.d("getClientsCount=%d", Arrays.toString(bytes));
//                } catch (IOException ioe) {
//                    Timber.e("validList ioe=%s", ioe.getMessage());
//                    ioe.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Timber.d("validList t=%s", t.getMessage());
//            }
//        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.clear();
    }
}
