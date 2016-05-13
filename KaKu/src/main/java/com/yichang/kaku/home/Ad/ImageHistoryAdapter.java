package com.yichang.kaku.home.ad;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ImageHisObj;
import com.yichang.kaku.request.GetAddReq;
import com.yichang.kaku.response.GetAddResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.List;

public class ImageHistoryAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ImageHisObj> list;
    private Context mContext;

    public ImageHistoryAdapter(Context context, List<ImageHisObj> list) {
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
        final ImageHisObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_tupianlishi, null);
            holder.iv_imagehis_image1 = (SimpleDraweeView) convertView.findViewById(R.id.iv_imagehis_image1);
            holder.iv_imagehis_image2 = (SimpleDraweeView) convertView.findViewById(R.id.iv_imagehis_image2);
            holder.tv_imagehis_time = (TextView) convertView.findViewById(R.id.tv_imagehis_time);
            holder.tv_imagehis_name = (TextView) convertView.findViewById(R.id.tv_imagehis_name);
            holder.tv_imagehis_reason = (TextView) convertView.findViewById(R.id.tv_imagehis_reason);
            holder.iv_imagehis_dian = (ImageView) convertView.findViewById(R.id.iv_imagehis_dian);
            holder.iv_imagehis_chongxin = (ImageView) convertView.findViewById(R.id.iv_imagehis_chongxin);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String type = obj.getFlag_type();
        if ("I".equals(type)) {
            holder.iv_imagehis_dian.setImageResource(R.drawable.imagehis_deng);
            holder.tv_imagehis_reason.setVisibility(View.GONE);
            holder.iv_imagehis_chongxin.setVisibility(View.GONE);
        } else if ("Y".equals(type)) {
            holder.iv_imagehis_dian.setImageResource(R.drawable.imagehis_gou);
            holder.tv_imagehis_reason.setVisibility(View.GONE);
            holder.iv_imagehis_chongxin.setVisibility(View.GONE);
        } else if ("F".equals(type)) {
            holder.iv_imagehis_dian.setImageResource(R.drawable.imagehis_cha);
            holder.tv_imagehis_reason.setVisibility(View.VISIBLE);
            holder.iv_imagehis_chongxin.setVisibility(View.VISIBLE);
            if (position != 0) {
                holder.iv_imagehis_chongxin.setVisibility(View.GONE);
            } else {
                holder.iv_imagehis_chongxin.setVisibility(View.VISIBLE);
            }
        }
        if ("C".equals(obj.getFlag_type_shoot())) {
            if ("L".equals(obj.getFlag_position())) {
                holder.tv_imagehis_name.setText("前左侧拍照");
            } else {
                holder.tv_imagehis_name.setText("前右侧拍照");
            }
        } else {
            if (position == list.size() - 1) {
                holder.tv_imagehis_name.setText("行驶证拍照");
            } else {
                holder.tv_imagehis_name.setText("驾驶证拍照");
            }
        }

        holder.tv_imagehis_time.setText(obj.getUpload_time());
        holder.tv_imagehis_reason.setText("审核失败：" + obj.getApprove_opinions());
        holder.iv_imagehis_image1.setImageURI(Uri.parse(KaKuApplication.qian_zhuikong + obj.getImage0_advert()));
        holder.iv_imagehis_image2.setImageURI(Uri.parse(KaKuApplication.qian_zhuikong + obj.getImage1_advert()));

        holder.iv_imagehis_chongxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("行驶证拍照".equals(holder.tv_imagehis_name.getText().toString())) {
                    Intent intent = new Intent(mContext, XingShiZhengImageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                } else {
                    GetAdd();
                }
            }
        });

        return convertView;
    }

    class ViewHolder {

        ImageView iv_imagehis_dian;
        ImageView iv_imagehis_chongxin;
        SimpleDraweeView iv_imagehis_image1;
        SimpleDraweeView iv_imagehis_image2;
        TextView tv_imagehis_time;
        TextView tv_imagehis_name;
        TextView tv_imagehis_reason;

    }

    public void GetAdd() {
        GetAddReq req = new GetAddReq();
        req.code = "60011";
        req.id_driver = Utils.getIdDriver();
        req.id_advert = KaKuApplication.id_advert;
        KaKuApiProvider.GetAdd(req, new KakuResponseListener<GetAddResp>(mContext, GetAddResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getadd res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.id_advert = t.advert.getId_advert();
                        GoToAdd();
                    } else {
                        LogUtil.showShortToast(mContext, t.msg);
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }


        });
    }

    public void GoToAdd() {
        Intent intent = new Intent();
        intent.setClass(mContext, AdImageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }
}