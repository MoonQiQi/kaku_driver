package com.yichang.kaku.member.address;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.AddrObj;
import com.yichang.kaku.request.AddrMorenReq;
import com.yichang.kaku.request.DeleteAddrReq;
import com.yichang.kaku.response.AddrMorenResp;
import com.yichang.kaku.response.DeleteAddrResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.List;

public class AddrAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<AddrObj> list;
    private Context mContext;

    public AddrAdapter(Context context, List<AddrObj> list) {
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
        final AddrObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_addr, null);
            holder.iv_addr_moren = (ImageView) convertView.findViewById(R.id.iv_addr_moren);
            holder.iv_addr_delete = (ImageView) convertView.findViewById(R.id.iv_addr_delete);
            holder.iv_addr_bianji = (ImageView) convertView.findViewById(R.id.iv_addr_bianji);
            holder.tv_addr_name = (TextView) convertView.findViewById(R.id.tv_addr_name);
            holder.tv_addr_phone = (TextView) convertView.findViewById(R.id.tv_addr_phone);
            holder.tv_addr_addr = (TextView) convertView.findViewById(R.id.tv_addr_addr);

            holder.rela_addr_moren = (RelativeLayout) convertView.findViewById(R.id.rela_addr_moren);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_addr_name.setText(obj.getName_addr());
        holder.tv_addr_phone.setText(obj.getPhone_addr());
        holder.tv_addr_addr.setText(obj.getAddr());
        if ("Y".equals(obj.getFlag_default())) {
            holder.iv_addr_moren.setImageResource(R.drawable.check_yuan);
        } else {
            holder.iv_addr_moren.setImageResource(R.drawable.uncheck_yuan);
        }

        holder.iv_addr_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //增加多次点击判断
                if (Utils.Many()) {
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                LogUtil.E("上下文" + mContext);
                builder.setTitle("提示");
                builder.setMessage("确认删除？");
                builder.setNegativeButton("是", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                        Delete(obj.getId_addr(), position);

                    }
                });

                builder.setPositiveButton("否", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });
                //builder.create().show();
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        /*holder.rela_addr_moren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoRen(obj.getId_addr(),position);
            }
        });*/

        holder.iv_addr_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //增加多次点击判断
                if (Utils.Many()) {
                    return;
                }
//               获取点击条目的位置
                KaKuApplication.itemPosition = position;
                Intent intent = new Intent(mContext, NewAddrActivity.class);
                KaKuApplication.name_addr = obj.getName_addr();
                KaKuApplication.phone_addr = obj.getPhone_addr();
                KaKuApplication.dizhi_addr = obj.getAddr();
                KaKuApplication.id_dizhi = obj.getId_addr();
                /*KaKuApplication.county_addr = "";
                KaKuApplication.province_addrname = "";
                KaKuApplication.city_addrname = "";*/

                KaKuApplication.flag_addr = obj.getFlag_default();
                KaKuApplication.isEditAddr = true;
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {

        ImageView iv_addr_moren;
        ImageView iv_addr_delete;
        ImageView iv_addr_bianji;
        TextView tv_addr_name;
        TextView tv_addr_phone;
        TextView tv_addr_addr;

        RelativeLayout rela_addr_moren;

    }

    public void Delete(String id_addr, final int position) {
        Utils.NoNet(mContext);
        DeleteAddrReq req = new DeleteAddrReq();
        req.code = "10016";
        req.id_addr = id_addr;
        KaKuApiProvider.DeleteAddr(req, new KakuResponseListener<DeleteAddrResp>(mContext, DeleteAddrResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("deleteaddr res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list.remove(position);
                        notifyDataSetChanged();
                    }
                    LogUtil.showShortToast(mContext, t.msg);
                }
            }

        });
    }

    private ShowProgress showProgress;

    public void setShowProgress(ShowProgress showProgress) {
        this.showProgress = showProgress;

    }

    public interface ShowProgress {

        public abstract void showDialog();

        public abstract void stopDialog();
    }

    ;


    public void MoRen(String id_addr, final int position) {

        Utils.NoNet(mContext);
        AddrMorenReq req = new AddrMorenReq();
        req.code = "10017";
        req.id_addr = id_addr;
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.MorenAddr(req, new KakuResponseListener<AddrMorenResp>(mContext, AddrMorenResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("morenaddr res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setFlag_default("N");
                        }
                        list.get(position).setFlag_default("Y");
                        notifyDataSetChanged();
                    }
                    LogUtil.showShortToast(mContext, t.msg);
                }
            }

        });
    }
}