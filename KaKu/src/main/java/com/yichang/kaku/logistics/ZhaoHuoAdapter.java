package com.yichang.kaku.logistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ZhaoHuoObj;
import com.yichang.kaku.request.CallReq;
import com.yichang.kaku.response.CallResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.List;

public class ZhaoHuoAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ZhaoHuoObj> list;
    private Context mContext;
    private String call_string;

    public ZhaoHuoAdapter(Context context, List<ZhaoHuoObj> list) {
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

        Viewholder1 holder1;
        final ZhaoHuoObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder1 = new Viewholder1();
            convertView = mInflater.inflate(R.layout.item_zhaohuo, null);
            holder1.iv_zhaohuo_call = (ImageView) convertView.findViewById(R.id.iv_zhaohuo_call);
            holder1.tv_zhaohuo_qidian = (TextView) convertView.findViewById(R.id.tv_zhaohuo_qidian);
            holder1.tv_zhaohuo_zhongdian = (TextView) convertView.findViewById(R.id.tv_zhaohuo_zhongdian);
            holder1.tv_zhaohuo_fabushijian = (TextView) convertView.findViewById(R.id.tv_zhaohuo_fabushijian);
            holder1.tv_zhaohuo_huoyuanxinxi = (TextView) convertView.findViewById(R.id.tv_zhaohuo_huoyuanxinxi);
            holder1.tv_zhaohuo_guanzhu = (TextView) convertView.findViewById(R.id.tv_zhaohuo_guanzhu);
            holder1.iv_adimageitem_image = (ImageView) convertView.findViewById(R.id.iv_adimageitem_image);
            holder1.rela_zhaohuo_item = (RelativeLayout) convertView.findViewById(R.id.rela_zhaohuo_item);
            convertView.setTag(holder1);
        } else {
            holder1 = (Viewholder1) convertView.getTag();
        }
        if ((position + 1) % 6 == 0) {
            holder1.rela_zhaohuo_item.setVisibility(View.GONE);
            holder1.iv_adimageitem_image.setVisibility(View.VISIBLE);
            holder1.iv_adimageitem_image.measure(0,0);
            BitmapUtil.getInstance(mContext).download(holder1.iv_adimageitem_image, KaKuApplication.qian_zhui + obj.getImage_roll());
        } else {
            holder1.rela_zhaohuo_item.setVisibility(View.VISIBLE);
            holder1.iv_adimageitem_image.setVisibility(View.GONE);
            holder1.tv_zhaohuo_qidian.setText(obj.getArea_depart());
            holder1.tv_zhaohuo_zhongdian.setText(obj.getArea_arrive());
            holder1.tv_zhaohuo_fabushijian.setText("发布时间：" + obj.getTime_pub());
            holder1.tv_zhaohuo_huoyuanxinxi.setText("货源信息：" + obj.getRemark_supply());
            holder1.tv_zhaohuo_guanzhu.setText(obj.getFocus_supply() + "人关注");
            holder1.iv_zhaohuo_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MobclickAgent.onEvent(mContext, "ZhaoHuoCall");
                    call_string = obj.getPhone_supply().split(",")[0];
                    Utils.Call(mContext, call_string);
                    Call(obj.getId_supply());
                }
            });
        }
        return convertView;
    }

    class Viewholder1 {
        ImageView iv_zhaohuo_call;
        TextView tv_zhaohuo_qidian;
        TextView tv_zhaohuo_zhongdian;
        TextView tv_zhaohuo_fabushijian;
        TextView tv_zhaohuo_huoyuanxinxi;
        TextView tv_zhaohuo_guanzhu;
        ImageView iv_adimageitem_image;
        RelativeLayout rela_zhaohuo_item;
    }

    public void Call(String id_supply) {
        CallReq req = new CallReq();
        req.code = "6004";
        req.id_driver = Utils.getIdDriver();
        req.id_supply = id_supply;
        KaKuApiProvider.Call(req, new BaseCallback<CallResp>(CallResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, CallResp t) {
                if (t != null) {
                    LogUtil.E("call res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

                    } else {
                        LogUtil.showShortToast(mContext, t.msg);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {

            }
        });
    }
}
