package cn.zheteng123.m_volunteer.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.util.WindowAttr;

/**
 * Created on 2017/2/12.
 */


public class HomeFragment extends Fragment {

    @BindView(R.id.roll_pager_view)
    RollPagerView mRollPagerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        // 设置状态栏高度
        View mViewStatusBarFixer = view.findViewById(R.id.v_status_bar_fix);
        mViewStatusBarFixer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        WindowAttr.getStatusBarHeight(getActivity()))
        );

        // 设置轮播图
        mRollPagerView.setAdapter(new StaticPagerAdapter() {
            private int[] imgs = {
                    R.drawable.banner1,
                    R.drawable.banner2,
            };

            @Override
            public View getView(ViewGroup container, int position) {
                ImageView view = new ImageView(container.getContext());
                view.setImageResource(imgs[position]);
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                ));
                return view;
            }

            @Override
            public int getCount() {
                return imgs.length;
            }
        });

        return view;
    }
}
