package com.yichang.kaku.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.member.login.LoginActivity;
import com.yichang.kaku.tools.Utils;

public class BottomTabFragment extends Fragment implements OnClickListener {

    /**
     * tab点击事件回调
     */
    private OnTabClickCallback onTabClickCallback;

    /**
     * 上下文
     */
    private Activity context;

    //todo 恢复发现页面

    private ImageView iv_tab_home1, iv_tab_home2, iv_tab_home4, iv_tab_home5;

    /**
     * 选中文字颜色
     */
    private int colorTextSelect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if (context instanceof OnTabClickCallback)
            onTabClickCallback = (OnTabClickCallback) getActivity();
        colorTextSelect = context.getResources().getColor(R.color.bottom_text_selected);
    }

    public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container,
                             android.os.Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.frgmt_bottom_tab, container, false);
        contentView.findViewById(R.id.btn_tab_home1).setOnClickListener(this);
        contentView.findViewById(R.id.btn_tab_home2).setOnClickListener(this);
        contentView.findViewById(R.id.btn_tab_home4).setOnClickListener(this);
        contentView.findViewById(R.id.btn_tab_home5).setOnClickListener(this);

        iv_tab_home1 = (ImageView) contentView.findViewById(R.id.iv_tab_home1);
        iv_tab_home2 = (ImageView) contentView.findViewById(R.id.iv_tab_home2);
        iv_tab_home4 = (ImageView) contentView.findViewById(R.id.iv_tab_home4);
        iv_tab_home5 = (ImageView) contentView.findViewById(R.id.iv_tab_home5);
        if (Constants.current_tab == Constants.TAB_POSITION_UNKONWN) {
            Constants.current_tab = Constants.TAB_POSITION_HOME1;
        }
        return contentView;
    }

    @Override
    public void onClick(View v) {
        if (!Utils.isLogin()) {
            context.startActivity(new Intent(context, LoginActivity.class));
            return;
        }

        int id = v.getId();
        clearTabState();
        if (R.id.btn_tab_home1 == id) {
            if (onTabClickCallback != null)
                onTabClickCallback.onHomeTabClick1();
            iv_tab_home1.setImageResource(R.drawable.tabbar_home12);
            Constants.current_tab = Constants.TAB_POSITION_HOME1;
        } else if (R.id.btn_tab_home2 == id) {
            if (onTabClickCallback != null)
                onTabClickCallback.onHomeTabClick2();
            KaKuApplication.id_service = "";
            KaKuApplication.type_service = "0";
            KaKuApplication.name_service = "做保养";
            iv_tab_home2.setImageResource(R.drawable.tabbar_home22);
            Constants.current_tab = Constants.TAB_POSITION_HOME2;
        } else if (R.id.btn_tab_home4 == id) {
            if (onTabClickCallback != null) {
                onTabClickCallback.onHomeTabClick4();
            }
            KaKuApplication.quanzitype = "left";
            iv_tab_home4.setImageResource(R.drawable.tabbar_home42);
            Constants.current_tab = Constants.TAB_POSITION_HOME4;
        } else if (R.id.btn_tab_home5 == id) {
            if (onTabClickCallback != null)
                onTabClickCallback.onHomeTabClick5();
            iv_tab_home5.setImageResource(R.drawable.tabbar_home52);
            Constants.current_tab = Constants.TAB_POSITION_HOME5;
        }
    }

    private void clearTabState() {
        iv_tab_home1.setImageResource(R.drawable.tabbar_home11);
        iv_tab_home2.setImageResource(R.drawable.tabbar_home21);
        iv_tab_home4.setImageResource(R.drawable.tabbar_home41);
        iv_tab_home5.setImageResource(R.drawable.tabbar_home51);
    }

    public void setTab(int index) {
        clearTabState();
        switch (index) {
            case Constants.TAB_POSITION_HOME1:
                iv_tab_home1.setImageResource(R.drawable.tabbar_home12);
                break;
            case Constants.TAB_POSITION_HOME2:
                iv_tab_home2.setImageResource(R.drawable.tabbar_home22);
                break;
            case Constants.TAB_POSITION_HOME4:
                iv_tab_home4.setImageResource(R.drawable.tabbar_home42);
                break;
            case Constants.TAB_POSITION_HOME5:
                iv_tab_home5.setImageResource(R.drawable.tabbar_home52);
                break;
        }
    }

    public OnTabClickCallback getOnTabClickCallback() {
        return onTabClickCallback;
    }

    public void setOnTabClickCallback(OnTabClickCallback onTabClickCallback) {
        this.onTabClickCallback = onTabClickCallback;
    }

    public static interface OnTabClickCallback {
        /**
         * 需要返回要设置的HomeTab的文字
         *
         * @return
         */
        public void onHomeTabClick1();

        public void onHomeTabClick2();

        public void onHomeTabClick4();

        public void onHomeTabClick5();
    }
}
