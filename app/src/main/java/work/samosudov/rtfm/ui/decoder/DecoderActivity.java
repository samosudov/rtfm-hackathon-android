package work.samosudov.rtfm.ui.decoder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView.OnQRCodeReadListener;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import work.samosudov.rtfm.Injection;
import work.samosudov.rtfm.R;
import work.samosudov.rtfm.ui.main.UserViewModel;
import work.samosudov.rtfm.ui.ViewModelFactory;

/**
 * Created by samosudovd on 11/07/2018.
 */

public class DecoderActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, OnQRCodeReadListener {

    QRCodeReaderView qrdecoderview;
    ImageView iv_close_qr;

    @BindView(R.id.main_layout)
    ViewGroup main_layout;

    private ViewModelFactory mViewModelFactory;
    private UserViewModel mViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    private static final int MY_PERMISSION_REQUEST_CAMERA = 0;
    public static final int SUCCESS_RESULT = 0;
    public static final int WRONG_RESULT = 1;
    public static final String QR_CODE_RESULT = "QR_CODE_RESULT";
    public static final String CHECK_RESULT = "CHECK_RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);
        ButterKnife.bind(this);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            initQRCodeReaderView();
        } else {
            requestCameraPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSION_REQUEST_CAMERA) {
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(main_layout, "Camera permission was granted.", Snackbar.LENGTH_SHORT).show();
            initQRCodeReaderView();
        } else {
            Snackbar.make(main_layout, "Camera permission request was denied.", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed in View
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra(QR_CODE_RESULT, text);
//        setResult(Activity.RESULT_OK, returnIntent);
//        finish();

        checkTransaction(text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (qrdecoderview != null) {
            qrdecoderview.startCamera();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (qrdecoderview != null) {
            qrdecoderview.stopCamera();
        }
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
                .subscribe((name) -> {
                    Timber.d("checkTransaction username=%s", name);
                    if (name != 0) {
                        showSuccess();
                    } else {
                        showWrong();
                    }
                }));
    }

    private void showSuccess() {
        showFragment(SUCCESS_RESULT);
    }

    private void showWrong() {
        showFragment(WRONG_RESULT);
    }

    private void showFragment(int result) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle b = new Bundle();
        b.putInt(CHECK_RESULT, result);
        ScanResultFragment sqf = new ScanResultFragment();
        sqf.setArguments(b);
        sqf.show(fragmentManager, "ScanResultFragment");
    }

    private void initQRCodeReaderView() {
        View content = getLayoutInflater().inflate(R.layout.content_decoder, main_layout, true);

        iv_close_qr = content.findViewById(R.id.iv_close_qr);
        iv_close_qr.setOnClickListener((v) -> finish());

        qrdecoderview = content.findViewById(R.id.qrdecoderview);
        qrdecoderview.setAutofocusInterval(2000L);
        qrdecoderview.setOnQRCodeReadListener(this);
        qrdecoderview.setFrontCamera();
        qrdecoderview.startCamera();
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Snackbar.make(main_layout, "Camera access is required to display the camera preview.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", (view) -> {
                    ActivityCompat.requestPermissions(DecoderActivity.this, new String[] {
                            Manifest.permission.CAMERA
                    }, MY_PERMISSION_REQUEST_CAMERA);
            }).show();
        } else {
            Snackbar.make(main_layout, "Permission is not available. Requesting camera permission.",
                    Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.CAMERA
            }, MY_PERMISSION_REQUEST_CAMERA);
        }
    }
}
