package com.yichang.kaku.guangbo;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaolafm.sdk.core.dao.BroadcastDao;
import com.kaolafm.sdk.core.modle.BroadcastRadioDetailData;
import com.kaolafm.sdk.core.modle.BroadcastRadioDetailListData;
import com.kaolafm.sdk.core.response.CommonResponse;
import com.kaolafm.sdk.core.util.NetworkUtil;
import com.kaolafm.sdk.vehicle.GeneralCallback;
import com.kaolafm.sdk.vehicle.JsonResultCallback;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;

import java.util.List;

public class GuangBoActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private static final int PAGE_SIZE = 20;

    private BroadcastDao mBroadcastDao;
    private LoadingView mLoadingView;
    private ListView mBroadcastLv;
    private BroadcastAdapter mBroadcastAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guangbo);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("电台列表");
        mBroadcastDao = new BroadcastDao(BroadcastFragment.class.getSimpleName());
        mBroadcastLv = (ListView) findViewById(R.id.lv_guangbo);
        mLoadingView = (LoadingView) findViewById(R.id.loading_view);
        mBroadcastLv.setOnItemClickListener(this);
        mLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delayInitData();
            }
        });
        mBroadcastAdapter = new BroadcastAdapter(context);
        mBroadcastLv.setAdapter(mBroadcastAdapter);
        delayInitData();
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

    /**
     * 请求数据
     */
    private void delayInitData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestBroadcastListData(1);
            }
        }, 500);
    }

    private void requestBroadcastListData(final int pageNum) {
        mBroadcastDao.getBroadcastList(1, "1",
                pageNum, PAGE_SIZE, 0, new JsonResultCallback<CommonResponse<BroadcastRadioDetailListData>>() {

                    @Override
                    public void onResult(Object obj) {
                        //hide loading
                        if (obj instanceof BroadcastRadioDetailListData) {
                            BroadcastRadioDetailListData broadcastRadioDetailListData = (BroadcastRadioDetailListData) obj;
                            mBroadcastAdapter.updateAdapter(broadcastRadioDetailListData.getDataList());
                        } else {
                            // 类型不匹配
                        }
                    }

                    @Override
                    public void onError(int error) {
                        LogUtil.E("error" + error);
                    }
                });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (NetworkUtil.isNetworkAvailable(context)) {
            KaKuApplication.mSelectedPosition = position;
            startPlay(position);
        }
    }

    /**
     * 进入广播播放器 TODO
     */
    private void startPlay(int position) {

        BroadcastRadioDetailData data = mBroadcastAdapter.getItem(position);
        if (null == data || data.getBroadcastId() == 0) {
            return;
        }
        KLAutoPlayerManager.getInstance(KaKuApplication.mContext).playBroadcast(data.getBroadcastId(), new GeneralCallback<Boolean>() {
            @Override
            public void onResult(Boolean aBoolean) {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
        mBroadcastAdapter.notifyDataSetChanged();
    }

    //adapter

    /**
     * 广播列表adapter
     */
    public class BroadcastAdapter extends BaseAdapter {

        private int mSelectColor;
        private int mNormalColor;
        private LayoutInflater mInflater;
        private List<BroadcastRadioDetailData> dataList;

        public BroadcastAdapter(Context context) {
            Resources res = context.getResources();
            mInflater = LayoutInflater.from(context);
            mSelectColor = res.getColor(R.color.black_20_transparent_color);
            mNormalColor = res.getColor(R.color.transparent_color);
        }


        /**
         * 更新数据
         */
        public void updateAdapter(List<BroadcastRadioDetailData> dataList) {
            this.dataList = dataList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return dataList == null ? 0 : dataList.size();
        }

        @Override
        public BroadcastRadioDetailData getItem(int position) {
            return dataList == null ? null : dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BroadcastRadioDetailData data = dataList.get(position);
            ViewHolder viewHolder;
            if (convertView == null) {

                convertView = mInflater.inflate(R.layout.item_broadcast_list, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.rootLayout = (RelativeLayout) convertView.findViewById(R.id.item_broadcast_root_layout);
                viewHolder.broadcastPlayingIndicatorImg = (ImageView) convertView.findViewById(R.id.item_broadcast_playing_indicator_img);
                viewHolder.broadcastItemText = (TextView) convertView.findViewById(R.id.item_broadcast_name_tv);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (position == KaKuApplication.mSelectedPosition) {
                //显示正在播放图标
                ViewUtil.setViewVisibility(viewHolder.broadcastPlayingIndicatorImg, View.VISIBLE);
                viewHolder.rootLayout.setBackgroundColor(mSelectColor);
            } else {
                // 显示正常图标
                ViewUtil.setViewVisibility(viewHolder.broadcastPlayingIndicatorImg, View.GONE);
                viewHolder.rootLayout.setBackgroundColor(mNormalColor);
            }
            viewHolder.broadcastItemText.setText(data.getName());
            return convertView;
        }
    }

    static class ViewHolder {

        private RelativeLayout rootLayout;
        /**
         * 正在播放图标
         */
        private ImageView broadcastPlayingIndicatorImg;
        /**
         * 广播名称
         */
        private TextView broadcastItemText;
    }

}
