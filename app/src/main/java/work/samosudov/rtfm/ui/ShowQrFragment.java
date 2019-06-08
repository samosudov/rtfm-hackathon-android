package work.samosudov.rtfm.ui;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import work.samosudov.rtfm.R;

/**
 * Created by samosudovd on 11/06/2018.
 */

public class ShowQrFragment extends DialogFragment {

//    @BindView(R.id.container)
//    ConstraintLayout container;
//    @BindView(R.id.tv_title)
//    TextView tv_title;
//    @BindView(R.id.iv_show_qr)
//    ImageView iv_show_qr;
//    @BindView(R.id.tv_expl)
//    TextView tv_expl;
//    @BindView(R.id.tv_addr)
//    TextView tv_addr;
//
//    private String addr;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        Dialog dialog = getDialog();
//        if (dialog != null) {
//            int width = ViewGroup.LayoutParams.MATCH_PARENT;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            dialog.getWindow().setLayout(width, height);
//        }
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_scan_result, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        ButterKnife.bind(this, view);
//
//        if (getArguments() == null) return;
//
//        addr = getArguments().getString(SHOW_QR_ADDRESS);
//        String cur = getArguments().getString(SHOW_QR_CURRENCY);
//        String am = getArguments().getString(SHOW_QR_AMOUNT);
//        Bitmap qr = QrUtil.textToQrCode(addr, 600);
//
//        if (qr == null) return;
//
//        if (cur != null)
//            tv_title.setText(String.format(getString(R.string.show_qr_title), cur.toUpperCase()));
//        iv_show_qr.setImageBitmap(qr);
//        if (cur != null && am != null)
//            tv_expl.setText(String.format(getString(R.string.show_qr_expl), am, cur.toUpperCase()));
//        tv_addr.setText(addr);
//    }
//
//    @OnClick({R.id.container, R.id.iv_close})
//    public void click(View v) {
//        dismiss();
//    }
//
//    @OnClick({R.id.iv_show_qr, R.id.tv_addr})
//    public void copy(View v) {
//        if (getActivity() == null) return;
//        ClipboardUtil.copyToClipBoard(getActivity(), addr);
//        Toast.makeText(getActivity(), R.string.addr_copied, Toast.LENGTH_SHORT).show();
//    }

}
