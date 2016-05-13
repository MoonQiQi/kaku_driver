package com.yichang.kaku.home.ad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.zero.ZeroActivity;
import com.yichang.kaku.home.zero.ZeroingActivity;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.request.QiNiuYunTokenReq;
import com.yichang.kaku.request.QiangImageReq;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.response.QiNiuYunTokenResp;
import com.yichang.kaku.response.ZeroResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import java.io.File;

public class QiangImageActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private ImageView iv_qiangimage_1, iv_qiangimage_2;
    private int anInt;
    private Button btn_qiangimage_tijiao;
    private final int CAMERA_WITH_DATA = 2;//拍照
    private final int CROP_RESULT_CODE = 3;//结果
    public static final String TMP_PATH4 = "clip_temp4.jpg";
    public static final String TMP_PATH5 = "clip_temp5.jpg";
    public String token1, token2;
    public String key1, key2;
    public String path1, path2;
    private Bitmap photo1, photo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiangimage);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("0元购车辆认证");

        iv_qiangimage_1 = (ImageView) findViewById(R.id.iv_qiangimage_1);
        iv_qiangimage_1.setOnClickListener(this);
        iv_qiangimage_2 = (ImageView) findViewById(R.id.iv_qiangimage_2);
        iv_qiangimage_2.setOnClickListener(this);
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        anInt = (outMetrics.widthPixels - 30) / 2;
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(anInt, anInt);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(anInt, anInt);
        params1.setMargins(10, 0, 5, 0);
        params2.setMargins(5, 0, 10, 0);
        iv_qiangimage_1.setLayoutParams(params1);
        iv_qiangimage_2.setLayoutParams(params2);
        btn_qiangimage_tijiao = (Button) findViewById(R.id.btn_qiangimage_tijiao);
        btn_qiangimage_tijiao.setOnClickListener(this);

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
        } else if (R.id.iv_qiangimage_1 == id || R.id.iv_qiangimage_2 == id) {
            if (R.id.iv_qiangimage_1 == id) {
                KaKuApplication.flag_image = "ben";
            } else if (R.id.iv_qiangimage_2 == id) {
                KaKuApplication.flag_image = "che";
            }
            startCapture();
        } else if (R.id.btn_qiangimage_tijiao == id) {
            if ("".equals(KaKuApplication.ImageBen) || KaKuApplication.ImageChe == null ||
                    "".equals(KaKuApplication.ImageBen) || KaKuApplication.ImageChe == null) {
                LogUtil.showShortToast(context, "必须上传2张图片才可提交");
                return;
            }
            QiNiuYunToken("1");
            QiNiuYunToken("2");
        }
    }

    /**
     * 通过照相获得图片
     *
     * @param
     */
    private void startCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if ("ben".equals(KaKuApplication.flag_image)) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    Environment.getExternalStorageDirectory(), TMP_PATH4)));
        } else if ("che".equals(KaKuApplication.flag_image)) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    Environment.getExternalStorageDirectory(), TMP_PATH5)));
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
                if ("ben".equals(KaKuApplication.flag_image)) {
                    path1 = data.getStringExtra(ClipImageActivity.RESULT_PATH);
                    photo1 = BitmapFactory.decodeFile(path1);
                    KaKuApplication.ImageBen = photo1;
                    iv_qiangimage_1.setImageBitmap(photo1);
                } else if ("che".equals(KaKuApplication.flag_image)) {
                    path2 = data.getStringExtra(ClipImageActivity.RESULT_PATH);
                    photo2 = BitmapFactory.decodeFile(path2);
                    KaKuApplication.ImageChe = photo2;
                    iv_qiangimage_2.setImageBitmap(photo2);
                }
                break;

            case CAMERA_WITH_DATA:
                if ("ben".equals(KaKuApplication.flag_image)) {
                    startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + TMP_PATH4);
                } else if ("che".equals(KaKuApplication.flag_image)) {
                    startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + TMP_PATH5);
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
        KaKuApplication.ImageBen = null;
        KaKuApplication.ImageChe = null;
    }

    public void QiNiuYunToken(final String sort) {
        Utils.NoNet(context);
        QiNiuYunTokenReq req = new QiNiuYunTokenReq();
        req.code = "qn01";
        req.sort = sort;
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
                    file = path1;
                } else if ("2".equals(sort)) {
                    file = path2;
                }

                UploadManager uploadManager = new UploadManager();
                uploadManager.put(file, key, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String arg0, ResponseInfo info, JSONObject response) {
                                // TODO Auto-generated method stub
                                if (info.isOK()) {
                                    IsThree();
                                }
                            }
                        }, null);
            }
        }).start();
    }

    private int num = 0;

    public void IsThree() {
        num++;
        if (num == 2) {
            Upload();
        }
    }

    public void Upload() {
        Utils.NoNet(context);
        showProgressDialog();
        QiangImageReq req = new QiangImageReq();
        req.code = "80011";
        req.image_license = key1;
        req.image_car = key2;
        KaKuApiProvider.QiangImage(req, new KakuResponseListener<ExitResp>(this, ExitResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("zerocommit res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        Zero();
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

    public void Zero() {
        showProgressDialog();
        ExitReq req = new ExitReq();
        req.code = "80010";
        KaKuApiProvider.zero(req, new KakuResponseListener<ZeroResp>(context, ZeroResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("zero res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("t", t);
                        intent.putExtras(bundle);
                        if ("N".equals(t.flag_audit)) {
                            intent.setClass(context, ZeroActivity.class);
                        } else {
                            intent.setClass(context, ZeroingActivity.class);
                        }
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
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

}
