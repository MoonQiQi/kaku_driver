package com.yichang.kaku.member.settings;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.SuggestionObj;

import java.util.List;

public class CommentListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<SuggestionObj> list;
    private Context mContext;

    public CommentListAdapter(Context context, List<SuggestionObj> list) {
        // TODO Auto-generated constructor stub
        this.list = list;
        this.mContext = context;
        if (mInflater == null) {
            mInflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list != null && !list.isEmpty() ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        final SuggestionObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_member_comment_list, null);

            holder.tv_comment_content = (TextView) convertView.findViewById(R.id.tv_comment_content);
            holder.tv_comment_time = (TextView) convertView.findViewById(R.id.tv_comment_time);
            holder.tv_comment_type = (TextView) convertView.findViewById(R.id.tv_comment_type);
            holder.tv_reply_time= (TextView) convertView.findViewById(R.id.tv_reply_time);
            holder.tv_reply_content= (TextView) convertView.findViewById(R.id.tv_reply_content);

            holder.ll_reply_title= (LinearLayout) convertView.findViewById(R.id.ll_reply_title);
            holder.ll_reply_content= (LinearLayout) convertView.findViewById(R.id.ll_reply_content);

            holder.view_reply=convertView.findViewById(R.id.view_reply);
            holder.view_reply_title=convertView.findViewById(R.id.view_reply_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_comment_content.setText(obj.getContent_suggest());
        holder.tv_comment_time.setText(obj.getCreate_time());
        /*"1："功能意见"
          "2："页面意见"
          "3："操作意见"
          "4："其它意见"*/
        if(obj.getType_suggest().equals("1")){
            holder.tv_comment_type.setText("功能意见");
        }else if(obj.getType_suggest().equals("2")){
            holder.tv_comment_type.setText("页面意见");
        }else if(obj.getType_suggest().equals("3")){
            holder.tv_comment_type.setText("操作意见");
        }else if(obj.getType_suggest().equals("4")){
            holder.tv_comment_type.setText("其它意见");
        }

        if(TextUtils.isEmpty(obj.getReply_content())){
            holder.ll_reply_content.setVisibility(View.GONE);
            holder.ll_reply_title.setVisibility(View.GONE);
            holder.view_reply_title.setVisibility(View.GONE);
            holder.view_reply.setVisibility(View.GONE);

        }else {
            holder.tv_reply_time.setText(obj.getReply_time());
            holder.tv_reply_content.setText(obj.getReply_content());
        }
        return convertView;
    }


    class ViewHolder {

        private TextView tv_comment_content;
        private TextView tv_comment_type;
        private TextView tv_comment_time;
        private TextView tv_reply_time;
        private TextView tv_reply_content;

        private LinearLayout ll_reply_title;
        private LinearLayout ll_reply_content;
        private View view_reply;
        private View view_reply_title;


    }


}

