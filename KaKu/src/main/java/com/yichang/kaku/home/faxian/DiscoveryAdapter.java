package com.yichang.kaku.home.faxian;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.callback.ShareContentCustomizeDemo;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.DiscoveryItemObj;
import com.yichang.kaku.request.DiscoveryAddFavorReq;
import com.yichang.kaku.request.DiscoveryCancelFavorReq;
import com.yichang.kaku.request.DiscoveryShareReq;
import com.yichang.kaku.response.DiscoveryAddFavorResp;
import com.yichang.kaku.response.DiscoveryShareResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class DiscoveryAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<DiscoveryItemObj> list;
    private Context mContext;
    private String flag;
    private int num_shoucang;
    private String code;
    private String str_shoucang;
    private String share_title, share_content, share_url, share_image;
    private String num;

    public DiscoveryAdapter(Context context, List<DiscoveryItemObj> list) {
        // TODO Auto-generated constructor stub
        this.list = list;
        this.mContext = context;
        if (mInflater == null) {
            mInflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list != null && !list.isEmpty() ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;
        DiscoveryItemObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_discovery, null);
            holder.tv_discovery_name = (TextView) convertView
                    .findViewById(R.id.tv_discovery_name);
            holder.tv_discovery_time = (TextView) convertView
                    .findViewById(R.id.tv_discovery_time);
            holder.tv_discovery_content = (TextView) convertView
                    .findViewById(R.id.tv_discovery_content);
            holder.tv_discoveryitem_shoucang = (TextView) convertView
                    .findViewById(R.id.tv_discoveryitem_shoucang);
            holder.tv_discoveryitem_pinglun = (TextView) convertView
                    .findViewById(R.id.tv_discoveryitem_pinglun);
            holder.iv_discovery_image = (ImageView) convertView
                    .findViewById(R.id.iv_discovery_image);
            holder.iv_discoveryitem_shoucang = (ImageView) convertView
                    .findViewById(R.id.iv_discoveryitem_shoucang);
            holder.rela_discoveryitem_shoucang = (RelativeLayout) convertView
                    .findViewById(R.id.rela_discoveryitem_shoucang);
            holder.rela_discoveryitem_pinglun = (RelativeLayout) convertView
                    .findViewById(R.id.rela_discoveryitem_pinglun);
            holder.rela_discoveryitem_fenxiang = (RelativeLayout) convertView
                    .findViewById(R.id.rela_discoveryitem_fenxiang);
            holder.line_discoveryitem_detail = (LinearLayout) convertView
                    .findViewById(R.id.line_discoveryitem_detail);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_discovery_name.setText(list.get(position).getName_news());
        holder.tv_discovery_time.setText(list.get(position).getTime_pub());
        holder.tv_discovery_content.setText(list.get(position).getIntro_news());
        holder.tv_discoveryitem_shoucang.setText(list.get(position).getNum_collection());
        holder.tv_discoveryitem_pinglun.setText(list.get(position).getNum_comments());

        String image = KaKuApplication.qian_zhui + list.get(position).getThumbnail_news();
        flag = list.get(position).getIs_collection();
        LogUtil.E("AAAAAA"+list.get(0).getIs_collection());
        BitmapUtil.getInstance(mContext).download(holder.iv_discovery_image, image);
        if ("Y".equals(flag)) {
            holder.iv_discoveryitem_shoucang.setImageResource(R.drawable.btn_discovery_favor_red);
            holder.iv_discoveryitem_shoucang.setTag("hong");

        } else {
            holder.iv_discoveryitem_shoucang.setImageResource(R.drawable.btn_discovery_favor_normal);
            holder.iv_discoveryitem_shoucang.setTag("bai");
        }

        final String id_news = list.get(position).getId_news();

        holder.rela_discoveryitem_shoucang.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Utils.Many()){
                    return;
                }
                // TODO Auto-generated method stub
                if (!Utils.checkNetworkConnection(mContext)) {
                    Toast.makeText(mContext, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("hong".equals(holder.iv_discoveryitem_shoucang.getTag())) {
                    holder.iv_discoveryitem_shoucang.setImageResource(R.drawable.btn_discovery_favor_normal);
                    num = list.get(position).getNum_collection();
                    num_shoucang = Integer.valueOf(num);
                    num_shoucang -= 1;
                    str_shoucang = String.valueOf(num_shoucang);
                    list.get(position).setNum_collection(str_shoucang);
                    list.get(position).setIs_collection("N");
                    code = "7005";
                    cancleFavor(id_news);

                } else {
                    holder.iv_discoveryitem_shoucang.setImageResource(R.drawable.btn_discovery_favor_red);
                    num = list.get(position).getNum_collection();
                    num_shoucang = Integer.valueOf(num);
                    num_shoucang += 1;
                    str_shoucang = String.valueOf(num_shoucang);
                    list.get(position).setNum_collection(str_shoucang);
                    list.get(position).setIs_collection("Y");
                    code = "7004";
                    addFavor(id_news);
                }

                notifyDataSetChanged();
            }
        });

        holder.rela_discoveryitem_pinglun.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Utils.Many()){
                    return;
                }
                // TODO Auto-generated method stub
                if (!Utils.checkNetworkConnection(mContext)) {
                    Toast.makeText(mContext, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                KaKuApplication.discoveryId = list.get(position).getId_news();
                Intent intent = new Intent(mContext, DiscoveryCommentActivity.class);
                mContext.startActivity(intent);
            }
        });

        holder.rela_discoveryitem_fenxiang.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Utils.Many()){
                    return;
                }
                showProgress.showDialog();
                if (!Utils.checkNetworkConnection(mContext)) {
                    Toast.makeText(mContext, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                getShareInfo(list.get(position).getId_news());

            }
        });

        holder.line_discoveryitem_detail.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Utils.Many()){
                    return;
                }
                // TODO Auto-generated method stub
                if (!Utils.checkNetworkConnection(mContext)) {
                    Toast.makeText(mContext, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(mContext, DiscoveryDetailActivity.class);
                intent.putExtra("id_news", list.get(position).getId_news());
                intent.putExtra("is_collection", list.get(position).getIs_collection());
                intent.putExtra("num_collection", list.get(position).getNum_collection());
                intent.putExtra("num_comments", list.get(position).getNum_comments());
                mContext.startActivity(intent);
                KaKuApplication.position = position;
            }
        });

        return convertView;
    }

    class ViewHolder {
        private TextView tv_discovery_name;
        private TextView tv_discovery_time;
        private TextView tv_discovery_content;
        private TextView tv_discoveryitem_shoucang;
        private TextView tv_discoveryitem_pinglun;
        private ImageView iv_discovery_image;
        private ImageView iv_discoveryitem_shoucang;
        private RelativeLayout rela_discoveryitem_shoucang;
        private RelativeLayout rela_discoveryitem_pinglun;
        private RelativeLayout rela_discoveryitem_fenxiang;
        private LinearLayout line_discoveryitem_detail;

        private Boolean isClickable = true;
    }

    public void addFavor(String id_news) {

        DiscoveryAddFavorReq req = new DiscoveryAddFavorReq();
        req.code = "7004";
        req.id_news = id_news;
        KaKuApiProvider.addDiscoveryFavor(req, new KakuResponseListener<DiscoveryAddFavorResp>(mContext, DiscoveryAddFavorResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                // TODO Auto-generated method stub
                if (t != null) {
                    LogUtil.E("shoucang res: " + t.res);
                }
            }

        });
    }

    public void cancleFavor(String id_news) {

        DiscoveryCancelFavorReq req = new DiscoveryCancelFavorReq();
        req.code = "7005";
        req.id_news = id_news;
        KaKuApiProvider.cancelDiscoveryFavor(req, new KakuResponseListener<DiscoveryAddFavorResp>(mContext, DiscoveryAddFavorResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                // TODO Auto-generated method stub
                if (t != null) {
                    LogUtil.E("quxiaoshoucang res: " + t.res);
                }
            }

        });
    }

    public void getShareInfo(String id_whiff) {
        DiscoveryShareReq req = new DiscoveryShareReq();
        req.code = "7008";
        req.id_news = id_whiff;
        KaKuApiProvider.getShareInfos(req, new KakuResponseListener<DiscoveryShareResp>(mContext, DiscoveryShareResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                // TODO Auto-generated method stub
                if (t != null) {
                    LogUtil.E("getshare res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        share_content = t.intro_news;
                        share_url = t.url;
                        share_image = t.image;
                        share_title = t.name_news;
                        Share();
                    }
                }
            }

        });
    }

    public void Share() {

        ShareSDK.initSDK(mContext);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字
        //oks.setNotification(R.drawable.ic_launcher, "金牌维修通");
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(share_title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(share_url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(share_content);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setImageUrl(share_image);
        oks.setUrl(share_url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("评论。。。");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("卡库");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(share_url);

        // 启动分享GUI
        oks.show(mContext);
        showProgress.stopDialog();
    }
    private ShowProgress showProgress;

    public void setShowProgress(ShowProgress showProgress){
        this.showProgress=showProgress;

    }

    public interface ShowProgress{

        public abstract  void showDialog();
        public abstract void stopDialog();
    }


}