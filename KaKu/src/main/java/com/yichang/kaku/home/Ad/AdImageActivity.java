package com.yichang.kaku.home.Ad;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.yichang.kaku.request.GetAddReq;
import com.yichang.kaku.request.QiNiuYunTokenReq;
import com.yichang.kaku.request.UploadCheTieImageReq;
import com.yichang.kaku.request.UploadImageReq;
import com.yichang.kaku.request.XiaDanReq;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.response.GetAddResp;
import com.yichang.kaku.response.QiNiuYunTokenResp;
import com.yichang.kaku.response.UploadImageResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import org.json.JSONObject;

import java.io.File;

public class AdImageActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private ImageView iv_adimage_1;
    private Button btn_adimage_tijiao;
    private final int CAMERA_WITH_DATA = 2;//拍照
    private final int CROP_RESULT_CODE = 3;//结果
    private Bitmap photo1;
    public static final String TMP_PATH1 = "clip_temp1.jpg";
    private KaKuApplication application;
    public String token1;
    public String key1;
    public String path1;
    private ImageView iv_adimage_wuchetie, iv_adimage_heart;
    private LinearLayout line_adimage_huodechetie;
    private RelativeLayout rela_adimage_1, rela_adimage_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adimage);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("上传图片");
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("联系客服");
        right.setOnClickListener(this);
        iv_adimage_wuchetie = (ImageView) findViewById(R.id.iv_adimage_wuchetie);
        iv_adimage_wuchetie.setOnClickListener(this);
        iv_adimage_heart = (ImageView) findViewById(R.id.iv_adimage_heart);
        iv_adimage_heart.setOnClickListener(this);

        iv_adimage_1 = (ImageView) findViewById(R.id.iv_adimage_1);
        iv_adimage_1.setOnClickListener(this);
        rela_adimage_1 = (RelativeLayout) findViewById(R.id.rela_adimage_1);
        rela_adimage_1.setOnClickListener(this);
        rela_adimage_2 = (RelativeLayout) findViewById(R.id.rela_adimage_2);
        rela_adimage_2.setOnClickListener(this);
        line_adimage_huodechetie = (LinearLayout) findViewById(R.id.line_adimage_huodechetie);
        line_adimage_huodechetie.setVisibility(View.GONE);
        btn_adimage_tijiao = (Button) findViewById(R.id.btn_adimage_tijiao);
        btn_adimage_tijiao.setOnClickListener(this);
        btn_adimage_tijiao.setEnabled(true);

        application = (KaKuApplication) getApplication();

        LogUtil.E("flag_nochetietv:" + KaKuApplication.flag_nochetietv);
        LogUtil.E("flag_heart:" + KaKuApplication.flag_heart);
        LogUtil.E("flag_position:" + KaKuApplication.flag_position);
        if ("you".equals(KaKuApplication.flag_nochetietv)) {
            iv_adimage_wuchetie.setVisibility(View.VISIBLE);
        } else {
            iv_adimage_wuchetie.setVisibility(View.GONE);
        }
        if ("Y".equals(KaKuApplication.flag_heart)) {
            iv_adimage_heart.setVisibility(View.VISIBLE);
        } else {
            iv_adimage_heart.setVisibility(View.GONE);
        }
        if ("L".equals(KaKuApplication.flag_position)){
            iv_adimage_1.setImageResource(R.drawable.xiezuoce);
        } else {
            iv_adimage_1.setImageResource(R.drawable.xieyouce);
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
            GetAdd();
        } else if (R.id.tv_right == id) {
            Utils.Call(AdImageActivity.this, "400-6867585");
        } else if (R.id.iv_adimage_1 == id) {
            KaKuApplication.flag_image = "chetie";
            startCapture();
        } else if (R.id.btn_adimage_tijiao == id) {
            if ("".equals(KaKuApplication.ImageZhong) || KaKuApplication.ImageZhong == null) {
                LogUtil.showShortToast(context, "必须拍摄照片才可提交");
                return;
            }
            //application.startTrace(context,Utils.getPhone());
            QiNiuYunToken();
        } else if (R.id.iv_adimage_wuchetie == id) {
            line_adimage_huodechetie.setVisibility(View.VISIBLE);
            iv_adimage_wuchetie.setVisibility(View.GONE);
        } else if (R.id.rela_adimage_1 == id) {
            Intent intent = new Intent(this, XingShiZhengImageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            line_adimage_huodechetie.setVisibility(View.GONE);
            iv_adimage_wuchetie.setVisibility(View.GONE);
            LogUtil.showShortToast(this, "找现场工作人员领取车贴,需拍摄行驶证审核");
        } else if (R.id.rela_adimage_2 == id) {
            XiaDan();
            line_adimage_huodechetie.setVisibility(View.GONE);
            iv_adimage_wuchetie.setVisibility(View.GONE);
        } else if (R.id.iv_adimage_heart == id) {
            Intent intent = new Intent(this, JiaShiZhengActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    /**
     * 通过照相获得图片
     *
     * @param
     */
    private void startCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if ("chetie".equals(KaKuApplication.flag_image)) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    Environment.getExternalStorageDirectory(), TMP_PATH1)));
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
                if ("chetie".equals(KaKuApplication.flag_image)) {
                    path1 = data.getStringExtra(ClipImageActivity.RESULT_PATH);
                    photo1 = BitmapFactory.decodeFile(path1);
                    KaKuApplication.ImageZhong = photo1;
                    iv_adimage_1.setImageBitmap(photo1);
                }
                if (KaKuApplication.ImageZhong != null) {
                    btn_adimage_tijiao.setBackgroundResource(R.drawable.btn_orange);
                }
                break;

            case CAMERA_WITH_DATA:
                if ("chetie".equals(KaKuApplication.flag_image)) {
                    startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + TMP_PATH1);
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
        KaKuApplication.ImageZhong = null;
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
                                    if ("60020".equals(KaKuApplication.flag_code)) {
                                        Upload2();
                                    } else {
                                        Upload();
                                    }
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
        req.image0_advert = key1;
        req.image_license = "";
        req.var_lat = Utils.getLat();
        req.var_lon = Utils.getLon();
        KaKuApiProvider.uploadImage(req, new KakuResponseListener<UploadImageResp>(this, UploadImageResp.class) {
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

    private void goToHome() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME);
        startActivity(intent);
        finish();
    }

    public void XiaDan() {
        showProgressDialog();
        XiaDanReq req = new XiaDanReq();
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.XiaDan(req, new KakuResponseListener<ExitResp>(this, ExitResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("xiadan res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.flag_recommended = "";
                        AlertDialog.Builder builder = new AlertDialog.Builder(AdImageActivity.this);
                        builder.setTitle("提示");
                        builder.setMessage("我们已通知您的邀请人了，请耐心等待");
                        builder.setNegativeButton("联系邀请人", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                Utils.Call(AdImageActivity.this, KaKuApplication.phone_driver);
                            }
                        });

                        builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                goToHome();
                            }
                        });
                        builder.create().show();
                    } else {
                        LogUtil.showLongToast(context, t.msg);
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
                        KaKuApplication.id_advert = t.advert.getId_advert();
                        KaKuApplication.flag_recommended = t.advert.getFlag_recommended();
                        KaKuApplication.flag_jiashinum = t.advert.getNum_privilege();
                        KaKuApplication.flag_position = t.advert.getFlag_position();
                        KaKuApplication.flag_heart = t.advert.getFlag_show();
                        GoToAdd(t.advert.getFlag_type());
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
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            GetAdd();
        }
        return false;
    }

    public void Upload2() {
        Utils.NoNet(context);
        showProgressDialog();
        UploadCheTieImageReq req = new UploadCheTieImageReq();
        req.code = "60020";
        req.flag_recommended = KaKuApplication.flag_recommended;
        req.image0_advert = key1;
        req.image_license = "";
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

}
