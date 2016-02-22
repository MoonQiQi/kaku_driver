package com.yichang.kaku.member;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BadgeView;
import com.yichang.kaku.global.BaseFragment;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.Ad.CheTieOrderListActivity;
import com.yichang.kaku.home.MyPrizeActivity;
import com.yichang.kaku.home.mycar.MyCarActivity;
import com.yichang.kaku.member.address.AddrActivity;
import com.yichang.kaku.member.cash.YueActivity;
import com.yichang.kaku.member.driver.AttentionShopsActivity;
import com.yichang.kaku.member.driver.DriverInfoActivity;
import com.yichang.kaku.member.driver.MsgActivity;
import com.yichang.kaku.member.driver.MyPointInfoActivity;
import com.yichang.kaku.member.recommend.MemberRecommendActivity;
import com.yichang.kaku.member.serviceorder.ServiceOrderListActivity;
import com.yichang.kaku.member.settings.MemberSettingsActivity;
import com.yichang.kaku.member.settings.MemberSettingsCommentActivity;
import com.yichang.kaku.member.truckorder.TruckOrderListActivity;
import com.yichang.kaku.obj.MemberDriverObj;
import com.yichang.kaku.request.MemberDriverReq;
import com.yichang.kaku.response.MemberDriverResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.CircularImage;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

public class MemberFragment extends BaseFragment implements OnClickListener {

    private Activity mActivity;
    //红色圆点，提示消息条数
//    private BadgeView badge;
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
    private TextView tv_member_points, tv_member_attention, tv_member_drivername;
    private TextView tv_member_coupon_title;
    private TextView tv_member_showservicephone;
    private LinearLayout ll_member_points, ll_member_attention;
    private TextView tv_member_showorders, tv_member_showcarorders;
    /*车品订单，服务订单快速入口*/
    private RelativeLayout rela_member_coupon, rela_member_certificate, rela_member_friends, rela_member_service, rela_member_settings;
    private RelativeLayout rela_member_prize,rela_member_comments,rela_member_yue;
    private LinearLayout ll_member_waiting_pay, ll_member_waiting_repair, ll_member_waiting_comment, ll_member_waiting_confirm, ll_member_waiting_mount;
    private LinearLayout ll_member_car_comment, ll_member_car_exchange, ll_member_car_pay, ll_member_car_receipt;
    //    优惠券信息
    private TextView tv_member_coupon_num;


    //司机个人信息
    private MemberDriverObj driverInfo;
    //	member页信息
    private MemberDriverResp driverResp;

    //车贴列表入口
    private RelativeLayout rela_member_sticker;
//红包动画
    private View mCoin;
    private static Boolean isClose = false;
    private static Boolean isImgShow = false;
    private String mRedEnvelopeUrl;


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
        //获取member页信息
        getDriverInfo();
    }

    private void init(View view) {
        initOrderList(view);

        initBadgeItems();

        initPageItems(view);

        /*初始化司机信息项*/
        initDriverInfo(view);

        /*初始化优惠券*/
        //initCoupon(view);

        /*初始化服务电话*/
        tv_member_showservicephone = (TextView) view.findViewById(R.id.tv_member_showservicephone);

        //获取member页信息
        //getDriverInfo();

        //初始化车贴列表入口
        rela_member_sticker= (RelativeLayout) view.findViewById(R.id.rela_member_sticker);
        rela_member_sticker.setOnClickListener(this);

        //红包入口
        mCoin = view.findViewById(R.id.iv_coin);

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
        /*rela_member_coupon = (RelativeLayout) view.findViewById(R.id.rela_member_coupon);
        rela_member_coupon.setOnClickListener(this);*/

        rela_member_certificate = (RelativeLayout) view.findViewById(R.id.rela_member_certificate);
        rela_member_certificate.setOnClickListener(this);

       /* rela_member_address = (RelativeLayout) view.findViewById(R.id.rela_member_address);
        rela_member_address.setOnClickListener(this);*/

        rela_member_friends = (RelativeLayout) view.findViewById(R.id.rela_member_friends);
        rela_member_friends.setOnClickListener(this);

        rela_member_service = (RelativeLayout) view.findViewById(R.id.rela_member_service);
        rela_member_service.setOnClickListener(this);

        rela_member_settings = (RelativeLayout) view.findViewById(R.id.rela_member_settings);
        rela_member_settings.setOnClickListener(this);

        rela_member_prize = (RelativeLayout) view.findViewById(R.id.rela_member_prize);
        rela_member_prize.setOnClickListener(this);

        rela_member_comments = (RelativeLayout) view.findViewById(R.id.rela_member_comments);
        rela_member_comments.setOnClickListener(this);

        rela_member_yue = (RelativeLayout) view.findViewById(R.id.rela_member_yue);
        rela_member_yue.setOnClickListener(this);
    }

    private void initOrderList(View view) {
        //显示所有服务订单
        tv_member_showorders = (TextView) view.findViewById(R.id.tv_member_showorders);
        tv_member_showorders.setOnClickListener(this);
//显示所有车品订单
        tv_member_showcarorders = (TextView) view.findViewById(R.id.tv_member_showcarorders);
        tv_member_showcarorders.setOnClickListener(this);

        /*初始化服务订单项*/
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

        iv_member_waiting_repair = (ImageView) view.findViewById(R.id.iv_member_waiting_repair);
        ll_member_waiting_repair = (LinearLayout) view.findViewById(R.id.ll_member_waiting_repair);
        ll_member_waiting_repair.setOnClickListener(this);
/*初始化车品订单项*/
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
//        消息按钮
        iv_member_msg = (ImageView) view.findViewById(R.id.iv_member_msg);
        iv_member_msg.setOnClickListener(this);

        /*初始化member页面layout*/
//        积分
        ll_member_points = (LinearLayout) view.findViewById(R.id.ll_member_points);
        ll_member_points.setOnClickListener(this);
//      关注商铺
        ll_member_attention = (LinearLayout) view.findViewById(R.id.ll_member_attention);
        ll_member_attention.setOnClickListener(this);

        iv_member_icon = (CircularImage) view.findViewById(R.id.iv_member_icon);
        iv_member_icon.setOnClickListener(this);
        tv_member_points = (TextView) view.findViewById(R.id.tv_member_points);//司机积分
//        tv_member_points.setOnClickListener(this);
        tv_member_attention = (TextView) view.findViewById(R.id.tv_member_attention);//司机关注
//        tv_member_attention.setOnClickListener(this);
        tv_member_drivername = (TextView) view.findViewById(R.id.tv_member_drivername);
        tv_member_drivername.setOnClickListener(this);
    }

    /*private void initCoupon(View view) {
        tv_member_coupon_title = (TextView) view.findViewById(R.id.tv_member_coupon_title);
        tv_member_coupon_num = (TextView) view.findViewById(R.id.tv_member_coupon_num);
    }*/

    private void initCarOrder() {

        if (driverResp.fwdd_dfk != null) {
            showPromptMsg(iv_member_waiting_pay, badge_service_pay, driverResp.fwdd_dfk);
        }
        if (driverResp.fwdd_daz != null) {
            showPromptMsg(iv_member_waiting_mount, badge_service_mount, driverResp.fwdd_daz);
        }
        if (driverResp.fwdd_dqr != null) {
            showPromptMsg(iv_member_waiting_confirm, badge_service_confirm, driverResp.fwdd_dqr);
        }
        if (driverResp.fwdd_dpj != null) {
            showPromptMsg(iv_member_waiting_comment, badge_service_comment, driverResp.fwdd_dpj);
        }
        if (driverResp.fwdd_fx != null) {
            showPromptMsg(iv_member_waiting_repair, badge_service_repair, driverResp.fwdd_fx);
        }

    }

    private void showPromptMsg(View view, BadgeView badge, String newOrderNum) {

//        badge = new BadgeView(getActivity(), view);
        if (!"0".equals(newOrderNum)) {
            badge.setVisibility(View.VISIBLE);
            badge.setText(newOrderNum);
            badge.show();

        } else {
            badge.setVisibility(View.GONE);
        }
    }

    private void initServiceOrder() {
        if (driverResp.cpdd_dfk != null) {
            showPromptMsg(iv_member_car_pay, badge_truck_pay, driverResp.cpdd_dfk);
        }
        if (driverResp.cpdd_dsh != null) {
            showPromptMsg(iv_member_car_receipt, badge_truck_receipt, driverResp.cpdd_dsh);
        }
        if (driverResp.cpdd_dpj != null) {
            showPromptMsg(iv_member_car_comment, badge_truck_comment, driverResp.cpdd_dpj);
        }
        if (driverResp.cpdd_thh != null) {
            showPromptMsg(iv_member_car_exchange, badge_truck_exchange, driverResp.cpdd_thh);
        }


    }


    /*初始化member页面司机订单信息*/
    private void initDriverInfo() {
//        设置头像
        if (!"".equals(driverInfo.getHead())) {
            BitmapUtil.getInstance(mActivity).download(iv_member_icon, KaKuApplication.qian_zhui + driverInfo.getHead());
        }
//        设置姓名
        this.tv_member_drivername.setText(driverInfo.getName_driver());
//        设置积分
        this.tv_member_points.setText(driverInfo.getPoint_now());
//        TODO 设置商铺
        //this.tv_member_attention.setText(driverInfo.getNum_shop());
        this.tv_member_attention.setText("￥ "+driverInfo.getMoney_balance());

//        todo 设置消息图片
        if (driverResp.flag_notice.equals("Y")) {
            iv_member_msg.setImageResource(R.drawable.member_msg_redpoint);
        } else {
            iv_member_msg.setImageResource(R.drawable.member_msg_normal);
        }
    }

    /*初始化其他项*/
    private void initOthers() {
        //tv_member_coupon_num.setText(driverResp.num_coupon + "张优惠券可用");
        tv_member_showservicephone.setText(driverResp.customer_tel);
    }


    /*根据用户ID：id_driver，获取司机个人信息，订单页信息*/
    private void getDriverInfo() {
        Utils.NoNet(getActivity());
        showProgressDialog();

        MemberDriverReq req = new MemberDriverReq();
        req.code = "10011";
        //todo 测试代码
        req.id_driver = Utils.getIdDriver();
        //todo 正式代码中使用下面的语句为req赋值
        //req.id_driver=Utils.getIdDriver();
        KaKuApiProvider.getMemberDriverInfo(req, new BaseCallback<MemberDriverResp>(MemberDriverResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, MemberDriverResp t) {
                LogUtil.E(t.driver.toString());
                if (t != null) {
                    if (Constants.RES.equals(t.res)) {

                        LogUtil.E("carlist res: " + t.res);
                        driverResp = t;
                        driverInfo = t.driver;
                            /*初始化司机信息*/
                        initDriverInfo();
        /*初始化服务订单*/
                        initServiceOrder();
        /*初始化车品订单*/
                        initCarOrder();

                        initOthers();

                        LogUtil.E("chaih t.flag_show:" + t.flag_show);
                        if ("Y".equals(t.flag_show)) {
                            isImgShow = true;
                            createCoin();


                        } else {
                            removeCoin();
                        }

                        mRedEnvelopeUrl =t.url_0;
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(mActivity);
                        }
                        LogUtil.showShortToast(mActivity, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });
    }

    private void createCoin() {
        mCoin.setVisibility(View.VISIBLE);

        mCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.E("isClose :" + isClose);
                if (isClose) {
                    resetAnimation();
                } else {
                    /*LogUtil.showShortToast(mActivity,"抢红包页面");*/
                    startActivity(new Intent(mActivity,MemberRedEnvelopeActivity.class).putExtra("url", mRedEnvelopeUrl));
                }

            }
        });
        startAnimation(5000);
    }
    private void removeCoin() {
        if (mCoin != null) {
            mCoin.clearAnimation();
            mCoin.setVisibility(View.GONE);
        }
    }


    private void startAnimation(Integer delayTime) {
        if (isImgShow) {
            RotateAnimation animation;
            animation = new RotateAnimation(0, -135, RotateAnimation.RELATIVE_TO_SELF, 0.8f, RotateAnimation.RELATIVE_TO_SELF, 0.2f);
            animation.setDuration(500);
            animation.setFillAfter(true);// 动画结束之后保持当时状态
            animation.setStartOffset(delayTime);// 设置动画延时开启的时间
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isClose = true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mCoin.startAnimation(animation);
        }
    }

    private void resetAnimation() {

        if (isImgShow) {
            RotateAnimation animation;
            animation = new RotateAnimation(-135, 0, RotateAnimation.RELATIVE_TO_SELF, 0.8f, RotateAnimation.RELATIVE_TO_SELF, 0.2f);
            animation.setDuration(500);
            animation.setFillAfter(true);// 动画结束之后保持当时状态
            animation.setStartOffset(0);// 设置动画延时开启的时间
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    isClose=false;
                    startAnimation(5000);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mCoin.startAnimation(animation);

        }

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
        } else if (R.id.ll_member_points == id) {
            //todo 司机信息 积分
            MobclickAgent.onEvent(mActivity, "JiFen");
            Intent intent = new Intent(mActivity, MyPointInfoActivity.class);
            startActivity(intent);
        } else if (R.id.ll_member_attention == id) {
            //todo 司机信息 关注商铺
            MobclickAgent.onEvent(mActivity, "Yue");
            Intent intent = new Intent(getActivity(), YueActivity.class);
            startActivity(intent);
            /*MobclickAgent.onEvent(mActivity, "GuanZhuShangPu");
            Intent intent = new Intent(mActivity, AttentionShopsActivity.class);
            startActivity(intent);*/
        } else if (R.id.tv_member_showorders == id) {
            MobclickAgent.onEvent(mActivity, "Ser_All");
            //todo 服务订单 显示所有订单
            KaKuApplication.color_order = "";
            GoToServiceOrderList("");
        } else if (R.id.ll_member_waiting_pay == id) {
            //todo 服务订单 待付款
            MobclickAgent.onEvent(mActivity, "Ser_A");
            KaKuApplication.color_order = "A";
            GoToServiceOrderList("A");
        } else if (R.id.ll_member_waiting_mount == id) {
            //todo 服务订单 待安装
            MobclickAgent.onEvent(mActivity, "Ser_B");
            KaKuApplication.color_order = "B";
            GoToServiceOrderList("B");
        } else if (R.id.ll_member_waiting_confirm == id) {
            //todo 服务订单 待确认
            MobclickAgent.onEvent(mActivity, "Ser_C");
            KaKuApplication.color_order = "C";
            GoToServiceOrderList("C");
        } else if (R.id.ll_member_waiting_comment == id) {
            //todo 服务订单 待评价
            MobclickAgent.onEvent(mActivity, "Ser_D");
            KaKuApplication.color_order = "D";
            GoToServiceOrderList("D");
        } else if (R.id.ll_member_waiting_repair == id) {
            //todo 服务订单 返修
            MobclickAgent.onEvent(mActivity, "Ser_E");
            KaKuApplication.color_order = "Z";
            GoToServiceOrderList("Z");
        } else if (R.id.tv_member_showcarorders == id) {
            //todo 车品订单 显示所有订单
            MobclickAgent.onEvent(mActivity, "Car_All");
            gotoTruckOrderLst("");
        } else if (R.id.ll_member_car_pay == id) {
            //todo 车品订单 待付款
            MobclickAgent.onEvent(mActivity, "Car_A");
            gotoTruckOrderLst("A");
        } else if (R.id.ll_member_car_receipt == id) {
            //todo 车品订单 待收货
            MobclickAgent.onEvent(mActivity, "Car_B");
            gotoTruckOrderLst("B");
        } else if (R.id.ll_member_car_comment == id) {
            //todo 车品订单 待评价
            MobclickAgent.onEvent(mActivity, "Car_C");
            gotoTruckOrderLst("E");
        } else if (R.id.ll_member_car_exchange == id) {
            //todo 车品订单 退换货
            MobclickAgent.onEvent(mActivity, "Car_D");
            gotoTruckOrderLst("Z");
        } /*else if (R.id.rela_member_coupon == id) {
            //todo 优惠券
            MobclickAgent.onEvent(mActivity, "WoDeYouHuiQuan");
            Intent intent = new Intent(getActivity(), MemberCouponsActivity.class);
            startActivity(intent);
        } */else if (R.id.rela_member_certificate == id) {
            //todo 20150909 车辆认证 不做车辆认证，修改为跳转到我的爱车界面
           /* Intent intent = new Intent(getActivity(), CartCertificationActivity.class);
            startActivity(intent);*/
            Intent intent=new Intent(mActivity, MyCarActivity.class);
            intent.putExtra("isToMember",true);
            startActivity(intent);
        } /*else if (R.id.rela_member_address == id) {
            //todo 收货地址
            MobclickAgent.onEvent(mActivity, "Addr");
            GoToAddr();
        } */else if (R.id.rela_member_friends == id) {
            //todo 推荐好友
            Intent intent = new Intent(getActivity(), MemberRecommendActivity.class);
            startActivity(intent);
        } else if (R.id.rela_member_service == id) {
            //todo 客服中心
            Utils.Call(mActivity, driverResp.customer_tel);
        } else if (R.id.rela_member_settings == id) {
            //todo 设置
            Intent intent = new Intent(getActivity(), MemberSettingsActivity.class);
            startActivity(intent);
        }else if (R.id.rela_member_prize == id) {
            //todo 我的奖品
            Intent intent = new Intent(getActivity(), MyPrizeActivity.class);
            startActivity(intent);
        }else if (R.id.rela_member_yue == id) {
            //todo 我的余额
            MobclickAgent.onEvent(mActivity, "GuanZhuShangPu");
            Intent intent = new Intent(mActivity, AttentionShopsActivity.class);
            startActivity(intent);

        }else if (R.id.rela_member_comments == id) {
            //todo 吐槽
            Intent intent = new Intent(getActivity(), MemberSettingsCommentActivity.class);
            startActivity(intent);
        }else if (R.id.rela_member_sticker == id) {
            //todo 车贴订单
            Intent intent = new Intent(getActivity(), CheTieOrderListActivity.class);
            startActivity(intent);
        }

    }

    private void gotoTruckOrderLst(String state) {
        Intent intent = new Intent(mActivity, TruckOrderListActivity.class);
        // intent.putExtra("state", state);
        KaKuApplication.truck_order_state = state;
        startActivity(intent);
    }

    public void GoToServiceOrderList(String state) {
        Intent intent = new Intent(mActivity, ServiceOrderListActivity.class);
        KaKuApplication.state_order = state;
        startActivity(intent);
    }

    public void GoToAddr() {
        Intent intent = new Intent(mActivity, AddrActivity.class);
        startActivity(intent);
    }


}
