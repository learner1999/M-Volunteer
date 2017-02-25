package cn.zheteng123.m_volunteer.ui.certificate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.zheteng123.m_volunteer.R;

public class CertificateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CertificateActivity.class);
        context.startActivity(intent);
    }
}
