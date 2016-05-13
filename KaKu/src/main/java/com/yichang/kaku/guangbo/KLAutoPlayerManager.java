package com.yichang.kaku.guangbo;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.kaolafm.sdk.core.dao.BroadcastDao;
import com.kaolafm.sdk.core.mediaplayer.BroadcastRadioListManager;
import com.kaolafm.sdk.core.mediaplayer.BroadcastRadioPlayerManager;
import com.kaolafm.sdk.core.mediaplayer.IPlayerListChangedListener;
import com.kaolafm.sdk.core.mediaplayer.IPlayerStateListener;
import com.kaolafm.sdk.core.mediaplayer.OnDownloadProgressListener;
import com.kaolafm.sdk.core.mediaplayer.PlayItem;
import com.kaolafm.sdk.core.mediaplayer.PlayerListManager;
import com.kaolafm.sdk.core.mediaplayer.PlayerManager;
import com.kaolafm.sdk.core.mediaplayer.PlayerRadioListManager;
import com.kaolafm.sdk.core.mediaplayer.RadioBean;
import com.kaolafm.sdk.core.modle.BroadcastRadioDetailData;
import com.kaolafm.sdk.core.modle.HistoryItem;
import com.kaolafm.sdk.core.modle.PlayerRadioListItem;
import com.kaolafm.sdk.core.response.CommonResponse;
import com.kaolafm.sdk.vehicle.GeneralCallback;
import com.kaolafm.sdk.vehicle.JsonResultCallback;

import java.util.List;

/******************************************
 * 类名称：KLAutoPlayerManager
 * 类描述：播放控制管理类（点播、广播）
 *
 * @version: 2.3.1
 * @author: chenglin wen
 * @time: 2016/7/28 14:09
 ******************************************/
public class KLAutoPlayerManager {
    private static final String TAG = KLAutoPlayerManager.class.getSimpleName();
    private Context mContext;

    private KLAutoPlayerManager() {
    }

    private static class KLAutoPlayerManagerInstance {
        private static final KLAutoPlayerManager KL_AUTO_PLAYER_MANAGER_INSTANCE = new KLAutoPlayerManager();
    }

    public static KLAutoPlayerManager getInstance(Context context) {
        if (KLAutoPlayerManagerInstance.KL_AUTO_PLAYER_MANAGER_INSTANCE.mContext == null && context != null) {
            KLAutoPlayerManagerInstance.KL_AUTO_PLAYER_MANAGER_INSTANCE.mContext =
                    context instanceof Activity ? context.getApplicationContext() : context;
        }
        return KLAutoPlayerManagerInstance.KL_AUTO_PLAYER_MANAGER_INSTANCE;
    }

    public void playNext() {
        if (isBroadcastPlayerEnable()) {
            BroadcastRadioPlayerManager.getInstance().playNext();
        } else {
            PlayerManager.getInstance(mContext).playNext();
        }
    }

    public void playPre() {
        if (isBroadcastPlayerEnable()) {
            BroadcastRadioPlayerManager.getInstance().playPre();
        } else {
            PlayerManager.getInstance(mContext).playPre();
        }
    }

    public void seek(int position) {
        if (isBroadcastPlayerEnable()) {
            BroadcastRadioPlayerManager.getInstance().seek(position);
        } else {
            PlayerManager.getInstance(mContext).seek(position);
        }
    }

    /**
     * 处理播放暂停事件
     */
    public void switchPlay() {
        if (isBroadcastPlayerEnable()) {
            BroadcastRadioPlayerManager broadcastRadioPlayerManager = BroadcastRadioPlayerManager.getInstance();
            broadcastRadioPlayerManager.switchPlayerStatus();
        } else {
            PlayerManager playerManager = PlayerManager.getInstance(mContext);
            playerManager.switchPlayerStatus();
        }
    }

    /**
     * 播放一个playitem
     *
     * @param playItem
     */
    public void play(PlayItem playItem) {
        if (playItem == null) {
            return;
        }
        if (isBroadcastPlayerEnable()) {
            BroadcastRadioPlayerManager.getInstance().play(playItem);
        } else {
            PlayerManager.getInstance(mContext).play(playItem);
        }
    }

    public List<PlayItem> getPlayList() {
        if (isBroadcastPlayerEnable()) {
            return BroadcastRadioListManager.getInstance().getPlayList();
        } else {
            return PlayerListManager.getInstance().getPlayList();
        }
    }

    public int getCurPosition() {
        if (isBroadcastPlayerEnable()) {
            return BroadcastRadioListManager.getInstance().getCurPosition();
        } else {
            return PlayerListManager.getInstance().getCurPosition();
        }
    }

    public PlayItem getCurPlayItem() {
        if (isBroadcastPlayerEnable()) {
            PlayItem playItem = BroadcastRadioListManager.getInstance().getCurPlayItem();
            if (playItem == null) {
                //处理只有流没有节目单的情况
                playItem = BroadcastRadioPlayerManager.getInstance().getPrePlayItem();
            }
            return playItem;
        } else {
            return PlayerListManager.getInstance().getCurPlayItem();
        }
    }

    /**
     * 为点播、广播注册列表改变listener
     *
     * @param iPlayerListChangedListener
     */
    public void registerPlayerListChangedListener(IPlayerListChangedListener iPlayerListChangedListener) {
        BroadcastRadioListManager.getInstance().registerPlayerListChangedListener(iPlayerListChangedListener);
        PlayerListManager.getInstance().registerPlayerListChangedListener(iPlayerListChangedListener);
    }

    /**
     * 为点播、广播卸载列表改变listener
     *
     * @param iPlayerListChangedListener
     */
    public void unRegisterPlayerListChangedListener(IPlayerListChangedListener iPlayerListChangedListener) {
        BroadcastRadioListManager.getInstance().unRegisterPlayerListChangedListener(iPlayerListChangedListener);
        PlayerListManager.getInstance().unRegisterPlayerListChangedListener(iPlayerListChangedListener);
    }

    /**
     * 为点播、广播注册播放状态listener
     *
     * @param iPlayerStateListener
     */
    public void addPlayerStateListener(IPlayerStateListener iPlayerStateListener) {
        BroadcastRadioPlayerManager.getInstance().addPlayerStateListener(iPlayerStateListener);
        PlayerManager.getInstance(mContext).addPlayerStateListener(iPlayerStateListener);
    }

    /**
     * 为点播、广播删除播放状态listener
     *
     * @param iPlayerStateListener
     */
    public void removePlayerStateListener(IPlayerStateListener iPlayerStateListener) {
        BroadcastRadioPlayerManager.getInstance().removePlayerStateListener(iPlayerStateListener);
        PlayerManager.getInstance(mContext).removePlayerStateListener(iPlayerStateListener);
    }

    /**
     * 为点播、广播注册歌曲缓冲进度listener
     *
     * @param onDownloadProgressListener
     */
    public void regDownloadProgressListener(OnDownloadProgressListener onDownloadProgressListener) {
        BroadcastRadioPlayerManager.getInstance().regDownloadProgressListener(onDownloadProgressListener);
        PlayerManager.getInstance(mContext).regDownloadProgressListener(onDownloadProgressListener);
    }

    /**
     * 为点播、广播删除歌曲缓冲进度listener
     *
     * @param onDownloadProgressListener
     */
    public void unRegDownloadProgressListener(OnDownloadProgressListener onDownloadProgressListener) {
        BroadcastRadioPlayerManager.getInstance().unRegDownloadProgressListener(onDownloadProgressListener);
        PlayerManager.getInstance(mContext).unRegDownloadProgressListener(onDownloadProgressListener);
    }

    /**
     * 注册点播、传统广播获取内容listener
     *
     * @param generalCallback
     */
    public void addGetContentListener(GeneralCallback<Boolean> generalCallback) {
        BroadcastRadioPlayerManager.getInstance().addGetContentListener(generalCallback);
        PlayerManager.getInstance(mContext).addGetContentListener(generalCallback);
    }

    /**
     * 移除点播、传统广播获取内容listener
     *
     * @param generalCallback
     */
    public void removeGetContentListener(GeneralCallback<Boolean> generalCallback) {
        BroadcastRadioPlayerManager.getInstance().removeGetContentListener(generalCallback);
        PlayerManager.getInstance(mContext).removeGetContentListener(generalCallback);
    }

    /**
     * 判断当前播放是否为广播
     *
     * @return true广播、false点播
     */
    public boolean isBroadcastPlayerEnable() {
        return BroadcastRadioPlayerManager.getInstance().isBroadcastPlayerEnable();
    }

    /**
     * 校验当前是否有播放器在播放内容
     *
     * @return
     */
    public boolean isPlayerPlaying() {
        return BroadcastRadioPlayerManager.getInstance().isPlaying() || PlayerManager.getInstance(mContext).isPlaying();
    }

    /**
     * 播放器是否在暂停中
     *
     * @return
     */
    public boolean isPlayerPause() {
        return BroadcastRadioPlayerManager.getInstance().isPaused() || PlayerManager.getInstance(mContext).isPaused();
    }

    /**
     * 获取当前播放的专辑、电台、广播 id
     *
     * @return
     */
    public long getCurAlbumId() {
        if (isBroadcastPlayerEnable()) {
            PlayItem playItem = getCurPlayItem();
            if (playItem != null) {
                return playItem.getAlbumId();
            } else {
                PlayItem prePlayItem = BroadcastRadioPlayerManager.getInstance().getPrePlayItem();
                if (prePlayItem != null) {
                    return prePlayItem.getAlbumId();
                }
            }
        } else {
            PlayerRadioListItem playerRadioListItem = PlayerRadioListManager.getInstance().getCurRadioItem();
            if (playerRadioListItem != null) {
                return playerRadioListItem.getRadioId();
            }
        }
        return 0L;
    }

    /**
     * 获取当前播放的专辑、电台、广播 名称
     *
     * @return
     */
    public String getCurAlbumName() {
        if (isBroadcastPlayerEnable()) {
            PlayItem playItem = getCurPlayItem();
            if (playItem != null) {
                return playItem.getAlbumName();
            } else {
                PlayItem prePlayItem = BroadcastRadioPlayerManager.getInstance().getPrePlayItem();
                if (prePlayItem != null) {
                    return prePlayItem.getAlbumName();
                }
            }
        } else {
            PlayerRadioListItem playerRadioListItem = PlayerRadioListManager.getInstance().getCurRadioItem();
            if (playerRadioListItem != null) {
                return playerRadioListItem.getRadioName();
            }
        }
        return null;
    }

    /**
     * 返回当前是否有下一首
     *
     * @return
     */
    public boolean hasNext() {
        if (isBroadcastPlayerEnable()) {
            return BroadcastRadioPlayerManager.getInstance().hasNext();
        } else {
            return PlayerManager.getInstance(mContext).hasNext();
        }
    }

    /**
     * 返回当前是否有上一首
     *
     * @return
     */
    public boolean hasPre() {
        if (isBroadcastPlayerEnable()) {
            return BroadcastRadioPlayerManager.getInstance().hasPre();
        } else {
            return PlayerManager.getInstance(mContext).hasPre();
        }
    }

    public void playRadio(RadioBean radioBean) {
        PlayerManager.getInstance(mContext).playRadio(radioBean);
    }

    public void playPgc(long id) {
        PlayerManager.getInstance(mContext).playPgc(id);
    }

    public void playAlbum(String albumId) {
        if (TextUtils.isEmpty(albumId)) {
            return ;
        }
        if (TextUtils.isDigitsOnly(albumId)) {
            long aId = 0L;
            try {
                aId = Long.parseLong(albumId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (aId == 0) {
                return;
            }
            PlayerManager.getInstance(mContext).playAlbum(aId);
        }
    }

    public void playAudio(String audioId) {
        if (TextUtils.isDigitsOnly(audioId)) {
            long aId = 0L;
            try {
                aId = Long.parseLong(audioId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (aId == 0) {
                return;
            }
            PlayerManager.getInstance(mContext).playAudio(aId);
        }
    }

    public void playAudio(HistoryItem historyItem, GeneralCallback<Boolean> generalCallback) {
        PlayerManager.getInstance(mContext).playAudio(historyItem, generalCallback);
    }

    public void playBroadcast(final long id, final GeneralCallback<Boolean> generalCallback) {
        BroadcastRadioPlayerManager.getInstance().playBroadcast(id, new GeneralCallback<Boolean>() {
            @Override
            public void onResult(Boolean aBoolean) {
                if (!aBoolean) {
                    new BroadcastDao(null).getBroadcastDetail(id, new JsonResultCallback<CommonResponse<BroadcastRadioDetailData>>() {
                        @Override
                        public void onResult(Object o) {
                            if (o instanceof BroadcastRadioDetailData) {
                                BroadcastRadioPlayerManager.getInstance().play(PlayItem.translateToPlayItem((BroadcastRadioDetailData) o));
                            }
                        }

                        @Override
                        public void onError(int i) {
                            if (generalCallback != null) {
                                generalCallback.onError(i);
                            }
                        }
                    });
                }
                if (generalCallback != null) {
                    generalCallback.onResult(aBoolean);
                }
            }

            @Override
            public void onError(int i) {
                if (generalCallback != null) {
                    generalCallback.onError(i);
                }
            }

            @Override
            public void onException(Throwable throwable) {
                if (generalCallback != null) {
                    generalCallback.onException(throwable);
                }
            }
        });
    }

    public void playBroadcast(final BroadcastRadioDetailData broadcastRadioDetailData, final GeneralCallback<Boolean> generalCallback) {
        BroadcastRadioPlayerManager.getInstance().playBroadcast(Long.valueOf(broadcastRadioDetailData.getBroadcastId()), new GeneralCallback<Boolean>() {
            @Override
            public void onResult(Boolean aBoolean) {
                if (!aBoolean) {
                    PlayItem playItem = PlayItem.translateToPlayItem(broadcastRadioDetailData);
                    BroadcastRadioPlayerManager.getInstance().play(PlayItem.translateToPlayItem(broadcastRadioDetailData));
                }
                if (generalCallback != null) {
                    generalCallback.onResult(aBoolean);
                }
            }

            @Override
            public void onError(int i) {
                if (generalCallback != null) {
                    generalCallback.onError(i);
                }
            }

            @Override
            public void onException(Throwable throwable) {
                if (generalCallback != null) {
                    generalCallback.onException(throwable);
                }
            }
        });
    }

    public void destroy() {
        PlayerManager.getInstance(mContext).destroy();
        BroadcastRadioPlayerManager.getInstance().destroy();
    }

}
