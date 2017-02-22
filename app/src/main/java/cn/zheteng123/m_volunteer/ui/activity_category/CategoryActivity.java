package cn.zheteng123.m_volunteer.ui.activity_category;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.api.Networks;
import cn.zheteng123.m_volunteer.entity.HomeActivityEntity;
import cn.zheteng123.m_volunteer.entity.PageInfo;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.ui.home.adapter.HomeActivityAdapter;
import cn.zheteng123.m_volunteer.util.WindowAttr;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CategoryActivity";

    private ListView popListView;
    private ProgressBar progressBar;
    private List<Map<String, String>> menuData1, menuData2, menuData3;
    private PopupWindow popMenu;
    private SimpleAdapter menuAdapter1, menuAdapter2, menuAdapter3;

    private LinearLayout product, sort, activity;
    private TextView districtTv, categoryTv, collationTv;

    private String currentDistrict, currentCategory, currentCollation;
    private int menuIndex = 0;

    private int mPageNow = 1, mPageSize = 10;
    private String mCategory;
    private String mDistrict;
    private int mCollation = 0;

    List<HomeActivityEntity> mHomeActivityList = new ArrayList<>();
    HomeActivityAdapter mHomeActivityAdapter;

    @BindView(R.id.lv_category_activity)
    ListView mLvActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        // 设置状态栏高度
        View mViewStatusBarFixer = findViewById(R.id.v_status_bar_fix);
        mViewStatusBarFixer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        WindowAttr.getStatusBarHeight(this))
        );

        Intent intent = getIntent();
        mCategory = intent.getStringExtra("category");

        initPopView();
        initMenuData();
        initPopMenu();

        // 设置 ListView
        mHomeActivityAdapter = new HomeActivityAdapter(this, mHomeActivityList);
        mLvActivity.setAdapter(mHomeActivityAdapter);
        getActivityList();
    }

    private void initMenuData() {
        menuData1 = new ArrayList<Map<String, String>>();
        String[] menuStr1 = new String[]{"全市", "上城区", "下城区", "西湖区", "拱墅区",
                "江干区", "滨江区", "萧山区", "市辖区", "余杭区", "桐庐县", "淳安县",
                "建德市", "富阳区", "临安市"};
        Map<String, String> map1;
        for (int i = 0, len = menuStr1.length; i < len; ++i) {
            map1 = new HashMap<String, String>();
            map1.put("name", menuStr1[i]);
            menuData1.add(map1);
        }

        menuData2 = new ArrayList<Map<String, String>>();
        String[] menuStr2 = new String[]{"全部类型", "青少年服务", "敬老助残", "扶贫帮困",
                "文明礼仪", "平安守护", "环境保护", "文化体育", "便民服务"};
        Map<String, String> map2;
        for (int i = 0, len = menuStr2.length; i < len; ++i) {
            map2 = new HashMap<String, String>();
            map2.put("name", menuStr2[i]);
            menuData2.add(map2);
        }

        menuData3 = new ArrayList<Map<String, String>>();
        String[] menuStr3 = new String[]{"最新发布", "距离最近"};
        Map<String, String> map3;
        for (int i = 0, len = menuStr3.length; i < len; ++i) {
            map3 = new HashMap<String, String>();
            map3.put("name", menuStr3[i]);
            menuData3.add(map3);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.supplier_list_product:
                districtTv.setTextColor(Color.parseColor("#39ac69"));
                popListView.setAdapter(menuAdapter1);
                popMenu.showAsDropDown(product, 0, 2);
                menuIndex = 0;
                break;
            case R.id.supplier_list_sort:
                categoryTv.setTextColor(Color.parseColor("#39ac69"));
                popListView.setAdapter(menuAdapter2);
                popMenu.showAsDropDown(product, 0, 2);
                menuIndex = 1;
                break;
            case R.id.supplier_list_activity:
                collationTv.setTextColor(Color.parseColor("#39ac69"));
                popListView.setAdapter(menuAdapter3);
                popMenu.showAsDropDown(product, 0, 2);
                menuIndex = 2;
                break;
        }
    }

    protected void initPopView() {
        product = (LinearLayout) findViewById(R.id.supplier_list_product);
        sort = (LinearLayout) findViewById(R.id.supplier_list_sort);
        activity = (LinearLayout) findViewById(R.id.supplier_list_activity);
        districtTv = (TextView) findViewById(R.id.supplier_list_product_tv);
        categoryTv = (TextView) findViewById(R.id.supplier_list_sort_tv);
        collationTv = (TextView) findViewById(R.id.supplier_list_activity_tv);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        product.setOnClickListener(this);
        sort.setOnClickListener(this);
        activity.setOnClickListener(this);
    }

    private void initPopMenu() {
        initMenuData();
        View contentView = View.inflate(this, R.layout.popwin_supplier_list,
                null);
        popMenu = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popMenu.setOutsideTouchable(true);
        popMenu.setBackgroundDrawable(new BitmapDrawable());
        popMenu.setFocusable(true);
        popMenu.setAnimationStyle(R.style.popwin_anim_style);
        popMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                districtTv.setTextColor(Color.parseColor("#5a5959"));
                categoryTv.setTextColor(Color.parseColor("#5a5959"));
                collationTv.setTextColor(Color.parseColor("#5a5959"));
            }
        });

        popListView = (ListView) contentView
                .findViewById(R.id.popwin_supplier_list_lv);
        contentView.findViewById(R.id.popwin_supplier_list_bottom)
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        popMenu.dismiss();
                    }
                });
        menuAdapter1 = new SimpleAdapter(this, menuData1,
                R.layout.item_listview_popwin, new String[]{"name"},
                new int[]{R.id.listview_popwind_tv});
        menuAdapter2 = new SimpleAdapter(this, menuData2,
                R.layout.item_listview_popwin, new String[]{"name"},
                new int[]{R.id.listview_popwind_tv});
        menuAdapter3 = new SimpleAdapter(this, menuData3,
                R.layout.item_listview_popwin, new String[]{"name"},
                new int[]{R.id.listview_popwind_tv});

        popListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                                    long arg3) {
                popMenu.dismiss();
                if (menuIndex == 0) {
                    mDistrict = currentDistrict = menuData1.get(pos).get("name");
                    districtTv.setText(currentDistrict);
                    if (currentDistrict.equals("全市")) {
                        mDistrict = null;
                    }
                    getActivityList();
                } else if (menuIndex == 1) {
                    mCategory = currentCategory = menuData2.get(pos).get("name");
                    categoryTv.setText(currentCategory);
                    if (currentCategory.equals("全部类型")) {
                        mCategory = null;
                    }
                    getActivityList();
                } else {
                    currentCollation = menuData3.get(pos).get("name");
                    collationTv.setText(currentCollation);
                    if (currentCollation.equals("最新发布")) {
                        mCollation = 0;
                    } else {
                        mCollation = 1;
                    }
                    getActivityList();
                }
            }
        });
    }

    private void getActivityList() {
        Networks
                .getInstance()
                .getActivityApi()
                .getActivityByParam(120, 30, mPageNow, mPageSize, mCategory, mCollation, mDistrict)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<PageInfo<HomeActivityEntity>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Result<PageInfo<HomeActivityEntity>> pageInfoResult) {
                        Log.d(TAG, "onNext: ");
                        mHomeActivityList.clear();
                        mHomeActivityList.addAll(pageInfoResult.getData().getList());
                        mHomeActivityAdapter.notifyDataSetChanged();
                    }
                });
    }
}
