package com.yichang.kaku.home.baoyang;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.yichang.kaku.home.ad.ClipImageActivity;
import com.yichang.kaku.request.GetFilterImageReq;
import com.yichang.kaku.request.QiNiuYunTokenReq;
import com.yichang.kaku.request.UploadFilterReq;
import com.yichang.kaku.response.GetFilterImageResp;
import com.yichang.kaku.response.QiNiuYunTokenResp;
import com.yichang.kaku.response.UploadFilterResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.popwindow.FilterImageWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ChooseFilterActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private ImageView iv_choosefilter_anlizhanshi;
    private EditText et_choosefilter_pinpai, et_choosefilter_fadongji, et_choosefilter_lvxin1, et_choosefilter_lvxin2;
    private ImageView iv_filterimage_1, iv_filterimage_2, iv_filterimage_3, iv_filterimage_4;
    private PopupWindow window;
    private TextView tv_takephoto, tv_myphoto, tv_exitphoto;
    private final int START_ALBUM_REQUESTCODE = 4;//相册
    private final int CAMERA_WITH_DATA = 2;//拍照
    private final int CROP_RESULT_CODE = 3;//结果
    public static final String FILTER_PATH1 = "filter_path1.jpg";
    public static final String FILTER_PATH2 = "filter_path2.jpg";
    public static final String FILTER_PATH3 = "filter_path3.jpg";
    public static final String FILTER_PATH4 = "filter_path4.jpg";
    private Bitmap photo1, photo2, photo3, photo4;
    public String token1, token2, token3, token4;
    public String key1 = "", key2 = "", key3 = "", key4 = "";
    public String path1, path2, path3, path4;
    private Button btn_choosefilter_commit;
    private int anInt;
    private int size = 0;
    private String type;
    byte[] byteArray1, byteArray2, byteArray3, byteArray4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosefilter);
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
        title.setText("选择滤芯");
        iv_choosefilter_anlizhanshi = (ImageView) findViewById(R.id.iv_choosefilter_anlizhanshi);
        iv_choosefilter_anlizhanshi.setOnClickListener(this);
        et_choosefilter_pinpai = (EditText) findViewById(R.id.et_choosefilter_pinpai);
        et_choosefilter_fadongji = (EditText) findViewById(R.id.et_choosefilter_fadongji);
        et_choosefilter_lvxin1 = (EditText) findViewById(R.id.et_choosefilter_lvxin1);
        et_choosefilter_lvxin2 = (EditText) findViewById(R.id.et_choosefilter_lvxin2);
        iv_filterimage_1 = (ImageView) findViewById(R.id.iv_filterimage_1);
        iv_filterimage_1.setOnClickListener(this);
        iv_filterimage_2 = (ImageView) findViewById(R.id.iv_filterimage_2);
        iv_filterimage_2.setOnClickListener(this);
        iv_filterimage_3 = (ImageView) findViewById(R.id.iv_filterimage_3);
        iv_filterimage_3.setOnClickListener(this);
        iv_filterimage_4 = (ImageView) findViewById(R.id.iv_filterimage_4);
        iv_filterimage_4.setOnClickListener(this);
        btn_choosefilter_commit = (Button) findViewById(R.id.btn_choosefilter_commit);
        btn_choosefilter_commit.setOnClickListener(this);

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        anInt = (outMetrics.widthPixels - 50) / 4;
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(anInt, anInt);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(anInt, anInt);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(anInt, anInt);
        LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(anInt, anInt);
        params1.setMargins(10, 10, 5, 0);
        params2.setMargins(5, 10, 5, 0);
        params3.setMargins(5, 10, 5, 0);
        params4.setMargins(5, 10, 10, 0);
        iv_filterimage_1.setLayoutParams(params1);
        iv_filterimage_2.setLayoutParams(params2);
        iv_filterimage_3.setLayoutParams(params3);
        iv_filterimage_4.setLayoutParams(params3);
        window = new PopupWindow();
        if (!"".equals(KaKuApplication.id_upkeep_picture)) {
            GetFilterImage();
            type = "old";
        } else {
            type = "new";
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
        } else if (R.id.iv_choosefilter_anlizhanshi == id) {
            showFilterImageWindow();
        } else if (R.id.iv_filterimage_1 == id) {
            KaKuApplication.flag_image = "filter1";
            ShowWindow(v);
        } else if (R.id.iv_filterimage_2 == id) {
            KaKuApplication.flag_image = "filter2";
            ShowWindow(v);
        } else if (R.id.iv_filterimage_3 == id) {
            KaKuApplication.flag_image = "filter3";
            ShowWindow(v);
        } else if (R.id.iv_filterimage_4 == id) {
            KaKuApplication.flag_image = "filter4";
            ShowWindow(v);
        } else if (R.id.tv_takephoto == id) {
            startCapture();
            window.dismiss();
        } else if (R.id.tv_myphoto == id) {
            startAlbum();
            window.dismiss();
        } else if (R.id.tv_exitphoto == id) {
            window.dismiss();
        } else if (R.id.btn_choosefilter_commit == id) {
            if (KaKuApplication.ImageFilter1 == null && KaKuApplication.ImageFilter2 == null &&
                    KaKuApplication.ImageFilter3 == null && KaKuApplication.ImageFilter4 == null &&
                    "".equals(et_choosefilter_fadongji.getText().toString().trim()) &&
                    "".equals(et_choosefilter_lvxin1.getText().toString().trim()) &&
                    "".equals(et_choosefilter_lvxin2.getText().toString().trim()) &&
                    "".equals(et_choosefilter_pinpai.getText().toString().trim())) {
                return;
            }
            Commit();
        }
    }

    public void ShowWindow(View v) {
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
        if (KaKuApplication.ImageFilter1 != null) {
            size++;
            QiNiuYunToken("1");
        }
        if (KaKuApplication.ImageFilter2 != null) {
            size++;
            QiNiuYunToken("2");
        }
        if (KaKuApplication.ImageFilter3 != null) {
            size++;
            QiNiuYunToken("3");
        }
        if (KaKuApplication.ImageFilter4 != null) {
            size++;
            QiNiuYunToken("4");
        }
        if (KaKuApplication.ImageFilter1 == null && KaKuApplication.ImageFilter2 == null &&
                KaKuApplication.ImageFilter3 == null && KaKuApplication.ImageFilter4 == null) {
            Upload();
        }
    }

    private void startCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if ("filter1".equals(KaKuApplication.flag_image)) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    Environment.getExternalStorageDirectory(), FILTER_PATH1)));
        } else if ("filter2".equals(KaKuApplication.flag_image)) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    Environment.getExternalStorageDirectory(), FILTER_PATH2)));
        } else if ("filter3".equals(KaKuApplication.flag_image)) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    Environment.getExternalStorageDirectory(), FILTER_PATH3)));
        } else if ("filter4".equals(KaKuApplication.flag_image)) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    Environment.getExternalStorageDirectory(), FILTER_PATH4)));
        }
        startActivityForResult(intent, CAMERA_WITH_DATA);
    }

    private void startAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, START_ALBUM_REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CROP_RESULT_CODE:
                if ("filter1".equals(KaKuApplication.flag_image)) {
                    path1 = data.getStringExtra(ClipImageActivity.RESULT_PATH);
                    photo1 = BitmapFactory.decodeFile(path1);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    photo1.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byteArray1 = baos.toByteArray();
                    KaKuApplication.ImageFilter1 = photo1;
                    iv_filterimage_1.setImageBitmap(photo1);
                } else if ("filter2".equals(KaKuApplication.flag_image)) {
                    path2 = data.getStringExtra(ClipImageActivity.RESULT_PATH);
                    photo2 = BitmapFactory.decodeFile(path2);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    photo2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byteArray2 = baos.toByteArray();
                    KaKuApplication.ImageFilter2 = photo2;
                    iv_filterimage_2.setImageBitmap(photo2);
                } else if ("filter3".equals(KaKuApplication.flag_image)) {
                    path3 = data.getStringExtra(ClipImageActivity.RESULT_PATH);
                    photo3 = BitmapFactory.decodeFile(path3);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    photo3.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byteArray3 = baos.toByteArray();
                    KaKuApplication.ImageFilter3 = photo3;
                    iv_filterimage_3.setImageBitmap(photo3);
                } else if ("filter4".equals(KaKuApplication.flag_image)) {
                    path4 = data.getStringExtra(ClipImageActivity.RESULT_PATH);
                    photo4 = BitmapFactory.decodeFile(path4);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    photo4.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byteArray4 = baos.toByteArray();
                    KaKuApplication.ImageFilter4 = photo4;
                    iv_filterimage_4.setImageBitmap(photo4);
                }
                break;

            case START_ALBUM_REQUESTCODE:
                startCropImageActivity(getFilePath(data.getData()));
                break;

            case CAMERA_WITH_DATA:
                if ("filter1".equals(KaKuApplication.flag_image)) {
                    startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + FILTER_PATH1);
                } else if ("filter2".equals(KaKuApplication.flag_image)) {
                    startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + FILTER_PATH2);
                } else if ("filter3".equals(KaKuApplication.flag_image)) {
                    startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + FILTER_PATH3);
                } else if ("filter4".equals(KaKuApplication.flag_image)) {
                    startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + FILTER_PATH4);
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // 裁剪图片的Activity
    private void startCropImageActivity(String path) {
        ClipImageActivity.startActivity(this, path, CROP_RESULT_CODE);
    }

    /**
     * 通过uri获取文件路径
     *
     * @param mUri
     * @return
     */
    public String getFilePath(Uri mUri) {
        try {
            if (mUri.getScheme().equals("file")) {
                return mUri.getPath();
            } else {
                return getFilePathByUri(mUri);
            }
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    // 获取文件路径通过url
    private String getFilePathByUri(Uri mUri) throws FileNotFoundException {
        Cursor cursor = getContentResolver()
                .query(mUri, null, null, null, null);
        cursor.moveToFirst();
        return cursor.getString(1);
    }

    public void QiNiuYunToken(final String sort) {
        showProgressDialog();
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

    private void uploadImg(final String token, final String key, final String sort) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] file = null;
                if ("1".equals(sort)) {
                    file = byteArray1;
                } else if ("2".equals(sort)) {
                    file = byteArray2;
                } else if ("3".equals(sort)) {
                    file = byteArray3;
                } else if ("4".equals(sort)) {
                    file = byteArray4;
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
        if (num == size) {
            Upload();
        }
    }

    public void Upload() {
        UploadFilterReq req = new UploadFilterReq();
        req.code = "40055";
        req.id_upkeep_bill = KaKuApplication.id_upkeep_bill;
        req.remark_engine = et_choosefilter_fadongji.getText().toString().trim();
        req.remark_series = et_choosefilter_pinpai.getText().toString().trim();
        req.remark_filter1 = et_choosefilter_lvxin1.getText().toString().trim();
        req.remark_filter2 = et_choosefilter_lvxin2.getText().toString().trim();
        req.image_series = key1;
        req.image_engine = key2;
        req.image_filter1 = key3;
        req.image_filter2 = key4;
        KaKuApiProvider.UploadFilter(req, new KakuResponseListener<UploadFilterResp>(this, UploadFilterResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("uploadfilter res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.id_upkeep_picture = t.id_upkeep_picture;
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

    public void GetFilterImage() {
        showProgressDialog();
        GetFilterImageReq req = new GetFilterImageReq();
        req.id_upkeep_picture = KaKuApplication.id_upkeep_picture;
        KaKuApiProvider.GetFilter(req, new KakuResponseListener<GetFilterImageResp>(this, GetFilterImageResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getfilter res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SetText(t);
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

    public void SetText(final GetFilterImageResp t) {
        et_choosefilter_pinpai.setText(t.upkeep_picture.getRemark_series());
        et_choosefilter_fadongji.setText(t.upkeep_picture.getRemark_engine());
        et_choosefilter_lvxin1.setText(t.upkeep_picture.getRemark_filter1());
        et_choosefilter_lvxin2.setText(t.upkeep_picture.getRemark_filter2());

        if (!"".equals(t.upkeep_picture.getImage_series())) {
            BitmapUtil.getInstance(context).download(iv_filterimage_1, t.upkeep_picture.getImage_series());
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Bitmap mBitmap = getBitMBitmap(t.upkeep_picture.getImage_series());
                    KaKuApplication.ImageFilter1 = mBitmap;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byteArray1 = baos.toByteArray();
                }
            }.start();


        }
        if (!"".equals(t.upkeep_picture.getImage_engine())) {
            BitmapUtil.getInstance(context).download(iv_filterimage_2, t.upkeep_picture.getImage_engine());
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Bitmap mBitmap = getBitMBitmap(t.upkeep_picture.getImage_engine());
                    KaKuApplication.ImageFilter2 = mBitmap;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byteArray2 = baos.toByteArray();
                }
            }.start();

        }
        if (!"".equals(t.upkeep_picture.getImage_filter1())) {
            BitmapUtil.getInstance(context).download(iv_filterimage_3, t.upkeep_picture.getImage_filter1());
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Bitmap mBitmap = getBitMBitmap(t.upkeep_picture.getImage_filter1());
                    KaKuApplication.ImageFilter3 = mBitmap;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byteArray3 = baos.toByteArray();
                }
            }.start();

        }
        if (!"".equals(t.upkeep_picture.getImage_filter2())) {
            BitmapUtil.getInstance(context).download(iv_filterimage_4, t.upkeep_picture.getImage_filter2());
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Bitmap mBitmap = getBitMBitmap(t.upkeep_picture.getImage_filter2());
                    KaKuApplication.ImageFilter4 = mBitmap;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byteArray4 = baos.toByteArray();
                }
            }.start();

        }
    }

    public static Bitmap getBitMBitmap(String urlpath) {
        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
            // TODO Auto-generated catch block
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KaKuApplication.ImageFilter1 = null;
        KaKuApplication.ImageFilter2 = null;
        KaKuApplication.ImageFilter3 = null;
        KaKuApplication.ImageFilter4 = null;
        byteArray1 = null;
        byteArray2 = null;
        byteArray3 = null;
        byteArray4 = null;
    }

    private void showFilterImageWindow() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                FilterImageWindow popWindow = new FilterImageWindow(ChooseFilterActivity.this);
                popWindow.show();
            }
        }, 0);
    }
}
