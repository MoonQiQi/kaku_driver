package com.yichang.kaku.guangbo;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kaolafm.sdk.core.mediaplayer.BroadcastRadioListManager;
import com.kaolafm.sdk.core.mediaplayer.IPlayerStateListener;
import com.kaolafm.sdk.core.mediaplayer.OnBroadcastRadioListChangedListener;
import com.kaolafm.sdk.core.mediaplayer.PlayItem;
import com.kaolafm.sdk.vehicle.BroadcastStatus;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yichang.kaku.R;

/******************************************
 * 类名称：PlayerFragment
 * 类描述：
 *
 * @version: 1.0
 * @author: chenglin wen
 * @time: 2016/8/31 11:30
 ******************************************/
public class PlayerFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "PlayerFragment";
    private KLAutoPlayerManager mKLAutoPlayerManager;

    private ImageView mCoverImg;

    private TextView mAlbumName;
    private TextView mAudioName;
    private TextView mDurationText;
    private TextView mProgressText;

    private Button mPlayOrPauseBtn;
    private Button mNextBtn;
    private Button mPreBtn;

    private SeekBar mSeebar;

    private boolean isSeeking = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKLAutoPlayerManager = KLAutoPlayerManager.getInstance(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_player, container, false);
        mCoverImg = (ImageView) parentView.findViewById(R.id.player_cover_img);
        mAlbumName = (TextView) parentView.findViewById(R.id.player_album_name_textview);
        mAudioName = (TextView) parentView.findViewById(R.id.player_audio_name_textview);
        mDurationText = (TextView) parentView.findViewById(R.id.player_duration);
        mProgressText = (TextView) parentView.findViewById(R.id.player_current_progress);
        mPlayOrPauseBtn = (Button) parentView.findViewById(R.id.player_play_pause);
        mNextBtn = (Button) parentView.findViewById(R.id.player_next);
        mPreBtn = (Button) parentView.findViewById(R.id.player_prev);
        mSeebar = (SeekBar) parentView.findViewById(R.id.player_seekbar);
        initListener();
        return parentView;
    }

    private void initListener() {
        mNextBtn.setOnClickListener(this);
        mPreBtn.setOnClickListener(this);
        mPlayOrPauseBtn.setOnClickListener(this);
        mSeebar.setOnSeekBarChangeListener(mSeekBarChangeListener);
        mKLAutoPlayerManager.addPlayerStateListener(mIplayerStateListener);
        BroadcastRadioListManager.getInstance().addOnBroadcastRadioChangedListener(mOnBroadcastRadioListChangedListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.player_next:
                if (!mKLAutoPlayerManager.hasNext()) {
                    Toast.makeText(getActivity(), R.string.toast_no_next, Toast.LENGTH_SHORT).show();
                    return;
                }
                PlayItem playItem = mKLAutoPlayerManager.getCurPlayItem();
                //处理直播中的节目点击下一首
                if (mKLAutoPlayerManager.isBroadcastPlayerEnable() && (playItem != null && BroadcastStatus.BROADCAST_STATUS_LIVING == playItem.getStatus())) {
                    Toast.makeText(getActivity(), R.string.toast_broadcast_tip_next_program, Toast.LENGTH_SHORT).show();
                    return;
                }
                mKLAutoPlayerManager.playNext();
                break;
            case R.id.player_prev:
                if (!mKLAutoPlayerManager.hasPre()) {
                    Toast.makeText(getActivity(), R.string.toast_no_prev, Toast.LENGTH_SHORT).show();
                    return;
                }
                mKLAutoPlayerManager.playPre();
                break;
            case R.id.player_play_pause:
                mKLAutoPlayerManager.switchPlay();
                break;
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
            mAlbumName.setText(mKLAutoPlayerManager.getCurAlbumName());
            mAudioName.setText(playItem.getTitle());
            mSeebar.setProgress(0);
            if (KLAutoPlayerManager.getInstance(getActivity()).isBroadcastPlayerEnable() && BroadcastStatus.BROADCAST_STATUS_LIVING == playItem.getStatus()) {
                //直播中的节目需要使用FinishTime来获取时长
                mDurationText.setText(DateFormatUtil.getCurrDate(playItem.getFinishTime()));
                //直播中的节目无法进行seek
                mSeebar.setEnabled(false);
            } else {
                mSeebar.setEnabled(true);
                mSeebar.setMax(playItem.getDuration());
            }
            loaderCover(playItem);
        }

        @Override
        public void onPlayerPlaying(PlayItem playItem) {
            mPlayOrPauseBtn.setText(R.string.player_pause);
        }

        @Override
        public void onPlayerPaused(PlayItem playItem) {
            mPlayOrPauseBtn.setText(R.string.player_play);
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
     * 联网获取封面图
     *
     * @param playItem
     */
    private void loaderCover(PlayItem playItem) {
        if (playItem == null) {
            return;
        }
        ImageLoader.getInstance().loadImage(playItem.getMediumPicUrl(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                if (mCoverImg != null) {
                    mCoverImg.setImageBitmap(loadedImage);
                }
            }
        });
    }

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

    @Override
    public void onDestroy() {
        //destroy 时要进行移除listener
        mKLAutoPlayerManager.removePlayerStateListener(mIplayerStateListener);
        BroadcastRadioListManager.getInstance().removeOnPlayingRadioChangedListener(mOnBroadcastRadioListChangedListener);
        //此方法必须调用，否则会造成应用退出还在播放的情况
        mKLAutoPlayerManager.destroy();
        super.onDestroy();
    }
}