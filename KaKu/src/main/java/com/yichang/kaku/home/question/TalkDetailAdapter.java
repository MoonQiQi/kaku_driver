package com.yichang.kaku.home.question;

import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.response.ConversationBean;
import com.yichang.kaku.tools.BitmapUtil;

/**
 * Created by xiaosu on 2015/11/13.
 */
public class TalkDetailAdapter extends BaseAdapter {

    private String headRight;
    private String headerLeft;
    private ConversationBean bean;
    private SparseIntArray stateContainer = new SparseIntArray();

    public TalkDetailAdapter(ConversationBean bean, String headRight, String headerLeft) {
        this.bean = bean;
        this.headRight = headRight;
        this.headerLeft = headerLeft;
    }

    @Override
    public int getCount() {
        return bean.list.size();
    }

    @Override
    public ConversationBean.conversationItem getItem(int position) {
        return bean.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        ConversationBean.conversationItem item = getItem(position);
        if (item.id_driver == 0) {//回答
            if (TextUtils.isEmpty(item.image)) {//回答的是文字
                return 0;
            } else {//回答的是图片
                return 3;
            }
        } else {//当前用户的信息
            if (TextUtils.isEmpty(item.image)) {//发送的是文字
                return 1;
            } else {//发送的是图片
                return 2;
            }
        }
    }

    public void updateMsgState(int position, @ConversationBean.State int state) {
        stateContainer.put(position, state);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        switch (getItemViewType(position)) {
            case 0://我的信息
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_talk_item_me, null);
                }
                valueView(position, convertView, 0, 0);
                break;
            case 1://回答的信息
                if (convertView == null) {
                    convertView = View.inflate(parent.getContext(), R.layout.layout_talk_item_other, null);
                }
                valueView(position, convertView, 0, 1);
                break;
            case 2://回答的图片
                if (convertView == null) {
                    convertView = View.inflate(parent.getContext(), R.layout.layout_talk_item_other_img, null);
                }
                valueView(position, convertView, 1, 2);
                break;
            case 3://发送的图片
                if (convertView == null) {
                    convertView = View.inflate(parent.getContext(), R.layout.layout_talk_item_me_img, null);
                }
                valueView(position, convertView, 1, 3);
                break;
        }

        return convertView;
    }

    private void valueView(int position, View convertView, int type, int itemType) {

        TextView talk_time = findView(convertView, R.id.talk_time);
        talk_time.setText(getItem(position).time_create);
        ImageView user_avatar = findView(convertView, R.id.user_avatar);
        /*初始化图像*/
        initAvatar(convertView, itemType, user_avatar);

        /*消息发送的状态*/
        findView(convertView, R.id.img_state).setVisibility(stateContainer.get(position, ConversationBean.STATE_SUC) == ConversationBean.STATE_SUC ? View.INVISIBLE : View.VISIBLE);
        switch (type) {
            case 0:
                TextView talk_content = findView(convertView, R.id.talk_content);
                talk_content.setText(getItem(position).content);
                break;
            case 1:
                ImageView img = findView(convertView, R.id.talk_img);
                BitmapUtil.getInstance(convertView.getContext()).download(img, "http://kaku.wekaku.com/index.php?m=Img&a=imgAction&img=" + getItem(position).image);
                break;
        }
    }

    private void initAvatar(View convertView, int itemType, ImageView user_avatar) {
        switch (itemType) {
            case 0:
            case 3:
                if (TextUtils.isEmpty(headerLeft)) {
                    user_avatar.setImageResource(R.drawable.icon_boy);
                } else {
                    BitmapUtil.getInstance(convertView.getContext()).download(user_avatar, "http://kaku.wekaku.com/index.php?m=Img&a=imgAction&img=" + headerLeft);
                }
                break;
            case 1:
            case 2:
                if (TextUtils.isEmpty(headRight)) {
                    user_avatar.setImageResource(R.drawable.icon_boy);
                } else {
                    BitmapUtil.getInstance(convertView.getContext()).download(user_avatar, "http://kaku.wekaku.com/index.php?m=Img&a=imgAction&img=" + headRight);
                }
                break;
        }
    }

    public <T extends View> T findView(View view, int id) {
        return (T) view.findViewById(id);
    }

}
