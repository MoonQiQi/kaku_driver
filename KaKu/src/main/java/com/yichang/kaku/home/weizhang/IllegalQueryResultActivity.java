package com.yichang.kaku.home.weizhang;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.response.IllegalQueryResp;
import com.yichang.kaku.tools.Utils;

/**
 * Created by xiaosu on 2015/11/9.
 * 违章查询结果
 */
public class IllegalQueryResultActivity extends BaseActivity {

    private TextView carNum;
    private TextView tv_num;
    private TextView tv_count;
    private TextView tv_money;
    private ListView his_list;
    private IllegalQueryResp illegalQueryResp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illegal_query_result);
        initUI();
    }

    private void initUI() {
        carNum = (TextView) findViewById(R.id.carNum);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_money = (TextView) findViewById(R.id.tv_money);
        his_list = (ListView) findViewById(R.id.his_list);
        ((TextView) findView(R.id.tv_mid)).setText("违章查询");
        /**结束页面*/
        findView(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.Many()) {
                    return;
                }
                onBackPressed();
            }
        });

        illegalQueryResp = (IllegalQueryResp) getIntent().getParcelableExtra("info");

        carNum.setText(getIntent().getStringExtra("carNum"));
        tv_num.setText(illegalQueryResp.data_count);
        tv_count.setText(illegalQueryResp.degree);
        tv_money.setText(illegalQueryResp.count);

        his_list.setAdapter(new InnerAdapter());
    }

    class InnerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return illegalQueryResp.data.size();
        }

        @Override
        public IllegalQueryResp.IllegalInfo getItem(int position) {
            return illegalQueryResp.data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflate(R.layout.layout_illegal_query_result);
            }
            //2105-12-12 2:15
            String[] split = getItem(position).time.split(" ");
            if (split != null && split.length == 2) {
                ((TextView) convertView.findViewById(R.id.time)).setText(split[1].substring(0, split[1].lastIndexOf(":")));
                ((TextView) convertView.findViewById(R.id.date)).setText(split[0]);
            }
            ((TextView) convertView.findViewById(R.id.position)).setText(getItem(position).street);
            ((TextView) convertView.findViewById(R.id.reason)).setText(getItem(position).reason);
            ((TextView) convertView.findViewById(R.id._deduct_marks)).setText(getItem(position).degree);
            ((TextView) convertView.findViewById(R.id._fine)).setText(getItem(position).count);

            return convertView;
        }
    }

}
