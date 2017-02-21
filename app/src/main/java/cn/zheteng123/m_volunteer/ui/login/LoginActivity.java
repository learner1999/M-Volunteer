package cn.zheteng123.m_volunteer.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.api.Networks;
import cn.zheteng123.m_volunteer.entity.Token;
import cn.zheteng123.m_volunteer.ui.main.MainActivity;
import cn.zheteng123.m_volunteer.util.LoginInfo;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @BindView(R.id.et_username)
    EditText mEtUsername;

    @BindView(R.id.et_password)
    EditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_login);


        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn_login)
    public void login() {
        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();

        if (username.equals("") || password.equals("")) {
            Toast.makeText(this, "请输入用户名和密码！", Toast.LENGTH_SHORT).show();
            return;
        }

        Networks
                .getInstance()
                .getLoginApi()
                .login("password", username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Token>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(LoginActivity.this, "登录失败！" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Token token) {
                        LoginInfo.token = token.getAccessToken();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }
}
