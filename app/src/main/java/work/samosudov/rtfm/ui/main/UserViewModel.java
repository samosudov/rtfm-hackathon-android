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
import timber.log.Timber;
import work.samosudov.rtfm.UserDataSource;
import work.samosudov.rtfm.persistence.User;

/**
 * View Model for the {@link UserActivity}
 */
public class UserViewModel extends ViewModel {

    private final UserDataSource mDataSource;

    private User mUser;

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
                    return user.getUserName();
                });
    }

    public Completable insert(final String userName) {
        return mDataSource.insertOrUpdateUser(new User(userName));
    }

    public Flowable<Integer> checkTransaction(final String userName) {
        return mDataSource.checkTransactions(userName)
                .map(user -> {
                    Timber.d("checkTransaction %b", user);
                    return user;
                });
    }
}
