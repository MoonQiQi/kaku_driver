package com.yichang.kaku.home.nouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ShopCarObj;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;

import java.util.List;

public class ShopCarAdapter extends BaseAdapter {
    private LayoutInflater mInflater;

    private List<ShopCarObj> list;
    private Context mContext;
    private int num;

    public int getMeasuredHeight() {
        return measuredHeight;
    }

    private int measuredHeight = -1;

    public ShopCarAdapter(Context context, List<ShopCarObj> list) {
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
        ShopCarObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_shopcar, null);
            holder.iv_baoyang_image = (ImageView) convertView.findViewById(R.id.iv_baoyang_image);
            holder.iv_baoyang_jiahao = (ImageView) convertView.findViewById(R.id.iv_baoyang_jiahao);
            holder.iv_baoyang_jianhao = (ImageView) convertView.findViewById(R.id.iv_baoyang_jianhao);
            holder.iv_baoyang_delete = (ImageView) convertView.findViewById(R.id.iv_baoyang_delete);
            //holder.iv_baoyang_genghuan = (ImageView) convertView.findViewById(R.id.iv_baoyang_genghuan);
            holder.tv_baoyang_name = (TextView) convertView.findViewById(R.id.tv_baoyang_name);
            holder.tv_baoyang_price = (TextView) convertView.findViewById(R.id.tv_baoyang_price);
            holder.tv_baoyang_num = (TextView) convertView.findViewById(R.id.tv_baoyang_num);

            convertView.setTag(holder);

            if (measuredHeight == -1) {
                convertView.measure(0, 0);
                measuredHeight = convertView.getMeasuredHeight();
            }
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BitmapUtil.getInstance(mContext).download(holder.iv_baoyang_image, KaKuApplication.qian_zhui + obj.getImage_item());
        holder.tv_baoyang_name.setText(obj.getName_item());
        holder.tv_baoyang_price.setText("¥ " + obj.getPrice_item());
        holder.tv_baoyang_num.setText("X" + obj.getNum_item());

        //加号
        holder.iv_baoyang_jiahao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //数量
                num = Integer.parseInt(list.get(position).getNum_item());
                holder.iv_baoyang_jianhao.setEnabled(true);

                num++;
                LogUtil.E("num2:" + num);
                list.get(position).setNum_item(String.valueOf(num));
                notifyDataSetChanged();

                if (callback != null) {
                    callback.callback(list);
                }
            }
        });

        //减号
        holder.iv_baoyang_jianhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数量
                num = Integer.parseInt(list.get(position).getNum_item());

                if (num == 1) {
                    holder.iv_baoyang_jianhao.setEnabled(false);
                    return;
                } else {
                    holder.iv_baoyang_jianhao.setEnabled(true);
                    num--;
                    list.get(position).setNum_item(String.valueOf(num));
                }
                LogUtil.E("num3:" + num);
                notifyDataSetChanged();

                if (callback != null) {
                    callback.callback(list);
                }
            }
        });

        //删除
        holder.iv_baoyang_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();

                if (callback != null) {
                    callback.callback(list);
                }
            }
        });

		/*//更换
        holder.iv_baoyang_genghuan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});*/

        return convertView;
    }

    class ViewHolder {

        ImageView iv_baoyang_image;
        ImageView iv_baoyang_jiahao;
        ImageView iv_baoyang_jianhao;
        ImageView iv_baoyang_delete;
        ImageView iv_baoyang_genghuan;
        TextView tv_baoyang_name;
        TextView tv_baoyang_price;
        TextView tv_baoyang_num;

    }

    public void setCallback(CallBack callback) {
        this.callback = callback;
    }

    private CallBack callback;

    public interface CallBack {
        void callback(List<ShopCarObj> list);
    }

    public void setList(List<ShopCarObj> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}