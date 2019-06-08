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

package work.samosudov.rtfm.ui.decoder;


import androidx.lifecycle.ViewModel;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import work.samosudov.rtfm.manager.CallCheckProto;
import work.samosudov.rtfm.persistence.txs.LocalTxsDataSource;
import work.samosudov.rtfm.persistence.txs.Tx;
import work.samosudov.rtfm.ui.main.UserActivity;

/**
 * View Model for the {@link UserActivity}
 */
public class DecoderViewModel extends ViewModel {

    private final LocalTxsDataSource mDataSource;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public DecoderViewModel(LocalTxsDataSource dataSource) {
        mDataSource = dataSource;
    }

    /**
     * Get the user name of the user.
     *
     * @return a {@link Flowable} that will emit every time the user name has been updated.
     */
    public Flowable<String> getTxId() {
        return mDataSource.getTx()
                // for every emission of the user, get the user name
                .map(tx -> tx.getId());
    }

    public Completable insert(final String id, final Double value, final Long time) {
        return mDataSource.insertOrUpdateUser(new Tx(id, value, time));
    }

    public Flowable<Integer> checkTransaction(final String userName) {
        return mDataSource.checkTransactions(userName)
                .map(count -> {
                    Timber.d("checkTransaction %b", count);
                    return count;
                });
    }

    public void transaction(Tx tx) {
//        mDisposable.add(checkTransaction(result)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe((name) -> {
//                    Timber.d("checkTransaction username=%s", name);
//                    if (name != 0) {
//                        showSuccessPushSave();
//                    } else {
//                        showWrongPushSave();
//                    }
//                }));
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.clear();
    }
}
