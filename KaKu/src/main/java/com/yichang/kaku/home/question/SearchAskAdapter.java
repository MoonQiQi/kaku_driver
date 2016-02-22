package com.yichang.kaku.home.question;

import android.content.Context;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseAdapter;
import com.yichang.kaku.response.SearchAskResp;

import java.util.List;

/**
 * Created by xiaosu on 2015/11/19.
 */
public class SearchAskAdapter extends BaseAdapter<SearchAskResp.Entity> {

    public SearchAskAdapter(List<SearchAskResp.Entity> datas) {
        super(datas);
    }

    @Override
    protected void getView(ViewHolder holder, SearchAskResp.Entity item, int position, Context context) {
        TextView content = holder.getView(R.id.question);
        TextView time = holder.getView(R.id.time);
        content.setText(item.content);
        time.setText(item.time_create);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_ask_search_item;
    }
}
