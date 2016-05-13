package com.yichang.kaku.home.ad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.request.QiNiuYunTokenReq;
import com.yichang.kaku.request.UploadImageReq;
import com.yichang.kaku.response.QiNiuYunTokenResp;
import com.yichang.kaku.response.UploadImageResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import java.io.File;

public class JiaShiZhengActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private TextView tv_xignshizheng_top;
    private ImageView iv_adimage_jiashizheng;
    private Button btn_adimage_tijiao;
    private final int CAMERA_WITH_DATA = 2;//拍照
    private final int CROP_RESULT_CODE = 3;//结果
    private Bitmap photo1;
    public static final String TMP_PATH8 = "clip_temp8.jpg";
    public String token1;
    public String key1;
    public String path1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjiashizheng);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("驾驶证拍摄");
        tv_xignshizheng_top = (TextView) findViewById(R.id.tv_xignshizheng_top);
        iv_adimage_jiashizheng = (ImageView) findViewById(R.id.iv_adimage_jiashizheng);
        iv_adimage_jiashizheng.setOnClickListener(this);
        btn_adimage_tijiao = (Button) findViewById(R.id.btn_adimage_tijiao);
        btn_adimage_tijiao.setOnClickListener(this);
        tv_xignshizheng_top.setText("您有 " + KaKuApplication.flag_jiashinum + " 次拍驾驶证赚收益的特权哦");
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
        } else if (R.id.iv_adimage_jiashizheng == id) {
            KaKuApplication.flag_image = "jiashizheng";
            startCapture();
        } else if (R.id.btn_adimage_tijiao == id) {
            if ("".equals(KaKuApplication.ImageJiaShiZheng) || KaKuApplication.ImageJiaShiZheng == null) {
                LogUtil.showShortToast(context, "必须拍摄照片才可提交");
                return;
            }
            QiNiuYunToken();
        }
    }

    /**
     * 通过照相获得图片
     *
     * @param
     */
    private void startCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if ("jiashizheng".equals(KaKuApplication.flag_image)) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    Environment.getExternalStorageDirectory(), TMP_PATH8)));
        }

        startActivityForResult(intent, CAMERA_WITH_DATA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CROP_RESULT_CODE:
                if ("jiashizheng".equals(KaKuApplication.flag_image)) {
                    path1 = data.getStringExtra(ClipImageActivity.RESULT_PATH);
                    photo1 = BitmapFactory.decodeFile(path1);
                    KaKuApplication.ImageJiaShiZheng = photo1;
                    iv_adimage_jiashizheng.setImageBitmap(photo1);
                }

                break;

            case CAMERA_WITH_DATA:
                if ("jiashizheng".equals(KaKuApplication.flag_image)) {
                    startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + TMP_PATH8);
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // 裁剪图片的Activity
    private void startCropImageActivity(String path) {
        ClipImageActivity.startActivity(this, path, CROP_RESULT_CODE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KaKuApplication.ImageJiaShiZheng = null;
    }

    public void QiNiuYunToken() {
        Utils.NoNet(context);
        btn_adimage_tijiao.setEnabled(false);
        QiNiuYunTokenReq req = new QiNiuYunTokenReq();
        req.code = "qn01";
        req.sort = "1";
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.QiNiuYunToken(req, new KakuResponseListener<QiNiuYunTokenResp>(this, QiNiuYunTokenResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("qiniuyuntoken res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        token1 = t.token;
                        key1 = t.key;
                        uploadImg(token1, key1);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                    btn_adimage_tijiao.setEnabled(true);
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }


        });
    }

    private void uploadImg(final String token, final String key) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String file = path1;
                UploadManager uploadManager = new UploadManager();
                uploadManager.put(file, key, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String arg0, ResponseInfo info, JSONObject response) {
                                // TODO Auto-generated method stub
                                if (info.isOK()) {
                                    Upload();
                                }
                            }
                        }, null);
            }
        }).start();
    }

    public void Upload() {
        Utils.NoNet(context);
        showProgressDialog();
        UploadImageReq req = new UploadImageReq();
        req.code = "60018";
        req.id_advert = KaKuApplication.id_advert;
        req.id_driver = Utils.getIdDriver();
        req.image0_advert = "";
        req.image_license = key1;
        req.var_lat = Utils.getLat();
        req.var_lon = Utils.getLon();
        KaKuApiProvider.uploadImage(req, new KakuResponseListener<UploadImageResp>(this, UploadImageResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("uploadimage res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.flag_get = t.flag_get;
                        KaKuApplication.money_coupon = t.money_coupon;
                        startActivity(new Intent(context, ImageFanKuiActivity.class));
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

    public void GetAdd() {
        Utils.GetAdType(baseActivity);
    }

}
