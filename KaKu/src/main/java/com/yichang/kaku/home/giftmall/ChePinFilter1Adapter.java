package com.yichang.kaku.home.giftmall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.ChePinAdapter1Obj;
import com.yichang.kaku.obj.ChePinAdapter2Obj;
import com.yichang.kaku.view.widget.MyGridView;

import java.util.List;

public class ChePinFilter1Adapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ChePinAdapter1Obj> list;
    private Context mContext;
    private List<ChePinAdapter2Obj> list_2;
    private ChePinFilter2Adapter adapter;

    public ChePinFilter1Adapter(Context context, List<ChePinAdapter1Obj> list) {
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
        final ChePinAdapter1Obj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_chepinadapter1, null);
            holder.tv_chetieadapter1_name = (TextView) convertView.findViewById(R.id.tv_chetieadapter1_name);
            holder.gv_chepinadapter1 = (MyGridView) convertView.findViewById(R.id.gv_chepinadapter1);
            holder.rela_item_filter1 = (RelativeLayout) convertView.findViewById(R.id.rela_item_filter1);
            holder.view_filter2 = convertView.findViewById(R.id.view_filter2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if ("R".equals(obj.getColor_type())) {
            holder.tv_chetieadapter1_name.setTextColor(mContext.getResources().getColor(R.color.color_red));
        } else {
            holder.tv_chetieadapter1_name.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        holder.tv_chetieadapter1_name.setText(obj.getName_type());

        holder.rela_item_filter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress.showDialog();
                showProgress.setId(obj.getId_goods_type(), position);
                obj.setFlag_root("R");
            }
        });
        list_2 = obj.getList();
        if (list_2.size() == 0) {
            holder.gv_chepinadapter1.setVisibility(View.GONE);
            holder.view_filter2.setVisibility(View.GONE);
        } else {
            holder.gv_chepinadapter1.setVisibility(View.VISIBLE);
            holder.view_filter2.setVisibility(View.VISIBLE);
            adapter = new ChePinFilter2Adapter(mContext, list_2);
            holder.gv_chepinadapter1.setAdapter(adapter);
            holder.gv_chepinadapter1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showProgress.showDialog();
                    showProgress.setId(obj.getList().get(position).getId_goods_type(), position);
                    for (int i = 0; i < obj.getList().size(); i++) {
                        obj.getList().get(i).setColor_type("B");
                    }
                    for (int i = 0; i<list.size();i++){
                        list.get(i).setColor_type("B");
                    }
                    obj.getList().get(position).setColor_type("R");
                }
            });
        }

        return convertView;
    }

    class ViewHolder {
        private TextView tv_chetieadapter1_name;
        private MyGridView gv_chepinadapter1;
        private View view_filter2;
        private RelativeLayout rela_item_filter1;

    }

    private ShowProgress showProgress;

    public void setShowProgress(ShowProgress showProgress) {
        this.showProgress = showProgress;

    }

    public interface ShowProgress {
        void showDialog();

        void stopDialog();

        void setId(String id, int position);
    }
}