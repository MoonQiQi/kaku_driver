package com.yichang.kaku.member.truckorder;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ConfirmOrderProductObj;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;

import java.util.List;

public class TruckOrderCommentAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ConfirmOrderProductObj> list;
    private Context mContext;

    public TruckOrderCommentAdapter(Context context, List<ConfirmOrderProductObj> list) {
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

        final ViewHolder holder;
        final ConfirmOrderProductObj obj = list.get(position);

        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_truck_order_comment, null);
            holder.iv_product_image = (ImageView) convertView.findViewById(R.id.iv_product_image);
            holder.rb_comment = (RatingBar) convertView.findViewById(R.id.rb_comment);
            holder.et_order_comment = (EditText) convertView.findViewById(R.id.et_order_comment);
            //holder.iv_comment_img1= (ImageView) convertView.findViewById(R.id.iv_comment_img1);
            holder.iv_comment_add = (ImageView) convertView.findViewById(R.id.iv_comment_add);
            holder.tv_comment_textsize = (TextView) convertView.findViewById(R.id.tv_comment_textsize);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BitmapUtil.getInstance(mContext).download(holder.iv_product_image, KaKuApplication.qian_zhui + obj.getImage_goods());

        holder.et_order_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String comment = String.valueOf(s);
                if ("".equals(comment)) {
                    comment = "好评！";
                }
                if (comment.length() <= 500) {
                    holder.tv_comment_textsize.setText(comment.length() + "/500");
                } else {
                    LogUtil.showShortToast(mContext, "已超出最大输入字符限制");
                    comment = comment.substring(0, 500);
                    holder.et_order_comment.setText(comment);
                    Editable ea = holder.et_order_comment.getEditableText();
                    Selection.setSelection(ea, ea.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
               /* holder.et_order_comment.setText(s.toString());*/
            }
        });

        holder.iv_comment_add.setTag(obj.getId_goods());


        //holder.iv_comment_add.setTag(R.drawable.tianjiatupian);
        holder.iv_comment_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callBack.setPicToView(holder.iv_comment_add, position);

            }
        });


        return convertView;
    }

    private ICallBack callBack;

    public void setCallBack(ICallBack callBack) {
        this.callBack = callBack;
    }

    interface ICallBack {
        void setPicToView(ImageView view, int adapterIndex);

    }


    class ViewHolder {

        private ImageView iv_product_image;
        private RatingBar rb_comment;
        private EditText et_order_comment;

        //private ImageView iv_comment_img1;
        private ImageView iv_comment_add;
        private TextView tv_comment_textsize;


    }


}

