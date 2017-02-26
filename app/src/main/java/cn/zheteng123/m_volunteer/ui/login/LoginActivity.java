package cn.zheteng123.m_volunteer.ui.login;

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

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.api.Networks;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.Token;
import cn.zheteng123.m_volunteer.entity.login.Role;
import cn.zheteng123.m_volunteer.ui.main.MainActivity;
import cn.zheteng123.m_volunteer.util.LoginInfo;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

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
        final String username = mEtUsername.getText().toString();
        final String password = mEtPassword.getText().toString();

        if (username.equals("") || password.equals("")) {
            Toast.makeText(this, "请输入用户名和密码！", Toast.LENGTH_SHORT).show();
            return;
        }


        Networks.getInstance().getLoginApi()
                .login("password", username, password)
                .flatMap(new Func1<Token, Observable<Result<List<Role>>>>() {
                    @Override
                    public Observable<Result<List<Role>>> call(Token token) {
                        LoginInfo.token = token.getAccessToken();
                        return Networks.getInstance().getLoginApi().getUserRoles();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<List<Role>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Result<List<Role>> roleResult) {
                        LoginInfo.sRole = roleResult.getData().get(0);

                        LoginInfo.sYWIMKit = YWAPI.getIMKitInstance(username, "23653358");

                        final IYWLoginService loginService = LoginInfo.sYWIMKit.getLoginService();

                        YWLoginParam loginParam = YWLoginParam.createLoginParam(username, password);

                        loginService.login(loginParam, new IWxCallback() {

                            @Override
                            public void onSuccess(Object... arg0) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }

                            @Override
                            public void onProgress(int arg0) {
                                // TODO Auto-generated method stub
                            }

                            @Override
                            public void onError(int errCode, String description) {
                                //如果登录失败，errCode为错误码,description是错误的具体描述信息
                            }
                        });
                    }
                });


    }
}
