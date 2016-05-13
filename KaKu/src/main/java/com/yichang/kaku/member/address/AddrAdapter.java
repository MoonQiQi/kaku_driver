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
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.AddrObj;
import com.yichang.kaku.tools.Utils;

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
            holder.tv_addr_area = (TextView) convertView.findViewById(R.id.tv_addr_area);

            holder.rela_addr_moren = (RelativeLayout) convertView.findViewById(R.id.rela_addr_moren);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_addr_name.setText(obj.getName_addr());
        holder.tv_addr_phone.setText(obj.getPhone_addr());
        holder.tv_addr_addr.setText(obj.getAddr());
        holder.tv_addr_area.setText(obj.getRemark_area());
        if ("Y".equals(obj.getFlag_default())) {
            holder.iv_addr_moren.setImageResource(R.drawable.check_yuan);
        } else {
            holder.iv_addr_moren.setImageResource(R.drawable.uncheck_yuan);
        }

        holder.iv_addr_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.Many()) {
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("提示");
                builder.setMessage("确认删除？");
                builder.setNegativeButton("是", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                        inface.Delete(obj.getId_addr());

                    }
                });

                builder.setPositiveButton("否", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.rela_addr_moren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inface.MoRen(obj.getId_addr());
            }
        });

        holder.iv_addr_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.Many()) {
                    return;
                }
                KaKuApplication.new_addr = "modify";
                KaKuApplication.itemPosition = position;
                Intent intent = new Intent(mContext, NewAddrActivity.class);
                KaKuApplication.name_addr = obj.getName_addr();
                KaKuApplication.phone_addr = obj.getPhone_addr();
                KaKuApplication.dizhi_addr = obj.getAddr();
                KaKuApplication.id_area = obj.getId_area();
                KaKuApplication.area_addr = obj.getRemark_area();
                KaKuApplication.id_dizhi = obj.getId_addr();
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
        TextView tv_addr_area;
        RelativeLayout rela_addr_moren;

    }

    private AddrInterface inface;

    public void setShowProgress(AddrInterface inface) {
        this.inface = inface;

    }

    public interface AddrInterface {

         void MoRen(String id_addr);

         void Delete(String id_addr);
    }

}