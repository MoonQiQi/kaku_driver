package com.yichang.kaku.home.mycar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.MyCarObj;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.view.KakuMultiLineTextView;

import java.util.List;

public class MyCarAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<MyCarObj> list;
    private Context mContext;

    public MyCarAdapter(Context context, List<MyCarObj> list) {
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
        final MyCarObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_mycar, null);
            holder.iv_mycaritem_image = (ImageView) convertView.findViewById(R.id.iv_mycaritem_image);
            holder.iv_mycaritem_edit = (ImageView) convertView.findViewById(R.id.iv_mycaritem_edit);
            holder.iv_mycaritem_moren = (ImageView) convertView.findViewById(R.id.iv_mycaritem_moren);
            holder.iv_mycaritem_shanchu = (ImageView) convertView.findViewById(R.id.iv_mycaritem_shanchu);
            holder.tv_mycaritem_pinpai = (KakuMultiLineTextView) convertView.findViewById(R.id.tv_mycaritem_pinpai);
            holder.tv_mycaritem_qudong = (KakuMultiLineTextView) convertView.findViewById(R.id.tv_mycaritem_qudong);

            holder.rela_edit= (RelativeLayout) convertView.findViewById(R.id.rela_edit);
            holder.view_caritem=convertView.findViewById(R.id.view_caritem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BitmapUtil.getInstance(mContext).download(holder.iv_mycaritem_image, KaKuApplication.qian_zhui + obj.getImage_brand());
        //BitmapUtil.getInstance(mContext).download(holder.iv_mycaritem_icon,KaKuApplication.qian_zhui+obj.getImage_model());
        holder.tv_mycaritem_pinpai.setText(obj.getNick_name());
        String milseage = obj.getTravel_mileage().toString().trim();
        String timeProduction = obj.getTime_production().toString().trim();
        if (TextUtils.isEmpty(milseage) || TextUtils.isEmpty(timeProduction)) {
            holder.tv_mycaritem_qudong.setText("信息待完善");
        } else {

            holder.tv_mycaritem_qudong.setText( obj.getTime_production() + "  " + obj.getTravel_mileage() + "公里");
        }
        if ("Y".equals(obj.getFlag_default())) {
            holder.iv_mycaritem_moren.setImageResource(R.drawable.sheweimoren_yes);
        } else {
            holder.iv_mycaritem_moren.setImageResource(R.drawable.sheweimoren_no);
        }

        if (obj.getFlag_check().trim().equals("Y")) {
            holder.rela_edit.setVisibility(View.VISIBLE);
            holder.view_caritem.setVisibility(View.VISIBLE);
            //默认
            holder.iv_mycaritem_moren.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*MoRen(obj.getId_driver_car(), position);*/
                    changeButtonState.changeDefaultCar(obj.getId_driver_car(), position);
                }
            });

            //编辑
            holder.iv_mycaritem_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edit(obj);
                }
            });

            //删除
            holder.iv_mycaritem_shanchu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("提示");
                    builder.setMessage("是否删除？");
                    builder.setNegativeButton("是", new android.content.DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                           // Delete(obj.getId_driver_car(), position);
                            changeButtonState.deleteCar(obj.getId_driver_car(), position);
                        }
                    });

                    builder.setPositiveButton("否", new android.content.DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            });
        }else if(obj.getFlag_check().trim().equals("N")){
            holder.rela_edit.setVisibility(View.GONE);
            holder.view_caritem.setVisibility(View.GONE);
            holder.tv_mycaritem_qudong.setText(obj.getNick_name1());
        }

        return convertView;
    }

    private void edit(MyCarObj obj) {
        Intent intent = new Intent(mContext, MyCarDetailActivity.class);
        intent.putExtra("id_driver_car", obj.getId_driver_car());
        mContext.startActivity(intent);
    }

    class ViewHolder {

        private ImageView iv_mycaritem_image;
        private ImageView iv_mycaritem_edit;
        private KakuMultiLineTextView tv_mycaritem_pinpai;
        private KakuMultiLineTextView tv_mycaritem_qudong;
        private ImageView iv_mycaritem_moren;
        private ImageView iv_mycaritem_shanchu;

        private RelativeLayout rela_edit;
        private View view_caritem;

    }

    public void setButtonState(IChangeButtonState changeButtonState) {
        this.changeButtonState = changeButtonState;
    }

    private IChangeButtonState changeButtonState;

    public interface IChangeButtonState {
        /*void changeButtonState();*/
        void changeDefaultCar(String id_driver_car, final int position);
        void deleteCar(String id_driver_car, final int position);
    }

}