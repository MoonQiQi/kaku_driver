package com.yichang.kaku.home.Ad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
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
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.request.QiNiuYunTokenReq;
import com.yichang.kaku.request.UploadImageReq;
import com.yichang.kaku.response.QiNiuYunTokenResp;
import com.yichang.kaku.response.UploadImageResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ReImageActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private TextView tv_adimage_yuanyin;
    private ImageView iv_adimage_1, iv_adimage_2, iv_adimage_3, iv_adimage_12, iv_adimage_22, iv_adimage_32;
    private Button btn_adimage_tijiao;
    // 创建一个以当前时间为名称的文件
    private File dataFile;
    private File photoFile;
    private boolean isTAKEPHOTO = false;
    private final int CAMERA_WITH_DATA = 2;//拍照
    private final int CROP_RESULT_CODE = 3;//结果
    private Bitmap photo1, photo2, photo3;
    private Map<Integer, Bitmap> bmpMap = new HashMap<>();
    private String imagePath;
    private int mAdapterIndex;
    private TextView tv_takephoto, tv_myphoto, tv_exitphoto;
    private int anInt;
    private String image0_advert;
    private String image1_advert;
    private String image2_advert;
    private String image0_approve;
    private String image1_approve;
    private String image2_approve;
    private Bundle bundle;
    private RelativeLayout rela_image1, rela_image2, rela_image3;
    public static final String TMP_PATH1 = "clip_temp4.jpg";
    public static final String TMP_PATH2 = "clip_temp5.jpg";
    public static final String TMP_PATH3 = "clip_temp6.jpg";
    private KaKuApplication application;
    public String token1,token2,token3;
    public String key1 = "",key2 = "",key3 = "";
    public String path1,path2,path3;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readimage);
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
        tv_adimage_yuanyin = (TextView) findViewById(R.id.tv_adimage_yuanyin);
        String string = "原因：" + KaKuApplication.reason_upload;
        SpannableStringBuilder style = new SpannableStringBuilder(string);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_red)), 3, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_adimage_yuanyin.setText(style);
        tv_adimage_yuanyin.setVisibility(View.VISIBLE);
        iv_adimage_1 = (ImageView) findViewById(R.id.iv_adimage_11);
        iv_adimage_12 = (ImageView) findViewById(R.id.iv_adimage_12);
        iv_adimage_1.setOnClickListener(this);
        iv_adimage_2 = (ImageView) findViewById(R.id.iv_adimage_21);
        iv_adimage_22 = (ImageView) findViewById(R.id.iv_adimage_22);
        iv_adimage_2.setOnClickListener(this);
        iv_adimage_3 = (ImageView) findViewById(R.id.iv_adimage_31);
        iv_adimage_32 = (ImageView) findViewById(R.id.iv_adimage_32);
        iv_adimage_3.setOnClickListener(this);
        rela_image1 = (RelativeLayout) findViewById(R.id.rela_image1);
        rela_image2 = (RelativeLayout) findViewById(R.id.rela_image2);
        rela_image3 = (RelativeLayout) findViewById(R.id.rela_image3);
        rela_image1.setOnClickListener(this);
        rela_image2.setOnClickListener(this);
        rela_image3.setOnClickListener(this);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        image0_advert = bundle.getString("image0_advert");
        image1_advert = bundle.getString("image1_advert");
        image2_advert = bundle.getString("image2_advert");
        image0_approve = bundle.getString("image0_approve");
        image1_approve = bundle.getString("image1_approve");
        image2_approve = bundle.getString("image2_approve");

        BitmapUtil.getInstance(context).download(iv_adimage_1, KaKuApplication.qian_zhuikong + image0_advert);
        BitmapUtil.getInstance(context).download(iv_adimage_2, KaKuApplication.qian_zhuikong + image1_advert);
        BitmapUtil.getInstance(context).download(iv_adimage_3, KaKuApplication.qian_zhuikong + image2_advert);

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        anInt = (outMetrics.widthPixels - 40) / 3;
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(anInt, anInt);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(anInt, anInt);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(anInt, anInt);
        params1.setMargins(10,10,5,0);
        params2.setMargins(5,10,5,0);
        params3.setMargins(5,10,10,0);
        rela_image1.setLayoutParams(params1);
        rela_image2.setLayoutParams(params2);
        rela_image3.setLayoutParams(params3);
        btn_adimage_tijiao = (Button) findViewById(R.id.btn_adimage_tijiao);
        btn_adimage_tijiao.setOnClickListener(this);

        if (TextUtils.equals(image0_approve, "N")) {
            iv_adimage_1.setEnabled(false);
            iv_adimage_12.setBackgroundResource(R.drawable.buchongshangchuan);
        } else {
            count++;
            iv_adimage_1.setEnabled(true);
            iv_adimage_12.setBackgroundResource(R.drawable.chongxinshangchuan);
        }
        if (TextUtils.equals(image1_approve, "N")) {
            iv_adimage_2.setEnabled(false);
            iv_adimage_22.setBackgroundResource(R.drawable.buchongshangchuan);
        } else {
            count++;
            iv_adimage_2.setEnabled(true);
            iv_adimage_22.setBackgroundResource(R.drawable.chongxinshangchuan);
        }
        if (TextUtils.equals(image2_approve, "N")) {
            iv_adimage_3.setEnabled(false);
            iv_adimage_32.setBackgroundResource(R.drawable.buchongshangchuan);
        } else {
            count++;
            iv_adimage_3.setEnabled(true);
            iv_adimage_32.setBackgroundResource(R.drawable.chongxinshangchuan);
        }
        application = (KaKuApplication) getApplication();
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
        } else if (R.id.tv_right == id) {
            Utils.Call(context, "400-6867585");
        } else if (R.id.iv_adimage_11 == id || R.id.iv_adimage_21 == id  || R.id.iv_adimage_31 == id) {
            if (R.id.iv_adimage_11 == id) {
                KaKuApplication.flag_image = "zhong";
            } else if (R.id.iv_adimage_21 == id) {
                KaKuApplication.flag_image = "zuo";
            } else if (R.id.iv_adimage_31 == id) {
                KaKuApplication.flag_image = "you";
            }
            startCapture();
        } else if (R.id.btn_adimage_tijiao == id) {
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                if (TextUtils.equals(image0_approve, "Y")) {
                    if ("".equals(KaKuApplication.ImageZhong) || KaKuApplication.ImageZhong == null) {
                        LogUtil.showShortToast(context, "必须上传车头照才可提交");
                        return;
                    }
                }
                if (TextUtils.equals(image1_approve, "Y")) {
                    if ("".equals(KaKuApplication.ImageZuo) || KaKuApplication.ImageZuo == null) {
                        LogUtil.showShortToast(context, "必须上传左侧照才可提交");
                        return;
                    }
                }
                if (TextUtils.equals(image2_approve, "Y")) {
                    if ("".equals(KaKuApplication.ImageYou) || KaKuApplication.ImageYou == null) {
                        LogUtil.showShortToast(context, "必须上传右侧照才可提交");
                        return;
                    }
                }
            application.startTrace(context,Utils.getPhone());
            if (TextUtils.equals(image0_approve, "Y")) {
                QiNiuYunToken("1");
            }
            if (TextUtils.equals(image1_approve, "Y")) {
                QiNiuYunToken("2");
            }
            if (TextUtils.equals(image2_approve, "Y")) {
                QiNiuYunToken("3");
            }
        }
    }

    /**
     * 通过照相获得图片
     *
     * @param
     */
    private void startCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if ("zhong".equals(KaKuApplication.flag_image)) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    Environment.getExternalStorageDirectory(), TMP_PATH1)));
        } else if ("zuo".equals(KaKuApplication.flag_image)) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    Environment.getExternalStorageDirectory(), TMP_PATH2)));
        } else if ("you".equals(KaKuApplication.flag_image)) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    Environment.getExternalStorageDirectory(), TMP_PATH3)));
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
                if ("zhong".equals(KaKuApplication.flag_image)) {
                    path1 = data.getStringExtra(ClipImageActivity.RESULT_PATH);
                    photo1 = BitmapFactory.decodeFile(path1);
                    KaKuApplication.ImageZhong = photo1;
                    iv_adimage_12.setBackgroundResource(0);
                    iv_adimage_1.setImageBitmap(photo1);
                } else if ("zuo".equals(KaKuApplication.flag_image)) {
                    path2 = data.getStringExtra(ClipImageActivity.RESULT_PATH);
                    photo2 = BitmapFactory.decodeFile(path2);
                    KaKuApplication.ImageZuo = photo2;
                    iv_adimage_22.setBackgroundResource(0);
                    iv_adimage_2.setImageBitmap(photo2);
                } else if ("you".equals(KaKuApplication.flag_image)) {
                    path3 = data.getStringExtra(ClipImageActivity.RESULT_PATH);
                    photo3 = BitmapFactory.decodeFile(path3);
                    KaKuApplication.ImageYou = photo3;
                    iv_adimage_32.setBackgroundResource(0);
                    iv_adimage_3.setImageBitmap(photo3);
                }
                break;

            case CAMERA_WITH_DATA:
                if ("zhong".equals(KaKuApplication.flag_image)) {
                    startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + TMP_PATH1);
                } else if ("zuo".equals(KaKuApplication.flag_image)) {
                    startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + TMP_PATH2);
                } else if ("you".equals(KaKuApplication.flag_image)) {
                    startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + TMP_PATH3);
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
        KaKuApplication.ImageZuo = null;
        KaKuApplication.ImageYou = null;
    }

    public void QiNiuYunToken(final String sort) {
        showProgressDialog();
        QiNiuYunTokenReq req = new QiNiuYunTokenReq();
        req.code = "qn01";
        req.sort = sort;
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.QiNiuYunToken(req, new BaseCallback<QiNiuYunTokenResp>(QiNiuYunTokenResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, QiNiuYunTokenResp t) {
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
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {

            }
        });
    }

    private void uploadImg(final String token , final String key , final String sort){

        new Thread(new Runnable(){
            @Override
            public void run() {
                String file = "";
                if ("1".equals(sort)){
                    file = path1;
                } else if ("2".equals(sort)){
                    file = path2;
                } else if ("3".equals(sort)){
                    file = path3;
                }

                UploadManager uploadManager = new UploadManager();
                uploadManager.put(file, key, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String arg0, ResponseInfo info, JSONObject response) {
                                // TODO Auto-generated method stub
                                if (info.isOK()){
                                    IsThree();
                                }
                            }
                        }, null);
            }
        }).start();
    }

    private int num = 0;
    public void IsThree(){
        num++;
        if (num == count){
            Upload();
        }
    }

    public void Upload(){
        UploadImageReq req = new UploadImageReq();
        req.code = "60018";
        req.id_advert = "1";
        req.id_driver = Utils.getIdDriver();
        req.image0_advert = key1;
        req.image1_advert = key2;
        req.image2_advert = key3;
        KaKuApiProvider.uploadImage(req, new BaseCallback<UploadImageResp>(UploadImageResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, UploadImageResp t) {
                if (t != null) {
                    LogUtil.E("uploadimage res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        startActivity(new Intent(context, ImageHistoryActivity.class));
                        finish();
                    }  else {
                        if (Constants.RES_TEN.equals(t.res)){
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });
    }

}
