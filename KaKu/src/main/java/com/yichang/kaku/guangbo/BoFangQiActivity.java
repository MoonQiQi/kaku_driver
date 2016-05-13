package com.yichang.kaku.guangbo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kaolafm.sdk.core.mediaplayer.BroadcastRadioListManager;
import com.kaolafm.sdk.core.mediaplayer.IPlayerStateListener;
import com.kaolafm.sdk.core.mediaplayer.OnBroadcastRadioListChangedListener;
import com.kaolafm.sdk.core.mediaplayer.PlayItem;
import com.kaolafm.sdk.vehicle.BroadcastStatus;
import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.response.GBpeopleResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class BoFangQiActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    public static final String TAG = "PlayerFragment";
    private KLAutoPlayerManager mKLAutoPlayerManager;

    private TextView mAlbumName;
    private TextView mAudioName;
    private TextView mDurationText;
    private TextView mProgressText;
    private ImageView iv_bofangqi_diantailiebiao;

    private ImageView mPlayOrPauseBtn;
    private ImageView mNextBtn;
    private ImageView mPreBtn;

    private SeekBar mSeebar;

    private boolean isSeeking = false;
    SharedPreferences.Editor editor = KaKuApplication.editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bofangqi);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        mKLAutoPlayerManager = KLAutoPlayerManager.getInstance(context);
        mAlbumName = (TextView) findViewById(R.id.player_album_name_textview);
        mAudioName = (TextView) findViewById(R.id.player_audio_name_textview);
        mDurationText = (TextView) findViewById(R.id.player_duration);
        title.setText(Utils.getGuangboName());
        mAudioName.setText(Utils.getJiemuName());
        iv_bofangqi_diantailiebiao = (ImageView) findViewById(R.id.iv_bofangqi_diantailiebiao);
        iv_bofangqi_diantailiebiao.setOnClickListener(this);
        mProgressText = (TextView) findViewById(R.id.player_current_progress);
        mPlayOrPauseBtn = (ImageView) findViewById(R.id.player_play_pause);
        mNextBtn = (ImageView) findViewById(R.id.player_next);
        mPreBtn = (ImageView) findViewById(R.id.player_prev);
        mSeebar = (SeekBar) findViewById(R.id.player_seekbar);
        initListener();
        getPeople();
    }

    private void initListener() {
        mNextBtn.setOnClickListener(this);
        mPreBtn.setOnClickListener(this);
        mPlayOrPauseBtn.setOnClickListener(this);
        mSeebar.setOnSeekBarChangeListener(mSeekBarChangeListener);
        mKLAutoPlayerManager.addPlayerStateListener(mIplayerStateListener);
        BroadcastRadioListManager.getInstance().addOnBroadcastRadioChangedListener(mOnBroadcastRadioListChangedListener);
    }

    public void getPeople() {
        showProgressDialog();
        ExitReq req = new ExitReq();
        req.code = "60057";
        KaKuApiProvider.gbPeople(req, new KakuResponseListener<GBpeopleResp>(context, GBpeopleResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("gbPeople res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        String stringss = t.fm_num + "位卡友在线收听";
                        SpannableStringBuilder styless = new SpannableStringBuilder(stringss);
                        styless.setSpan(new ForegroundColorSpan(Color.rgb(225, 0, 0)), 0, styless.length() - 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mAlbumName.setText(styless);

                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                    }
                }
                stopProgressDialog();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME4);
            startActivity(intent);
            finish();
        } else if (R.id.iv_bofangqi_diantailiebiao == id) {
            MobclickAgent.onEvent(context, "diantailiebiao");
            Intent intent = new Intent(this, GuangBoActivity.class);
            startActivity(intent);
        } else if (R.id.player_next == id) {
            if (!mKLAutoPlayerManager.hasNext()) {
                Toast.makeText(context, R.string.toast_no_next, Toast.LENGTH_SHORT).show();
                return;
            }
            PlayItem playItem = mKLAutoPlayerManager.getCurPlayItem();
            //处理直播中的节目点击下一首
            if (mKLAutoPlayerManager.isBroadcastPlayerEnable() && (playItem != null && BroadcastStatus.BROADCAST_STATUS_LIVING == playItem.getStatus())) {
                Toast.makeText(context, R.string.toast_broadcast_tip_next_program, Toast.LENGTH_SHORT).show();
                return;
            }
            mKLAutoPlayerManager.playNext();
        } else if (R.id.player_prev == id) {
            if (!mKLAutoPlayerManager.hasPre()) {
                Toast.makeText(context, R.string.toast_no_prev, Toast.LENGTH_SHORT).show();
                return;
            }
            mKLAutoPlayerManager.playPre();
        } else if (R.id.player_play_pause == id) {
            mKLAutoPlayerManager.switchPlay();
        }
    }

    /**
     * 播放器状态回调listener
     */
    IPlayerStateListener mIplayerStateListener = new IPlayerStateListener() {
        @Override
        public void onIdle(PlayItem playItem) {

        }

        @Override
        public void onPlayerPreparing(PlayItem playItem) {
            if (playItem == null) {
                return;
            }
            editor.putString(Constants.NAME_GUANGBO, mKLAutoPlayerManager.getCurAlbumName());
            editor.putString(Constants.NAME_JIEMU, playItem.getTitle());
            editor.commit();
            title.setText(Utils.getGuangboName());
            mAudioName.setText(Utils.getJiemuName());
            mSeebar.setProgress(0);
            if (KLAutoPlayerManager.getInstance(context).isBroadcastPlayerEnable() && BroadcastStatus.BROADCAST_STATUS_LIVING == playItem.getStatus()) {
                //直播中的节目需要使用FinishTime来获取时长
                mDurationText.setText(DateFormatUtil.getCurrDate(playItem.getFinishTime()));
                //直播中的节目无法进行seek
                mSeebar.setEnabled(false);
            } else {
                mSeebar.setEnabled(true);
                mSeebar.setMax(playItem.getDuration());
            }
        }

        @Override
        public void onPlayerPlaying(PlayItem playItem) {
            mPlayOrPauseBtn.setImageResource(R.drawable.zanting);
        }

        @Override
        public void onPlayerPaused(PlayItem playItem) {
            mPlayOrPauseBtn.setImageResource(R.drawable.bofang);
        }

        @Override
        public void onProgress(String s, int i, int i1, boolean b) {
            if (!isSeeking) {
                mSeebar.setProgress(i);
            }
        }

        @Override
        public void onPlayerFailed(PlayItem playItem, int i, int i1) {

        }

        @Override
        public void onPlayerEnd(PlayItem playItem) {

        }

        @Override
        public void onSeekStart(String s) {
            isSeeking = true;
        }

        @Override
        public void onSeekComplete(String s) {
            isSeeking = false;
        }

        @Override
        public void onBufferingStart(PlayItem playItem) {

        }

        @Override
        public void onBufferingEnd(PlayItem playItem) {

        }
    };

    private OnBroadcastRadioListChangedListener mOnBroadcastRadioListChangedListener = new OnBroadcastRadioListChangedListener() {

        @Override
        public void onProgramUpdated(int i) {

        }

        @Override
        public void onLivingCountDown(String s) {
            if (mKLAutoPlayerManager.isBroadcastPlayerEnable()) {
                PlayItem playItem = mKLAutoPlayerManager.getCurPlayItem();
                //如果是传统广播直播中的节目，则进行刷新直播中的时间
                if (playItem != null && (playItem.getStatus() == BroadcastStatus.BROADCAST_STATUS_LIVING)) {
                    mProgressText.setText(s);
                }
            }
        }
    };

    /**
     * seekbar listener
     */
    private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            refreshPlaytime(progress, seekBar.getMax());
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int progress = seekBar.getProgress();
            int maxValue = seekBar.getMax();
            int seekto = (maxValue - progress) < 1000 ? maxValue : progress;
            mKLAutoPlayerManager.seek(seekto);
        }
    };

    /**
     * 刷新时间
     *
     * @param progress
     * @param duration
     */
    private void refreshPlaytime(long progress, long duration) {
        mDurationText.setText(DateFormatUtil.getDescriptiveTime(duration));
        mProgressText.setText(DateFormatUtil.getDescriptiveTime(progress));
    }

    /*@Override
    public void onDestroy() {
        //destroy 时要进行移除listener
        mKLAutoPlayerManager.removePlayerStateListener(mIplayerStateListener);
        BroadcastRadioListManager.getInstance().removeOnPlayingRadioChangedListener(mOnBroadcastRadioListChangedListener);
        //此方法必须调用，否则会造成应用退出还在播放的情况
        mKLAutoPlayerManager.destroy();
        super.onDestroy();
    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME4);
            startActivity(intent);
            finish();
        }
        return false;
    }

    public void setCallback(CallBack callback) {
        this.callback = callback;
    }

    private CallBack callback;

    public interface CallBack {

        void stopFM(KLAutoPlayerManager mKLAutoPlayerManager, OnBroadcastRadioListChangedListener mOnBroadcastRadioListChangedListener, IPlayerStateListener mIplayerStateListener);

    }

}
