package work.samosudov.rtfm.ui.decoder;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import work.samosudov.rtfm.R;

import static work.samosudov.rtfm.ui.decoder.DecoderActivity.CHECK_RESULT;
import static work.samosudov.rtfm.ui.decoder.DecoderActivity.SUCCESS_RESULT;
import static work.samosudov.rtfm.ui.decoder.DecoderActivity.WRONG_RESULT;

/**
 * Created by samosudovd on 11/06/2018.
 */

public class ScanResultFragment extends DialogFragment {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_show_qr)
    ImageView iv_show_qr;
    @BindView(R.id.tv_explain)
    TextView tv_explain;
    @BindView(R.id.cancel) Button cancel;
    @BindView(R.id.cash) Button cash;

    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private Drawable ds;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scan_result, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (getArguments() == null) return;

        int resInt = getArguments().getInt(CHECK_RESULT);

        if (resInt == SUCCESS_RESULT) {
            setSuccess();
        } else if (resInt == WRONG_RESULT) {
            ds = ResourcesCompat.getDrawable(getResources(),
                    R.drawable.wrong_icon, null);
            tv_title.setText("Платеж не совершен");
            //cancel
            cancel.setVisibility(View.VISIBLE);
            cancel.setOnClickListener((v) -> dismiss());
            //MONEY
            cash.setVisibility(View.VISIBLE);
            cash.setOnClickListener((v) -> setSuccess());
        }
        iv_show_qr.setImageDrawable(ds);

    }

    private void setSuccess() {
        tv_title.setText("Платеж совершен");
        tv_explain.setVisibility(View.VISIBLE);
        tv_explain.setText("Операция выполнена");
        ds = ResourcesCompat.getDrawable(getResources(),
                R.drawable.success_icon, null);
        iv_show_qr.setImageDrawable(ds);

        cancel.setVisibility(View.GONE);
        //MONEY
        cash.setVisibility(View.GONE);

        mDisposable.add(Completable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(this::dismiss));
    }

    @Override
    public void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
    }

}
