package com.yichang.kaku.home.mycar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.SeriesObj;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.Utils;

import java.util.ArrayList;
import java.util.List;

public class CheXiActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private ListView lv_chexi;
    private ImageView iv_chexi_car;
    private TextView tv_chexi_car;
    private List<SeriesObj> chexi_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chexi);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        Bundle bundle = getIntent().getExtras();
        String name_brand = bundle.getString("name_brand");
        String image_brand = bundle.getString("image_brand");
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("选择车系");
        chexi_list = (List<SeriesObj>) bundle.getSerializable("chexi_list");
        iv_chexi_car = (ImageView) findViewById(R.id.iv_chexi_car);
        tv_chexi_car = (TextView) findViewById(R.id.tv_chexi_car);
        tv_chexi_car.setText(name_brand);
        BitmapUtil.getInstance(context).download(iv_chexi_car, KaKuApplication.qian_zhui + image_brand);
        lv_chexi = (ListView) findViewById(R.id.lv_chexi);
        lv_chexi.setOnItemClickListener(this);
        CheXiAdapter adapter = new CheXiAdapter(context, chexi_list);
        lv_chexi.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context, AddCarActivity.class);
        intent.putExtra("id", chexi_list.get(position).getId_series());
        intent.putExtra("name", chexi_list.get(position).getName_series());
        setResult(13, intent);
        finish();
    }
}
