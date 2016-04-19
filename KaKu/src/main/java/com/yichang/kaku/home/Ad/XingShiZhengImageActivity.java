package com.yichang.kaku.home.Ad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
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
import com.yichang.kaku.request.GetAddReq;
import com.yichang.kaku.request.QiNiuYunTokenReq;
import com.yichang.kaku.request.UploadCheTieImageReq;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.response.GetAddResp;
import com.yichang.kaku.response.QiNiuYunTokenResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import org.json.JSONObject;

import java.io.File;

public class XingShiZhengImageActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private ImageView iv_xingshizhengimage;
    private Button btn_xingshizhengimage_tijiao;
    private final int CAMERA_WITH_DATA = 2;//拍照
    private final int CROP_RESULT_CODE = 3;//结果
    public static final String TMP_PATH7 = "clip_temp7.jpg";
    public String token1, token2;
    public String key1, key2;
    public String path1, path2;
    private Bitmap photo1, photo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xingshizhengimage);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("核验车辆信息");

        iv_xingshizhengimage = (ImageView) findViewById(R.id.iv_xingshizhengimage);
        iv_xingshizhengimage.setOnClickListener(this);
        btn_xingshizhengimage_tijiao = (Button) findViewById(R.id.btn_xingshizhengimage_tijiao);
        btn_xingshizhengimage_tijiao.setOnClickListener(this);
        btn_xingshizhengimage_tijiao.setEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        GetAdd2();

    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            GetAdd();
        } else if (R.id.iv_xingshizhengimage == id) {
            KaKuApplication.flag_image = "xingshizheng";
            startCapture();
        } else if (R.id.btn_xingshizhengimage_tijiao == id) {
            if ("".equals(KaKuApplication.ImageXingShiZheng) || KaKuApplication.ImageXingShiZheng == null) {
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
        if ("xingshizheng".equals(KaKuApplication.flag_image)) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    Environment.getExternalStorageDirectory(), TMP_PATH7)));
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
                if ("xingshizheng".equals(KaKuApplication.flag_image)) {
                    path1 = data.getStringExtra(ClipImageActivity.RESULT_PATH);
                    photo1 = BitmapFactory.decodeFile(path1);
                    KaKuApplication.ImageXingShiZheng = photo1;
                    iv_xingshizhengimage.setImageBitmap(photo1);
                }
                break;

            case CAMERA_WITH_DATA:
                if ("xingshizheng".equals(KaKuApplication.flag_image)) {
                    startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + TMP_PATH7);
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
        KaKuApplication.ImageXingShiZheng = null;
    }

    public void QiNiuYunToken() {
        Utils.NoNet(context);
        btn_xingshizhengimage_tijiao.setEnabled(false);
        showProgressDialog();
        QiNiuYunTokenReq req = new QiNiuYunTokenReq();
        req.code = "qn01";
        req.sort = "1";
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.QiNiuYunToken(req, new KakuResponseListener<QiNiuYunTokenResp>(this, QiNiuYunTokenResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    btn_xingshizhengimage_tijiao.setEnabled(true);
                    LogUtil.E("qiniuyuntoken res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        token1 = t.token;
                        key1 = t.key;
                        uploadImg(token1, key1);

                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
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

        UploadCheTieImageReq req = new UploadCheTieImageReq();
        req.code = "60020";
        req.flag_recommended = KaKuApplication.flag_recommended;
        req.image0_advert = "";
        req.image1_advert = "";
        req.image2_advert = "";
        req.image_license = key1;
        req.var_lat = Utils.getLat();
        req.var_lon = Utils.getLon();
        req.id_advert = KaKuApplication.id_advert;
        KaKuApiProvider.uploadCheTieImage(req, new KakuResponseListener<ExitResp>(this, ExitResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("uploadimage res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        GetAdd();
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

        });
    }

    public void GetAdd() {
        showProgressDialog();
        GetAddReq req = new GetAddReq();
        req.code = "60011";
        req.id_driver = Utils.getIdDriver();
        req.id_advert = KaKuApplication.id_advert;
        KaKuApiProvider.GetAdd(req, new KakuResponseListener<GetAddResp>(this, GetAddResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getadd res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        GoToAdd(t.advert.getFlag_type());
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

        });
    }

    public void GetAdd2() {
        showProgressDialog();
        GetAddReq req = new GetAddReq();
        req.code = "60011";
        req.id_driver = Utils.getIdDriver();
        req.id_advert = KaKuApplication.id_advert;
        KaKuApiProvider.GetAdd(req, new KakuResponseListener<GetAddResp>(this, GetAddResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getadd res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.id_advert = t.advert.getId_advert();
                        KaKuApplication.flag_recommended = t.advert.getFlag_recommended();
                        KaKuApplication.flag_jiashinum = t.advert.getNum_privilege();
                        KaKuApplication.flag_position = t.advert.getFlag_position();
                        KaKuApplication.flag_heart = t.advert.getFlag_show();
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

        });
    }

    public void GoToAdd(String flag_type) {
        Intent intent = new Intent();
        if ("N".equals(flag_type)) {
            intent.setClass(context, Add_NActivity.class);
        } else if ("Y".equals(flag_type)) {
            intent.setClass(context, Add_YActivity.class);
        } else if ("E".equals(flag_type)) {
            intent.setClass(context, Add_EActivity.class);
        } else if ("I".equals(flag_type)) {
            intent.setClass(context, Add_IActivity.class);
        } else if ("F".equals(flag_type)) {
            intent.setClass(context, Add_FActivity.class);
        } else if ("P".equals(flag_type)) {
            intent.setClass(context, Add_PActivity.class);
        } else if ("A".equals(flag_type)) {
            intent.setClass(context, CheTieListActivity.class);
        } else if ("M".equals(flag_type)) {
            intent.setClass(context, Add_MActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            GetAdd();
        }
        return false;
    }

}
