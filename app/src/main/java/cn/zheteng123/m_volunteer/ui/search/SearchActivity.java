package cn.zheteng123.m_volunteer.ui.search;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.ui.searchresult.SearchResultActivity;
import cn.zheteng123.m_volunteer.util.WindowAttr;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.v_status_bar_fix)
    View mViewStatusBarFixer;

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @BindView(R.id.btn_search)
    Button mBtnSearch;

    @BindView(R.id.et_search)
    EditText mEtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        // 设置状态栏高度
        mViewStatusBarFixer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        WindowAttr.getStatusBarHeight(this))
        );

        // 设置返回键
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.finish();
            }
        });

        // 设置搜索键监听事件
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = mEtSearch.getText().toString();
                if (query.equals("")) {
                    Toast.makeText(SearchActivity.this, "请输入关键词", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);
            }
        });
    }
}
