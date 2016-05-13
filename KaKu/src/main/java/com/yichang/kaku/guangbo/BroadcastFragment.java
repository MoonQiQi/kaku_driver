package com.yichang.kaku.guangbo;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaolafm.sdk.core.dao.BroadcastDao;
import com.kaolafm.sdk.core.modle.BroadcastRadioDetailData;
import com.kaolafm.sdk.core.modle.BroadcastRadioDetailListData;
import com.kaolafm.sdk.core.response.CommonResponse;
import com.kaolafm.sdk.core.util.NetworkUtil;
import com.kaolafm.sdk.vehicle.ErrorCode;
import com.kaolafm.sdk.vehicle.GeneralCallback;
import com.kaolafm.sdk.vehicle.JsonResultCallback;
import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;

import java.util.List;

/**
 * Created by kaolafm on 2016/8/31.
 */
public class BroadcastFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final int PAGE_SIZE = 20;

    private int mSelectedPosition;
    private BroadcastDao mBroadcastDao;
    private LoadingView mLoadingView;
    private ListView mBroadcastLv;
    private BroadcastAdapter mBroadcastAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBroadcastDao = new BroadcastDao(BroadcastFragment.class.getSimpleName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_broadcast, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        delayInitData();
    }


    private void initView(View view) {
        mBroadcastLv = (ListView) view.findViewById(R.id.album_lv);
        mLoadingView = (LoadingView) view.findViewById(R.id.loading_view);
        mBroadcastLv.setOnItemClickListener(this);
        initAdapter();
        mLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delayInitData();
            }
        });
    }

    private void initAdapter() {
        mBroadcastAdapter = new BroadcastAdapter(getActivity());
        mBroadcastLv.setAdapter(mBroadcastAdapter);
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

    //     /**
//     * 获取广播电台列表数据
//     * @param type       1 类别 2 类型
//     * @param classifyId 分类id
//     * @param pageNum    请求页码 1, 2, 3...
//     * @param pageSize   每页条目数
//     * @param area       地区ID非必传
//     * @param cb         获取数据回调
//     * @param typeRef    数据模板
//     */
    private void requestBroadcastListData(final int pageNum) {

        int cityCode = 0;
        mBroadcastDao.getBroadcastList(1, "1",
                pageNum, PAGE_SIZE, cityCode, new JsonResultCallback<CommonResponse<BroadcastRadioDetailListData>>() {

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
                        onTaskError(error);
                    }
                });
    }

    /**
     * 弹出toast
     *
     * @param msg
     */
    private void showToast(String msg) {
        if (KaKuApplication.mContext != null && !TextUtils.isEmpty(msg)) {
            Toast.makeText(getResourceContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    private Context getResourceContext() {
        return KaKuApplication.mContext;
    }

    /**
     * 任务执行失败
     *
     * @param error
     */
    private void onTaskError(int error) {

        String errorString = "";
        switch (error) {
            case ErrorCode.NO_NET_WORK_ERROR://网络错误
                errorString = getResourceContext().getString(R.string.no_network);
                showToast(errorString);
                break;
            case ErrorCode.SERVER_INNER_ERROR:
                errorString = getResourceContext().getString(R.string.error_server_exception);
               /* if (BuildConfig.DEBUG) {
                    showToast(errorString);
                }*/
                break;
            case ErrorCode.SERVER_PARAMS_ERROR:
                errorString = getResourceContext().getString(R.string.error_param);
               /* if (BuildConfig.DEBUG) {
                    showToast(errorString);
                }*/
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (NetworkUtil.isNetworkAvailable(getActivity())) {
            mSelectedPosition = position;
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

            if (position == mSelectedPosition) {
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
