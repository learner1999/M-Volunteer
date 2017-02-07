package cn.zheteng123.m_volunteer.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created on 2017/2/7.
 */


public class BaseActivity extends AppCompatActivity implements BaseFuncIml, View.OnClickListener {

    private static final String TAG = "BaseActivity";

    private int mFragmentId;

    protected Fragment mCurrFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initData();
        initView();
        initListener();
        initLoad();
    }

    @Override
    public void onClick(View v) {
        
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initLoad() {

    }

    public void setFragmentId(int fragmentId) {
        mFragmentId = fragmentId;
    }

    public Fragment getCurrFragment() {
        return mCurrFragment;
    }

    public void setCurrFragment(Fragment currFragment) {
        mCurrFragment = currFragment;
    }

    protected void toFragment(Fragment toFragment) {
        if (mCurrFragment == null) {
            Toast.makeText(this, "mCurrFragment is null", Toast.LENGTH_SHORT).show();
            return;
        }

        if (toFragment == null) {
            Toast.makeText(this, "toFragment is null", Toast.LENGTH_SHORT).show();
            return;
        }

        if (toFragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(mCurrFragment)
                    .show(toFragment)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(mCurrFragment)
                    .add(mFragmentId, toFragment)
                    .show(toFragment)
                    .commit();
        }
    }

    protected void openActivity(Class<? extends BaseActivity> toActivity) {
        openActivity(toActivity, null);
    }

    protected void openActivity(Class<? extends BaseActivity> toActivity, Bundle parameters) {
        Intent intent = new Intent(this, toActivity);
        if (parameters != null) {
            intent.putExtras(parameters);
        }
        startActivity(intent);
    }

    protected void setToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
