package com.yichang.kaku.home.question;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseAdapter;
import com.yichang.kaku.response.MyAskListResp;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

/**
 * Created by xiaosu on 2015/11/17.
 * 我的提问列表
 */
public class MyAskListAdapter extends BaseAdapter<MyAskListResp.ListEntity> {

    private final MyAskListResp.UserEntity user;

    public MyAskListAdapter(List<MyAskListResp.ListEntity> datas, MyAskListResp.UserEntity user) {
        super(datas);
        this.user = user;
    }

    @Override
    protected void getView(ViewHolder holder, MyAskListResp.ListEntity item, int position, Context context) {
        ImageView iv_pic = holder.getView(R.id.iv_pic);
        ImageView iv_flag = holder.getView(R.id.iv_flag);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView question = holder.getView(R.id.question);
        TextView time = holder.getView(R.id.time);
        TextView res_per_num = holder.getView(R.id.res_per_num);

        tv_name.setText(TextUtils.isEmpty(user.name_driver) ? "佚名" : user.name_driver);
        question.setText(item.content);
        time.setText(item.time_create);
        res_per_num.setText(item.count);
        iv_flag.setImageResource(TextUtils.equals(item.flag_question, "Y") ? R.drawable.img_tiwen_yijiejue : R.drawable.img_tiwen_weijiejue);

        if (TextUtils.isEmpty(user.head)) {
            iv_pic.setImageResource(R.drawable.icon_boy);
        } else {
            BitmapUtil.getInstance(context).download(iv_pic, "http://kaku.wekaku.com/index.php?m=Img&a=imgAction&img=" + user.head);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_ask_my_que_item;
    }

}
