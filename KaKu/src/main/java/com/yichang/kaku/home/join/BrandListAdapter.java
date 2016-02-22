package com.yichang.kaku.home.join;

import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.response.BrandListResp;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;


/**
 * Created by linrui on 2015/10/20.
 */
public class BrandListAdapter extends BaseAdapter {

    private List<BrandListResp.ItemEntity> data;
    public SparseBooleanArray itemStateCache = new SparseBooleanArray();

    public BrandListAdapter(List<BrandListResp.ItemEntity> data,List<BrandListResp.ItemEntity> choosed) {
        this.data = data;

        for (BrandListResp.ItemEntity item1 : choosed) {
            for (BrandListResp.ItemEntity item2 : data) {
                if (item2.id_brand.equals(item1.id_brand)) {
                    itemStateCache.put(data.indexOf(item2), true);
                    break;
                }
            }
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.layout_allbrand_list_item, null);
            convertView.setTag(findById(convertView, R.id.iv_right));
        }

        ImageView iv_brand = findById(convertView, R.id.iv_brand);
        TextView name_brand = findById(convertView, R.id.name_brand);

        View iv_right = (View) convertView.getTag();
        //是否被选中
        iv_right.setVisibility(itemStateCache.get(position, false) ? View.VISIBLE : View.INVISIBLE);

        //索引
        TextView tv_index = findById(convertView, R.id.tv_index);
        LinearLayout ll_index = findById(convertView, R.id.ll_index);

        BrandListResp.ItemEntity entity = data.get(position);

        String currentLetter = entity.getPinyin().charAt(0) + "";

        // 分组, 只显示首次出现新的字母的条目索引
        String indexStr = null;

        if (position == 0) {
            // 当前是第一个条目, 直接显示
            indexStr = currentLetter;
        } else {
            // 不是第一个条目, 跟前一个比较
            // 前一个拼音首字母
            String previousLetter = data.get(position - 1).getPinyin().charAt(0) + "";

            if (!TextUtils.equals(previousLetter, currentLetter)) {
                // 不相同时, 显示当前的索引栏
                indexStr = currentLetter;
            }
        }

        //填充数据
        ll_index.setVisibility(indexStr == null ? View.GONE : View.VISIBLE);
        tv_index.setText(currentLetter);
        BitmapUtil.getInstance(parent.getContext()).download(iv_brand, "http://kaku.wekaku.com/index.php?m=Img&a=imgAction&img=" + entity.image_brand);
        name_brand.setText(entity.name_brand);

        return convertView;
    }

    public <T extends View> T findById(View root, int resId) {
        return (T) root.findViewById(resId);
    }

}
