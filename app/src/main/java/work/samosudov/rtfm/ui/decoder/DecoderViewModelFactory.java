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
import androidx.lifecycle.ViewModelProvider;

import work.samosudov.rtfm.UserDataSource;
import work.samosudov.rtfm.persistence.main.LocalUserDataSource;
import work.samosudov.rtfm.persistence.txs.LocalTxsDataSource;
import work.samosudov.rtfm.ui.main.UserViewModel;

/**
 * Factory for ViewModels
 */
public class DecoderViewModelFactory implements ViewModelProvider.Factory {

    private final LocalUserDataSource mDataSource;

    public DecoderViewModelFactory(LocalUserDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DecoderViewModel.class)) {
            return (T) new DecoderViewModel(mDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
