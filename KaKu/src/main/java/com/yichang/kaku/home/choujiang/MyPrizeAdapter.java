package com.yichang.kaku.home.choujiang;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.JiangPinObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.ArrayList;
import java.util.List;

public class MyPrizeAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<JiangPinObj> list;
    private Context mContext;
    //存放bitmap的列表
    List<Bitmap> mBimpList = new ArrayList<>();


    public MyPrizeAdapter(Context context, List<JiangPinObj> list) {
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
        final JiangPinObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_myprize, null);
            holder.iv_choujiangitem_image = (ImageView) convertView.findViewById(R.id.iv_choujiangitem_image);
            holder.iv_choujiangitem_lingqu = (ImageView) convertView.findViewById(R.id.iv_choujiangitem_lingqu);
            holder.tv_choujiangitem_name = (TextView) convertView.findViewById(R.id.tv_choujiangitem_name);
            holder.tv_choujiangitem_time = (TextView) convertView.findViewById(R.id.tv_choujiangitem_time);
            holder.tv_choujiangitem_jifen = (TextView) convertView.findViewById(R.id.tv_choujiangitem_jifen);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BitmapUtil.getInstance(mContext).download(holder.iv_choujiangitem_image, KaKuApplication.qian_zhui + obj.getImage_prize());
        holder.tv_choujiangitem_name.setText(obj.getName_prize());
        holder.tv_choujiangitem_time.setText(obj.getTime_wheel());

        if ("0".equals(obj.getType())) {
            //积分
            holder.tv_choujiangitem_jifen.setVisibility(View.VISIBLE);
            holder.iv_choujiangitem_lingqu.setVisibility(View.GONE);
        } else {
            holder.tv_choujiangitem_jifen.setVisibility(View.GONE);
            holder.iv_choujiangitem_lingqu.setVisibility(View.VISIBLE);
            if ("N".equals(obj.getFlag_exchange())) {
                holder.iv_choujiangitem_lingqu.setImageResource(R.drawable.qulingqu);
                holder.iv_choujiangitem_lingqu.setEnabled(true);
            } else if ("I".equals(obj.getFlag_exchange())) {
                holder.iv_choujiangitem_lingqu.setImageResource(R.drawable.lingquzhong);
                holder.iv_choujiangitem_lingqu.setEnabled(false);
            } else if ("Y".equals(obj.getFlag_exchange())) {
                holder.iv_choujiangitem_lingqu.setImageResource(R.drawable.wancheng);
                holder.iv_choujiangitem_lingqu.setEnabled(false);
            }

            /*holder.iv_choujiangitem_lingqu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.Many()){
                        return;
                    }
                    if ("2".equals(obj.getType())){
                        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                        builder.setTitle("提示");
                        builder.setMessage("确认领取奖品？");
                        builder.setNegativeButton("是", new android.content.DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                LingQuPrize(obj.getId_wheel_win(),holder.iv_choujiangitem_lingqu,obj);
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
                    } else {
                    Intent intent = new Intent(mContext, OkPrizeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name_prize", obj.getName_prize());
                    bundle.putString("image_prize", obj.getImage_prize());
                    bundle.putString("id_wheel_win", obj.getId_wheel_win());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);

                    }
                }
            });*/
        }

        return convertView;
    }

    class ViewHolder {

        ImageView iv_choujiangitem_image;
        ImageView iv_choujiangitem_lingqu;
        TextView tv_choujiangitem_name;
        TextView tv_choujiangitem_time;
        TextView tv_choujiangitem_jifen;

    }

}