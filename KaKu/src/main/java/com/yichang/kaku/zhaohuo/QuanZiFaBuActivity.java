package com.yichang.kaku.zhaohuo;

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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
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
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.request.QiNiuYunTokenReq;
import com.yichang.kaku.request.QuanziSubmitReq;
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
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

public class QuanZiFaBuActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private GridView gv_fabuquanzi;
    private EditText et_quanzifabu_title, et_quanzifabu_content;
    private GridAdapter gvAdapter;
    public static Bitmap bimap;
    private PopupWindow window;
    private TextView tv_takephoto;
    private TextView tv_myphoto;
    private TextView tv_exitphoto;
    public String token1, token2, token3;
    public String key1, key2, key3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanzifabu);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (window.isShowing()) {
            window.dismiss();
        }
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("发布话题");
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("发布");
        right.setOnClickListener(this);
        et_quanzifabu_title = (EditText) findViewById(R.id.et_quanzifabu_title);
        et_quanzifabu_content = (EditText) findViewById(R.id.et_quanzifabu_content);
        gv_fabuquanzi = (GridView) findViewById(R.id.gv_fabuquanzi);
        gv_fabuquanzi.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gvAdapter = new GridAdapter(this);
        gvAdapter.update();
        gv_fabuquanzi.setAdapter(gvAdapter);
        gv_fabuquanzi.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parentView, View view, int arg2,
                                    long arg3) {
                if (Utils.Many()) {
                    return;
                }
                if (arg2 == Bimp.tempSelectBitmap.size()) {
                    ShowWindow(view);
                    window.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                } else {
                    Intent intent = new Intent(context, GalleryActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });
        window = new PopupWindow();
        window = new PopupWindow();

        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.icon_addpic_unfocused);
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
        } else if (R.id.tv_right == id) {
            KaKuApplication.quanzi_title = et_quanzifabu_title.getText().toString().trim();
            KaKuApplication.quanzi_content = et_quanzifabu_content.getText().toString().trim();
            if (TextUtils.isEmpty(KaKuApplication.quanzi_title)) {
                LogUtil.showShortToast(context, "圈子标题不能为空");
                return;
            }
            if (TextUtils.isEmpty(KaKuApplication.quanzi_content)) {
                LogUtil.showShortToast(context, "圈子内容不能为空");
                return;
            }
            Commit();
        } else if (R.id.tv_takephoto == id) {
            photo();
            window.dismiss();
        } else if (R.id.tv_myphoto == id) {
            KaKuApplication.PingJiaShopOrAd = "Quanzi";
            Intent intent = new Intent(context, AlbumActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
            window.dismiss();

        } else if (R.id.tv_exitphoto == id) {
            window.dismiss();

        }
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

    private void uploadImg(final String token, final String key, final String sort) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String file = "";

                if ("1".equals(sort)) {
                    file = Bimp.tempSelectBitmap.get(0).getImagePath();
                } else if ("2".equals(sort)) {
                    file = Bimp.tempSelectBitmap.get(1).getImagePath();
                } else if ("3".equals(sort)) {
                    file = Bimp.tempSelectBitmap.get(2).getImagePath();
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

    private int num = 0;

    public void IsSize() {
        num++;
        if (num == Bimp.tempSelectBitmap.size()) {
            Upload();
        }
    }


    public void Upload() {
        showProgressDialog();
        QuanziSubmitReq req = new QuanziSubmitReq();
        req.code = "70013";
        req.name_circle = KaKuApplication.quanzi_title;
        req.content = KaKuApplication.quanzi_content;
        req.image_circle1 = key1;
        req.image_circle2 = key2;
        req.image_circle3 = key3;
        KaKuApiProvider.QuanziSubmit(req, new KakuResponseListener<ShopCommitResp>(this, ShopCommitResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("uploadimage res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME4);
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

    public void ShowWindow(View v) {
        KaKuApplication.quanzi_title = et_quanzifabu_title.getText().toString().trim();
        KaKuApplication.quanzi_content = et_quanzifabu_content.getText().toString().trim();
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

    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String hint = (String) msg.obj;
            if (!TextUtils.isEmpty(hint)) {
                LogUtil.showShortToast(getApplicationContext(),
                        msg.obj.toString());
            }
            super.handleMessage(msg);
        }
    };

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
            if (Bimp.tempSelectBitmap.size() == 3) {
                return 3;
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
                            getResources(), R.drawable.icon_addpic_unfocused);
                }
                holder.image.setImageBitmap(holderBmp);
                if (position == 3) {
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
                if (Bimp.tempSelectBitmap.size() < 3 && resultCode == RESULT_OK) {

                    String fileName = String.valueOf(System.currentTimeMillis());
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    FileUtils.saveBitmap(bm, fileName);

                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    takePhoto.setImagePath(FileUtils.SDPATH + fileName + ".JPEG");
                    Bimp.tempSelectBitmap.add(takePhoto);
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
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME4);
            startActivity(intent);
            finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        //页面关闭时清空评论内容
        KaKuApplication.pingjiaOrderContent = "";
        //页面关闭时清空图片列表
        Bimp.tempSelectBitmap.clear();
        Bimp.max = 0;
        super.onDestroy();

    }

}
