package com.yichang.kaku.home.ad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.request.AdCommitReq;
import com.yichang.kaku.request.QiNiuYunTokenReq;
import com.yichang.kaku.response.QiNiuYunTokenResp;
import com.yichang.kaku.response.ShopCommitResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.uploadimages.activity.AlbumActivity;
import com.yichang.kaku.view.uploadimages.activity.GalleryActivity;
import com.yichang.kaku.view.uploadimages.util.Bimp;
import com.yichang.kaku.view.uploadimages.util.FileUtils;
import com.yichang.kaku.view.uploadimages.util.ImageItem;
import com.yichang.kaku.view.uploadimages.util.PublicWay;
import com.yichang.kaku.view.uploadimages.util.Res;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

public class PingJiaAdActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private EditText et_pingjiaorder_content;
    private ImageView iv_pingjiaorder_image;
    private PopupWindow window;
    private TextView tv_takephoto;
    private TextView tv_myphoto;
    private TextView tv_exitphoto, tv_pingjiaorder_name, tv_pingjiaorder_addr;
    private String imagePath;
    private Button btn_pingjiaorder_fabiao;

    private GridView noScrollgridview;
    private GridAdapter gvAdapter;
    public static Bitmap bimap;

    ///scrollView
    private Boolean isShowTop;
    public String token1, token2, token3, token4;
    public String key1, key2, key3, key4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingjiaad);
        Res.init(this);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        et_pingjiaorder_content.setText(KaKuApplication.pingjia_ad);
        KaKuApplication.PingJiaShopOrAd = "Ad";
        if (window.isShowing()) {
            window.dismiss();
        }
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("评价任务");
        et_pingjiaorder_content = (EditText) findViewById(R.id.et_pingjiaorder_content);
        btn_pingjiaorder_fabiao = (Button) findViewById(R.id.btn_pingjiaorder_fabiao);
        btn_pingjiaorder_fabiao.setOnClickListener(this);
        window = new PopupWindow();

        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.btn_fzp);

        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gvAdapter = new GridAdapter(this);
        gvAdapter.update();
        noScrollgridview.setAdapter(gvAdapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parentView, View view, int position,
                                    long arg3) {
                if (Utils.Many()) {
                    return;
                }
                if (position == Bimp.tempSelectBitmap.size()) {
                    ShowWindow(view);
                    window.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                } else {
                    Intent intent = new Intent(context, GalleryActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", position);
                    startActivity(intent);
                }
            }
        });

        isShowTop = getIntent().getBooleanExtra("showTop", false);
        if (KaKuApplication.flag_IsDetailToPingJia) {
            KaKuApplication.flag_IsDetailToPingJia = false;
            Bimp.tempSelectBitmap.clear();
            Bimp.max = 0;
        }
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
        } else if (R.id.tv_takephoto == id) {
            photo();
            window.dismiss();
        } else if (R.id.tv_myphoto == id) {
            Intent intent = new Intent(context, AlbumActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
            window.dismiss();

        } else if (R.id.tv_exitphoto == id) {
            window.dismiss();

        } else if (R.id.btn_pingjiaorder_fabiao == id) {
            KaKuApplication.pingjia_ad = et_pingjiaorder_content.getText().toString().trim();
            if (TextUtils.isEmpty(KaKuApplication.pingjia_ad)) {
                LogUtil.showShortToast(context, "评价内容不能为空");
                return;
            }
            Commit();
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height += 5;
        listView.setLayoutParams(params);
    }

    public void ShowWindow(View v) {
        KaKuApplication.pingjia_ad = et_pingjiaorder_content.getText().toString().trim();
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        window.setWidth(outMetrics.widthPixels);
        window.setHeight(outMetrics.heightPixels / 4);
        View view = getLayoutInflater().inflate(R.layout.popupwindow, null);
        tv_takephoto = (TextView) view.findViewById(R.id.tv_takephoto);
        tv_takephoto.setOnClickListener(this);
        tv_myphoto = (TextView) view.findViewById(R.id.tv_myphoto);
        tv_myphoto.setOnClickListener(this);
        tv_exitphoto = (TextView) view.findViewById(R.id.tv_exitphoto);
        tv_exitphoto.setOnClickListener(this);
        window.setContentView(view);
        window.setBackgroundDrawable(null);
        window.showAtLocation(v, Gravity.BOTTOM, 0, 10);
    }

    public void Commit() {
        if (Bimp.tempSelectBitmap.size() == 0) {
            Upload();
        } else {
            for (int i = 1; i <= Bimp.tempSelectBitmap.size(); i++) {
                QiNiuYunToken(i + "");
            }
        }

    }

    public void QiNiuYunToken(final String sort) {
        Utils.NoNet(context);
        QiNiuYunTokenReq req = new QiNiuYunTokenReq();
        req.code = "qn01";
        req.sort = sort;
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.QiNiuYunToken(req, new KakuResponseListener<QiNiuYunTokenResp>(this, QiNiuYunTokenResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("qiniuyuntoken res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if ("1".equals(sort)) {
                            token1 = t.token;
                            key1 = t.key;
                            uploadImg(token1, key1, sort);
                        } else if ("2".equals(sort)) {
                            token2 = t.token;
                            key2 = t.key;
                            uploadImg(token2, key2, sort);
                        } else if ("3".equals(sort)) {
                            token3 = t.token;
                            key3 = t.key;
                            uploadImg(token3, key3, sort);
                        } else if ("4".equals(sort)) {
                            token4 = t.token;
                            key4 = t.key;
                            uploadImg(token4, key4, sort);
                        }

                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    private int num = 0;

    public void IsSize() {
        num++;
        if (num == Bimp.tempSelectBitmap.size()) {
            Upload();
        }
    }

    private void uploadImg(final String token, final String key, final String sort) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String file = "";
                LogUtil.E("File0:" + Bimp.tempSelectBitmap.get(0).toString());
                LogUtil.E("Size:" + Bimp.tempSelectBitmap.size());
                LogUtil.E("Sort:" + sort);
                if ("1".equals(sort)) {
                    file = Bimp.tempSelectBitmap.get(0).getImagePath();
                } else if ("2".equals(sort)) {
                    file = Bimp.tempSelectBitmap.get(1).getImagePath();
                } else if ("3".equals(sort)) {
                    file = Bimp.tempSelectBitmap.get(2).getImagePath();
                } else if ("4".equals(sort)) {
                    file = Bimp.tempSelectBitmap.get(3).getImagePath();
                }


                UploadManager uploadManager = new UploadManager();
                uploadManager.put(file, key, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String arg0, ResponseInfo info, JSONObject response) {
                                // TODO Auto-generated method stub
                                if (info.isOK()) {
                                    IsSize();
                                }
                            }
                        }, null);
            }
        }).start();
    }


    public void Upload() {
        Utils.NoNet(context);
        showProgressDialog();
        AdCommitReq req = new AdCommitReq();
        req.code = "30022";
        req.content_eval = KaKuApplication.pingjia_ad;
        req.id_advert = KaKuApplication.id_advert;
        req.image_eval = key1;
        req.image_eval1 = key2;
        req.image_eval2 = key3;
        req.image_eval3 = key4;
        KaKuApiProvider.commitAd(req, new KakuResponseListener<ShopCommitResp>(this, ShopCommitResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("uploadimage res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        Intent intent = new Intent(PingJiaAdActivity.this, AdPingJiaListActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;
        private Bitmap holderBmp;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            if (Bimp.tempSelectBitmap.size() == 4) {
                return 4;
            }
            return (Bimp.tempSelectBitmap.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.tempSelectBitmap.size()) {
                if (holderBmp == null) {
                    holderBmp = BitmapFactory.decodeResource(
                            getResources(), R.drawable.btn_fzp);
                }
                holder.image.setImageBitmap(holderBmp);
                if (position == 4) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
            }
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        gvAdapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.tempSelectBitmap.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            Bimp.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }
    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    protected void onRestart() {
        gvAdapter.update();
        super.onRestart();
    }

    private static final int TAKE_PICTURE = 0x000001;

    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < 4 && resultCode == RESULT_OK) {
                    LogUtil.E("SizeBe" + Bimp.tempSelectBitmap.size());
                    String fileName = String.valueOf(System.currentTimeMillis());
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    FileUtils.saveBitmap(bm, fileName);

                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    takePhoto.setImagePath(FileUtils.SDPATH + fileName + ".JPEG");
                    Bimp.tempSelectBitmap.add(takePhoto);
                    LogUtil.E("takePhoto" + takePhoto.toString());
                    LogUtil.E("SizeAf" + Bimp.tempSelectBitmap.size());
                    LogUtil.E("SizeAfffff" + Bimp.tempSelectBitmap.get(0).toString());
                }
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            for (int i = 0; i < PublicWay.activityList.size(); i++) {
                if (null != PublicWay.activityList.get(i)) {
                    PublicWay.activityList.get(i).finish();
                }
            }
            //System.exit(0);
            this.finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        //页面关闭时清空评论内容
        KaKuApplication.pingjia_ad = "";
        //页面关闭时清空图片列表
        Bimp.tempSelectBitmap.clear();
        Bimp.max = 0;
        super.onDestroy();

    }
}
