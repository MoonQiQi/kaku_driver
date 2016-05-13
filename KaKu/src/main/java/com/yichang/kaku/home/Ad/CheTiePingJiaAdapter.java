package com.yichang.kaku.home.ad;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.member.ShowImageActivity;
import com.yichang.kaku.obj.CheTiePingJiaObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class CheTiePingJiaAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<CheTiePingJiaObj> list;
    private Context mContext;

    public CheTiePingJiaAdapter(Context context, List<CheTiePingJiaObj> list) {
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

    public int dip2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        CheTiePingJiaObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_chetiepingjia, null);
            holder.iv_chetiepingjia1 = (ImageView) convertView.findViewById(R.id.iv_chetiepingjia1);
            holder.iv_chetiepingjia2 = (ImageView) convertView.findViewById(R.id.iv_chetiepingjia2);
            holder.iv_chetiepingjia3 = (ImageView) convertView.findViewById(R.id.iv_chetiepingjia3);
            holder.iv_chetiepingjia4 = (ImageView) convertView.findViewById(R.id.iv_chetiepingjia4);
            holder.tv_chetiepingjia_phone = (TextView) convertView.findViewById(R.id.tv_chetiepingjia_phone);
            holder.tv_chetiepingjia_time = (TextView) convertView.findViewById(R.id.tv_chetiepingjia_time);
            holder.tv_chetiepingjia_content = (TextView) convertView.findViewById(R.id.tv_chetiepingjia_content);
            holder.rela_chetiepingjia = (RelativeLayout) convertView.findViewById(R.id.rela_chetiepingjia);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if (!"".equals(obj.getImage_eval())) {
            holder.iv_chetiepingjia1.setVisibility(View.VISIBLE);
            BitmapUtil.getInstance(mContext).download(holder.iv_chetiepingjia1, KaKuApplication.qian_zhuikong + obj.getImage_eval());
        } else {
            holder.iv_chetiepingjia1.setVisibility(View.GONE);
        }

        if (!"".equals(obj.getImage_eval1())) {
            holder.iv_chetiepingjia2.setVisibility(View.VISIBLE);
            BitmapUtil.getInstance(mContext).download(holder.iv_chetiepingjia2, KaKuApplication.qian_zhuikong + obj.getImage_eval1());
        } else {
            holder.iv_chetiepingjia2.setVisibility(View.GONE);
        }

        if (!"".equals(obj.getImage_eval2())) {
            holder.iv_chetiepingjia3.setVisibility(View.VISIBLE);
            BitmapUtil.getInstance(mContext).download(holder.iv_chetiepingjia3, KaKuApplication.qian_zhuikong + obj.getImage_eval2());
        } else {
            holder.iv_chetiepingjia3.setVisibility(View.GONE);
        }

        if (!"".equals(obj.getImage_eval3())) {
            holder.iv_chetiepingjia4.setVisibility(View.VISIBLE);
            BitmapUtil.getInstance(mContext).download(holder.iv_chetiepingjia4, KaKuApplication.qian_zhuikong + obj.getImage_eval3());
        } else {
            holder.iv_chetiepingjia4.setVisibility(View.GONE);
        }

        holder.tv_chetiepingjia_phone.setText(obj.getName_driver());
        holder.tv_chetiepingjia_time.setText(obj.getTime_eval());
        holder.tv_chetiepingjia_content.setText(obj.getContent_eval());

        holder.iv_chetiepingjia1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageList(holder);
                Intent intent = new Intent(mContext, ShowImageActivity.class);
                intent.putExtra("position", 0);
                mContext.startActivity(intent);
            }
        });
        holder.iv_chetiepingjia2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageList(holder);
                Intent intent = new Intent(mContext, ShowImageActivity.class);
                intent.putExtra("position", 1);
                mContext.startActivity(intent);
            }
        });
        holder.iv_chetiepingjia3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageList(holder);
                Intent intent = new Intent(mContext, ShowImageActivity.class);
                intent.putExtra("position", 2);
                mContext.startActivity(intent);
            }
        });
        holder.iv_chetiepingjia4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageList(holder);
                Intent intent = new Intent(mContext, ShowImageActivity.class);
                intent.putExtra("position", 3);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    private void getImageList(ViewHolder holder) {
        for (int i = 0; i < holder.rela_chetiepingjia.getChildCount(); i++) {
            View view = holder.rela_chetiepingjia.getChildAt(i);
            if (view.getVisibility() == View.VISIBLE) {
                KaKuApplication.mBimpList.add(convertViewToBitmap(view, i));
            }
        }
    }

    public Bitmap convertViewToBitmap(View view, int sortNum) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dip2px(60), dip2px(60));
        params.leftMargin = sortNum * (dip2px(10) + dip2px(60)) + dip2px(15);
        params.topMargin = dip2px(10);
        view.setLayoutParams(params);
        return bitmap;
    }

    class ViewHolder {

        ImageView iv_chetiepingjia1;
        ImageView iv_chetiepingjia2;
        ImageView iv_chetiepingjia3;
        ImageView iv_chetiepingjia4;
        TextView tv_chetiepingjia_phone;
        TextView tv_chetiepingjia_time;
        TextView tv_chetiepingjia_content;
        RelativeLayout rela_chetiepingjia;
    }
}