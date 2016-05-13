package com.yichang.kaku.member;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BadgeView;
import com.yichang.kaku.global.BaseFragment;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.ad.CheTieOrderListActivity;
import com.yichang.kaku.home.baoyang.BaoYangOrderListActivity;
import com.yichang.kaku.home.choujiang.MyPrizeActivity;
import com.yichang.kaku.home.mycar.MyCarActivity;
import com.yichang.kaku.member.cash.YueActivity;
import com.yichang.kaku.member.driver.DriverInfoActivity;
import com.yichang.kaku.member.driver.MsgActivity;
import com.yichang.kaku.member.driver.MyPointInfoActivity;
import com.yichang.kaku.member.settings.MemberSettingsActivity;
import com.yichang.kaku.member.truckorder.TruckOrderListActivity;
import com.yichang.kaku.money.TuiJianJiangLiActivity;
import com.yichang.kaku.request.MemberDriverReq;
import com.yichang.kaku.response.MemberDriverResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.CircularImage;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class Home5Fragment extends BaseFragment implements OnClickListener {

    private Activity mActivity;
    //红色圆点，提示消息条数
    private BadgeView badge_service_pay;
    private BadgeView badge_service_mount;
    private BadgeView badge_service_confirm;
    private BadgeView badge_service_comment;
    private BadgeView badge_service_repair;
    private BadgeView badge_truck_pay;
    private BadgeView badge_truck_receipt;
    private BadgeView badge_truck_exchange;
    private BadgeView badge_truck_comment;

    //消息入口
    private ImageView iv_member_msg;
    //服务订单入口
    private ImageView iv_member_waiting_pay, iv_member_waiting_repair, iv_member_waiting_mount, iv_member_waiting_comment, iv_member_waiting_confirm;
    //车品订单入口
    private ImageView iv_member_car_pay, iv_member_car_receipt, iv_member_car_exchange, iv_member_car_comment;
    //头像，点击进入个人信息编辑页
    private CircularImage iv_member_icon;
    private TextView tv_member_drivername;
    private TextView tv_member_showorders, tv_member_showcarorders;
    private LinearLayout ll_member_waiting_pay, ll_member_waiting_repair, ll_member_waiting_comment, ll_member_waiting_confirm, ll_member_waiting_mount;
    private LinearLayout ll_member_car_comment, ll_member_car_exchange, ll_member_car_pay, ll_member_car_receipt;

    private ImageView iv_member_1, iv_member_2, iv_member_3, iv_member_4, iv_member_5, iv_member_6, iv_member_7, iv_member_8, iv_member_9;
    private ImageView iv_member_vip, iv_member_xunzhang1, iv_member_xunzhang2;
    private TextView tv_member_3, tv_member_4, tv_member_6, tv_member_setting;
    private String tell;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member, container, false);
        init(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDriverInfo();
    }

    private void init(View view) {
        initOrderList(view);
        initBadgeItems();
        initPageItems(view);
        initDriverInfo(view);

    }

    private void initBadgeItems() {
        badge_service_pay = new BadgeView(getActivity(), iv_member_waiting_pay);
        badge_service_mount = new BadgeView(getActivity(), iv_member_waiting_mount);
        badge_service_confirm = new BadgeView(getActivity(), iv_member_waiting_confirm);
        badge_service_comment = new BadgeView(getActivity(), iv_member_waiting_comment);
        badge_service_repair = new BadgeView(getActivity(), iv_member_waiting_repair);
        badge_truck_pay = new BadgeView(getActivity(), iv_member_car_pay);
        badge_truck_receipt = new BadgeView(getActivity(), iv_member_car_receipt);
        badge_truck_exchange = new BadgeView(getActivity(), iv_member_car_exchange);
        badge_truck_comment = new BadgeView(getActivity(), iv_member_car_comment);
    }

    private void initPageItems(View view) {

        iv_member_1 = (ImageView) view.findViewById(R.id.iv_member_1);
        iv_member_1.setOnClickListener(this);
        iv_member_2 = (ImageView) view.findViewById(R.id.iv_member_2);
        iv_member_2.setOnClickListener(this);
        iv_member_3 = (ImageView) view.findViewById(R.id.iv_member_3);
        iv_member_3.setOnClickListener(this);
        iv_member_4 = (ImageView) view.findViewById(R.id.iv_member_4);
        iv_member_4.setOnClickListener(this);
        iv_member_5 = (ImageView) view.findViewById(R.id.iv_member_5);
        iv_member_5.setOnClickListener(this);
        iv_member_6 = (ImageView) view.findViewById(R.id.iv_member_6);
        iv_member_6.setOnClickListener(this);
        iv_member_7 = (ImageView) view.findViewById(R.id.iv_member_7);
        iv_member_7.setOnClickListener(this);
        iv_member_8 = (ImageView) view.findViewById(R.id.iv_member_8);
        iv_member_8.setOnClickListener(this);
        iv_member_9 = (ImageView) view.findViewById(R.id.iv_member_9);
        iv_member_9.setOnClickListener(this);
        tv_member_setting = (TextView) view.findViewById(R.id.tv_member_setting);
        tv_member_setting.setOnClickListener(this);
        tv_member_3 = (TextView) view.findViewById(R.id.tv_member_3);
        tv_member_4 = (TextView) view.findViewById(R.id.tv_member_4);
        tv_member_6 = (TextView) view.findViewById(R.id.tv_member_6);
        iv_member_vip = (ImageView) view.findViewById(R.id.iv_member_vip);
        iv_member_xunzhang1 = (ImageView) view.findViewById(R.id.iv_member_xunzhang1);
        iv_member_xunzhang2 = (ImageView) view.findViewById(R.id.iv_member_xunzhang2);
    }

    private void initOrderList(View view) {
        tv_member_showorders = (TextView) view.findViewById(R.id.tv_member_showorders);
        tv_member_showorders.setOnClickListener(this);
        tv_member_showcarorders = (TextView) view.findViewById(R.id.tv_member_showcarorders);
        tv_member_showcarorders.setOnClickListener(this);

        iv_member_waiting_pay = (ImageView) view.findViewById(R.id.iv_member_waiting_pay);
        ll_member_waiting_pay = (LinearLayout) view.findViewById(R.id.ll_member_waiting_pay);
        ll_member_waiting_pay.setOnClickListener(this);

        iv_member_waiting_comment = (ImageView) view.findViewById(R.id.iv_member_waiting_comment);
        ll_member_waiting_comment = (LinearLayout) view.findViewById(R.id.ll_member_waiting_comment);
        ll_member_waiting_comment.setOnClickListener(this);

        iv_member_waiting_confirm = (ImageView) view.findViewById(R.id.iv_member_waiting_confirm);
        ll_member_waiting_confirm = (LinearLayout) view.findViewById(R.id.ll_member_waiting_confirm);
        ll_member_waiting_confirm.setOnClickListener(this);

        iv_member_waiting_mount = (ImageView) view.findViewById(R.id.iv_member_waiting_mount);
        ll_member_waiting_mount = (LinearLayout) view.findViewById(R.id.ll_member_waiting_mount);
        ll_member_waiting_mount.setOnClickListener(this);

        iv_member_car_comment = (ImageView) view.findViewById(R.id.iv_member_car_comment);
        ll_member_car_comment = (LinearLayout) view.findViewById(R.id.ll_member_car_comment);
        ll_member_car_comment.setOnClickListener(this);

        iv_member_car_exchange = (ImageView) view.findViewById(R.id.iv_member_car_exchange);
        ll_member_car_exchange = (LinearLayout) view.findViewById(R.id.ll_member_car_exchange);
        ll_member_car_exchange.setOnClickListener(this);

        iv_member_car_pay = (ImageView) view.findViewById(R.id.iv_member_car_pay);
        ll_member_car_pay = (LinearLayout) view.findViewById(R.id.ll_member_car_pay);
        ll_member_car_pay.setOnClickListener(this);

        iv_member_car_receipt = (ImageView) view.findViewById(R.id.iv_member_car_receipt);
        ll_member_car_receipt = (LinearLayout) view.findViewById(R.id.ll_member_car_receipt);
        ll_member_car_receipt.setOnClickListener(this);
    }

    private void initDriverInfo(View view) {

        iv_member_msg = (ImageView) view.findViewById(R.id.iv_member_msg);
        iv_member_msg.setOnClickListener(this);
        iv_member_icon = (CircularImage) view.findViewById(R.id.iv_member_icon);
        iv_member_icon.setOnClickListener(this);
        tv_member_drivername = (TextView) view.findViewById(R.id.tv_member_drivername);
        tv_member_drivername.setOnClickListener(this);
    }

    private void showPromptMsg(View view, BadgeView badge, String newOrderNum) {

        if (!"0".equals(newOrderNum)) {
            badge.setVisibility(View.VISIBLE);
            badge.setText(newOrderNum);
            badge.setTextSize(8);
            badge.show();

        } else {
            badge.setVisibility(View.GONE);
        }
    }

    private void getDriverInfo() {
        Utils.NoNet(getActivity());
        showProgressDialog();
        MemberDriverReq req = new MemberDriverReq();
        req.code = "10011";
        KaKuApiProvider.getMemberDriverInfo(req, new KakuResponseListener<MemberDriverResp>(mActivity, MemberDriverResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    if (Constants.RES.equals(t.res)) {
                        LogUtil.E("carlist res: " + t.res);
                        SetText(t);

                    } else {
                        LogUtil.showShortToast(mActivity, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    public void SetText(MemberDriverResp t) {
        if (!"".equals(t.driver.getHead())) {
            BitmapUtil.getInstance(mActivity).download(iv_member_icon, KaKuApplication.qian_zhuikong + t.driver.getHead());
        }
        tell = t.customer_tel;
        tv_member_drivername.setText(t.driver.getName_driver());
        tv_member_3.setText("￥ " + t.driver.getMoney_balance());
        tv_member_4.setText(t.driver.getPoint_now());
        tv_member_6.setText(t.num_coupon + "张");
        if ("Y".equals(t.driver.getFlag_advert())) {
            iv_member_vip.setImageResource(R.drawable.wo_vip_huiyuan);
        } else {
            iv_member_vip.setImageResource(R.drawable.wo_vip_putong);
        }
        if ("Y".equals(t.driver.getFlag_kkxz())) {
            iv_member_xunzhang1.setVisibility(View.VISIBLE);
        } else {
            iv_member_xunzhang1.setVisibility(View.GONE);
        }
        if ("Y".equals(t.driver.getFlag_ddexz())) {
            iv_member_xunzhang2.setVisibility(View.VISIBLE);
        } else {
            iv_member_xunzhang2.setVisibility(View.GONE);
        }

        if (t.cpdd_dfk != null) {
            showPromptMsg(iv_member_car_pay, badge_truck_pay, t.cpdd_dfk);
        }
        if (t.cpdd_dsh != null) {
            showPromptMsg(iv_member_car_receipt, badge_truck_receipt, t.cpdd_dsh);
        }
        if (t.cpdd_dpj != null) {
            showPromptMsg(iv_member_car_comment, badge_truck_comment, t.cpdd_dpj);
        }
        if (t.cpdd_thh != null) {
            showPromptMsg(iv_member_car_exchange, badge_truck_exchange, t.cpdd_thh);
        }
        if (t.bydd_dfk != null) {
            showPromptMsg(iv_member_waiting_pay, badge_service_pay, t.bydd_dfk);
        }
        if (t.bydd_dfh != null) {
            showPromptMsg(iv_member_waiting_mount, badge_service_mount, t.bydd_dfh);
        }
        if (t.bydd_dsh != null) {
            showPromptMsg(iv_member_waiting_confirm, badge_service_confirm, t.bydd_dsh);
        }
        if (t.bydd_dpj != null) {
            showPromptMsg(iv_member_waiting_comment, badge_service_comment, t.bydd_dpj);
        }
        if (t.fwdd_fx != null) {
            showPromptMsg(iv_member_waiting_repair, badge_service_repair, t.fwdd_fx);
        }
        /*if (driverResp.flag_notice.equals("Y")) {
            iv_member_msg.setImageResource(R.drawable.member_msg_redpoint);
        } else {
            iv_member_msg.setImageResource(R.drawable.member_msg_normal);
        }*/
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(mActivity);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.iv_member_msg == id) {
            //todo 消息详情
            MobclickAgent.onEvent(mActivity, "Msg");
            Intent intent = new Intent(mActivity, MsgActivity.class);
            startActivity(intent);
        } else if (R.id.iv_member_icon == id) {
            //todo 司机信息 头像
            MobclickAgent.onEvent(mActivity, "TouXiang");
            Intent intent = new Intent(mActivity, DriverInfoActivity.class);
            startActivity(intent);
        } else if (R.id.tv_member_drivername == id) {
            //todo 司机信息 姓名
            MobclickAgent.onEvent(mActivity, "TouXiang");
            Intent intent = new Intent(mActivity, DriverInfoActivity.class);
            startActivity(intent);
        } else if (R.id.iv_member_4 == id) {
            //todo 司机信息 积分
            MobclickAgent.onEvent(mActivity, "JiFen");
            Intent intent = new Intent(mActivity, MyPointInfoActivity.class);
            startActivity(intent);
        } else if (R.id.iv_member_3 == id) {
            //todo 我的余额
            MobclickAgent.onEvent(mActivity, "Yue");
            Intent intent = new Intent(getActivity(), YueActivity.class);
            startActivity(intent);
        } else if (R.id.tv_member_showorders == id) {
            //todo 服务订单 显示所有订单
            MobclickAgent.onEvent(mActivity, "Ser_A");
            KaKuApplication.color_order = "";
            GoToServiceOrderList("");
        } else if (R.id.ll_member_waiting_pay == id) {
            //todo 服务订单 待付款
            MobclickAgent.onEvent(mActivity, "Ser_A");
            KaKuApplication.color_order = "A";
            GoToServiceOrderList("A");
        } else if (R.id.ll_member_waiting_mount == id) {
            //todo 服务订单 待评价
            MobclickAgent.onEvent(mActivity, "Ser_A");
            KaKuApplication.color_order = "E";
            GoToServiceOrderList("E");
        } else if (R.id.ll_member_waiting_confirm == id) {
            //todo 服务订单 待确认
            MobclickAgent.onEvent(mActivity, "Ser_A");
            KaKuApplication.color_order = "C";
            GoToServiceOrderList("D");
        } else if (R.id.ll_member_waiting_comment == id) {
            //todo 服务订单 待评价
            MobclickAgent.onEvent(mActivity, "Ser_A");
            KaKuApplication.color_order = "D";
            GoToServiceOrderList("E");
        } else if (R.id.tv_member_showcarorders == id) {
            //todo 车品订单 显示所有订单
            MobclickAgent.onEvent(mActivity, "Car_A");
            gotoTruckOrderLst("");
        } else if (R.id.ll_member_car_pay == id) {
            //todo 车品订单 待付款
            MobclickAgent.onEvent(mActivity, "Car_A");
            gotoTruckOrderLst("A");
        } else if (R.id.ll_member_car_receipt == id) {
            //todo 车品订单 待收货
            MobclickAgent.onEvent(mActivity, "Car_A");
            gotoTruckOrderLst("B");
        } else if (R.id.ll_member_car_comment == id) {
            //todo 车品订单 待评价
            MobclickAgent.onEvent(mActivity, "Car_A");
            gotoTruckOrderLst("E");
        } else if (R.id.ll_member_car_exchange == id) {
            //todo 车品订单 退换货
            MobclickAgent.onEvent(mActivity, "Car_A");
            gotoTruckOrderLst("Z");
        } else if (R.id.iv_member_6 == id) {
            //todo 优惠券
            MobclickAgent.onEvent(mActivity, "WoDeYouHuiQuan");
            Intent intent = new Intent(getActivity(), WoDeYouHuiQuanActivity.class);
            startActivity(intent);
        } else if (R.id.iv_member_1 == id) {
            //todo 我的爱车
            MobclickAgent.onEvent(mActivity, "Car");
            Intent intent = new Intent(mActivity, MyCarActivity.class);
            intent.putExtra("isToMember", true);
            startActivity(intent);
        } else if (R.id.iv_member_8 == id) {
            //todo 推荐好友
            MobclickAgent.onEvent(mActivity, "TuiJian");
            //Intent intent = new Intent(getActivity(), MemberRecommendActivity.class);
            Intent intent = new Intent(getActivity(), TuiJianJiangLiActivity.class);
            startActivity(intent);
        } else if (R.id.iv_member_7 == id) {
            //todo 客服中心
            MobclickAgent.onEvent(mActivity, "Call");
            Intent intent = new Intent(getActivity(), KeFuHelpActivity.class);
            startActivity(intent);
        } else if (R.id.tv_member_setting == id) {
            MobclickAgent.onEvent(mActivity, "Setting");
            Intent intent = new Intent(getActivity(), MemberSettingsActivity.class);
            startActivity(intent);
        } else if (R.id.iv_member_5 == id) {
            //todo 我的奖品
            MobclickAgent.onEvent(mActivity, "Prize");
            Intent intent = new Intent(getActivity(), MyPrizeActivity.class);
            startActivity(intent);
        } /*else if (R.id.rela_member_yue == id) {
            //todo 关注商铺
            MobclickAgent.onEvent(mActivity, "GuanZhuShangPu");
            Intent intent = new Intent(mActivity, AttentionShopsActivity.class);
            startActivity(intent);
        } else if (R.id.rela_member_comments == id) {
            //todo 吐槽
            MobclickAgent.onEvent(mActivity, "TuCao");
            Intent intent = new Intent(getActivity(), MemberSettingsCommentActivity.class);
            startActivity(intent);
        }*/ else if (R.id.iv_member_2 == id) {
            //todo 车贴订单
            MobclickAgent.onEvent(mActivity, "MemCheTie");
            KaKuApplication.chetie_order_to = "member";
            Intent intent = new Intent(getActivity(), CheTieOrderListActivity.class);
            startActivity(intent);
        } else if (R.id.iv_member_9 == id) {
            //todo 仓库
            Intent intent = new Intent(getActivity(), CangKuActivity.class);
            startActivity(intent);
        }

    }

    private void gotoTruckOrderLst(String state) {
        Intent intent = new Intent(mActivity, TruckOrderListActivity.class);
        KaKuApplication.truck_order_state = state;
        startActivity(intent);
    }

    public void GoToServiceOrderList(String state) {
        Intent intent = new Intent(mActivity, BaoYangOrderListActivity.class);
        KaKuApplication.state_order = state;
        startActivity(intent);
    }

}
