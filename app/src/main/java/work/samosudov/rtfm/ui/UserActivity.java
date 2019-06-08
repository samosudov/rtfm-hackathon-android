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

package work.samosudov.rtfm.ui;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import work.samosudov.rtfm.Injection;
import work.samosudov.rtfm.R;

import static work.samosudov.rtfm.ui.DecoderActivity.QR_CODE_RESULT;


/**
 * Main screen of the app. Displays a user name and gives the option to update the user name.
 */
public class UserActivity extends AppCompatActivity {

    private static final String TAG = UserActivity.class.getSimpleName();
    private static final int QR_CODE_REQUEST_ADDR = 1;

    @BindView(R.id.start_work) Button start_work;
    @BindView(R.id.end_work) Button end_work;

    private ViewModelFactory mViewModelFactory;

    private UserViewModel mViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);
        start_work.setOnClickListener(v -> startSession());
        end_work.setOnClickListener(v -> setUser());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Subscribe to the emissions of the user name from the view model.
        // Update the user name text view, at every onNext emission.
        // In case of error, log the exception.
//        mDisposable.add(mViewModel.getUserName()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(userName -> user_name.setText(userName),
//                        throwable -> Log.e(TAG, "Unable to update username", throwable)));

    }

    private void startSession() {
        Intent intent = new Intent(this, DecoderActivity.class);
        startActivityForResult(intent, QR_CODE_REQUEST_ADDR);
    }

    private void setUser() {
        mDisposable.add(mViewModel.insert("namenew")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.d("setUser completed")));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        String result = data.getStringExtra(QR_CODE_RESULT);
        if (result.isEmpty()) return;

        Timber.d("onActivityResult res=%s", result);
        checkTransaction(result);

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
    }

    public void commitFragment(Fragment fragment) {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.frameLayout, fragment)
//                .commit();
    }

    private void checkTransaction(String result) {
//        String userName = user_name_input.getText().toString();
//        // Disable the update button until the user name update has been done
//        scan_qr.setEnabled(false);
        // Subscribe to updating the user name.
        // Re-enable the button once the user name has been updated
        mDisposable.add(mViewModel.checkTransaction(result)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((name) -> Timber.d("checkTransaction username=%s", name)));
    }
}
