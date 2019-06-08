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

package work.samosudov.rtfm;

import android.content.Context;

import work.samosudov.rtfm.persistence.AppDatabase;
import work.samosudov.rtfm.persistence.main.LocalUserDataSource;
import work.samosudov.rtfm.persistence.txs.LocalTxsDataSource;
import work.samosudov.rtfm.ui.decoder.DecoderViewModelFactory;
import work.samosudov.rtfm.ui.main.UserViewModelFactory;

/**
 * Enables injection of data sources.
 */
public class Injection {

    public static UserDataSource provideUserDataSource(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        return new LocalUserDataSource(database.userDao());
    }

    public static UserViewModelFactory provideViewModelFactory(Context context) {
        UserDataSource dataSource = provideUserDataSource(context);
        return new UserViewModelFactory(dataSource);
    }

    public static LocalTxsDataSource provideTxsDataSource(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        return new LocalTxsDataSource(database.txDao());
    }

    public static DecoderViewModelFactory provideTxsViewModelFactory(Context context) {
        LocalTxsDataSource dataSource = provideTxsDataSource(context);
        return new DecoderViewModelFactory(dataSource);
    }
}
