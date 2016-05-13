package com.yichang.kaku.home.weizhang;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.obj.IllegalInfo;
import com.yichang.kaku.response.IllegalQueryResp;
import com.yichang.kaku.tools.Utils;


public class IllegalQueryResultActivity extends BaseActivity {

    private TextView carNum;
    private TextView tv_num;
    private TextView tv_count;
    private TextView tv_money;
    private ListView his_list;
    private IllegalQueryResp t;
    private int num = 0, count = 0, money = 0;

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

        t = (IllegalQueryResp) getIntent().getSerializableExtra("t");

        for (int i = 0; i < t.lists.size(); i++) {
            num = t.lists.size();
            count += Integer.parseInt(t.lists.get(i).getFen());
            money += Integer.parseInt(t.lists.get(i).getMoney());
        }

        tv_num.setText(num + "");
        tv_count.setText(count + "");
        tv_money.setText(money + "");

        his_list.setAdapter(new InnerAdapter());
    }

    class InnerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return t.lists.size();
        }

        @Override
        public IllegalInfo getItem(int position) {
            return t.lists.get(position);
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

            String[] split = getItem(position).getDate().split(" ");
            if (split != null && split.length == 2) {
                ((TextView) convertView.findViewById(R.id.time)).setText(split[1].substring(0, split[1].lastIndexOf(":")));
                ((TextView) convertView.findViewById(R.id.date)).setText(split[0]);
            }
            ((TextView) convertView.findViewById(R.id.position)).setText(getItem(position).getArea());
            ((TextView) convertView.findViewById(R.id.reason)).setText(getItem(position).getAct());
            ((TextView) convertView.findViewById(R.id._deduct_marks)).setText(getItem(position).getFen());
            ((TextView) convertView.findViewById(R.id._fine)).setText(getItem(position).getMoney());

            return convertView;
        }
    }

}
