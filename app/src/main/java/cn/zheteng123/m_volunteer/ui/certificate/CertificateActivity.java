package cn.zheteng123.m_volunteer.ui.certificate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.api.Networks;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.certificate.CertificateEntity;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CertificateActivity extends AppCompatActivity {

    private static final String TAG = "CertificateActivity";

    @BindView(R.id.et_address)
    EditText mEtAddress;

    @BindView(R.id.et_receiver)
    EditText mEtReceiver;

    @BindView(R.id.et_receiver_phone)
    EditText mEtReceiverPhone;

    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        initListener();
    }

    private void initListener() {
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CertificateEntity certificateEntity = new CertificateEntity();
                certificateEntity.setAddress(mEtAddress.getText().toString());
                certificateEntity.setReceiver(mEtReceiver.getText().toString());
                certificateEntity.setReceiverPhone(mEtReceiverPhone.getText().toString());
                certificateEntity.setTimestamp("2017-02-24T16:00:06.314Z");
                certificateEntity.setCertificateStatusId(1);
                Networks
                        .getInstance()
                        .getCertificateApi()
                        .addCertificate(certificateEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Result<String>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "onError: " + e.getMessage());
                                Toast.makeText(CertificateActivity.this, "您已经申请过了，请不要重新申请！", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(Result<String> stringResult) {
                                Toast.makeText(CertificateActivity.this, "申请成功！请耐心等待寄送！", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
            }
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CertificateActivity.class);
        context.startActivity(intent);
    }
}
