package com.yichang.kaku.home.zero;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.ShareContentCustomizeDemo;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.home.ad.QiangImageActivity;
import com.yichang.kaku.response.ZeroResp;
import com.yichang.kaku.tools.Utils;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ZeroingActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private TextView tv_zero_people, tv_zero_yuanjia, tv_zero_left, tv_zero_right;
    private RelativeLayout rela_zero_shenqing;
    private String type;
    private ImageView iv_zero;
    private String title_share, url_share, content_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zeroing);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("0元购");
        tv_zero_people = (TextView) findViewById(R.id.tv_zero_people);
        tv_zero_left = (TextView) findViewById(R.id.tv_zero_left);
        tv_zero_right = (TextView) findViewById(R.id.tv_zero_right);
        tv_zero_yuanjia = (TextView) findViewById(R.id.tv_zero_yuanjia);
        rela_zero_shenqing = (RelativeLayout) findViewById(R.id.rela_zero_shenqing);
        rela_zero_shenqing.setOnClickListener(this);
        iv_zero = (ImageView) findViewById(R.id.iv_zero);
        ZeroResp t = (ZeroResp) getIntent().getSerializableExtra("t");
        SetText(t);
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.rela_zero_shenqing == id) {
            if ("F".equals(type)) {
                startActivity(new Intent(this, QiangImageActivity.class));
                finish();
            } else if ("I".equals(type)) {
                Utils.Call(context, "400-6867585");
            } else if ("Y".equals(type)) {
                Share();
            }
        }
    }

    public void SetText(ZeroResp t) {
        if ("I".equals(t.flag_audit)) {
            type = "I";
            tv_zero_left.setText("请耐心等待客服审核");
            tv_zero_right.setText("联系客服");
            iv_zero.setImageResource(R.drawable.zero1);
        } else if ("Y".equals(t.flag_audit)) {
            type = "Y";
            tv_zero_left.setText("恭喜您0元车贴申请成功");
            tv_zero_right.setText("点击分享");
            iv_zero.setImageResource(R.drawable.zero3);
        } else if ("F".equals(t.flag_audit)) {
            type = "F";
            tv_zero_left.setText("很遗憾，审核未通过");
            tv_zero_right.setText("重新申请");
            iv_zero.setImageResource(R.drawable.zero2);
        }
        title_share = t.title;
        url_share = t.url;
        content_share = t.content;
    }


    public void Share() {

        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字
        //oks.setNotification(R.drawable.ic_launcher, "金牌维修通");
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title_share);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url_share);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content_share);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setImageUrl("http://manage.360kaku.com/index.php?m=Img&a=imgAction&img=icon_share.png");
        oks.setUrl(url_share);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("评论。。。");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("卡库");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url_share);

        // 启动分享GUI
        oks.show(context);
    }

}
