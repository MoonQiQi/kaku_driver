package com.yichang.kaku.home.question;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseAdapter;
import com.yichang.kaku.response.AskMianResp;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

/**
 * Created by xiaosu on 2015/11/16.
 * 问答主页的adapter
 */
public class AskMainAdapter extends BaseAdapter<AskMianResp.ListEntity> {

    public AskMainAdapter(List<AskMianResp.ListEntity> datas) {
        super(datas);
    }

    @Override
    protected void getView(ViewHolder holder, AskMianResp.ListEntity item, int position, Context context) {
        TextView name_ask = holder.getView(R.id.tv_name);
        TextView con_ask = holder.getView(R.id.question);
        TextView time = holder.getView(R.id.time);
        TextView res_num = holder.getView(R.id.res_num);
        TextView res_per_num = holder.getView(R.id.res_per_num);
        ImageView iv_ask = holder.getView(R.id.iv_pic);

        TextView name_resp = holder.getView(R.id.res_name);
        TextView con_resp = holder.getView(R.id.res_);

        name_ask.setText(item.name_driver);
        con_ask.setText(item.content);
        if (TextUtils.isEmpty(item.head)) {
            iv_ask.setImageResource(R.drawable.icon_boy);
        } else {
            BitmapUtil.getInstance(context).download(iv_ask, "http://kaku.wekaku.com/index.php?m=Img&a=imgAction&img=" + item.head);
        }

        res_num.setText(item.num.num);
        res_per_num.setText(item.num.person);
        time.setText(item.time_create);
        if (null != item.reply) {
            name_resp.setText(item.reply.name_user);
            con_resp.setText("回答：" + item.reply.content);
        }

        /*动态设置分割线*/
        if (position == 0){
            holder.getView(R.id.divider).setVisibility(View.GONE);
        }else {
            holder.getView(R.id.divider).setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_ask_main_item;
    }
}
